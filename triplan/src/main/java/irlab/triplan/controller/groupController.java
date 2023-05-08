package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.service.groupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public List<groupDTO> getGroup(@RequestParam(name="userId") Integer userId){
        return groupservice.getGroup(userId);
    }
}
