package version3.client;

import lombok.AllArgsConstructor;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
//根据不同的通讯方式 使用不同的代理
public class RPCProxy implements InvocationHandler {
    //不同的通讯方式
    private RPCClient client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据调用的方法等信息 生成request
        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();

        //根据不同的通信方式 调用不同的sendRequest方法发送request
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
