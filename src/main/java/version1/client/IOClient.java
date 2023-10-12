package version1.client;

import version1.common.Request;
import version1.common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//对发送请求  和   获取响应 做的抽象
public class IOClient {
    public static Response sendRequest(String host, int port, Request request){
        try (Socket socket = new Socket(host, port)) {
            //获取对象输入流 和 输出流
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());

            //将数据响应到socket中
            ops.writeObject(request);
            ops.flush();//发送数据

            //获取响应数据
            Response response = (Response) ips.readObject();
            //将响应结果向上返回
            return response;
        }catch (IOException | ClassNotFoundException e){
            System.out.println("客户端发送请求失败！");
            return null;
        }
    }
}
