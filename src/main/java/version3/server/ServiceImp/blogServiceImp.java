package version3.server.ServiceImp;

import version3.common.Blog;
import version3.service.blogService;

public class blogServiceImp implements blogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}
