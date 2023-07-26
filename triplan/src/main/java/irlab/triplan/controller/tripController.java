package irlab.triplan.controller;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.DTO.tripDTO;
import irlab.triplan.service.groupService;
import irlab.triplan.service.tripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InterruptedIOException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class tripController {
    private final tripService tripservice;
    private final groupService groupservice;

    @GetMapping("")
    public Map<String, Object> getTrip(@RequestParam(name = "group_id")Integer group_id){
        List<tripDTO> td = tripservice.getGroupInTrip(group_id);
        Map<String, Object> res = new HashMap<>();
        if(td.isEmpty()){
            res.put("Message","해당하는 trip이 없습니다.");
            return res;
        }
        res.put("Message", "성공");
        res.put("Data", td);
        return res;
    }

    @GetMapping("/home")
    public Map<String, Object> getGroupInTrip(@RequestParam(name="user_id") Integer user_id){
        Map<String, Object> res = new HashMap<>();
        List<groupDTO> gt = groupservice.getGroup(user_id);
        if(gt.isEmpty()){
            res.put("Message","해당하는 group이 없습니다.");
        }
        List list = new ArrayList();
        for(int i = 0; i < gt.size(); i++){
            List<tripDTO> t = tripservice.getGroupDate(gt.get(i).getGroup_id());
            list.add(t);
//            System.out.println(list.get(i));
        }
        res.put("Message", "성공");
        res.put("Data", list);
        return res;
    }

    @GetMapping("/member")
    public Map<String, Object> getTripMemeber(@RequestParam(name="trip_id") Integer trip_id){
        Map<String, Object> res = tripservice.getTripMember(trip_id);
        return res;
    }

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> CreateTrip(Integer group_id, MultipartFile trip_path){
        Map<String, Object> res = tripservice.CreateTrip(group_id, trip_path);
        return res;
    }

    @PostMapping(value = "/member/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> insertMember(Integer trip_id, Integer user_id){
        Map<String, Object> res = tripservice.insertMember(trip_id, user_id);
        return res;
    }

    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> editTrip(Integer trip_id, String trip_name, LocalDateTime start_date, LocalDateTime end_date, MultipartFile trip_path, String pre_path){
        Map<String, Object> res = tripservice.editTrip(trip_id, trip_name, start_date, end_date, trip_path, pre_path);
        return res;
    }

    @DeleteMapping(value = "/member/delete", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> deleteMember(Integer trip_id, Integer user_id){
        Map<String, Object> res = tripservice.deleteMember(trip_id, user_id);
        return res;
    }

}
