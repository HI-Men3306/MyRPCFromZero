package version5.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version5.common.response;

public class nettyClientHandler extends SimpleChannelInboundHandler<response> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, response response) throws Exception {

        //将服务器响应回来的数据存入channel中  供后续使用
        AttributeKey<response> key = AttributeKey.valueOf("response");
        ctx.channel().attr(key).set(response);
    }
}
