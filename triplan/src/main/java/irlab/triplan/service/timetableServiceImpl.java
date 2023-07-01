package irlab.triplan.service;

import irlab.triplan.DTO.timetableDTO;
import irlab.triplan.entity.timetable;
import irlab.triplan.repository.timetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class timetableServiceImpl implements timetableService{
    private final timetableRepository tRepository;
    @Override
    public Map<String,Object> getTimetable(Integer trip_id, Integer type) {
        Map<String, Object> res = new HashMap<>();
        List<timetable> t = new ArrayList<>();
        if (trip_id != null & type == 0){
            t = tRepository.getAllTimetable(trip_id);
        } else if (trip_id != null & type == 1) {
            Map<String, Object> schedule = tRepository.findTrip(trip_id);
            t = tRepository.getTimetable(trip_id, Timestamp.valueOf(schedule.get("start_date").toString()+" 00:00:00"), Timestamp.valueOf(schedule.get("end_date").toString()+" 23:59:59"));
        }
        else{
            res.put("Message","req 확인");
            return res;
        }
        List<timetableDTO> data = new ArrayList<>();
        t.forEach(s -> data.add(timetableDTO.toDto(s)));
        res.put("Message","성공");
        res.put("Data",data);
        return res;
    }

    @Override
    public Map<String, Object> createTimetable(Integer trip_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date) {
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null | title == null | title == "" | start_date == null | end_date == null){
            res.put("Message","req 확인");
            return res;
        }
        tRepository.createTimetable(trip_id, title, memo, Timestamp.valueOf(start_date), Timestamp.valueOf(end_date));
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> deleteTimetable(Integer timetable_id) {
        Map<String, Object> res = new HashMap<>();
        if(timetable_id == null){
            res.put("Message","req 확인");
            return res;
        }
        tRepository.deleteTimetable(timetable_id);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> editTimetable(Integer timetable_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date) {
        Map<String, Object> res = new HashMap<>();
        if(timetable_id == null | title == null | title == "" | start_date == null | end_date == null){
            res.put("Message","req 확인");
            return res;
        }
        tRepository.updateTimetable(timetable_id, title, memo, Timestamp.valueOf(start_date), Timestamp.valueOf(end_date));
        res.put("Message","성공");
        return res;
    }
}
