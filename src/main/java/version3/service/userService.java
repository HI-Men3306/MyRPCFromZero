package version3.service;

import version3.common.User;

public interface userService {
    User selectUserById(int id);
    int insertUser(User user);
}
