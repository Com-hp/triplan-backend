package irlab.triplan.controller;

import irlab.triplan.service.timetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class timetableController {
    private final timetableService tservice;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> createSchedule(Integer trip_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date){
        return tservice.createTimetable(trip_id, title, memo, start_date, end_date);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> deleteSchedule(Integer timetable_id){
        return tservice.deleteTimetable(timetable_id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> editSchedule(Integer timetable_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date){
        return tservice.editTimetable(timetable_id, title, memo, start_date, end_date);
    }

    @GetMapping()
    public Map<String, Object> getAllSchedule(@RequestParam("trip_id") Integer trip_id){
        return tservice.getTimetable(trip_id,0 );
    }
    @GetMapping(value = "/trip")
    public Map<String, Object> getSchedule(@RequestParam("trip_id") Integer trip_id){
        return tservice.getTimetable(trip_id, 1);
    }
}
