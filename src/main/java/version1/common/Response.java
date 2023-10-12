package version1.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对服务器响应的数据进行统一的处理
@Data
@Builder
public class Response implements Serializable {
    //响应状态码
    private int code;

    //响应状态信息
    private String message;

    //响应数据
    private Object data;

    //成功静态方法
    public static Response success(Object data){
        return Response.builder().code(200).data(data).build();
    }

    //失败静态方法
    public static Response failed(){
        return Response.builder().code(500).message("服务器发生错误").build();
    }
}
