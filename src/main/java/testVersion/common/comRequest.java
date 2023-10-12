package testVersion.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对客户端发送请求的统一封装
@Data
@Builder
public class comRequest implements Serializable {
    private String interfaceName;//要动态代理的接口类型
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] params;
}
