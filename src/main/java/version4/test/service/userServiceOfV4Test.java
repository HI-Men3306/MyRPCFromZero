package version4.test.service;

import version4.test.common.userOfV4Test;

public interface userServiceOfV4Test {
    userOfV4Test selectUserById(Integer id);
    Integer insertUser(userOfV4Test user);
}
