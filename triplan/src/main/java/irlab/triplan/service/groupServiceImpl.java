package irlab.triplan.service;

import irlab.triplan.DTO.groupDTO;
import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import irlab.triplan.repository.groupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void CreateGroup(Integer user_id, String group_name, String group_pw){
        grouprepository.CreateGroup(group_name, group_pw);
        Integer group_id = grouprepository.selectGroupId();
        groupuserrepository.createGroupUser(user_id, group_id);
    }
}
