package version2.Test.Client;

import version2.Test.common.RPCRequest;
import version2.Test.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//对发送request和接收response的封装
public class IOClient {
    public static RPCResponse sendRequest(RPCRequest request,String host,int port){
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ops.writeObject(request);
            ops.flush();

            RPCResponse response = (RPCResponse) ips.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端发送请求失败");
            return RPCResponse.fail();
        }
    }
}
