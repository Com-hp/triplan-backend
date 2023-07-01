package irlab.triplan.controller;

import irlab.triplan.service.timetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class timetableController {
    private final timetableService tservice;

    @PostMapping
    public Map<String, Object> createSchedule(@RequestBody Map<String, Object> res){
        return null;
    }
}
