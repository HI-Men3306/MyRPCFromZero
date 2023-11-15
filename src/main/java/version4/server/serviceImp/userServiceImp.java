package version4.server.serviceImp;

import version4.common.Blog;
import version4.common.User;
import version4.service.userService;

public class userServiceImp implements userService {
    @Override
    public User selectUserById(Integer id) {
        User user = User.builder()
                .sex('男')
                .username("张三")
                .id(id).build();
        System.out.println("服务器查询到的数据为" + user);
        return user;
    }

    @Override
    public Blog selectBlog(Integer id) {
        Blog blog = Blog.builder().id(id).title("哈哈").userId(id).build();
        System.out.println("服务器查询到的数据为" + blog);
        return blog;
    }

    @Override
    public int insertUser(User user) {
        System.out.println("服务端插入数据成功！");
        return 1;
    }
}
