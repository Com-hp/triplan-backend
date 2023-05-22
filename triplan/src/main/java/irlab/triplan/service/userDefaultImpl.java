package irlab.triplan.service;

import irlab.triplan.DTO.userDefaultDTO;
import irlab.triplan.entity.userDefault;
import irlab.triplan.repository.userDefaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class userDefaultImpl implements userDefaultService{
    private final userDefaultRepository udr;
    @Override
    public List<userDefaultDTO> getDefaultImage(){
        List<userDefault> ud = udr.findAll();
        List<userDefaultDTO> udd = new ArrayList<>();
        ud.forEach(s -> udd.add(userDefaultDTO.toDto(s)));
        return udd;
    }
}
