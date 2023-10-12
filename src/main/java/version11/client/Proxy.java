package version11.client;

import lombok.AllArgsConstructor;
import version11.common.RPCresponse;
import version11.common.request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//负责生成代理对象  完成对不同请求的统一封装
@AllArgsConstructor
public class Proxy implements InvocationHandler {
    private String host;
    private int port;

    public Object getProxyInstance(Class target){
        return java.lang.reflect.Proxy.newProxyInstance(target.getClassLoader()
        ,new Class[]{target},this);
    }

    @Override
    //每当代理对象调用一次方法  就会经由此方法 动态的调用对应的方法，并将对应方法的返回值返回
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装请求 并发送请求
        //需要注意的是 这里method.getDeclaringClass()和method.getClass()的区别
        //getClass方法返回实例对象的Class对象，而getDeclaringClass方法返回声明该实例的类的Class对象。
        request request = version11.common.request.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes()).build();

        //传输数据
        RPCresponse response = IOClient.sendRequest(host, port, request);

        return response.getData();
    }
}
