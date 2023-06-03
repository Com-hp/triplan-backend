package irlab.triplan.DTO;

import irlab.triplan.entity.userDefault;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userDefaultDTO {
    private Integer defalut_id;
    private String defalut_path;

    public static userDefaultDTO toDto(userDefault d){
        return new userDefaultDTO(d.getDefalut_id(), "/default" +d.getDefault_path());
    }
}
