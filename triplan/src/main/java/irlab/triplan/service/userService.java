package irlab.triplan.service;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.user;

import java.util.Map;

public interface userService {
    Map<String, Object> CreateUser(String user_name, String access_token, Integer default_path);

    Map<String, Object> findByAccessToken(String access_token);

    Integer countUser(String access_token);

    Map<String, Object> deleteUser(Integer user_id);

    Map<String, Object> changeImg(Integer user_id, String user_name, Integer default_id);
}
