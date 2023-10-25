package version3.nettyTest.service;

import version3.nettyTest.common.user;

public interface userService {
    user selectUserById(int id);
    int insertUser(user user);
}
