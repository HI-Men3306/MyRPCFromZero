package version4.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

//对响应数据的统一封装
@Data
@Builder
public class RPCResponse implements Serializable {
    //响应状态码  类似http的状态码 用来标记当前响应的状态
    private int code;
    //存放响应的数据结果
    private Object data;
    //存放结果参数类型
    private Class<?> dataType;
    //响应的信息
    private String message;

    //成功的响应数据
    public static RPCResponse success(Object data) {
        return RPCResponse.builder().data(data).code(200).build();
    }

    //失败的响应数据
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("响应失败").build();
    }


}
