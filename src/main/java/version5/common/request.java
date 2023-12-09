package version5.common;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class request implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] params;

}
