package com.project.sangil_be.CiCD;

import com.project.sangil_be.dto.FeedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    @GetMapping("/test3")
            public String test(){
        return "HI";
    }

}
