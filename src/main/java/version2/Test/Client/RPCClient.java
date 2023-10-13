package version2.Test.Client;

import version2.Test.commonService.blogService;
import version2.Test.commonService.userService;
import version2.common.Blog;
import version2.common.User;

public class RPCClient {
    public static void main(String[] args) {
        RPCProxy rpcProxy = new RPCProxy("127.0.0.1", 8899);
        //获取用户服务的代理对象
        userService userSer = (userService) rpcProxy.getProxy(userService.class);
        //获取blog服务的代理对象
        blogService blogSer = (blogService) rpcProxy.getProxy(blogService.class);

        //调用不同服务中的方法
        User user = userSer.selectUserById(12);
        System.out.println("客户端查询到的用户为" + user);

        Blog blogById = blogSer.getBlogById(124);
        System.out.println("客户端查询到的blog为" + blogById);
    }
}
