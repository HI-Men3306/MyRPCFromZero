package version2.service;

import version2.common.User;

//user服务接口
public interface userService {
    User selectUserById(int id);
    int insertUser(User user);
}
