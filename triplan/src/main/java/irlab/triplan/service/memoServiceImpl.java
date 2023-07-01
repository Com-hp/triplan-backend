package irlab.triplan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import irlab.triplan.DTO.*;
import irlab.triplan.entity.memo;
import irlab.triplan.repository.memoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class memoServiceImpl implements memoService{
    private final memoRepository memorepository;

    @Override
    public void classificationURL(Integer trip_id, Integer user_id, String url) {
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
    }
    @Transactional
    public List<memoDTO> getClass(Integer trip_id){
        List<memo> m = memorepository.findClass(trip_id);
        List<memoDTO> md = new ArrayList<>();
        m.forEach(s -> md.add(memoDTO.toDto(s)));
        return md;
    }

    @Override
    public Map<String, Object> createMemo(Integer trip_id, Integer category_id, Integer user_id, String content, String image_path) {
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null || category_id == null || user_id == null ||((content == null || content == "") && image_path == null)){
            res.put("Message","req 확인");
            return res;
        }
        memorepository.createMemo(trip_id, category_id, user_id, content, image_path);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> editMemo(Integer classification_id, Integer category_id, String content, String image_path) {
        Map<String, Object> res = new HashMap<>();
        if(classification_id == null || category_id == null || ((content == null || content == "") && image_path == null)){
            res.put("Message","req 확인");
            return res;
        }
        memorepository.editMemo(classification_id, category_id, content, image_path);
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
