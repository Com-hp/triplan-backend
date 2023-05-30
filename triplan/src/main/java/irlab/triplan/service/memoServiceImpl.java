package irlab.triplan.service;

import irlab.triplan.DTO.*;
import irlab.triplan.entity.group;
import irlab.triplan.entity.memo;
import irlab.triplan.repository.memoRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class memoServiceImpl implements memoService{
    private final memoRepository memorepository;

    @Override
    @Transactional
    public List<memoDTO> getClass(Integer trip_id){
        List<memo> m = memorepository.findClass(trip_id);
        List<memoDTO> md = new ArrayList<>();
        m.forEach(s -> md.add(memoDTO.toDto(s)));
        return md;
    }

}
