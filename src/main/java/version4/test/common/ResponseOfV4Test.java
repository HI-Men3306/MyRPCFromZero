package version4.test.common;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Data
public class ResponseOfV4Test implements Serializable {
    private String message;
    private Integer code;
    private Object data;
    private Class<?> dataType;

    public static ResponseOfV4Test success(Object data){
        return ResponseOfV4Test.builder().code(200).data(data).dataType(data.getClass()).build();
    }

    public static ResponseOfV4Test fail(){
        return ResponseOfV4Test.builder().code(500).message("响应失败").build();
    }
}
