package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import irlab.triplan.repository.groupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //final 필드 값을 파라미터로 받는 생성자 생성
public class groupServiceImpl implements groupService{
    private final groupReporitory grouprepository;
    private final groupUserRepository groupuserrepository;
    private final Path path = Path.of("src/main/resources/static/group");
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

    @Override
    public Map<String, Object> ModifyGroup(Integer group_id, String group_name, MultipartFile group_path, String pre_path){
        Map<String, Object> res = new HashMap<>();
        String result;
        //예외처리
        if(group_id == null || (pre_path == null || pre_path.equals(""))){
            res.put("Error", "group_id와 pre_path는 null일 수 없습니다.");
            return res;
        }
        //그룹 이름만 변경했을 때
        if(group_path.isEmpty()){
            grouprepository.updateGroupName(group_id, group_name);
        }
        //이미지 파일만 변경했을 때
        if(group_name == null || group_name.equals("")){
            result = PutFile(group_path, pre_path);
            if(result.equals("실패")){
                res.put("Error", "올바른 확장자가 아닙니다.");
            }
            grouprepository.updateGroupPath(group_id, result);
        }
        //그룹명과 파일 모두 변경했을 때
        result = PutFile(group_path, pre_path);
        grouprepository.updateGroup(group_id, group_name, result);
        res.put("Message", "성공");
        return res;
    }

    private String PutFile(MultipartFile file, String pre_file){
        String res;
        if(file.getContentType().startsWith("image") == false){
            res = "실패";
            return res;
        }
        String newFilename = "group" + System.nanoTime() + (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        String oldFilename = path +"/"+pre_file;
        try{
            Files.copy(file.getInputStream(), path.resolve(newFilename));
            File f = new File(URLDecoder.decode(oldFilename, "UTF-8"));
            f.delete();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return newFilename;
    }
}
