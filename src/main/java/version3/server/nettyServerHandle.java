package version3.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@AllArgsConstructor
public class nettyServerHandle extends SimpleChannelInboundHandler<RPCRequest> {
    private serviceInstanceProvider provider;
    //当服务器接收到客户端发送的请求之后  在这里反射执行本地的方法 并将结果response回去
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest request) throws Exception {
        System.out.println("服务器接收到的请求为" + request.toString());
        //调用本地方法 并获取响应结果
        RPCResponse response = getResponse(request);

        //打印客户端信息
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        InetAddress address = socketAddress.getAddress();
        String hostName = address.getHostName();
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        System.out.println("客户端地址" + clientIp + " 姓名" + hostName);

        //将响应结果 响应回客户端
        ctx.writeAndFlush(response);
        //关闭连接  释放资源
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //反射调用本地方法
    private RPCResponse getResponse(RPCRequest request){
        //获取request中所要调用的方法所属的服务实体对象
        Object serviceInstance = provider.getServiceInstance(request.getInterfaceName());
        try {
            //获取要调用的方法
            Method method = serviceInstance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //执行方法 获取结果
            Object result = method.invoke(serviceInstance, request.getParams());
            return RPCResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("服务器：调用本地方法失败");
            return RPCResponse.fail();
        }
    }
}
