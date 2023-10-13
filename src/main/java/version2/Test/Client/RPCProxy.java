package version2.Test.Client;

import lombok.AllArgsConstructor;
import version2.Test.common.RPCRequest;
import version2.Test.common.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@AllArgsConstructor
public class RPCProxy implements InvocationHandler {
    private String host;
    private int port;

    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据需要动态代理的方法 生成统一的request

        System.out.println(method.getDeclaringClass().getName() + "  " + method.getName());

        RPCRequest request = RPCRequest.builder()
                //为什么是method.getDeclaringClass().getName()？ 这样才是获取实例类对象的名称
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();
        //发送request请求
        RPCResponse response = IOClient.sendRequest(request,host,port);
        return response.getData();
    }
}
