package version4.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version4.common.RPCResponse;

public class nettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

    //实现了SimpleChannelInboundHandler的处理器优先级最高
    //这里实现的是当客户端响应response之后，立即将response存放进channel中 供后续使用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponse rpcResponse) throws Exception {

        System.out.println("收到的响应为" + rpcResponse.toString());

        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(key).set(rpcResponse);
        ctx.channel().close();
    }
}
