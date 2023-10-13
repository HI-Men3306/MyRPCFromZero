package version2.Test.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RPCResponse implements Serializable {
    private int code;
    private String message;
    private Object data;

    //响应成功
    public static RPCResponse success(Object data){
        return RPCResponse.builder().code(200).data(data).build();
    }

    //响应失败
    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).message("响应失败").build();
    }
}
