package version2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//普通BIO的服务器实现版本
public class simpleRPCServer implements PRCServerInterface{
    private serviceInterfaceProvider provider;

    //暴露的服务对象实例
    public simpleRPCServer(serviceInterfaceProvider serviceProvide){
        this.provider = serviceProvide;
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器启动成功！");
            while (true){
                Socket socket = serverSocket.accept();
                //开启一个新线程去处理针对于当前的通信
                //普通版本的服务器 对于每个新的request 都创建一个新的线程去处理
                new Thread(new workThread(socket,provider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败！");
        }
    }

    @Override
    public void stop() {

    }
}
