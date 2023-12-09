package version5.test.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import version5.test.codec.jsonSerializer;
import version5.test.codec.myDecode;
import version5.test.codec.myEnCode;

public class nettyCliIni extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new myEnCode(new jsonSerializer()))
                .addLast(new myDecode())
                .addLast(new nettyCliHandler());
    }
}
