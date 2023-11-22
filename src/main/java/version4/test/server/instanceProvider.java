package version4.test.server;

import java.util.HashMap;
import java.util.Map;

public class instanceProvider {
    Map<String,Object> provider = new HashMap<>();

    //添加实例对象
    public void putInstance(Object target){
        Class<?>[] interfaces = target.getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            provider.put(aClass.getName(),target);
        }
    }

    //获取实例对象
    public Object getInstance(String target){
        return provider.get(target);
    }
}
