package irlab.triplan.DTO;

import irlab.triplan.entity.trip;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class tripDTO {
    private Integer trip_id;
    private String trip_name;
    private String trip_path;
    private String start_date;
    private String end_date;

    public static tripDTO toDto(Map<String, Object> t){
        return new tripDTO(Integer.parseInt(String.valueOf(t.get("trip_id"))), String.valueOf(t.get("trip_name")), ("/images/resources/trip/"+t.get("trip_path")), String.valueOf(t.get("start_date")), String.valueOf(t.get("end_date")));
    }
}
