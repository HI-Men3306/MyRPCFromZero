package version5.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import version5.common.request;
import version5.common.response;
import version5.test.register.ZKServerRegister;
import version5.test.register.serviceRegister;

import java.net.InetSocketAddress;

public class nettyClient implements client{
    private Bootstrap bootstrap;
    private serviceRegister register;

    public nettyClient() {
        this.bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new nettyCliIni());
        register = new ZKServerRegister();
    }

    @Override
    public response sendRequest(request req) {
        //通过ZK注册中心获取需要调用的服务所属的地址和端口
        InetSocketAddress inetSocketAddress = register.serviceDiscovery(req.getInterfaceName());
        String host = inetSocketAddress.getHostName();
        int port = inetSocketAddress.getPort();
        try {
            //创建连接并阻塞  等待连接成功
            ChannelFuture connect = bootstrap.connect(host, port).sync();

            //发送request
            connect.channel().writeAndFlush(req);
            //阻塞当前进行直到异步任务完成
            connect.channel().closeFuture().sync();
            //必须同步的是异步任务  也就是上面的那个才是阻塞当前进程直到异步任务结束
            //connect.sync();
            //从channel中获取response
            AttributeKey key = AttributeKey.valueOf("response");
            response res = (response) connect.channel().attr(key).get();
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return response.fail();
        }
    }
}
