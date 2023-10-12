package version11.server;

import version11.common.user;
import version11.service.userService;

import java.util.Random;
import java.util.UUID;

public class userServiceImpl implements userService {
    @Override
    public user selectUserById(int id) {
        System.out.println("客户端查询了"+id+"的用户");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        user user11 = user.builder().username(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user11;
    }


    @Override
    public Integer insertUser(user user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }
}
