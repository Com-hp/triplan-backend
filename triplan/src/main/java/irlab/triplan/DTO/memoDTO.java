package irlab.triplan.DTO;

import irlab.triplan.entity.memo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Value
@Data
@AllArgsConstructor
public class memoDTO {
    private Integer classification_id;
    private Integer Trip_id;
    private String category;
    private Integer user_id;
    private String User_name;
    private String content;
    private String image_path;
    private LocalDateTime content_datetime;
    private Integer is_url;
    private Integer like_count;

    public static memoDTO toDto(memo m){
        if(m.getIs_url() == 1)
            return new memoDTO(m.getClassification_id(), m.getCTrip().getT_id(), m.getCategory(), m.getCUser().getUser_id(), m.getCUser().getUser_name(), m.getContent(), m.getImage_path(), m.getContent_datetime(), m.getIs_url(), m.getLike_count());
        else
            return new memoDTO(m.getClassification_id(), m.getCTrip().getT_id(), m.getCategory(), m.getCUser().getUser_id(), m.getCUser().getUser_name(), m.getContent(), ("/memo/"+m.getImage_path()), m.getContent_datetime(), m.getIs_url(), m.getLike_count());
    }
}
