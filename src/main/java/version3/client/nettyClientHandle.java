package version3.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import version3.common.RPCResponse;

//客户端从服务器接收的数据格式为RPCResponse
public class nettyClientHandle extends SimpleChannelInboundHandler<RPCResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponse rpcResponse) throws Exception {

        System.out.print(" 线程为： " +Thread.currentThread().getName());
        System.out.println(this.getClass().getName() + " :客户端handle中获取的response为:" + rpcResponse.toString());

        //将服务器发送过来的response 存入channel中
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(key).set(rpcResponse);

        //当异步任务完成之后 关闭通道
        //ctx.channel().close(); 和 ctx.close();的区别是什么？
        //channel是关闭客户端与服务器的连接的channel  而直接close是关闭整个pipeline
        ctx.channel().close();
    }
}
