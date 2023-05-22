package irlab.triplan.service;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.DTO.userDefaultDTO;
import irlab.triplan.entity.user;
import irlab.triplan.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return res;
    }

    @Override
    public Integer countUser(String access_token) {
        Integer res = userrepository.countUser(access_token);
        return res;
    }
}
