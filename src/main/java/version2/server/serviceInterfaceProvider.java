package version2.server;

import java.util.HashMap;
import java.util.Map;


//干什么的？  在利用反射机制调用方法时 需要被调用方法所属的服务实体对象  但是服务器存在多个不同的服务，这就需要把所有的服务实体对象创建出来
//用于反射调用不同的方法   但是如何将方法中和服务实体对象一一对应起来？  这个类就是干的这个事情
public class serviceInterfaceProvider {
    //key：方法所属的接口(类)名称   value：接口（类）实体对象
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
