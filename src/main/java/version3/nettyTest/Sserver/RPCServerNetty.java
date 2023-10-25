package version3.nettyTest.Sserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RPCServerNetty implements RPCServer{
    private InterfaceProvider provider;
    private ServerBootstrap bootstrap;

    public RPCServerNetty(InterfaceProvider provider) {
        this.provider = provider;
    }
    @Override
    public void Start(int port) {
        try {
            System.out.println("基于netty的服务器启动了");
            bootstrap = new ServerBootstrap();
            NioEventLoopGroup boss = new NioEventLoopGroup();
            NioEventLoopGroup work = new NioEventLoopGroup();
            bootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer(provider));

            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("服务器连接异常");
        }
    }

    @Override
    public void Stop() {

    }
}
