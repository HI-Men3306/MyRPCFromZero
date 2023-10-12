package version1.server.serviceImpl;

import version1.common.User;
import version1.service.userService;

import java.util.Random;
import java.util.UUID;

public class userServiceImpl implements userService {
    @Override
    public User selectUserById(int id) {
        System.out.println("客户端查询了"+id+"的用户");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }
}
