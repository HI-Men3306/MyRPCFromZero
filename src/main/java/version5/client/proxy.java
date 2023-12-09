package version5.client;

import lombok.AllArgsConstructor;
import version5.common.request;
import version5.common.response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class proxy implements InvocationHandler {
    private client clientTool;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据反射调用的方法 生成request 并发生request请求 返回服务器响应结果
        request req = request.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();
        //发生request
        response res = clientTool.sendRequest(req);
        //返回响应结果
        return res.getData();
    }

    //获取代理对象
    Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }
}
