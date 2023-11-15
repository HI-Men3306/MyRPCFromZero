package version4.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import version4.codec.jsonSerializer;
import version4.codec.myDecode;
import version4.codec.myEncode;

public class clientChannelInitializerV4 extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //给管道添加自定义处理器
        channel.pipeline()
                //使用json进行序列化
                .addLast(new myEncode(new jsonSerializer()))
                .addLast(new myDecode())
                .addLast(new nettyClientHandler());
    }
}
