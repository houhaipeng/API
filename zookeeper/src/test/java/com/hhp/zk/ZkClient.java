package com.hhp.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ZkClient {

    private String connectString = "192.168.0.108:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String node = zooKeeper.create("/hhp", "houhaipeng".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(node);
    }

    
}
