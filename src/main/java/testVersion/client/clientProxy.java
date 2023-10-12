package testVersion.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import testVersion.common.comRequest;
import testVersion.common.comResponse;

@Data
@AllArgsConstructor
@Builder
public class clientProxy implements InvocationHandler {
    private String host;
    private int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        if(args != null){
            System.out.println(args[0]);
        }
        comRequest request = comRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())//设置要代理的接口名
                .methodName(method.getName())//设置要代理对象的方法名
                .paramsType(method.getParameterTypes())//设置要代理对象的参数类型
                .params(args).build();//设置参数

        //调用IOClient 发送请求
        comResponse response = IOClient.sendRequest(host, port, request);
        //返回响应结果
        return response;
    }

    //获取生成的代理对象
    public Object getProxy(Class<?> target){
        return Proxy.newProxyInstance(target.getClassLoader()
        ,new Class[]{target},this);
    }
}
