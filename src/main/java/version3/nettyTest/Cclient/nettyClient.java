package version3.nettyTest.Cclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import version3.nettyTest.common.request;
import version3.nettyTest.common.response;

@AllArgsConstructor
public class nettyClient implements RPCClient{
    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    static {
        bootstrap = new Bootstrap();
        //配置客户端启动器
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new TestClientChannelInitializer());
    }

    @Override
    public response sendRequest(request Req) {
        try {
            //在建立连接时 直接阻塞当前线程 直到连接成功
            ChannelFuture connect = bootstrap.connect(host, port).sync();
            //发送请求
            connect.channel().writeAndFlush(Req);

            //阻塞当前线程直到异步任务完成
            connect.channel().closeFuture().sync();

            //从channel中获取服务器响应的数据并向上返回
            AttributeKey<response> key = AttributeKey.valueOf("response");
            response response = connect.channel().attr(key).get();
            System.out.println("从channel中取出的response为" + response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("客户端连接失败/发送请求失败！");
            return response.Fail();
        }
    }
}
