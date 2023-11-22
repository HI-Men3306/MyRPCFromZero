package version4.test.server.serviceImp;

import version4.test.common.blogOfV4Test;
import version4.test.service.blogServiceOfV4Test;

import java.util.Random;

public class blogServiceImpOfV4Test implements blogServiceOfV4Test {
    @Override
    public Integer insertBlog(blogOfV4Test blog) {
        System.out.println("--------------------------------服务器插入--博客--成功--------------------------------");
        return 1;
    }

    @Override
    public blogOfV4Test selectBlogById(Integer id) {
        return blogOfV4Test.builder()
                .userId(new Random().nextInt())
                .title("这是一个博客")
                .userId(id).build();

    }
}
