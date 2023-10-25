package version3.nettyTest.Cclient;

import lombok.AllArgsConstructor;
import version3.nettyTest.common.request;
import version3.nettyTest.common.response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCProxy implements InvocationHandler {
    //不同的客户端有各自不同的通信方式   所以动态的使用不同的客户端
    private RPCClient client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(method.getName() + "方法被执行了");
        //根据要反射调用的方法等信息 封装request
        request Req = request.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();

        //客户端发送调用请求
        System.out.println("客户端要发送的请求为" + Req);

        response response = client.sendRequest(Req);
        return response.getData();
    }

    //获取动态代理对象
    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
