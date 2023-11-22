package version4.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

public class nettyClient implements clientInterface{
    private String host;
    private int port;
    private static Bootstrap bootstrap;

    public nettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        bootstrap = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new clientIniV4OfTest());
    }
    @Override
    public ResponseOfV4Test sendRequest(RequestOfV4Test request) throws InterruptedException {
        //连接服务器 并阻塞至连接成功
        ChannelFuture connect = bootstrap.connect(host, port).sync();
        Channel channel = connect.channel();
        //发送request请求给服务器
        channel.writeAndFlush(request);
        channel.closeFuture().sync();
        //从channel中获取response
        AttributeKey<ResponseOfV4Test> key = AttributeKey.valueOf("response");
        ResponseOfV4Test response = channel.attr(key).get();
        return response;
    }
}
