package version4.test.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class nettyServer implements serverInterface {
    private instanceProvider provider;

    @Override
    public void start(int port) {
        //初始化netty服务器
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                .childHandler(new serverIniOfV4Test(provider));

        //监听指定端口
        ChannelFuture future = null;
        try {
            //阻塞式监听
            future = bootstrap.bind(port).sync();
            //确保后续资源得到完全释放
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
