package version2.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import version2.common.RPCRequest;
import version2.common.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理调用客户端的对应方法
@Data
@AllArgsConstructor
public class RPCProxy implements InvocationHandler {
    //要发送请求的ip地址
    private String host;
    //要发送请求的端口号
    private int port;

    //获取代理对象
    public Object getProxy(Class target){
        //参数分别为：被代理的类对象的类加载器   被代理的类对象的类的类型   代理对象
        return Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target},this);
    }

    //每当使用反射调用方法时   都会触发这里的动态代理
    //这里的主要功能是根据要调用的实例对象的方法和参数  创建一个request请求，然后直接发送该request请求，实现无痕的rpc调用
    @Override
    //参数详解： proxy为被代理的对象实例    method为要调用的实例对象的方法     args为调用方法的参数
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("反射调用的方法为" + method.getName());

        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())//要调用的类的名称（这里为接口）
                .methodName(method.getName())//要调用的方法名称
                .params(args)//参数
                .paramsType(method.getParameterTypes())//调用方法的参数类型
                .build();
        //调用IOClient发送request
        RPCResponse response = IOClient.sendRequest(host, port, request);
        //将响应中的数据向上返回
        return response.getData();
    }
}
