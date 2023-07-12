package irlab.triplan.controller;

import irlab.triplan.DTO.memoDTO;
import irlab.triplan.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/class")
public class memoController {
    private final memoService memoservice;

    @GetMapping
    public Map<String, Object> getClass(Integer trip_id, Integer user_id){
        return memoservice.getClass(trip_id, user_id);
    }

    @PostMapping(value = "/memo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createMemo(Integer trip_id, Integer user_id, String content, MultipartFile image_path, String category){
        return memoservice.createMemo(trip_id, user_id, content, image_path, category);
    }

    @PostMapping(value = "/url", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> classificationURL(Integer trip_id, Integer user_id, String content){
        return memoservice.classificationURL(trip_id, user_id, content);
    }

    @PutMapping(value = "/memo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> editMemo(Integer classification_id, String category, String content, MultipartFile image_path, String pre_path, Integer user_id){
        return memoservice.editMemo(classification_id, category, content, image_path, pre_path, user_id);
    }
    @DeleteMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> deleteMemo(Integer classification_id, Integer user_id){
        return memoservice.deleteMemo(classification_id, user_id);
    }
    @PostMapping(value = "/like", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> createLike(Integer classification_id, Integer user_id){
        return memoservice.createLike(classification_id, user_id);
    }

    @DeleteMapping(value = "/like", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> deleteLike(Integer classification_id, Integer user_id){
        return memoservice.deleteLike(classification_id, user_id);
    }
}
