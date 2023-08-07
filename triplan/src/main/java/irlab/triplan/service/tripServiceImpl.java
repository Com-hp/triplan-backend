package irlab.triplan.service;

import irlab.triplan.DTO.tripDTO;
import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.trip;
import irlab.triplan.repository.tripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class tripServiceImpl implements tripService{
    private final tripRepository triprepository;
    private final Path path = Path.of("resources/trip");

    @Override
    public Map<String, Object> getGroupInTrip(Integer group_id){
        Map<String, Object> res = new HashMap<>();
        if(group_id == null){
            res.put("Message","해당하는 trip이 없습니다.");
            return res;
        }
        List<Map<String, Object>> t = triprepository.findGroupInTrip(group_id);
        List<tripDTO> td = new ArrayList<>();
        t.forEach(s -> td.add(tripDTO.toDto(s)));
        res.put("Message" , "성공");
        res.put("Data", td);
        return res;
    }

    @Override
    public List<tripDTO> getGroupDate(Integer group_id){
        List<Map<String, Object>> t = triprepository.findGroupDate(group_id);
        List<tripDTO> td = new ArrayList<>();
        t.forEach(s -> td.add(tripDTO.toDto(s)));
        return td;
    }

    @Override
    public Map<String, Object> getTripMember(Integer trip_id){
        List<Map<String, Object>> t = triprepository.findByMember(trip_id);
        Map<String, Object> res = new HashMap<>();
        res.put("Message", "성공");
        res.put("Data",t);
        return res;
    }

    @Override
    public Map<String, Object> CreateTrip(Integer group_id, MultipartFile trip_path, String trip_name, LocalDate start_date, LocalDate end_date){
        Map<String, Object> res = new HashMap<>();
        if(group_id == null || trip_name.equals("") || start_date.equals("") || end_date.equals("")){
            res.put("Message","null값이 존재합니다.");
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
        trip_name = trip_name.replaceAll("\"","");
        triprepository.InsertTrip(group_id, newFilename,trip_name,start_date,end_date);
        res.put("Message", "성공");
        return res;
    }

    @Override
    public Map<String, Object> insertMember(Integer trip_id, Integer user_id){
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null || user_id == null || trip_id.equals("") || user_id.equals("")){
            res.put("Message", "trip_id 또는 user_id가 null 입니다.");
        }
        triprepository.insertMember(trip_id, user_id);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> editTrip(Integer trip_id, String trip_name, LocalDate start_date, LocalDate end_date, MultipartFile trip_path, String pre_path){
        Map<String, Object> res = new HashMap<>();
        res.put("Message", "성공");
        if(trip_id == null || pre_path.equals("")){
            res.put("Message", "trip_id와 pre_path 값을 확인해주세요");
            return res;
        }
        String newFilename = "trip" + System.nanoTime() + (trip_path.getOriginalFilename().substring(trip_path.getOriginalFilename().lastIndexOf(".")));
        String oldFilename = pre_path;
        //파일 받을 때 images 붙여서 오기 때문에 앞에 삭제 필요?
        try{
            Files.copy(trip_path.getInputStream(), path.resolve(newFilename));
            File f = new File(URLDecoder.decode(pre_path.substring(7), StandardCharsets.UTF_8));
            f.delete();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        triprepository.editTrip(trip_id, trip_name, start_date, end_date, newFilename);
        return res;
    }

    @Override
    public Map<String, Object> deleteMember(Integer trip_id, Integer user_id){
        Map<String, Object> res = new HashMap<>();
        if(trip_id == null || user_id == null || trip_id.equals("") || user_id.equals("")){
            res.put("Message", "trip_id 또는 user_id가 null 입니다.");
        }
        triprepository.deleteMember(trip_id, user_id);
        res.put("Message", "성공");
        return res;
    }
}
