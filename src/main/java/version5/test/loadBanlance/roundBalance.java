package version5.test.loadBanlance;

import java.util.List;

//轮询负载均衡   即所有服务器轮换着提供服务
public class roundBalance implements LoadBalance {
    private int choose = -1;
    @Override
    public String balance(List<String> list) {
        choose ++;
        choose %= list.size();
        return list.get(choose);
    }
}
