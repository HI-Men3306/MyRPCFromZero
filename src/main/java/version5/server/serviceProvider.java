package version5.server;

import version5.register.ZKServiceRegister;
import version5.register.serviceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class serviceProvider {
    private Map<String,Object> map;
    private String host;
    private int port;
    private serviceRegister register;

    public serviceProvider(String host, int port) {
        this.host = host;
        this.port = port;
        map = new HashMap<>();
        register = new ZKServiceRegister();
    }

    //在添加服务实体对象时 同时将客户端服务注册到ZK上
    void addServiceInstance(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            //本地的服务实例 和 接口名映射
            map.put(aClass.getName(), service);
            //将服务注册到ZK上
            register.register(aClass.getName(),new InetSocketAddress(host,port));
        }
    }

    //获取本地服务实例对象
    Object getInstance(String interfaceName){
        return map.get(interfaceName);
    }
}
