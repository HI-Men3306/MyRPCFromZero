package version3.nettyTest.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class request implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] params;
}
