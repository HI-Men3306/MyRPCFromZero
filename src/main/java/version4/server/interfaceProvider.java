package version4.server;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class interfaceProvider {
    //根据类名提供 服务实体对象
    private Map<String,Object> map;

    public interfaceProvider() {
        this.map = new HashMap<>();
    }

    public void putInstance(Class<?> target){
        //获取该类对象所实现的所有接口
        Class<?>[] interfaces = target.getInterfaces();
        //将对应的接口名 和 对应的实体一一对应起来
        for (Class<?> aClass : interfaces) {
            try {
                map.put(aClass.getName(), target.getConstructor().newInstance());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void putInstanceByInstance(Object target){
        //获取该类对象所实现的所有接口
        Class<?>[] interfaces = target.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            map.put(clazz.getName(),target);
        }
    }

    public Object getInstance(String interfaceName){
        return map.get(interfaceName);
    }
}
