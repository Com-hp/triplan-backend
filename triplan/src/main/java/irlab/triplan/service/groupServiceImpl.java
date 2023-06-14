package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import irlab.triplan.repository.groupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //final 필드 값을 파라미터로 받는 생성자 생성
public class groupServiceImpl implements groupService{
    private final groupReporitory grouprepository;
    private final groupUserRepository groupuserrepository;
    @Override
    public List<group> readGroup(){
        return grouprepository.findAll();
    }

    @Override
    public List<groupDTO> getGroup(Integer user_id){
        List<group> g = grouprepository.findByGroup(user_id);
        List<groupDTO> gd = new ArrayList<>();
        g.forEach(s -> gd.add(groupDTO.toDto(s)));
        return gd;
    }

    @Override
    public Map<String, Object> CreateGroup(Integer user_id, String group_name, String group_pw){
        Map<String, Object> res = new HashMap<>();
        if(user_id == null || (group_name == null || group_name.equals(""))){
            res.put("Message", "null 값 있음");
            return res;
        }
        if(!group_pw.matches("[0-9|a-z|A-Z]*")){
            res.put("Message", "group_pw에 미허용 글자 포함됨");
            return res;
        }
        else if(group_pw.length() != 4){
            res.put("Message","group_pw가 4자리가 아님");
            return res;
        }
        String group_code;
        boolean is;
        List<String> code_list = grouprepository.selectCode();
        do {
            long a =  code_list.stream().distinct().count();
            group_code = createCode();
            code_list.add(group_code);
            long b = code_list.stream().distinct().count();
            is = a == b;
        }while(is);
        grouprepository.CreateGroup(group_name, group_pw, group_code);
        Integer group_id = grouprepository.selectGroupId();
        groupuserrepository.createGroupUser(user_id, group_id);
        res.put("Message","성공");
        return res;
    }

    private String createCode(){
        Random random = new Random();
        return random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
