package version4.service;

import version4.common.User;

public interface userService {
    User selectUserById(int id);
    int insertUser(User user);
}
