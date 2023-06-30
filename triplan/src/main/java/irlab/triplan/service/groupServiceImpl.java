package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import irlab.triplan.repository.groupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public Map<String, Object> CreateGroup(String group_name, String group_pw, Integer user_id, MultipartFile group_path){
        Map<String, Object> res = new HashMap<>();
        //예외처리
        if(user_id == null || (group_name == null || group_name.equals("")) || (group_pw == null || group_path.equals(""))){
            res.put("Error", "null값이 존재합니다.");
            return res;
        }
        if(!group_pw.matches("[0-9|a-z|A-Z]*")){
            res.put("Error", "group_pw에 미허용 글자가 포함되어 있습니다.");
            return res;
        }
        else if(group_pw.length() != 4){
            res.put("Error","group_pw가 4자리가 아닙니다.");
            return res;
        }
        if(group_path.isEmpty()){
            res.put("Error", "파일이 비어있습니다.");
            return res;
        }
        if(group_path.getContentType().startsWith("image") == false){
            res.put("Error", "이미지 파일이 아닙니다.");
            return res;
        }

        //group_code 설정
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

        //파일 업로드
        Path path = Path.of("src/main/resources/static/group");
        String newFilename = "group" + System.nanoTime() + (group_path.getOriginalFilename().substring(group_path.getOriginalFilename().lastIndexOf(".")));
        try{
            Files.copy(group_path.getInputStream(), path.resolve(newFilename));
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        //database에 입력
        grouprepository.CreateGroup(group_name, group_pw, group_code, newFilename);
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
