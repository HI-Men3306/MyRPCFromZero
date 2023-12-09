package version5.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class nettyServer implements server{
    private NioEventLoopGroup boss;
    private NioEventLoopGroup work;
    private ServerBootstrap bootstrap;
    private serviceProvider provider;

    public nettyServer(serviceProvider provider) {
        this.provider = provider;
    }

    @Override
    public void start(int port) {
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap()
                .group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new nettyServerIni(provider));
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
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
