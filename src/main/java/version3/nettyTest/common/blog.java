package version3.nettyTest.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class blog implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
}
