package irlab.triplan.DTO;

import irlab.triplan.entity.timetable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@AllArgsConstructor
public class timetableDTO {
    private Integer timetable_id;
    private Integer trip_id;
    private String title;
    private String memo;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

    public static timetableDTO toDto(timetable t){
        return new timetableDTO(t.getTimetable_id(), t.getTTrip().getT_id(), t.getTitle(), t.getMemo(), t.getS_date(), t.getE_date());
    }
}
