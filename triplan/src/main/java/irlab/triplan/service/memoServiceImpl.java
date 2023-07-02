package irlab.triplan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import irlab.triplan.DTO.*;
import irlab.triplan.entity.memo;
import irlab.triplan.repository.memoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
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
    private final Path path = Path.of("src/main/resources/static/memo");
    @Override
    public Map<String, Object> classificationURL(Integer trip_id, Integer user_id, String url) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL tmp = new URL("http://localhost:5000/"+url);
            HttpURLConnection conn = (HttpURLConnection) tmp.openConnection();
            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            Map<String, String> result = mapper.readValue(sb.toString(), Map.class);

            memorepository.classificationURL(trip_id, Integer.valueOf(result.get("category"))+1,user_id,url,result.get("image_url"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String ,Object> res = new HashMap<>();
        res.put("Message", "성공");
        return res;
    }
    @Transactional
    public List<memoDTO> getClass(Integer trip_id){
        List<memo> m = memorepository.findClass(trip_id);
        List<memoDTO> md = new ArrayList<>();
        m.forEach(s -> md.add(memoDTO.toDto(s)));
        return md;
    }

    @Override
    public Map<String, Object> createMemo(Integer trip_id, Integer category_id, Integer user_id, String content, MultipartFile image_path) {
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null || category_id == null || user_id == null ||((content == null || content == "") && image_path.isEmpty())){
            res.put("Message","req 확인");
            return res;
        }
        else if(image_path.getContentType().startsWith("image") == false){
            res.put("Message", "이미지 파일이 아님");
            return res;
        }
        String newFilename;
        if(!image_path.isEmpty()) {
            newFilename = "memo" + System.nanoTime() + (image_path.getOriginalFilename().substring(image_path.getOriginalFilename().lastIndexOf('.')));
            try {
                Files.copy(image_path.getInputStream(), path.resolve(newFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else newFilename = "대강 기본 이미지이름.jpg";
        memorepository.createMemo(trip_id, category_id, user_id, content, newFilename);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> editMemo(Integer classification_id, Integer category_id, String content, MultipartFile image_path, String pre_path) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || category_id == null || ((content == null || content == "") && image_path.isEmpty()) || (pre_path == null || pre_path =="")){
            res.put("Message","req 확인");
            return res;
        }
        if(image_path.isEmpty()){
            memorepository.editMemo(classification_id, category_id, content, pre_path);
        }
        else{
            String newFilename = "memo" + System.nanoTime() + (image_path.getOriginalFilename().substring(image_path.getOriginalFilename().lastIndexOf('.')));
            try {
                Files.copy(image_path.getInputStream(), path.resolve(newFilename));
                File f = new File(URLDecoder.decode("src/main/resources/static"+pre_path, "UTF-8"));
                memorepository.editMemo(classification_id, category_id, content, newFilename);
                f.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        res.put("Message", "성공");
        return res;
    }

}
