package version11.service;

import version11.common.user;

public interface userService {
    user selectUserById(int id);
    Integer insertUser(user user);
}
