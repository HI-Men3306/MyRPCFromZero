package version3.nettyTest.Cclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version3.nettyTest.common.response;

public class TestClientHandle extends SimpleChannelInboundHandler<response> {

    //当服务器通过netty传输数据过来之后  这里的channelRead0立即读取响应过来的数据
    //继承了SimpleChannelInboundHandler的handle处理器的优先级比其他所有的handle优先级都高
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, response res) throws Exception {
        System.out.println("客户端第一个收到响应的数据为" + res);
        //将服务器响应的数据存放进channel中
        AttributeKey<response> key = AttributeKey.valueOf("response");
        ctx.channel().attr(key).set(res);
        //当前异步任务处理完成 关闭channel连接
        //ctx.channel().close();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }


}
