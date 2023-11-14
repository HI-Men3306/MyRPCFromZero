package version3.nettyTest.Sserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version3.nettyTest.common.request;
import version3.nettyTest.common.response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class nettyServerHandle extends SimpleChannelInboundHandler<request> {
    private InterfaceProvider provider;
    @Override
    //当请求发送到服务器时 这里会首先收到请求消息
    protected void channelRead0(ChannelHandlerContext ctx, request request) throws Exception {
        //调用本地方法 封装结果进response中
        response response = this.getResponse(request);
        //响应回去
        ctx.channel().writeAndFlush(response);
        //关闭连接
        ctx.channel().close();
    }
    public response getResponse(request request){
        try {
            //反射调用本地方法
            //获取对应服务实例
            Object instance = provider.getInstance(request.getInterfaceName());
            System.out.println("获取的服务实例对象接口为" + instance);
            //获取要调用的方法
            Method method = instance.getClass().getMethod(request.getMethodName(),request.getParamsType());
            //反射执行方法
            Object result = method.invoke(instance, request.getParams());

            return response.Success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("服务器调用方法失败");
            return response.Fail();
        }

    }
}
