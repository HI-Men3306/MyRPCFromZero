package version4.test.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class blogOfV4Test implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
}
