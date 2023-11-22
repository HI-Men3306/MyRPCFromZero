package version4.test.server.serviceImp;

import version4.test.common.userOfV4Test;
import version4.test.service.userServiceOfV4Test;

import java.util.Random;

public class userServiceImpOfV4Test implements userServiceOfV4Test {
    @Override
    public userOfV4Test selectUserById(Integer id) {
        return userOfV4Test.builder()
                .age(new Random().nextInt(100))
                .sex("男")
                .username("扎昂三")
                .id(id)
                .build();
    }

    @Override
    public Integer insertUser(userOfV4Test user) {
        System.out.println("--------------------------------服务器插入--用户--成功！--------------------------------");
        return 1;
    }
}
