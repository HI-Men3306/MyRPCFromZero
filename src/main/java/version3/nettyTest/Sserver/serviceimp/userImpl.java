package version3.nettyTest.Sserver.serviceimp;

import version3.nettyTest.common.user;
import version3.nettyTest.service.userService;

public class userImpl implements userService {
    @Override
    public user selectUserById(int id) {
        user user22 = user.builder()
                .sex('男')
                .username("张三")
                .id(12).build();
        System.out.println("服务器查询到的数据为" + user22);
        return user22;
    }

    @Override
    public int insertUser(user user) {
        System.out.println("服务端插入数据成功！");
        return 1;
    }
}
