package version3.nettyTest.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class response implements Serializable {
    private String message;
    private int code;
    private Object data;

    public static response Success(Object data){
        return response.builder().code(200)
                .data(data).build();
    }

    public static response Fail(){
        return response.builder().code(500).message("响应失败").build();
    }
}
