package version5.test.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.AllArgsConstructor;
import version5.test.codec.jsonSerializer;
import version5.test.codec.myDecode;
import version5.test.codec.myEnCode;

@AllArgsConstructor
public class nettySerIni extends ChannelInitializer {
    private ServerInterfaceProvider provider;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new myDecode())
                .addLast(new myEnCode(new jsonSerializer()))
                .addLast(new nettySerHandler(provider));
    }
}
