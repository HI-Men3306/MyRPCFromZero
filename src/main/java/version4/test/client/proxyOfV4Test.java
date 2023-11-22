package version4.test.client;

import lombok.AllArgsConstructor;
import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class proxyOfV4Test implements InvocationHandler {
    clientInterface client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装request请求
        RequestOfV4Test request = RequestOfV4Test.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();
        //rpc调用服务器
        ResponseOfV4Test response = client.sendRequest(request);
        //返回response中的数据
        return response.getData();
    }
    public Object getProxy(Class<?> target){//获取代理对象
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
