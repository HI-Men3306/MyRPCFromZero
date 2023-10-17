package version3.server;

import version3.server.ServiceImp.blogServiceImp;
import version3.server.ServiceImp.userServiceImp;
import version3.service.blogService;
import version3.service.userService;

public class ServerTest {
    public static void main(String[] args) {
        //创建实例暴露对象
        serviceInstanceProvider provider = new serviceInstanceProvider();
        //创建服务端的所有服务实例对象
        userService userService = new userServiceImp();
        blogService blogService = new blogServiceImp();

        //将本地服务实例对象添加进实例暴露对象中
        provider.provideServiceInstance(userService);
        provider.provideServiceInstance(blogService);

        //创建基于线程池的服务器
        RPCServerInterface server = new threadPoolServer(provider);
        //启动服务
        server.start(8899);
    }
}
