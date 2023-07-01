package irlab.triplan.DTO;

import irlab.triplan.entity.group;
import irlab.triplan.entity.trip;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Value
@Data
@AllArgsConstructor
public class groupDTO {
    private Integer group_id;
    private String group_name;
    private String group_code;
    private String group_pw;
    private String group_path;

    public static groupDTO toDto(group g) {
        return new groupDTO(g.getGroup_id(), g.getGroup_name(), g.getGroup_code(), g.getGroup_pw(), ("/group/"+g.getGroup_path()));
    }
}
