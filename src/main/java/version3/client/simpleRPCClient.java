package version3.client;

import lombok.AllArgsConstructor;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//传统的BIO方式发送request
@AllArgsConstructor
public class simpleRPCClient implements RPCClient{
    private String host;
    private int port;

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try (Socket socket = new Socket(host, port)) {
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());

            //发送数据
            ops.writeObject(request);
            ops.flush();

            //读取响应
            RPCResponse response = (RPCResponse) ips.readObject();
            return response;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("发送请求失败！");
        }
        return null;
    }
}
