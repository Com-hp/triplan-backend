package irlab.triplan.DTO;

import irlab.triplan.entity.category;
import lombok.*;

@Getter
@Setter
@Value
@Data
@AllArgsConstructor
public class categoryDTO {
    private Integer category_id;
    private String classifiacation;

    public static categoryDTO toDto(category c){
        return new categoryDTO(c.getCategory_id(), c.getClassification());
    }
}
