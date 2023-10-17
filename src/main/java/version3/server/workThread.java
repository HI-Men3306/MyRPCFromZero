package version3.server;

import lombok.AllArgsConstructor;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
@AllArgsConstructor
public class workThread implements Runnable{
    private serviceInstanceProvider provider;
    private Socket socket;

    @Override
    public void run() {
        try {
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            //读取request
            RPCRequest request = (RPCRequest) ips.readObject();
            //根据request中的接口名 和 provider中的服务实例对象进行匹配 反射调用方法
            RPCResponse response = getResponse(provider,request);
            //将response响应会客户端
            ops.writeObject(response);
            ops.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("读取request失败！");
        }
    }

    private RPCResponse getResponse(serviceInstanceProvider provider, RPCRequest request) {
        //获取调用方法需要的服务实例对象
        Object serviceInstance = provider.getServiceInstance(request.getInterfaceName());
        try {
            //获取方法对象
            Method method = serviceInstance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //反射执行方法
            Object result = method.invoke(serviceInstance, request.getParams());
            //将结果封装进response中并返回
            return RPCResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("反射执行方法失败！");
            return RPCResponse.fail();
        }
    }
}
