package version0.service.serviceImp;

import version0.entity.User;
import version0.service.userService;

import java.util.Random;
import java.util.UUID;

public class userServiceImp implements userService {
    @Override
    public User selectUserById(int id) {
        System.out.println("服务器需要查询的用户的id为" + id);
        Random random = new Random();
        //创建用户
        User user = User.builder().userName(UUID.randomUUID().toString()).id(id).sex(random.nextBoolean()).build();

        return user;
    }
}
