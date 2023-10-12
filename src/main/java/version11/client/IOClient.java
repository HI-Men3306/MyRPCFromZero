package version11.client;

import version11.common.RPCresponse;
import version11.common.request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//对远程发送请求的封装
public class IOClient {
    public static RPCresponse sendRequest(String host, int port, request req) throws IOException {
        try (Socket socket = new Socket(host, port)) {
            //获取输入输出流
            ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ipt = new ObjectInputStream(socket.getInputStream());

            //发送请求
            opt.writeObject(req);
            opt.flush();
            //接收响应
            RPCresponse response = (RPCresponse) ipt.readObject();

            //返回响应
            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端发送数据失败");
        }
        return null;
    }
}
