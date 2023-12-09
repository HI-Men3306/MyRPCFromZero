package version5.common;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class blog implements Serializable {
    private Integer id;
    private String title;
    private Integer userId;
}
