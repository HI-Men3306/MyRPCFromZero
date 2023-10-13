package version2.Test.commonService;

import version2.common.User;

public interface userService {
    User selectUserById(int id);
    int insertUser(User user);
}
