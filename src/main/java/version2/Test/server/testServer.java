package version2.Test.server;

import version2.Test.commonService.blogService;
import version2.Test.commonService.userService;
import version2.Test.server.serviceImp.blogServiceImpl;
import version2.Test.server.serviceImp.userServiceImpl;

public class testServer {
    public static void main(String[] args) {
        //创建服务实例暴露对象
        interfaceProvider interfaceProvider = new interfaceProvider();

        //创建服务器本地的所有服务实例
        userService userService = new userServiceImpl();
        blogService blogService = new blogServiceImpl();
        //将服务实例添加进暴露对象中
        interfaceProvider.provideInterface(userService);
        interfaceProvider.provideInterface(blogService);

        ThreadPoolRPCServer poolRPCServer = new ThreadPoolRPCServer(interfaceProvider);
        poolRPCServer.start(8899);

    }
}
