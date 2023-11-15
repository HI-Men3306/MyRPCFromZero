package version4.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import version4.codec.myDecode;
import version4.codec.myEncode;

public class clientChannelInitializerV4 extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //给管道添加自定义处理器
        channel.pipeline()
                .addLast(new myEncode())
                .addLast(new myDecode())
                .addLast(new nettyClientHandler());
    }
}
