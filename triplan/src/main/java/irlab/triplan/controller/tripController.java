package irlab.triplan.controller;

import irlab.triplan.DTO.tripDTO;
import irlab.triplan.service.tripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class tripController {
    private final tripService tripservice;

    @GetMapping("/home")
    public List<tripDTO> getGroupInTrip(@RequestParam(name="groupId") Integer groupId){
        return tripservice.getGroupInTrip(groupId);
    }
}
