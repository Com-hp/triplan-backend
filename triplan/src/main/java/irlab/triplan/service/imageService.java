package irlab.triplan.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface imageService {
    Map<String, Object> editImage(String type, Integer method, MultipartFile file);
}
