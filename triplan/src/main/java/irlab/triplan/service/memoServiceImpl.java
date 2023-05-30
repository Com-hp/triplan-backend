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

}
    @Transactional
    public List<memoDTO> getClass(Integer trip_id){
        List<memo> m = memorepository.findClass(trip_id);
        List<memoDTO> md = new ArrayList<>();
        m.forEach(s -> md.add(memoDTO.toDto(s)));
        return md;
    }

}
