package irlab.triplan.service;

import irlab.triplan.DTO.categoryDTO;
import irlab.triplan.DTO.memoDTO;
import irlab.triplan.DTO.tripDTO;
import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.memo;
import irlab.triplan.repository.memoRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class memoServiceImpl implements memoService{
    private final memoRepository memorepository;

}
