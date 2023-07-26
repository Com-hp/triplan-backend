package irlab.triplan.service;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.DTO.userDefaultDTO;
import irlab.triplan.entity.user;
import irlab.triplan.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements userService{
    private final userRepository userrepository;

    @Override
    public Map<String, Object> CreateUser(String user_name, String access_token, Integer default_path) {
        Map<String, Object> res = new HashMap<>();
        if(user_name == "" || user_name.isEmpty() || access_token == "" || access_token.isEmpty() || default_path == null){
            res.put("Message","req 확인");
        }
        userrepository.CreateUser(user_name, access_token, default_path);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> findByAccessToken(String access_token) {
        Map<String, Object> res = new HashMap<>();
        Integer existsUser = countUser(access_token);
        if(existsUser == 0){
            res.put("Message", "is_new");
            return res;
        }
        user u = userrepository.findByAccessToken(access_token);
        userDTO uD = userDTO.toDto(u);
        res = userDTO.res(u, userDefaultDTO.toDto(uD.getDefault_id()));
        Integer cnt = userrepository.countEndTrip(u.getUser_id());
        res.put("trip_cnt", cnt);
        return res;
    }

    @Override
    public Integer countUser(String access_token) {
        Integer res = userrepository.countUser(access_token);
        return res;
    }

    @Override
    public Map<String, Object> deleteUser(Integer user_id){
        Map<String, Object> res = new HashMap<>();
        if(user_id == null){
            res.put("Message", "null");
            return res;
        }
        userrepository.deleteUser(user_id);
        res.put("Message", "삭제 성공");
        return res;
    }

    @Override
    public Map<String, Object> changeImg(Integer user_id, String user_name, Integer default_id){
        Map<String, Object> res = new HashMap<>();
        if(user_name == null || user_name.equals("") || default_id == null || user_id == null){
            res.put("Message", "null");
            return res;
        }
        userrepository.changeImg(user_id, user_name, default_id);
        res.put("Message", "변경 성공");
        return res;
    }
}
