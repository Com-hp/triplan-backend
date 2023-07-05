package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import irlab.triplan.repository.groupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor //final 필드 값을 파라미터로 받는 생성자 생성
public class groupServiceImpl implements groupService{
    private final groupReporitory grouprepository;
    private final groupUserRepository groupuserrepository;
    private final Path path = Path.of("src/main/resources/static/group");
    private final Path oldpath = Path.of("src/main/resources/static");
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
        if(user_id == null || (group_name == null || group_name.equals("")) || (group_pw == null || group_path.isEmpty())){
            res.put("Message", "null값이 존재합니다.");
            return res;
        }
        group_pw = group_pw.replaceAll("\"","");
        if(!Pattern.compile("^[0-9a-zA-Z]*").matcher(group_pw).find()){
            System.out.println(group_pw);
            res.put("Message", "group_pw에 미허용 글자가 포함되어 있습니다.");
            return res;
        }
        else if(group_pw.length() != 4){
            System.out.println(group_pw.length());
            res.put("Message","group_pw가 4자리가 아닙니다.");
            return res;
        }
        if(group_path.getContentType().startsWith("image") == false){
            res.put("Message", "이미지 파일이 아닙니다.");
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
        String newFilename;
        if(group_path.isEmpty()){
            newFilename = "image.png";
        }
        else{
            newFilename = "group" + System.nanoTime() + (group_path.getOriginalFilename().substring(group_path.getOriginalFilename().lastIndexOf(".")));
            try{
                Files.copy(group_path.getInputStream(), path.resolve(newFilename));
            }catch(IOException e){
                throw new RuntimeException(e);
            }
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
    public Map<String, Object> SelectCode(String group_code){
        Map<String, Object> res = new HashMap<>();
        if(group_code.equals("") || group_code.length() != 6){
            res.put("Message", "null 값이 존재하거나 group_code가 6자리가 아닙니다.");
            return res;
        }
        String group_name = grouprepository.findByGroupName(group_code);
        res.put("Message", "성공");
        res.put("Data", group_name);
        return res;
    }

    @Override
    public Map<String, Object> ModifyGroup(Integer group_id, String group_name, MultipartFile group_path, String pre_path){
        Map<String, Object> res = new HashMap<>();
        String result;
        //예외처리
        if(group_id == null || (pre_path == null || pre_path.equals(""))){
            res.put("Message", "group_id와 pre_path는 null일 수 없습니다.");
            return res;
        }
        //그룹 이름만 변경했을 때
        if(group_path.isEmpty()){
            grouprepository.updateGroupName(group_id, group_name);
        }
        //이미지 파일만 변경했을 때
        else if(group_name == null || group_name.equals("")){
            result = PutFile(group_path, pre_path);
            if(result.equals("실패")){
                res.put("Message", "올바른 확장자가 아닙니다.");
            }
            grouprepository.updateGroupPath(group_id, result);
        }
        else{
            //그룹명과 파일 모두 변경했을 때
            result = PutFile(group_path, pre_path);
            grouprepository.updateGroup(group_id, group_name, result);
        }
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
        String oldFilename = oldpath +"/"+pre_file; //
        try{
            Files.copy(file.getInputStream(), path.resolve(newFilename));
            File f = new File(URLDecoder.decode(oldFilename, "UTF-8"));
            f.delete();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return newFilename;
    }

    @Override
    public Map<String, Object> GroupMember(Integer group_id){
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> member = grouprepository.findByMemeber(group_id);
        if(member.isEmpty()){
            System.out.println("비었음");
            res.put("Message", "group_id가 비었다.");
        }
//        member.forEach(s -> System.out.println(s.get("user_id")));
        res.put("Message","성공");
        res.put("Data", member);
        return res;
    }

    @Override
    public Map<String, Object> GroupJoin(Integer user_id, Integer group_id, String group_pw){
        Map<String, Object> res = new HashMap<>();
        //예외처리
        if(user_id == null || group_id == null || group_pw == null){
            res.put("Message", "null값이 존재합니다.");
        }
        //group_pw, cnt (해당 group_id 수), exist (group_id에 해당하는 user_id 존재 여부 (0:X/1:O))
        Map<String, Object> check = grouprepository.findByCheck(user_id, group_id);
        if(String.valueOf(check.get("cnt")).equals("0")){
            res.put("Message", "존재하지 않는 group_id 입니다.");
            return res;
        }
        if(String.valueOf(check.get("exist")).equals("1")){
            res.put("Message","이미 가입된 user_id 입니다.");
            return res;
        }
        if(!check.get("group_pw").equals(group_pw)){
            res.put("Message", "비밀번호가 동일하지 않습니다.");
            return res;
        }
        //필요하면 user_id가 존재하는지 여부도 체크!

        //모든 조건 충족 시 그룹 가입 진행
        grouprepository.InsertJoin(group_id, user_id);
        res.put("Message","성공");
        return res;
    }

    @Override
    public Map<String, Object> GroupDelete(Integer user_id, Integer group_id){
        Map<String, Object> res = new HashMap<>();
        //예외처리
        if(user_id == null || group_id == null){
            res.put("Message", "null 값이 존재합니다.");
            return res;
        }
        Map<String, Object> check = grouprepository.findByCheck(user_id, group_id);
        if(String.valueOf(check.get("exist")).equals("0")){
            res.put("Message","가입되지 않은 user_id 입니다.");
            return res;
        }
        else if(String.valueOf(check.get("group_id")).equals("0")){
            res.put("Message", "존재하지 않는 group_id 입니다.");
            return res;
        }
        //groupUser 삭제
        grouprepository.DeleteGroupUser(group_id, user_id);
        Integer exist = grouprepository.ExistGroup(group_id);
        //group 삭제
        if(exist == 0){
            grouprepository.DeleteGroup(group_id);
            File f = new File(URLDecoder.decode(path + "/" + check.get("group_path"), StandardCharsets.UTF_8));
            f.delete();
        }
        res.put("Message", "성공");
        return res;
    }
}
