package version11.client;

import version11.common.user;
import version11.service.userService;

public class Client {

    public static void main(String[] args) {
        Proxy proxy = new Proxy("127.0.0.1", 8899);
        //创建代理对象
        userService serviceProxy = (userService) proxy.getProxyInstance(userService.class);

        //代理对象调用方法
        user user = serviceProxy.selectUserById(10);
        System.out.println("查询到的结果是" + user);

        user user11 = version11.common.user.builder().username("张三").id(100).sex(true).build();
        serviceProxy.insertUser(user11);

    }
}
