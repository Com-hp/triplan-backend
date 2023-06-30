package irlab.triplan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class imageServiceImpl implements imageService{
    @Override
    public Map<String, Object> editImage(String type, Integer method, MultipartFile file){
        Map<String, Object> res = new HashMap<>();

        //예외 처리
        if(file.isEmpty()){
            res.put("Error", "파일이 비어있습니다.");
            return res;
        }
        if(file.getContentType().startsWith("image") == false){ //file의 content-type이 image로 시작하는지 파악
            res.put("Error", "이미지 파일이 아닙니다.");
            return res;
        }
        if(type != "group" && type != "trip" && type != "memo" && (type == null || type.equals(""))){
            res.put("Error", "type이 없거나 올바르지 않음");
            return res;
        }
        if(1 < method || 0 > method || method == null){
            res.put("Error", "method가 없거나 올바르지 않음");
            return res;
        }

        //파일명 지정
        String originalFileExtension = ".png";
        if(file.getContentType().contains("image/jpeg")){
            originalFileExtension = ".jpg";
        }
        String newFilename;
        Path path;

        //group일 때
        if(type.equals("group")){
            newFilename = "group"+ System.nanoTime() + originalFileExtension;
            path = Path.of("src/main/resources/static/group");
            try{
                Files.copy(file.getInputStream(), path.resolve(newFilename));
                if(method == 0){
                    //그룹 이미지 등록 ->
                }
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        //trip일 때
        else if(type.equals("trip")){
            newFilename = "trip"+ System.nanoTime() + originalFileExtension;
            path = Path.of("src/main/resources/static/trip");
        }
        //memo일 때
        else if(type.equals("memo")){
            newFilename = "memo"+ System.nanoTime() + originalFileExtension;
            path = Path.of("src/main/resources/static/memo");

        }
        res.put("Message", "성공");
        return res;
    }
}
