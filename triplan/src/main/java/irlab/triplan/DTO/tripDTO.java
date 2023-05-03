package irlab.triplan.DTO;

import irlab.triplan.entity.trip;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class tripDTO {
    private Integer t_id;
    private String t_name;
    private String t_path;
    private LocalDate s_date;
    private LocalDate e_date;

    public static tripDTO toDto(trip t){
        return new tripDTO(t.getT_id(),t.getT_name(), t.getT_path(), t.getS_date(), t.getE_date());
    }
}
