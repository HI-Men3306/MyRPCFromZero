package version5.service;

import version5.common.User;

public interface userService {
    User selectUserById(Integer id);
    Integer insertUser(User user);
}
