package irlab.triplan.controller;

import irlab.triplan.service.imageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final imageService imageservice;
    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> EditImage(String type, Integer method, MultipartFile file){
        Map<String, Object> res = imageservice.editImage(type, method, file);
        return res;
    }
}
