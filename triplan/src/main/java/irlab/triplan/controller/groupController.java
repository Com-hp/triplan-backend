package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.service.groupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class groupController {
    private final groupService groupservice;
    @GetMapping("/")
    public List<group> groupFind() {
        return groupservice.readGroup();
    }

    @GetMapping("/{userId}")
    public List<groupDTO> getGroup(@PathVariable Integer userId){
        return groupservice.getGroup(userId);
    }
}
