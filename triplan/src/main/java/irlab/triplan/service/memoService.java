package irlab.triplan.service;

import irlab.triplan.DTO.memoDTO;

import java.util.List;

public interface memoService {
    List<memoDTO> getClass(Integer trip_id);

}
