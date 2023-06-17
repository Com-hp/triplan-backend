package irlab.triplan.service;

import irlab.triplan.DTO.memoDTO;

import java.util.List;
import java.util.Map;

public interface memoService {

    void classificationURL(Integer trip_id, Integer user_id, String url);
    List<memoDTO> getClass(Integer trip_id);
    Map<String, Object> createMemo(Integer trip_id, Integer category_id, Integer user_id, String content, String image_path);
    Map<String, Object> editMemo(Integer classification_id, Integer category_id, String content, String image_path);
}
