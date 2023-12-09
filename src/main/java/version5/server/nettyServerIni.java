package version5.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import version5.codec.jsonSerializer;
import version5.codec.myDecode;
import version5.codec.myEncode;

@AllArgsConstructor
public class nettyServerIni extends ChannelInitializer<SocketChannel> {
    protected serviceProvider provider;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new myEncode(new jsonSerializer()))
                .addLast(new myDecode())
                .addLast(new nettyServerHandler(provider));
    }
}
