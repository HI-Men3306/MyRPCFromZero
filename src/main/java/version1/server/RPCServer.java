package version1.server;

import version1.common.Request;
import version1.common.Response;
import version1.server.serviceImpl.userServiceImpl;
import version1.service.userService;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) throws IOException {
        userService service = new userServiceImpl();

        try {
            System.out.println("服务器启动了！");
            ServerSocket serverSocket = new ServerSocket(8899);

            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(() -> {
                    try {
                        //获取输出和输入流
                        ObjectInputStream ips = new ObjectInputStream(accept.getInputStream());
                        ObjectOutputStream ops = new ObjectOutputStream(accept.getOutputStream());

                        //获取传输过来的request
                        Request request = (Request) ips.readObject();

                        System.out.println("传输过来的数据为" + request.toString());

                        //根据request中的数据  来获取需要调用的userService中的对应方法对象
                        Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsType());
                        //反射机制 调用对应的方法   返回值为 执行该方法之后的返回值
                        Object invoke = method.invoke(service, request.getParams());

                        //将结果封装进response中
                        Response response = Response.success(invoke);

                        //将封装后带有查询结果的response响应到客户端
                        ops.writeObject(response);
                        ops.flush();//发送数据
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        System.out.println("IO错误！");
                    }
                }).start();
            }
        } catch (Exception e) {
            System.out.println("服务器启动失败！");
        }

    }
}
