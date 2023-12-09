package version5.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class response implements Serializable {
    private String message;
    private int code;
    private Object data;
    private Class<?> dataType;
    public static response success(Object data){
        return response.builder()
                .data(data)
                .code(200)
                .dataType(data.getClass())
                .build();
    }

    public static response fail(){
        return response.builder()
                .code(500)
                .message("response响应失败")
                .build();
    }
}
