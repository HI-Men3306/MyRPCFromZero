package version2.Test.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RPCRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] params;
}
