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
    private Integer trip_id;
    private String trip_name;
    private String trip_path;
    private LocalDate start_date;
    private LocalDate end_date;

    public static tripDTO toDto(trip t){
        return new tripDTO(t.getT_id(),t.getT_name(), ("/images/resources/trip/"+t.getT_path()), t.getS_date(), t.getE_date());
    }
}
