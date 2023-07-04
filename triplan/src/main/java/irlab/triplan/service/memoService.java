package irlab.triplan.service;

import irlab.triplan.DTO.memoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface memoService {

    Map<String, Object> classificationURL(Integer trip_id, Integer user_id, String url);
    List<memoDTO> getClass(Integer trip_id);
    Map<String, Object> createMemo(Integer trip_id, Integer user_id, String content, MultipartFile image_path, String category);
    Map<String, Object> editMemo(Integer classification_id, String category, String content, MultipartFile image_path, String pre_path);
    Map<String, Object> createLike(Integer classification_id, Integer user_id);
    Map<String, Object> deleteLike(Integer classification_id, Integer user_id);
}
