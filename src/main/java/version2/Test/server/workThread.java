package version2.Test.server;

import lombok.AllArgsConstructor;
import version2.Test.common.RPCRequest;
import version2.Test.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class workThread implements Runnable{
    private interfaceProvider provider;
    private Socket socket;

    @Override
    public void run() {
        try {
            //获取输入输出流
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            //读取request
            RPCRequest request = (RPCRequest) ips.readObject();

            System.out.println("读取到的request为" + request);

            //调用本地方法 获得封装有执行结果的response
            RPCResponse response = getResponse(request);
            //将response响应回客户端
            ops.writeObject(response);
            ops.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("读取request失败");
        }
    }

    //根据request信息 反射调用不同服务的方法
    private RPCResponse getResponse(RPCRequest request) {
        //获取需要调用方法的所属服务实例对象
        Object serviceInstance = provider.getServiceInstance(request.getInterfaceName());

        try {
            //获取对应服务所需要调用的方法
            Method method = serviceInstance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //反射执行方法
            Object result = method.invoke(serviceInstance, request.getParams());
            //将执行结果封装进response中 并返回
            return RPCResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误！");
            return RPCResponse.fail();
        }
    }

}
