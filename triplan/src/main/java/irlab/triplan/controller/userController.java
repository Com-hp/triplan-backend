package irlab.triplan.controller;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.user;
import irlab.triplan.service.userService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping
public class userController {
    private final userService userservice;
    @PostMapping("/login")
    public Map<String, Object> CreateUser(String user_name, String access_token, Integer default_id){
        userservice.CreateUser(user_name, access_token, default_id);
        Map<String, Object> res = new HashMap<>();
        res.put("Message", "성공");
        return res;
    }
    
    @GetMapping("/login")
    @Transactional
    public Map<String, Object> login(@RequestParam(name="access_token")String access_token){
        Integer cnt = userservice.countUser(access_token);
        Map<String, Object> res = new HashMap<>();
        if(cnt == 0){
            res.put("Message", "is_new");
        }
        else{
            System.out.println("test");
            res = userservice.findByAccessToken(access_token);
        }
        return res;
    }
}
