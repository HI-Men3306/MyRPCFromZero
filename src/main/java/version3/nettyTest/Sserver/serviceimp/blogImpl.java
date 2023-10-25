package version3.nettyTest.Sserver.serviceimp;

import version3.nettyTest.common.blog;
import version3.nettyTest.service.blogService;

public class blogImpl implements blogService {
    @Override
    public blog getBlogById(Integer id) {
        blog blog22 = blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog22;
    }
}
