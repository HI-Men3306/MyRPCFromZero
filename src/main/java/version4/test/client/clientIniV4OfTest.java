package version4.test.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import version4.test.codec.jsonSerializer;
import version4.test.codec.myDecodeOfV4Test;
import version4.test.codec.myEncodeOfV4Test;

public class clientIniV4OfTest extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new myEncodeOfV4Test(new jsonSerializer()))//自定义编码处理器
                .addLast(new myDecodeOfV4Test())//自定义解码处理器
                .addLast(new clientHandlerOfV4Test());//优先处理管道中传输过来的数据
    }
}
