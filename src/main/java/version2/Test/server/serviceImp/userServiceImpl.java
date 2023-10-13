package version2.Test.server.serviceImp;

import version2.Test.commonService.userService;
import version2.common.User;

public class userServiceImpl implements userService {
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
