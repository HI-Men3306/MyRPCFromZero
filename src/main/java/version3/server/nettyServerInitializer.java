package version3.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class nettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private serviceInstanceProvider provider;
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                //解决粘包问题
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                .addLast(new LengthFieldPrepender(4))
                //将java对象转换为字节流在channel中传输
                .addLast(new ObjectEncoder())
                .addLast(new ObjectDecoder(new ClassResolver() {
                    @Override
                    public Class<?> resolve(String className) throws ClassNotFoundException {
                        return Class.forName(className);
                    }
                }));

        channel.pipeline().addLast(new nettyServerHandle(provider));
    }
}
