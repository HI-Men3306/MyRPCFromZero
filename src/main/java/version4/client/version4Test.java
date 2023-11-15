package version4.client;

import version4.common.Blog;
import version4.common.User;
import version4.service.blogService;
import version4.service.userService;

public class version4Test {
    public static void main(String[] args) {
        clientInterface client = new nettyClient("localhost",8899);
        //获取代理对象
        proxy proxy = new proxy(client);
        blogService blog = (blogService) proxy.getProxy(blogService.class);
        userService user = (userService) proxy.getProxy(userService.class);

        Blog blogById = blog.getBlogById(20);
        System.out.println("查询的blog为" + blogById);
        User user1 = user.selectUserById(12);
        System.out.println("查询的用户为" + user1);
    }
}
