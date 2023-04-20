package irlab.triplan.service;

import irlab.triplan.entity.group;
import irlab.triplan.repository.groupReporitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //final 필드 값을 파라미터로 받는 생성자 생성
public class groupServiceImpl implements groupService{
    private final groupReporitory grouprepository;

    @Override
    public List<group> readGroup(){
        return grouprepository.findAll();
    }
}
