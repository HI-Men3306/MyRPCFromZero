package version5.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import version5.codec.jsonSerializer;
import version5.codec.myDecode;
import version5.codec.myEncode;

public class nettyClientIni extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new myDecode())
                .addLast(new myEncode(new jsonSerializer()))
                .addLast(new nettyClientHandler());
    }
}
