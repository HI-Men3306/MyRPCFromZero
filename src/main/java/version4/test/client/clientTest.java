package version4.test.client;

import version4.test.common.blogOfV4Test;
import version4.test.common.userOfV4Test;
import version4.test.service.blogServiceOfV4Test;
import version4.test.service.userServiceOfV4Test;

public class clientTest {
    public static void main(String[] args) {
        //创建需要调用的服务的代理对象
        clientInterface client = new nettyClient("localhost",8899);
        proxyOfV4Test proxy = new proxyOfV4Test(client);

        //代理获取服务对象
        userServiceOfV4Test userService = (userServiceOfV4Test)proxy.getProxy(userServiceOfV4Test.class);
        blogServiceOfV4Test blogService = (blogServiceOfV4Test)proxy.getProxy(blogServiceOfV4Test.class);

        //调用不同的方法
        userOfV4Test resUser = userService.selectUserById(12);
        System.out.println("-----------------查询到的用户为：" + resUser + "-----------------");

        Integer integer = userService.insertUser(userOfV4Test.builder().sex("男").username("张三").age(12).build());
        System.out.println("-----------------插入成功的用户的数量为：" + integer + "-----------------");

        blogOfV4Test blog = blogService.selectBlogById(122);
        System.out.println("-----------------查询到的blog为：" + blog + "-----------------");

        Integer resBlogInsert = blogService.insertBlog(blogOfV4Test.builder().title("哈哈哈哈").userId(12).id(111).build());
        System.out.println("-----------------插入成功的blog的数量为：" + resBlogInsert + "-----------------");


    }
}
