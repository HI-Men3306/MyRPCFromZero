package version2.client;

import version2.common.RPCRequest;
import version2.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//实现客户端发送socket的底层实现
public class IOClient {
    //不会阻塞住的
    /*public static RPCResponse sendRequest(String host, int port, RPCRequest request) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        //获取socket输入输出流
        ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());

        //发送request
        ops.writeObject(request);
        ops.flush();
        //获取响应结果
        RPCResponse response = (RPCResponse) ips.readObject();
        socket.close();
        return response;
    }*/

    //会阻塞住的
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("进入");
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("发送前");
            ops.writeObject(request);
            ops.flush();
            System.out.println("接收前");
            RPCResponse response = (RPCResponse) ips.readObject();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端发送请求失败");
            return RPCResponse.fail();
        }
    }

}
