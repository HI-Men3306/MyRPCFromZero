package version0.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder//方便构造对象
@Data//自动生成get set方法
@NoArgsConstructor  //自动创建无参构造
@AllArgsConstructor //自动创建全参构造
//在socket中 序列化是将对象的状态信息转换为可以存储或传输的形式的过程  只有被实现了Serializable的对象才可以被序列化
public class User implements Serializable {
    private Integer id;
    private String userName;
    private Boolean sex;
}
