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
import java.util.Set;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping
public class userController {
    private final userService userservice;
    @PostMapping("/login")
    public Map<String, Object> CreateUser(@RequestBody Map<String, Object> req){
        userservice.CreateUser((String) req.get("user_name"), (String) req.get("access_token"), (Integer) req.get("default_id"));
        Map<String, Object> res = new HashMap<>();
        res.put("Message", "DB 확인 필요");
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
            res = userservice.findByAccessToken(access_token);
        }
        return res;
    }

    @DeleteMapping("/withdraw")
    public Map<String, Object> deleteUser(@RequestBody Map<String, Object> req){
        Map<String, Object> res = userservice.deleteUser((Integer) req.get("user_id"));
        return res;
    }

    @PutMapping("/change")
    public Map<String, Object> changeImg(@RequestBody Map<String, Object> req){
        Map<String, Object> res = userservice.changeImg((Integer) req.get("user_id"), (String) req.get("user_name"), (Integer) req.get("image_id"));
        return res;
    }
}
