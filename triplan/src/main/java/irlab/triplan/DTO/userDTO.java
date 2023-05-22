package irlab.triplan.DTO;

import irlab.triplan.entity.user;
import irlab.triplan.entity.userDefault;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Value
@Data
@AllArgsConstructor
public class userDTO {
    private Integer user_id;
    private String user_name;
    private String access_token;
    private userDefault default_id;
    public static userDTO toDto(user u){
        return new userDTO(u.getUid(), u.getUser_name(), u.getAccess_token(), u.getDefault_id());
    }

    public static Map<String, Object> res(user u, userDefaultDTO ud){
        Map<String, Object> r = new HashMap<>();
        r.put("user_id",u.getUid());
        r.put("user_name", u.getUser_name());
        r.put("default", ud);
        return r;
    }
}
