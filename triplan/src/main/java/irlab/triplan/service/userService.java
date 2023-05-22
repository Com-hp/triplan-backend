package irlab.triplan.service;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.user;

import java.util.Map;

public interface userService {
    void CreateUser(String user_name, String access_token, Integer default_path);

    Map<String, Object> findByAccessToken(String access_token);

    Integer countUser(String access_token);
}
