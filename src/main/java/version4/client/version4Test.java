package version4.client;

import version4.common.Blog;
import version4.common.User;
import version4.service.blogService;
import version4.service.userService;

public class version4Test {
    public static void main(String[] args) {
        // 构建一个使用java Socket/ netty/....传输的客户端
        clientInterface rpcClient = new nettyClient("127.0.0.1", 8899);
        // 把这个客户端传入代理客户端
        proxy rpcClientProxy = new proxy(rpcClient);
        // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
        userService userServices = (userService) rpcClientProxy.getProxy(userService.class);

        User user = User.builder().username("张三").id(100).sex('男').build();
        Integer integer = userServices.insertUser(user);
        System.out.println("向服务端插入数据："+integer + "------------------------------------------");

        // 调用方法
        User userByUserId = userServices.selectUserById(10);
        System.out.println("从服务端得到的user为：" + userByUserId);
        //Blog blog = userServices.selectBlog(12);
        System.out.println("查询到的user为" + user + "-----------------------------------------------");


        blogService blogServices = (blogService) rpcClientProxy.getProxy(blogService.class);

        Blog blogById = blogServices.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById + "-----------------------------------------------");
    }
}
