package com.hhp.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributedLock {

    private final String connectString = "192.168.0.108:3001,192.168.0.108:4001,192.168.0.108:5001";
    private final int sessionTimeout = 2000;
    private final ZooKeeper zooKeeper;
    private CountDownLatch connectLatch = new CountDownLatch(1);
    private CountDownLatch waitLatch = new CountDownLatch(1);
    private String waitPath;
    private String currentNode;

    public DistributedLock() throws IOException, InterruptedException, KeeperException {

        //获取连接
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //connectLatch如果连接上zk,可以释放
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    connectLatch.countDown();//-1
                }
                //waitLatch 需要释放
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getType().equals(waitPath)) {
                    waitLatch.countDown();
                }
            }
        });
        //等待上面执行完成后，往下走
        connectLatch.await();
        //判断根节点/lock是否存在,不监听
        Stat exists = zooKeeper.exists("/locks", false);
        //如果不存在
        if (exists == null) {
            //创建/locks节点
            zooKeeper.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

    }


    //对zk加锁
    public void zkLock() throws InterruptedException, KeeperException {
        //创建对应的临时带序号节点（即加锁）
        currentNode = zooKeeper.create("/locks/" + "seq-", null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        //判断创建的节点是否是最小的序号节点，如果是获取锁，否则监听该节点前一个节点
        List<String> children = zooKeeper.getChildren("/locks", false);
        //如果children只有一个值，那就直接获取锁，如果有多个节点，需要判断谁最小
        if (children.size() == 1) {
            return;
        } else {
            //排序
            Collections.sort(children);
            //获取节点名称,seq-00000000
            String currentNodeNo = currentNode.substring("/locks/".length());
            //通过seq-00000000获取该节点在children集合的位置
            int index = children.indexOf(currentNodeNo);
            //判断
            if (index == -1) {
                System.out.println("数据异常");
            } else if (index == 0) {
                //只有一个节点，可以获取锁了
                return;
            } else {
                //需要监听它前一个节点
                waitPath = "/locks/" + children.get(index - 1);
                zooKeeper.getData(waitPath, true, null);
                //等待监听
                waitLatch.await();
                return;
            }
        }

    }

    //解锁
    public void unZkLock() {
        //删除节点
        try {
            zooKeeper.delete(currentNode, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
