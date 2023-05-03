package irlab.triplan.service;

import irlab.triplan.DTO.tripDTO;
import irlab.triplan.entity.trip;
import irlab.triplan.repository.tripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class tripServiceImpl implements tripService{
    private final tripRepository triprepository;

    @Override
    public List<tripDTO> getGroupInTrip(Integer id){
        List<trip> t = triprepository.findGroupInTrip(id);
        List<tripDTO> td = new ArrayList<>();
        t.forEach(s -> td.add(tripDTO.toDto(s)));
        return td;
    }
}
