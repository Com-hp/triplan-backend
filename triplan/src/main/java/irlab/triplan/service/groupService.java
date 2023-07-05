package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface groupService {
    List<group> readGroup();
    List<groupDTO> getGroup(Integer user_id);
    Map<String, Object> CreateGroup(String group_name, String group_pw, Integer user_id, MultipartFile group_path);
    Map<String, Object> ModifyGroup(Integer group_id, String group_name, MultipartFile group_path, String pre_path);
    Map<String, Object> GroupMember(Integer group_id);
    Map<String, Object> SelectCode(String group_code);
    Map<String, Object> GroupJoin(Integer user_id, Integer group_id, String group_pw);
    Map<String, Object> GroupDelete(Integer user_id, Integer group_id);
}
