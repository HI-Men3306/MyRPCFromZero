package version5.test.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class nettyServer implements server{
    private ServerInterfaceProvider provider;
    private final ServerBootstrap bootstrap;
    private final NioEventLoopGroup boss;
    private final NioEventLoopGroup work;

    public nettyServer(ServerInterfaceProvider provider) {
        this.provider = provider;
        this.boss = new NioEventLoopGroup();
        this.work = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(boss,work)
                .childHandler(new nettySerIni(provider));
    }

    @Override
    public void start(int port) {
        try {
            //服务器阻塞连接
            ChannelFuture channel = bootstrap.bind(port).sync();
            //等待异步任务完成之后  关闭缓冲区
            channel.channel().closeFuture().sync();
            channel.channel().close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
