package version3.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

//以netty为底层通信 发送请求
@AllArgsConstructor
public class nettyRPCClient implements RPCClient{
    private String host;
    private int port;
    private final static EventLoopGroup group;
    private final static Bootstrap bootstrap;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //对bootstrap进行配置
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                //注册自定义的处理器
                .handler(new clientChannelInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            //在连接的时候直接阻塞   等待连接建立完成  不然连接异步连接还没建立  主线程就执行完了
            ChannelFuture connect = bootstrap.connect(host, port).sync();
            Channel channel = connect.channel();

            System.out.println(this.getClass().getName() + " 线程为： " +Thread.currentThread().getName() + " : 客户端要发送的请求为:" + request);


            //发送request请求
            channel.writeAndFlush(request);

            System.out.println(this.getClass().getName() + "发送request成功");

            //阻塞当前线程 直到异步任务完成 为什么要阻塞当前线程 直到异步任务完成？
            //因为在异步任务中 接收了服务器响应回的数据 并在异步任务中将其存放在了channel中
            //为什么又要阻塞当前线程？   因为要让异步任务来得及处理数据 在这里为(将服务器响应结果存入channel中)
            channel.closeFuture().sync();
            //从channel中获取服务器响应的结果
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse rpcResponse = channel.attr(key).get();

            System.out.println("客户端从channel中获取的数据为" + rpcResponse);

            return rpcResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
