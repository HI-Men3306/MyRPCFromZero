package testVersion.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
//对响应的统一封装
@Data
@Builder
public class comResponse implements Serializable {
    private int code;//响应状态码
    private Object data;//响应的数据
    private String message;//响应信息

    //错误响应信息
    public static comResponse failed(){
        return comResponse.builder().code(500).message("响应失败").build();
    }

    //成功响应信息
    public static comResponse success(Object data){
        return comResponse.builder()
                .code(200)
                .data(data).build();
    }
}
