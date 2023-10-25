package version3.nettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        //创建客户端bootstrap引导类
        Bootstrap bootstrap = new Bootstrap();
        System.out.println("客户端运行：" + Thread.currentThread().getName());
        //配置客户端
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //配置handle pipeline
                        socketChannel.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.print(Thread.currentThread().getName());
                                        System.out.println("客户端收到的回复为:" + msg);
                                    }
                                })
                                //.addLast(new LengthFieldPrepender(1))
                                .addLast(new StringEncoder());
                    }
                });

        //和服务器连接  并获取连接之后创建的缓冲区
        Channel channel = bootstrap.connect("localhost", 8080).channel();

        //向服务端发送请求连接
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            if(s.isEmpty())
                continue;
            //利用缓冲区 向服务器发送数据
            channel.writeAndFlush(s);
        }
    }
}
