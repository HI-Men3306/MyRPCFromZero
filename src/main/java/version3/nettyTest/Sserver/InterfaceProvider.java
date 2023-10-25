package version3.nettyTest.Sserver;

import java.util.HashMap;
import java.util.Map;

//服务实例对象暴露接口
public class InterfaceProvider {
    Map<String,Object> hash;
    public InterfaceProvider(){
        hash = new HashMap<>();
    }

    public void putInstance(Object serviceInstance){
        //获取该服务实现的所有接口
        Class<?>[] interfaces = serviceInstance.getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            hash.put(aClass.getName(),serviceInstance);
        }
    }

    public Object getInstance(String interfaceName){
        return hash.get(interfaceName);
    }
}
