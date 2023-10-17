package version3.client;

import version3.common.Blog;
import version3.common.User;
import version3.service.blogService;
import version3.service.userService;

public class clientTest {
    public static void main(String[] args) {
        //创建一个基于BIO通信的request发送方式
        RPCClient client = new simpleRPCClient("127.0.0.1", 8899);

        //创建基于BIO方式的代理对象
        RPCProxy rpcProxy = new RPCProxy(client);
        //获取BIO方式的代理服务对象1
        userService userService = (userService) rpcProxy.getProxy(userService.class);
        //获取BIO方式的代理服务对象2
        blogService blogService = (blogService) rpcProxy.getProxy(blogService.class);

        //调用不同服务中的方法
        User user = User.builder().username("漳卅").id(12).sex('男').build();
        int i = userService.insertUser(user);
        System.out.println("插入用户成功的记录条数为" + i);
        User res = userService.selectUserById(144);
        System.out.println("查询到的用户为" + res);

        Blog blogById = blogService.getBlogById(123);
        System.out.println("查询到的blog为" + blogById);
    }
}
