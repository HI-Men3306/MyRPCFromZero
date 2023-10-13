package version2.server;

import java.util.HashMap;
import java.util.Map;

public class serviceInterfaceProvider {
    //存放多个
    private Map<String,Object> interfaceProvider;

    public serviceInterfaceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    //提供对外暴露实现类的功能
    //传入的是一个服务对象实例
    public void provideServiceInterface(Object service){
        //一个服务对象可能实现多个接口  调用这些多个接口中的方法所需要调用的实例对象还是传入的这个服务对象
        //获取该对象实现的所有接口
        Class<?>[] interfaces = service.getClass().getInterfaces();
        //将所有的接口名和实例对象放入map中 实现对外暴露
        for (Class<?> clazz : interfaces) {

            //一个服务对象可能实现多个接口  调用这些多个接口中的方法所需要调用的实例对象还是传入的这个服务对象
            //参数详解 实现的接口名字   服务对象实例
            interfaceProvider.put(clazz.getName(),service);
        }
    }

    //根据接口名 获取对象实例
    public Object getServiceInstance(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
