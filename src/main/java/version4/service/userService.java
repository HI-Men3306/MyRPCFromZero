package version4.service;

import version4.common.Blog;
import version4.common.User;

public interface userService {
    User selectUserById(Integer id);
    //原先的问题是这里的类型为int  导致使用json无法序列化 直接阻塞了
    //原因 ：fastjson将int类型的数据序列化之后 再将其反序列化 会自动数据转换为Integer类型
    //Blog selectBlog(int id);
    Blog selectBlog(Integer id);
    int insertUser(User user);
}
