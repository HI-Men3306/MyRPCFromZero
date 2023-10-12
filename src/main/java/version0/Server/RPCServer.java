package version0.Server;


import version0.entity.User;
import version0.service.serviceImp.userServiceImp;
import version0.service.userService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        userService service = new userServiceImp();
        try {
            //获取一个监听本地端口8899的socket服务
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务器启动了");
            //每有一个请求就创建一个新的线程去处理
            while (true) {
                //监听客户端请求  每当客户端发送一个请求 就会获得一个socket
                //当没有客户端发起请求时，serverSocket.accept()方法会阻塞 一直卡在这，直到一个客户端连接之后才会创建socket对象
                //也就说 如果没有客户端请求 就一直卡在这，下面的获取对象流压根就不会执行
                Socket socket = serverSocket.accept();

                new Thread(() -> {
                    try {
                        //获取序列化 反序列化流 并转换为对象流
                        ObjectOutputStream ops = (ObjectOutputStream) socket.getOutputStream();
                        ObjectInputStream ips = (ObjectInputStream) socket.getInputStream();

                        //读取客户端发送的请求数据
                        int id = ips.readInt();
                        //查询
                        User user = service.selectUserById(id);

                        //将结果响应回去
                        ops.writeObject(user);
                        //刷新缓冲区 直接响应回去
                        ops.flush();

                    } catch (IOException e) {
                        System.out.println("IO读取失败");
                    }
                }).start();
            }


        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}
