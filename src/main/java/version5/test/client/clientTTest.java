package version5.test.client;

import version5.common.User;
import version5.common.blog;
import version5.service.blogService;
import version5.service.userService;

public class clientTTest {
    public static void main(String[] args) {
        //创建基于netty的客户端
        client client = new nettyClient();
        //创建代理对象
        proxy proxy = new proxy(client);
        //获取代理服务对象
        userService uService = (userService) proxy.getProxy(userService.class);
        blogService bService = (blogService) proxy.getProxy(blogService.class);

        //调用服务
        Integer uRes = uService.insertUser(new User(123, "张三"));
        System.out.println("---------------------------插入用户结果" + uRes + "---------------------------");
        User user = uService.selectUserById(12);
        System.out.println("---------------------------查询用户结果" + user + "---------------------------");

        Integer bRes = bService.insertBlog(new blog(22, "张三", 333));
        System.out.println("---------------------------插入blog结果" + bRes + "---------------------------");
        blog blog = bService.selectBlogById(222);
        System.out.println("---------------------------查询blog" + blog + "---------------------------");
    }
}
