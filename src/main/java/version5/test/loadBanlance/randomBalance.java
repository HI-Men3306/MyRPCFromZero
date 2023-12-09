package version5.test.loadBanlance;

import java.util.List;
import java.util.Random;

//随机负载均衡  即随机挑选一个服务器提供服务
public class randomBalance implements LoadBalance{
    @Override
    public String balance(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
