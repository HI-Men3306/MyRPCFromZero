package version3.nettyTest.Sserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerInitializer extends ChannelInitializer {
    private InterfaceProvider provider;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                .addLast(new LengthFieldPrepender(4))
                .addLast(new ObjectDecoder(new ClassResolver() {
                    @Override
                    public Class<?> resolve(String s) throws ClassNotFoundException {
                        return Class.forName(s);
                    }
                }))
                .addLast(new ObjectEncoder())
                .addLast(new nettyServerHandle(provider));
    }
}
