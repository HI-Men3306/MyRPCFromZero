package version3.nettyTest.Cclient;

import version3.nettyTest.common.blog;
import version3.nettyTest.common.user;
import version3.nettyTest.service.blogService;
import version3.nettyTest.service.userService;

public class TestClient {
    public static void main(String[] args) {
        RPCClient client = new nettyClient("127.0.0.1",8899);
        //获取动态代理对象
        RPCProxy rpcProxy = new RPCProxy(client);
        // 创建被代理对象
        userService userPro = (userService) rpcProxy.getProxy(userService.class);
        blogService blogPro = (blogService) rpcProxy.getProxy(blogService.class);

        //rpc调用方法
        user uu = user.builder().id(12).username("哈哈").sex('男').build();
        userPro.insertUser(uu);
        user user = userPro.selectUserById(12);
        System.out.println("查询的用户数据为" + user);

        blog blogById = blogPro.getBlogById(1111);
        System.out.println("查询到的博客为" + blogById);
    }
}
