package irlab.triplan.service;

import irlab.triplan.DTO.memoDTO;

import java.util.List;

public interface memoService {

    void classificationURL(Integer trip_id, Integer user_id, String url);

    List<memoDTO> getClass(Integer trip_id);

}
