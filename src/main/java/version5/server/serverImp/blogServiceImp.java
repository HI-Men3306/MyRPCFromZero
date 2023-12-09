package version5.server.serverImp;

import version5.common.blog;
import version5.service.blogService;

import java.util.Random;

public class blogServiceImp implements blogService {
    @Override
    public blog selectBlogById(Integer id) {
        System.out.println("服务器查询到的 blog id为" + id);
        return blog.builder()
                .id(id)
                .title("标题")
                .userId(new Random().nextInt())
                .build();
    }

    @Override
    public Integer insertBlog(blog Blog) {
        System.out.println("服务器插入插入数据成功，插入的博客为" + Blog);
        return 1;
    }
}
