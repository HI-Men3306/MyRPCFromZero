package version5.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version5.common.request;
import version5.common.response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class nettyServerHandler extends SimpleChannelInboundHandler<request> {
    private serviceProvider provider;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, request request) throws Exception {
        //解析request并调用本地方法
        response res = getResponse(request);
        //将response响应回客户端
        ctx.writeAndFlush(res);
        ctx.channel().close();
    }

    private response getResponse(request req) {
        //获取本地服务实例对象
        Object instance = provider.getInstance(req.getInterfaceName());
        try {
            //获取需要调用的方法
            Method method = instance.getClass().getMethod(req.getMethodName(), req.getParamsType());
            //反射调用方法
            Object result = method.invoke(instance, req.getParams());
            //将结果封装进response中
            return response.success(result);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return response.fail();
        }
    }
}
