package version5.test.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version5.common.response;

public class nettyCliHandler extends SimpleChannelInboundHandler<response> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, response response) throws Exception {
        //将响应回来的response存入管道中 供后续使用
        AttributeKey key = AttributeKey.valueOf("response");
        ctx.channel().attr(key).set(response);
    }
}
