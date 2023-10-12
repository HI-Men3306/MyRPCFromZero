package version0.service;

import version0.entity.User;

public interface userService {
    User selectUserById(int id);
}
