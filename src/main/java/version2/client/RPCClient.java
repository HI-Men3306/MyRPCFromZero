package version2.client;

import version2.common.Blog;
import version2.common.User;
import version2.service.userService;
import version2.service.blogService;

public class RPCClient {
    public static void main(String[] args) {
        //创建代理对象
        RPCProxy rpcProxy = new RPCProxy("127.0.0.1", 8899);
        //服务一
        userService service = (userService) rpcProxy.getProxy(userService.class);
        User user = service.selectUserById(12);
        System.out.println("The result of the client is " + user);
        int i = service.insertUser(User.builder().sex('男').id(12).username("张三").build());
        System.out.println("The result of the insert operation is " + i);

        //服务二
        blogService blogService = (blogService) rpcProxy.getProxy(blogService.class);
        Blog blogById = blogService.getBlogById(12);
        System.out.println(blogById);
    }
}
