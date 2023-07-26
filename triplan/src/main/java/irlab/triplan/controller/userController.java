package irlab.triplan.controller;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.user;
import irlab.triplan.service.userService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping
public class userController {
    private final userService userservice;
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> CreateUser(String user_name, String access_token, Integer default_id){
        Map<String, Object> res = userservice.CreateUser(user_name, access_token, default_id);
        return res;
    }
    
    @GetMapping("/login")
    @Transactional
    public Map<String, Object> login(@RequestParam(name="access_token")String access_token){
        Map<String, Object> res = userservice.findByAccessToken(access_token);
        return res;
    }

    @DeleteMapping("/withdraw")
    public Map<String, Object> deleteUser(Integer user_id){
        Map<String, Object> res = userservice.deleteUser(user_id);
        return res;
    }

    @PutMapping("/change")
    public Map<String, Object> changeImg(Integer user_id, String user_name, Integer image_id){
        Map<String, Object> res = userservice.changeImg(user_id, user_name, image_id);
        return res;
    }
}
