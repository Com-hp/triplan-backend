package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;

import java.util.List;
import java.util.Map;

public interface groupService {
    List<group> readGroup();
    List<groupDTO> getGroup(Integer user_id);
    Map<String, Object> CreateGroup(Integer user_id, String group_name, String group_pw);
}
