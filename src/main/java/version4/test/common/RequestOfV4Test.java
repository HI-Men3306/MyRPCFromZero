package version4.test.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Data
public class RequestOfV4Test implements Serializable {
    private String interfaceName;
    private Object[] params;
    private Class<?>[] paramsType;
    private String methodName;

}
