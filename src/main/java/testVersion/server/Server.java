package testVersion.server;

import testVersion.common.comRequest;
import testVersion.common.comResponse;
import testVersion.server.ServiceImp.studentServiceImp;
import testVersion.service.studentService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

//服务端
public class Server {
    public static void main(String[] args) {
        studentService service = new studentServiceImp();
        //获取serverSocket
        try (ServerSocket serverSocket = new ServerSocket(8899)) {
            System.out.println("服务器启动了");
            while (true){
                //以BIO形式 监听窗口 获取请求
                Socket socket = serverSocket.accept();
                //启用新的线程来处理请求
                new Thread(()->{
                    //获取输入输出流
                    try {
                        ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ipt = new ObjectInputStream(socket.getInputStream());

                        //获取发送的请求
                        comRequest request = (comRequest) ipt.readObject();

                        System.out.println("传输过来的数据为" + request);

                        //反射调用对应的方法
                        Class<? extends studentService> aClass = service.getClass();
                        Method method = aClass.getMethod(request.getMethodName(), request.getParamsType());
                        Object result = method.invoke(service, request.getParams());
                        //将响应结果响应回去
                        opt.writeObject(comResponse.success(result));
                        opt.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
