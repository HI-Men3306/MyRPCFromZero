package version5.register;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZKServiceRegister implements serviceRegister{
    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";

    public ZKServiceRegister() {
        //设置重试策略 三次重试机会 每次间隔都会增加1000毫秒
        RetryPolicy policy = new ExponentialBackoffRetry(1000,3);
        client = CuratorFrameworkFactory.builder()
                .retryPolicy(policy)//重试机制
                .connectString("127.0.0.1:2181")//指定要连接的zookeeper的地址和端口号  2181为默认ZK端口号
                .namespace(ROOT_PATH)//指定根节点目录 隔离不同程序 避免冲突
                .sessionTimeoutMs(40000)//设置会话超时时间
                .build();
        //curator客户端建立与ZK服务器的连接
        client.start();
        System.out.println("Curator连接到Zookeeper服务器中....");
    }

    @Override
    public void register(String serviceName, InetSocketAddress address) {
        try {
            // 如果当前节点还未创建 将serviceName节点创建成为永久节点  即使服务器宕机也不会消失
            if(client.checkExists().forPath("/" + serviceName) == null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            //创建临时节点   格式为： /服务名/主机名:端口号
            String path = "/" + serviceName + "/" +getInetSocketAddress(address);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已经存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            //获取所有以  /服务名 开头的服务节点
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            // 这里默认用的第一个，后面的版本再加上负载均衡
            // 将第一个节点的信息解析成地址
            return parseAddress(strings.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将地址解析为字符串   格式为:  主机名:端口号
    private String getInetSocketAddress(InetSocketAddress address){
        return address.getHostName() + ":" + address.getPort();
    }

    //将字符串解析为地址    格式为： 主机名:端口号
    private InetSocketAddress parseAddress(String address){
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }
}
