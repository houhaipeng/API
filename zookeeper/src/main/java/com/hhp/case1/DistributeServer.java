package com.hhp.case1;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    private String connectString = "192.168.0.108:3001,192.168.0.108:4001,192.168.0.108:5001";
    private int sessionTimeout = 2000;
    private ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //1. 获取zk连接
        DistributeServer server = new DistributeServer();
        server.getConnect();

        //2. 注册服务器到zk集群（服务器和客户端对zk来说都是客户端）
        server.register(args[0]);

        //3. 启动业务逻辑(用sleep代替)
        server.business();

    }

    private void business() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void register(String hostname) throws KeeperException, InterruptedException {
        //创建临时顺序节点
        String node = zooKeeper.create("/servers/" + hostname, hostname.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online");
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
