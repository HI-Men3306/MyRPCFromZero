package testVersion.client;

import testVersion.common.comRequest;
import testVersion.common.comResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//对客户端发送请求的封装
public class IOClient {
    //发送请求到服务器上 并返回响应数据
    public static comResponse sendRequest(String host, int port, comRequest request){
        //创建套接字
        try (Socket socket = new Socket(host, port)) {
            //获取输入输出流
            ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ipt = new ObjectInputStream(socket.getInputStream());
            //发送请求
            opt.writeObject(request);
            opt.flush();

            //获取响应结果 并返回
            return (comResponse) ipt.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端发送请求失败");
        }
        return null;
    }
}
