package version4.test.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class userOfV4Test implements Serializable {
    private String username;
    private Integer age;
    private String sex;
    private Integer id;
}
