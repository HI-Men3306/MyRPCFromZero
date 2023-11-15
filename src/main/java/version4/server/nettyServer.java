package version4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class nettyServer implements ServerInterface{
    private interfaceProvider provider;

    @Override
    public void start(int port) {
        System.out.println("基于netty的客户端启动了");
        //创建主 副  线程池group
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        //创建serverBootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        //配置serverBootstrap
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new nettyServerInitializerV4(provider));

        try {
            //服务器建立连接
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            //阻塞连接 在执行后续代码之前 确保此次管道连接使用的资源完全释放，避免资源的浪费
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    @Override
    public void stop() {

    }
}
