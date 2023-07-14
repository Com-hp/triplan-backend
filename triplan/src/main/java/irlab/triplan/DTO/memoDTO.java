package irlab.triplan.DTO;

import irlab.triplan.entity.memo;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

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
    private Integer is_like;


    public static memoDTO toDto(Map<String,Object> m){
        if(Integer.valueOf(String.valueOf(m.get("is_url"))) == 1){
            return new memoDTO(Integer.parseInt(String.valueOf(m.get("classification_id"))), Integer.parseInt(String.valueOf(m.get("trip_id"))), String.valueOf(m.get("category")), Integer.parseInt(String.valueOf(m.get("user_id"))), String.valueOf(m.get("user_name")), String.valueOf(m.get("content")), String.valueOf(m.get("image_path")), Timestamp.valueOf(String.valueOf(m.get("content_datetime"))).toLocalDateTime(), Integer.valueOf(String.valueOf(m.get("is_url"))), Integer.parseInt(String.valueOf(m.get("like_count"))), Integer.parseInt(String.valueOf(m.get("is_like"))));
        }
        else
            return new memoDTO(Integer.parseInt(String.valueOf(m.get("classification_id"))), Integer.parseInt(String.valueOf(m.get("trip_id"))), String.valueOf(m.get("category")), Integer.parseInt(String.valueOf(m.get("user_id"))), String.valueOf(m.get("user_name")), String.valueOf(m.get("content")), "/images/resources/memo/"+String.valueOf(m.get("image_path")), Timestamp.valueOf(String.valueOf(m.get("content_datetime"))).toLocalDateTime(), Integer.valueOf(String.valueOf(m.get("is_url"))), Integer.parseInt(String.valueOf(m.get("like_count"))), Integer.parseInt(String.valueOf(m.get("is_like"))));
    }
}//수정중