package version2.client;

import version2.common.RPCRequest;
import version2.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//实现客户端发送socket的底层实现
public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);
        //获取socket输入输出流
        ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());

        //发送request
        ops.writeObject(request);
        //获取响应结果
        RPCResponse response = (RPCResponse) ips.readObject();
        return response;
    }
}
