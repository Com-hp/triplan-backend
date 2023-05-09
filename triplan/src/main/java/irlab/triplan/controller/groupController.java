package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.service.groupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class groupController {
    private final groupService groupservice;
    @GetMapping("/groupList")
    public List<group> groupFind() {
        return groupservice.readGroup();
    }

    @GetMapping("")
    public List<groupDTO> getGroup(@RequestParam(name="user_id") Integer user_id){
        return groupservice.getGroup(user_id);
    }

    @PostMapping("/new")
    public Map<String, Object> CreateGroup(Integer user_id, String group_name, String group_pw){
        groupservice.CreateGroup(user_id, group_name, group_pw);
        Map<String, Object> res = new HashMap<>();
        res.put("Message", "성공");
        return res;
    }
}
