package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.service.groupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> CreateGroup(String group_name, String group_pw, Integer user_id, MultipartFile group_path){
        Map<String, Object> res = groupservice.CreateGroup(group_name, group_pw, user_id, group_path);
        return res;
    }
}
