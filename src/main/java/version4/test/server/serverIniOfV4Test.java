package version4.test.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.AllArgsConstructor;
import version4.test.codec.jsonSerializer;
import version4.test.codec.myDecodeOfV4Test;
import version4.test.codec.myEncodeOfV4Test;

@AllArgsConstructor
public class serverIniOfV4Test extends ChannelInitializer {
    private instanceProvider provider;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new myDecodeOfV4Test())
                .addLast(new myEncodeOfV4Test(new jsonSerializer()))
                .addLast(new serverHandlerOfV4Test(provider));
    }
}
