package version5.test.client;

import lombok.AllArgsConstructor;
import version5.common.request;
import version5.common.response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class proxy implements InvocationHandler {
    private client Client;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据反射信息 生成request
        request req = request.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();
        //发送request请求
        response res = Client.sendRequest(req);
        return res.getData();
    }

    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, this);
    }
}
