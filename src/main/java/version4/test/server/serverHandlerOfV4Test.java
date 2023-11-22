package version4.test.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class serverHandlerOfV4Test extends SimpleChannelInboundHandler<RequestOfV4Test> {
    private instanceProvider provider;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestOfV4Test request) throws Exception {

        System.out.println("read0读取到的request请求为" + request);

        //调用服务器本地方法 获取response
        ResponseOfV4Test response = getResponse(request);

        System.out.println("本地响应的数据为" + response);
        //响应response给客户端
        ctx.channel().writeAndFlush(response).sync();
        ctx.close();
    }

    ResponseOfV4Test getResponse(RequestOfV4Test request){
        //获取对应的实例对象
        Object instance = provider.getInstance(request.getInterfaceName());
        try {
            //获取需要执行的本地方法
            Method method = instance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //反射调用本地方法
            Object result = method.invoke(instance, request.getParams());
            return ResponseOfV4Test.success(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return ResponseOfV4Test.fail();
    }
}
