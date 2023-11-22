package version4.test.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version4.test.common.ResponseOfV4Test;

public class clientHandlerOfV4Test extends SimpleChannelInboundHandler<ResponseOfV4Test> {

    @Override
    //经由管道中的编码器 传输的jsonbyte数据已经被转换为了response数据
    //在这里被接收处理   这里的优先级最高
    //这里需要做的是将response存放进channel中 供后续使用
    protected void channelRead0(ChannelHandlerContext ctx, ResponseOfV4Test response) throws Exception {

        System.out.println("read0中的response为" + response);

        AttributeKey<Object> key = AttributeKey.valueOf("response");
        ctx.channel().attr(key).set(response);
        ctx.channel().close();
    }
}
