package version4.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class nettyServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private interfaceProvider provider;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest request) throws Exception {
        System.out.println("反序列化之后的值到这里了");
        System.out.println(request.getParams()[0].getClass());
        System.out.println(request);
        //根据request调用本地的方法
        RPCResponse response = getResponse(request);
        //将结果返回响应回去
        ctx.writeAndFlush(response);
        //关闭当前连接管道
        ctx.close();
    }
    RPCResponse getResponse(RPCRequest request){
        //根据request中需要调取的方法(接口)名 获取本地方法的实例对象
        Object instance = provider.getInstance(request.getInterfaceName());
        try {
            //获取需要执行的本地方法
            Method method = instance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //反射调用本地方法
            Object result = method.invoke(instance, request.getParams());

            return RPCResponse.success(result);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return RPCResponse.fail();
        }
    }
}
