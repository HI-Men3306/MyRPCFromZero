package version0.client;

import version0.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args){
        try {
            //创建socket 向本地88899端口发起请求
            Socket socket = new Socket("127.0.0.1", 8899);

            //获取序列化和反序列化流 并将其转换为对象流 （socket获取到的为字节流）
            ObjectInputStream ips = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());


            //发送请求 获取用户信息
            int i = new Random().nextInt();
            ops.writeInt(i);
            ops.flush();//发送请求

            //如果服务器处理请求 需要一定的时间  那么客户端会一直阻塞在获取响应处
            //获取服务器响应数据
            User user = (User) ips.readObject();
            System.out.println("根据服务器查询到的数据为" + user);


        } catch (IOException | ClassNotFoundException e) {
            System.out.println("客户端启动失败");
        }
    }
}
