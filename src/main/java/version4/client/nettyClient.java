package version4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

//基于netty实现的客户端
public class nettyClient implements clientInterface{
    private String host;
    private int port;
    private final static EventLoopGroup group;
    private final static Bootstrap bootstrap;
    public nettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new clientChannelInitializerV4());
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //获取管道
            Channel channel = future.channel();
            //发送request请求
            channel.writeAndFlush(request);
            //阻塞线程等待服务器响应结果返回
            channel.closeFuture().sync();

            //从管道中获取响应结果
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
