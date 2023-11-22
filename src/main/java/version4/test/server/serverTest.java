package version4.test.server;

import version4.test.server.serviceImp.blogServiceImpOfV4Test;
import version4.test.server.serviceImp.userServiceImpOfV4Test;
import version4.test.service.blogServiceOfV4Test;
import version4.test.service.userServiceOfV4Test;

public class serverTest {
    public static void main(String[] args) {
        //创建存放实例对象的provider
        instanceProvider provider = new instanceProvider();
        //创建服务器本地服务对象并将其添加到provider中
        blogServiceOfV4Test blogService = new blogServiceImpOfV4Test();
        userServiceOfV4Test userService = new userServiceImpOfV4Test();
        provider.putInstance(blogService);
        provider.putInstance(userService);

        //创建服务器
        serverInterface server = new nettyServer(provider);
        server.start(8899);
    }
}
