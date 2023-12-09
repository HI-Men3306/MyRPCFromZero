package version5.test.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version5.common.request;
import version5.common.response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class nettySerHandler extends SimpleChannelInboundHandler<request> {
    private ServerInterfaceProvider provider;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, request request) throws Exception {

        //反射调用本地方法并响应会客户端
        response response = getResponse(request);
        ctx.channel().writeAndFlush(response);
        ctx.channel().close();
    }
    response getResponse(request req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取本地服务实例对象
        Object instance = provider.getInstance(req.getInterfaceName());
        //获取需要调用的方法
        Method method = instance.getClass().getMethod(req.getMethodName(), req.getParamsType());
        //反射调用本地方法
        Object res = method.invoke(instance, req.getParams());
        return response.success(res);
    }
}
