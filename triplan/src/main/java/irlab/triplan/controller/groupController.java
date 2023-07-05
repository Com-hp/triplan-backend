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

    @PutMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> ModifyGroup(Integer group_id, String group_name, MultipartFile group_path, String pre_path){
        Map<String, Object> res = groupservice.ModifyGroup(group_id, group_name, group_path, pre_path);
        return res;
    }

    @GetMapping("/member")
    public Map<String, Object> GroupMember(Integer group_id){
        Map<String, Object> res = groupservice.GroupMember(group_id);
        return res;
    }

    @GetMapping("/join")
    public Map<String, Object> SelectCode(String group_code){
        Map<String, Object> res = groupservice.SelectCode(group_code);
        return res;
    }

    @PostMapping(value = "/join", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> GroupJoin(Integer user_id, Integer group_id, String group_pw){
        Map<String, Object> res = groupservice.GroupJoin(user_id, group_id, group_pw);
        return res;
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> GroupDelete(Integer user_id, Integer group_id){
        Map<String, Object> res = groupservice.GroupDelete(user_id, group_id);
        return res;
    }
}
