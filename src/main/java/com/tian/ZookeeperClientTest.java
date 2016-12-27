package com.tian;

import org.apache.zookeeper.*;

import static org.jboss.netty.handler.codec.rtsp.RtspHeaders.Values.CLIENT_PORT;

/**
 * Created by Administrator on 2016/12/27.
 */
public class ZookeeperClientTest {

    public static void main(String[] args) throws Exception{
        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper("127.0.0.1",
                2000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println("1." + new String(zk.getData("/testRootPath",false,null)));
        // 取出子目录节点列表
        System.out.println("2." + zk.getChildren("/testRootPath",true));
        // 修改子目录节点数据
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
        System.out.println("3. 目录节点状态：["+zk.exists("/testRootPath",true)+"]");
        // 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println("4." + new String(zk.getData("/testRootPath/testChildPathTwo",true,null)));
        // 删除子目录节点
//        zk.delete("/testRootPath/testChildPathTwo",-1);
//        zk.delete("/testRootPath/testChildPathOne",-1);
        // 删除父目录节点
//        zk.delete("/testRootPath",-1);
        // 关闭连接
        zk.close();
    }
}
