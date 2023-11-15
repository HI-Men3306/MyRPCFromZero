package version4.server.serviceImp;

import version4.common.Blog;
import version4.common.User;
import version4.service.blogService;

public class blogServiceImp implements blogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }

    @Override
    public User selectUser(Integer id) {
        User user = User.builder()
                .sex('男')
                .username("张三")
                .id(id).build();
        System.out.println("服务器查询到的数据为" + user);
        return user;
    }
}
