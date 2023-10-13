package version2.server.ServiceImp;

import version2.common.User;
import version2.service.userService;

public class userServiceImp implements userService {
    @Override
    public User selectUserById(int id) {
        User user = User.builder()
                .sex('男')
                .username("张三")
                .id(12).build();
        System.out.println("服务器查询到的数据为" + user);
        return user;
    }

    @Override
    public int insertUser(User user) {
        System.out.println("服务端插入数据成功！");
        return 1;
    }
}
