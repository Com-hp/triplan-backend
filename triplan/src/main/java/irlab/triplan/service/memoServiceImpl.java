package irlab.triplan.service;

import irlab.triplan.DTO.memoDTO;
import irlab.triplan.repository.memoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class memoServiceImpl implements memoService{
    private final memoRepository memorepository;
    private final Path path = Path.of("resources/memo");

    private final String tmp = "http://localhost:5000/URL";

    private RestTemplate restTemplate;

    @Override
    @Transactional
    public Map<String, Object> getClass(Integer trip_id, Integer user_id) {
        Map<String ,Object> res = new HashMap<>();
        if(trip_id == null){
            res.put("Message","req 확인");
            return res;
        }
        List<Map<String,Object>> tmp = memorepository.getClass(trip_id, user_id);
        List<memoDTO> md = new ArrayList<>();
        tmp.forEach(s -> {
            md.add(memoDTO.toDto(s));
        });
        res.put("Message", "성공");
        res.put("Data", md);
        return res;
    }

    @Override
    public Map<String, Object> classificationURL(Integer trip_id, Integer user_id, String url) {
        Map<String ,Object> res = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("url", url);

            HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, headers);
            ResponseEntity<String> tmp_res = restTemplate.postForEntity(tmp, req, String.class);
            JSONParser parser = new JSONParser(tmp_res.getBody());
            Map<String, Object> result = (Map<String, Object>) parser.parse();
            memorepository.classificationURL(trip_id, (String) result.get("category"),user_id,url, (String) result.get("image_url"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        res.put("Message", "성공");
        return res;
    }

    @Override
    public Map<String, Object> createMemo(Integer trip_id, Integer user_id, String content, MultipartFile image_path, String category) {
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null || (category == null || category.equals("")) || user_id == null ||((content == null || content.equals("")) && image_path.isEmpty())){
            res.put("Message","req 확인");
            return res;
        }
        if((content != null || content != "")&& image_path.isEmpty()){//content만 등록할 때
            memorepository.createMemo_only_content(trip_id,user_id,content,category);
        }
        else{//이미지 등록
            if(!image_path.getContentType().startsWith("image")){
                res.put("Message", "이미지 파일이 아님");
                return res;
            }
            String newFilename = "memo" + System.nanoTime() + (image_path.getOriginalFilename().substring(image_path.getOriginalFilename().lastIndexOf('.')));
            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                System.out.println("666666");
                Files.copy(image_path.getInputStream(), path.resolve(newFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if ((content == null || content.equals(""))){//사진만 등록할 때
                memorepository.createMemo_only_file(trip_id,user_id,newFilename,category);
            }
            else{//둘 다 있을 때
                memorepository.createMemo(trip_id,user_id,content,newFilename,category);
            }
        }
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> editMemo(Integer classification_id, String category, String content, MultipartFile image_path, String pre_path, Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || (category == null || category.equals(""))){
            res.put("Message","req 확인");
            return res;
        }
        Map<String,Object> preData = memorepository.preprocessing(classification_id);
        if(!preData.get("user_id").equals(user_id)){
            res.put("Message","작성자가 아닙니다");
            return res;
        }
        if(image_path.isEmpty()){
            if ((content == null || content.equals("")) && image_path.isEmpty()&&(pre_path==null||pre_path == "")){
                res.put("Message","내용과 이미지가 다 없음");
                return res;
            }
            memorepository.editMemo_only_content(classification_id, category, content);
        }
        else{
            String newFilename = "memo" + System.nanoTime() + (image_path.getOriginalFilename().substring(image_path.getOriginalFilename().lastIndexOf('.')));
            try {
                if(!Files.exists(path)){
                    try {
                        Files.createDirectories(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Files.copy(image_path.getInputStream(), path.resolve(newFilename));
                File f = new File(URLDecoder.decode(pre_path.substring(7), StandardCharsets.UTF_8));
                memorepository.editMemo(classification_id, category, content, newFilename);
                f.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> deleteMemo(Integer classification_id, Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || user_id == null){
            res.put("Message","req 확인");
            return res;
        }
        Map<String, Object> preData = memorepository.preprocessing(classification_id);
        if(!preData.get("user_id").equals(user_id)){
            res.put("Message","작성자가 아닙니다");
            return res;
        }
        memorepository.deleteMemo(classification_id);
        System.out.println(preData.get("is_url"));
        if((Integer.valueOf(preData.get("is_url").toString())==0)){
            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            File f = new File(URLDecoder.decode(path + "/" + preData.get("image_path"), StandardCharsets.UTF_8));
            f.delete();
        }
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> createLike(Integer classification_id, Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || user_id == null){
            res.put("Message","req 확인");
            return res;
        }
        if(memorepository.existsLike(classification_id, user_id) != 0){
            res.put("Message", "이미 처리된 메모");
            return res;
        }
        memorepository.createLike(classification_id, user_id);
        memorepository.updateLikeCnt(classification_id, 1);
        res.put("Message", "성공");
        return res;
    }

    @Override
    public Map<String, Object> deleteLike(Integer classification_id, Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || user_id == null){
            res.put("Message","req 확인");
            return res;
        }
        if(memorepository.existsLike(classification_id, user_id) == 0){
            res.put("Message", "처리되지 않은 메모");
            return res;
        }
        memorepository.deleteLike(classification_id, user_id);
        memorepository.updateLikeCnt(classification_id, -1);
        res.put("Message", "성공");
        return res;
    }

}
