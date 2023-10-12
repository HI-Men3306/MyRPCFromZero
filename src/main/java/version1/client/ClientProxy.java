package version1.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import version1.common.Request;
import version1.common.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;
    @Override
    //参数为 代理对象的实例  被代理的方法对象  方法需要的参数
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //创建根据参数创建request对象
        Request request = Request.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes()).build();

        //调用底层IO  传输request请求
        Response response = IOClient.sendRequest(host, port, request);

        return response.getData();
    }

    //用作动态获取类对象用
    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
