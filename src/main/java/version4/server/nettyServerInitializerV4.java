package version4.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import version4.codec.jsonSerializer;
import version4.codec.myDecode;
import version4.codec.myEncode;


@AllArgsConstructor
public class nettyServerInitializerV4 extends ChannelInitializer<SocketChannel> {
    private interfaceProvider provider;
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                //使用json进行序列化
                .addLast(new myEncode(new jsonSerializer()))
                .addLast(new myDecode())
                //自定义处理器
                .addLast(new nettyServerHandler(provider));
    }
}
