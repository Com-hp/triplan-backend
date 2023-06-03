package irlab.triplan.controller;

import irlab.triplan.DTO.userDefaultDTO;
import irlab.triplan.service.userDefaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class userDefaultController {
    private final userDefaultService userDefaultService;

    @GetMapping
    public List<userDefaultDTO> getDefaultImage(){
        return userDefaultService.getDefaultImage();
    }

}
