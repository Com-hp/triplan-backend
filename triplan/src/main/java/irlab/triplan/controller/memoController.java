package irlab.triplan.controller;

import irlab.triplan.DTO.memoDTO;
import irlab.triplan.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<memoDTO> getClass(@RequestParam(name = "trip_id") Integer trip_id){
        return memoservice.getClass(trip_id);
    }

    @PostMapping("/memo")
    public Map<String, Object> createMemo(@RequestBody Map<String, Object> req){
        return memoservice.createMemo((Integer) req.get("trip_id"), (Integer) req.get("category_id"), (Integer) req.get("user_id"), req.get("content").toString(), req.get("image_path").toString());
    }

    @PostMapping("/url")
    public Map<String, Object> classificationURL(@RequestBody Map<String, Object> req){
        Map<String, Object> res = new HashMap<>();
        memoservice.classificationURL((Integer) req.get("trip_id"), (Integer) req.get("user_id"), (String) req.get("content"));
        res.put("Message","성공");
        return res;
    }

    @PutMapping("/memo")
    public Map<String, Object> editMemo(@RequestBody Map<String, Object> req){
        return memoservice.editMemo((Integer) req.get("classification_id"), (Integer) req.get("category_id"), req.get("content").toString(), req.get("image_path").toString());
    }

    @PostMapping("/like")
    public Map<String, Object> createLike(@RequestBody Map<String, Object> req){
        return memoservice.createLike((Integer) req.get("classification_id"), (Integer) req.get("user_id"));
    }

    @DeleteMapping("/like")
    public Map<String, Object> deleteLike(@RequestBody Map<String, Object> req){
        return memoservice.deleteLike((Integer) req.get("classification_id"), (Integer) req.get("user_id"));
    }
}
