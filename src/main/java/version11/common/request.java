package version11.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对客户端发送的请求进行统一的封装
@Data
@Builder
public class request implements Serializable {
    //要调用的类名(接口名) 方法名 参数 参数类型
    private String interfaceName;
    private String methodName;
    private Object[] params;
    private Class<?>[] paramsType;
}
