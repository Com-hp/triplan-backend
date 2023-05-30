package irlab.triplan.controller;

import irlab.triplan.DTO.memoDTO;
import irlab.triplan.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
public class memoController {
    private final memoService memoservice;

    @GetMapping
    public List<memoDTO> getClass(@RequestParam(name = "trip_id") Integer trip_id){
        return memoservice.getClass(trip_id);
    }

    @PostMapping("/url")
    public Map<String, Object> classificationURL(@RequestBody Map<String, Object> req){
        Map<String, Object> res = new HashMap<>();

        return res;
    }
}
