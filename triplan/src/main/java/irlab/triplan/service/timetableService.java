package irlab.triplan.service;

import irlab.triplan.DTO.timetableDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface timetableService {
    Map<String, Object> getTimetable(Integer trip_id, Integer type);

    Map<String, Object> createTimetable(Integer trip_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date);

    Map<String, Object> deleteTimetable(Integer timetable_id);

    Map<String, Object> editTimetable(Integer timetable_id, String title, String memo, LocalDateTime start_date, LocalDateTime end_date);
}
