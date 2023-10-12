package version11.server;

import lombok.SneakyThrows;
import version11.common.RPCresponse;
import version11.common.request;
import version11.service.userService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        userService service = new userServiceImpl();
        try (ServerSocket serverSocket = new ServerSocket(8899)) {
            //使用BIO的方式监听客户端请求
            //什么是BIO？   阻塞IO  一个接一个的处理请求 阻塞后面的请求
            System.out.println("服务器启动了");
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        //获取输入输出流
                        ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ipt = new ObjectInputStream(socket.getInputStream());

                        //获取请求
                        request req = (request) ipt.readObject();
                        //获取被代理类类对象
                        Class<?> userServiceClass = userService.class;
                        //获取请求所要调用的方法
                        //Method method = userService.class.getMethod(req.getMethodName(), req.getParamsType());
                        Method method = userServiceClass.getMethod(req.getMethodName(), req.getParamsType());
                        //反射执行方法
                        //invoke参数详解：  1.所属的对象的名字  2.参数列表
                        Object res = method.invoke(service, req.getParams());
                        //封装并响应结果
                        opt.writeObject(RPCresponse.success(res));
                        opt.flush();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器响应失败");
        }
    }
}
