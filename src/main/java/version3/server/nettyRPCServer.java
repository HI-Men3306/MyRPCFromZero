package version3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class nettyRPCServer implements RPCServerInterface{
    private serviceInstanceProvider provider;
    @Override
    public void start(int port) {
        System.out.println("Netty服务端启动了...");

        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup();//主线程池  负责连接
            EventLoopGroup workGroup = new NioEventLoopGroup();//副线程池  负责处理请求
            //创建服务端引导类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置引导类
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new nettyServerInitializer(provider));
            // 同步阻塞
            System.out.println("启动了");
            //为什么要在服务器建立连接时阻塞主线程？
            //由于网络通信是异步的，服务器无法立即知道是否有客户端发起了连接请求。
            //因此，为了能够及时响应客户端的连接请求，我们需要阻塞当前线程，直到有客户端连接到服务器为止。
            ChannelFuture channelFuture = bootstrap.bind(port).sync();


            //下面的阻塞是干什么的？
            //确保在继续执行后续代码之前，通道的连接已经被成功关闭。这样可以确保资源得到正确的释放，避免潜在的资源泄漏问题
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
