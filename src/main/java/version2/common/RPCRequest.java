package version2.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
//对客户端发送请求的统一封装
public class RPCRequest implements Serializable {
    //要调用的服务类名，客户端只知道接口名，在服务端中用接口名指向实现类
    private String interfaceName;
    //调用方法需要的参数
    private Object[] params;
    //调用方法所需要的参数的类型
    private Class<?>[] paramsType;
    //要调用的方法名
    private String methodName;
}
