package version2.service;

import version2.common.User;

public interface userService {
    User selectUserById(int id);
    int insertUser(User user);
}
