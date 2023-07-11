package irlab.triplan.service;

import irlab.triplan.DTO.tripDTO;
import irlab.triplan.entity.trip;
import irlab.triplan.repository.tripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class tripServiceImpl implements tripService{
    private final tripRepository triprepository;
    private final Path path = Path.of("src/main/resources/static/trip");

    @Override
    public List<tripDTO> getGroupInTrip(Integer group_id){
        if(group_id == null){
            return null;
        }
        List<trip> t = triprepository.findGroupInTrip(group_id);
        List<tripDTO> td = new ArrayList<>();
        t.forEach(s -> td.add(tripDTO.toDto(s)));
        return td;
    }

    @Override
    public List<tripDTO> getGroupDate(Integer group_id){
        List<trip> t = triprepository.findGroupDate(group_id);
        List<tripDTO> td = new ArrayList<>();
        t.forEach(s -> td.add(tripDTO.toDto(s)));
        return td;
    }

    @Override
    public Map<String, Object> CreateTrip(Integer group_id, MultipartFile trip_path){
        Map<String, Object> res = new HashMap<>();
        if(group_id == null){
            res.put("Message","group_id가 없습니다.");
            return res;
        }
        if(trip_path.isEmpty()){
            res.put("Message","file이 없습니다.");
        }
        if(!trip_path.getContentType().startsWith("image")){
            res.put("Message","이미지 파일이 아닙니다.");
        }
        String newFilename;
        if(trip_path.isEmpty()){
            newFilename = "image.png";
        }
        else{
            newFilename = "trip" + System.nanoTime() + (trip_path.getOriginalFilename().substring(trip_path.getOriginalFilename().lastIndexOf(".")));
            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Files.copy(trip_path.getInputStream(), path.resolve(newFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        triprepository.InsertTrip(group_id, newFilename);
        res.put("Message", "성공");
        return res;
    }
}
