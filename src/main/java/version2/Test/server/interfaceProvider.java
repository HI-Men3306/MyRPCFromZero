package version2.Test.server;

import java.util.HashMap;
import java.util.Map;

//向外暴露不同服务的实例对象  形成request中的接口名和服务实例之间的一一对应
public class interfaceProvider {
    private Map<String,Object> provider;

    public interfaceProvider() {
        this.provider = new HashMap<>();
    }

    public void provideInterface(Object serviceInstance){
        Class<?>[] interfaces = serviceInstance.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            //参数：实现的接口名    实现该接口的服务实例对象
            provider.put(clazz.getName(),serviceInstance);
        }
    }

    //根据接口名获取服务实例对象
    public Object getServiceInstance(String interfaceName){
        return provider.get(interfaceName);
    }
}
