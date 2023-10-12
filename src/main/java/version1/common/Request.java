package version1.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对客户端发起的请求进行统一的封装处理
@Data
@Builder
public class Request implements Serializable {
    //调用的类名
    private String interfaceName;

    //调用的方法名
    private String methodName;

    //传递的参数类型
    private Class<?>[] paramsType;

    //传递的参数
    private Object[] params;
}
