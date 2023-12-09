package version5.test.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import version5.test.loadBanlance.LoadBalance;
import version5.test.loadBanlance.roundBalance;

import java.net.InetSocketAddress;
import java.util.List;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;
import static org.apache.zookeeper.CreateMode.PERSISTENT;

public class ZKServerRegister implements serviceRegister {
    private CuratorFramework client;
    private final static String ROOT_PATH = "myRPC";
    private LoadBalance loadBalance;

    public ZKServerRegister() {
        this.client = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(20000)//会话超时时间
                .namespace(ROOT_PATH)//根目录 用作隔离不同的服务
                .retryPolicy(new ExponentialBackoffRetry(3,1000))//ZK重试机制
                .connectString("127.0.0.1:2181")//客户端连接服务器的地址和端口
                .build();
        client.start();
        //实现负载均衡
        loadBalance = new roundBalance();
        System.out.println("Zk连接成功");
    }

    @Override
    //将服务和对应的地址端口号注册到ZK上
    public void register(String serviceName, InetSocketAddress address) {
        try {
            //检查是否存在该serviceName节点
            if(client.checkExists().forPath("/" + serviceName) == null){
                //不存在  将serviceName设置为永久节点
                client.create().creatingParentsIfNeeded().withMode(PERSISTENT).forPath("/" + serviceName);
            }
            //将当前地址挂在当前serviceName后面  这样该地址节点就属于该serviceName的一部分了
            String path = "/" + serviceName + "/" + getAddress(address);
            //将地址注册成为临时节点 即服务下线时删除
            client.create().creatingParentsIfNeeded().withMode(EPHEMERAL).forPath(path);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("此服务已经存在");
        }

    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            //获取所有以serviceName为前缀的节点名称
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            System.out.println("当前服务器集群的大小为" + strings.size());
            //负载均衡的选择一个服务器提供服务
            String balanceServer = loadBalance.balance(strings);
            System.out.println("选择的服务器为" + balanceServer);
            return parseInetSocketAddress(balanceServer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    String getAddress(InetSocketAddress address){
        String hostName = address.getHostName();
        int port = address.getPort();
        return hostName + ":" + port;
    }
    InetSocketAddress parseInetSocketAddress(String address){
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }
}
