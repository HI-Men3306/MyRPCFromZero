package version11.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对服务器响应的数据做统一的封装 模仿http请求 给出响应状态码
@Data
@Builder
public class RPCresponse implements Serializable {
    private int code;
    private String message;
    private Object data;
    //响应成功
    public static RPCresponse success(Object data){
        return RPCresponse.builder().data(data).code(200).build();
    }
    //响应失败
    public static RPCresponse fail(){
        return RPCresponse.builder().code(500).message("服务器错误，响应数据失败").build();
    }
}
