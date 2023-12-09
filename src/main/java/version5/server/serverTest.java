package version5.server;

import version5.server.serverImp.blogServiceImp;
import version5.server.serverImp.userServiceImp;
import version5.service.blogService;
import version5.service.userService;

public class serverTest {
    public static void main(String[] args) {
        //创建服务暴露接口
        serviceProvider provider = new serviceProvider("localhost",8899);
        //创建服务器本地服务对象
        userService uService = new userServiceImp();
        blogService bService = new blogServiceImp();
        //添加服务实例对象 并将实例服务注册到zookeeper上
        provider.addServiceInstance(uService);
        provider.addServiceInstance(bService);

        //创建netty服务器
        server server = new nettyServer(provider);
        server.start(8899);
    }
}
