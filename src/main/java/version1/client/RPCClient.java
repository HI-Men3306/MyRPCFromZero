package version1.client;

import version1.common.Request;
import version1.common.User;
import version1.service.userService;

import java.util.Random;
import java.util.UUID;

public class RPCClient {
    public static void main(String[] args) {
        //创建代理对象
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        //获取需要调用的服务器的服务类
        userService service = clientProxy.getProxy(userService.class);

        //调用服务器的方法1
        User user = service.selectUserById(10);
        System.out.println("从服务器获取的user为" + user);

        //方法2
        User data = User.builder().userName(UUID.randomUUID().toString()).id(10).sex(true).build();
        int i = service.insertUser(data);
        System.out.println("向服务器插入数据条数" + i);
    }
}
