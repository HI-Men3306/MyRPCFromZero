package version3.server;

import java.util.HashMap;
import java.util.Map;

public class serviceInstanceProvider {
    private Map<String, Object> provider;

    public serviceInstanceProvider() {
        this.provider = new HashMap<>();
    }

    //对外暴露服务对象实例  形成接口和服务对象实例之间的一一映射
    public void provideServiceInstance(Object serviceInstance) {
        //获取服务实例对象实现的所有接口
        Class<?>[] interfaces = serviceInstance.getClass().getInterfaces();
        //将接口存入实例map中
        for (Class<?> clazz : interfaces) {
            provider.put(clazz.getName(), serviceInstance);
        }
    }

    //获取服务对象实例
    public Object getServiceInstance(String interfaceName) {
        return provider.get(interfaceName);
    }

}
