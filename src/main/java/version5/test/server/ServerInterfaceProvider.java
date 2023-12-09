package version5.test.server;

import version5.test.register.ZKServerRegister;
import version5.test.register.serviceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerInterfaceProvider {
    private Map<String,Object> map;
    private serviceRegister register;
    private String host;
    private int port;

    public ServerInterfaceProvider(String host,int port) {
        map = new HashMap<>();
        register = new ZKServerRegister();
        this.host = host;
        this.port = port;
    }

    void putInstance(Object target){
        System.out.println("注册成功！");
        Class<?>[] interfaces = target.getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            map.put(aClass.getName(), target);
            register.register(aClass.getName(),new InetSocketAddress(host,port));
        }
    }

    Object getInstance(String interfaceName){
        return map.get(interfaceName);
    }
}
