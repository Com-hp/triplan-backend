package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.DTO.tripDTO;
import irlab.triplan.service.groupService;
import irlab.triplan.service.tripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class tripController {
    private final tripService tripservice;
    private final groupService groupservice;

    @GetMapping("/home")
    public List getGroupInTrip(@RequestParam(name="user_id") Integer user_id){
        List<groupDTO> gt = groupservice.getGroup(user_id);
        List list = new ArrayList();
        for(int i = 0; i < gt.size(); i++){
            List<tripDTO> t = tripservice.getGroupInTrip(gt.get(i).getGroup_id());
            list.add(t);
        }
        return list;
    }
}
