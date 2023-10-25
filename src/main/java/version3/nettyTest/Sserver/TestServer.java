package version3.nettyTest.Sserver;

import version3.nettyTest.Sserver.serviceimp.blogImpl;
import version3.nettyTest.Sserver.serviceimp.userImpl;
import version3.nettyTest.service.blogService;
import version3.nettyTest.service.userService;

public class TestServer {
    public static void main(String[] args) {
        //创建暴露服务实例  对象
        InterfaceProvider interfaceProvider = new InterfaceProvider();
        //创建服务对象实例
        blogService blog = new blogImpl();
        userService user = new userImpl();
        //暴露接口
        interfaceProvider.putInstance(blog);
        interfaceProvider.putInstance(user);

        RPCServer server = new RPCServerNetty(interfaceProvider);
        try {
            server.Start(8899);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
