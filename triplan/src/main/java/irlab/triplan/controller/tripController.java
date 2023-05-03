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

    @GetMapping("/home/{id}")
    public List<tripDTO> getGroupInTrip(@PathVariable Integer id){
        return tripservice.getGroupInTrip(id);
    }
}
