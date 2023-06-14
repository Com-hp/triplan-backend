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
    public void CreateUser(String user_name, String access_token, Integer default_path) {
        userrepository.CreateUser(user_name, access_token, default_path);
    }

    @Override
    public Map<String, Object> findByAccessToken(String access_token) {
        user u = userrepository.findByAccessToken(access_token);
        userDTO uD = userDTO.toDto(u);
        Map<String, Object> res = userDTO.res(u, userDefaultDTO.toDto(uD.getDefault_id()));
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
