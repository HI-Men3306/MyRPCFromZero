package version2.server;

import version2.server.ServiceImp.blogServiceImp;
import version2.server.ServiceImp.userServiceImp;
import version2.service.blogService;
import version2.service.userService;

public class simpleServer {
    public static void main(String[] args) {
        //创建服务器的多个服务实例
        userService userService = new userServiceImp();
        blogService blogService = new blogServiceImp();
        //创建接口和实例的对应map实例   对外暴露 多个不同的服务实例
        serviceInterfaceProvider provider = new serviceInterfaceProvider();
        provider.provideServiceInterface(userService);
        provider.provideServiceInterface(blogService);

        //服务器开始工作
        //普通版本的服务器
        simpleRPCServer simpleRPCServer = new simpleRPCServer(provider);
        //simpleRPCServer.start(8899);

        //线程池版本的服务器
        ThreadPoolRPCServer threadPoolRPCServer = new ThreadPoolRPCServer(provider);
        threadPoolRPCServer.start(8899);
    }
}
