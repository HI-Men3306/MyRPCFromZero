package version5.server.serverImp;

import version5.common.User;
import version5.service.userService;

public class userServiceImp implements userService {
    @Override
    public User selectUserById(Integer id) {
        System.out.println("服务器查询的用户id为" + id);
        return User.builder()
                .id(id)
                .name("张三")
                .build();
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("服务器插入数据成功，插入的用户为" + user);
        return 1;
    }
}
