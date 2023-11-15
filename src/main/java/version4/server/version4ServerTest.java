package version4.server;

import version4.server.serviceImp.blogServiceImp;
import version4.server.serviceImp.userServiceImp;
import version4.service.blogService;
import version4.service.userService;

public class version4ServerTest {
    public static void main(String[] args) {
        //创建实例provider
        interfaceProvider provider = new interfaceProvider();
        //创建本地方法实例
        userService uService = new userServiceImp();
        blogService bService = new blogServiceImp();
        //添加本地实例对象进provider中
        /*provider.putInstance(uService.getClass());
        provider.putInstance(bService.getClass());*/
        provider.putInstanceByInstance(uService);
        provider.putInstanceByInstance(bService);
        //启动netty服务器
        ServerInterface server = new nettyServer(provider);
        server.start(8899);

    }
}
