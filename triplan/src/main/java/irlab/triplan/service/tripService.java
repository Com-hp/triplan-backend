package irlab.triplan.service;

import irlab.triplan.DTO.tripDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface tripService {
    List<tripDTO> getGroupInTrip(Integer group_id);
    List<tripDTO> getGroupDate(Integer group_id);
    Map<String, Object> CreateTrip(Integer group_id, MultipartFile trip_path);
    Map<String, Object> getTripMember(Integer trip_id);
    Map<String, Object> insertMember(Integer trip_id, Integer user_id);
    Map<String, Object> deleteMember(Integer trip_id, Integer user_id);
    Map<String, Object> editTrip(Integer trip_id, String trip_name, LocalDate start_date, LocalDate end_date, MultipartFile trip_path, String pre_path);
}
