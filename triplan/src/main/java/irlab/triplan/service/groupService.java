package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;

import java.util.List;

public interface groupService {
    List<group> readGroup();
    List<groupDTO> getGroup(Integer user_id);
}
