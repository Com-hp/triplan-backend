package irlab.triplan.service;

import irlab.triplan.DTO.tripDTO;
import java.util.List;

public interface tripService {
    List<tripDTO> getGroupInTrip(Integer id);
}
