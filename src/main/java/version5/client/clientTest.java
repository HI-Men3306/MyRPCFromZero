package version5.client;

import version5.common.User;
import version5.common.blog;
import version5.service.blogService;
import version5.service.userService;

public class clientTest {
    public static void main(String[] args) {
        //创建代理对象
        client Client = new nettyClient();
        proxy proxy = new proxy(Client);

        //获取服务对象代理对象
        userService uService = (userService) proxy.getProxy(userService.class);
        blogService bService = (blogService) proxy.getProxy(blogService.class);

        //通过代理对象调用方法
        User user = User.builder().name("hyq").id(5).build();
        blog Blog = blog.builder().userId(55).title("this is a title name").id(54).build();

        Integer integer = uService.insertUser(user);
        System.out.println("--------------------------------插入用户结果" + integer + "--------------------------------");
        User resUer = uService.selectUserById(15);
        System.out.println("--------------------------------查找的用户信息为:" + resUer + "--------------------------------");

        Integer insertBlog = bService.insertBlog(Blog);
        System.out.println("--------------------------------插入blog结果" + insertBlog + "--------------------------------");
        blog blog = bService.selectBlogById(144);
        System.out.println("--------------------------------查找的blog信息为:" + blog + "--------------------------------");
    }
}
