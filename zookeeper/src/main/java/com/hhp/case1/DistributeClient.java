package com.hhp.case1;


import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    //对于client来说，也是zk的客户端
    private String connectString = "192.168.0.108:3001,192.168.0.108:4001,192.168.0.108:5001";
    private ZooKeeper zooKeeper;
    private int sessionTimeout = 2000;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DistributeClient client = new DistributeClient();
        //1. 获取zk连接
        client.getConnect();

        //2. 监听/servers下面子节点的数据变化
        client.getServerList();

        //3. 业务逻辑(sleep)
        client.business();
    }

    private void business() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getServerList() throws KeeperException, InterruptedException {
        //获取/servers节点下的所有子节点并监听
        List<String> children = zooKeeper.getChildren("/servers", true);
        List<String> servers = new ArrayList<>();
        for (String child : children) {
            // /servers/server1->child=server1
            //节点值
            byte[] data = zooKeeper.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }
        System.out.println(servers);
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent watchedEvent) {
                //由于监听只注册一次，所以触发监听之后再监听一次
                getServerList();
            }
        });
    }
}
