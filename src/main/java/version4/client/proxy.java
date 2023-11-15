package version4.client;

import lombok.AllArgsConstructor;
import version4.common.RPCRequest;
import version4.common.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class proxy implements InvocationHandler {
    private clientInterface client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //创建request请求对象
        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes()).build();

        //调用不同客户端发送request请求
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
