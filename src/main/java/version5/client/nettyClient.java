package version5.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import version5.common.request;
import version5.common.response;
import version5.register.ZKServiceRegister;
import version5.register.serviceRegister;

import java.net.InetSocketAddress;

public class nettyClient implements client{
    private static Bootstrap bootstrap;
    private serviceRegister register;
    //方便复用 节约资源
    static {
        //初始化netty客户端
        bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new nettyClientIni());
    }

    public nettyClient() {
        //创建zookeeper作为注册中心
        this.register = new ZKServiceRegister();
    }

    @Override
    public response sendRequest(request request) {
        //在注册中心中发现服务所属的地址和端口
        InetSocketAddress address = register.serviceDiscovery(request.getInterfaceName());
        String host = address.getHostName();
        int port = address.getPort();
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //发送请求
            future.channel().writeAndFlush(request);
            //阻塞直到异步任务完成
            future.channel().closeFuture().sync();
            //当异步任务完成之后 response已经在异步任务中存入了channel中 直接获取即可
            AttributeKey<response> key = AttributeKey.valueOf("response");
            response res = future.channel().attr(key).get();
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return response.fail();
        }
    }
}
