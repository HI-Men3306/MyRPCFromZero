package version3.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class simpleRPCServer implements RPCServerInterface{
    private serviceInstanceProvider provider;

    public simpleRPCServer(serviceInstanceProvider provider) {
        this.provider = provider;
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("基于BIO服务器启动成功！");
            while (true){
                Socket socket = serverSocket.accept();
                //开启一个新的线程去进行request和response的收发工作
                new Thread(new workThread(provider,socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("基于BIO服务器启动失败！");
        }
    }

    @Override
    public void stop() {

    }
}
