package version1.service;

import version1.common.User;

public interface userService {
    User selectUserById(int id);
    Integer insertUser(User user);
}
