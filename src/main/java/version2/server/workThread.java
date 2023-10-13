package version2.server;

import lombok.AllArgsConstructor;
import version2.common.RPCRequest;
import version2.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

//真正执行接收和发送 的socket底层
@AllArgsConstructor
public class workThread implements Runnable{

    private Socket socket;
    private serviceInterfaceProvider provider;


    @Override
    public void run() {
        try {
            //获取输出输出流
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            //读取request
            RPCRequest request = (RPCRequest) ips.readObject();
            //调用本地服务中的方法
            RPCResponse response = getResponse(request);
            //将response响应到客户端
            ops.writeObject(response);
            ops.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //根据request中的数据  反射调用本地不同服务中的方法
    private RPCResponse getResponse(RPCRequest request) {
        //根据request中接口  获取服务对象实例
        Object serviceInstance = provider.getServiceInstance(request.getInterfaceName());
        //获取需要调用的方法对象
        Method method = null;
        try {
            method = serviceInstance.getClass().getMethod(request.getMethodName(), request.getParamsType());
            //反射执行方法 并获取执行结果
            Object result = method.invoke(serviceInstance, request.getParams());
            //将结果封装到response中并返回
            return RPCResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("反射执行方法错误！");
            return RPCResponse.fail();
        }

    }
}
