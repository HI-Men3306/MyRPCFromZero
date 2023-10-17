package version2.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//实现Serializable 是为了让类可序列化 只有序列化的类才能进行传输
public class User implements Serializable {
    private String username;
    private Integer id;
    private char sex;
}
