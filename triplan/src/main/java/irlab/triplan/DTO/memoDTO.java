package irlab.triplan.DTO;

import irlab.triplan.entity.category;
import irlab.triplan.entity.memo;
import irlab.triplan.entity.trip;
import irlab.triplan.entity.user;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Value
@Data
@AllArgsConstructor
public class memoDTO {
    private Integer classification_id;
    private trip cTrip;
    private category cCategory;
    private user cUser;
    private String content;
    private String image_path;
    private LocalDateTime content_datetime;
    private Integer is_url;
    private Integer like_count;

    public static memoDTO toDto(memo m){
        return new memoDTO(m.getClassification_id(), m.getCTrip(), m.getCCategory(), m.getCUser(), m.getContent(), m.getImage_path(), m.getContent_datetime(), m.getIs_url(), m.getLike_count());
    }
}
