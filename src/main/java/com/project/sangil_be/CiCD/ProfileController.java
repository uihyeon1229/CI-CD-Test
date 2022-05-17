package com.project.sangil_be.CiCD;

//import lombok.RequiredArgsConstructor;
//
//
//import org.springframework.core.env.Environment;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class ProfileController {
//    private final Environment env;
//    //테스트5
//
//    @GetMapping("/profile")
//    public String profile() {
//        List<String> profiles = Arrays.asList(env.getActiveProfiles());
//        List<String> realProfiles = Arrays.asList("real1", "real2");
//        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);
////테스트2
//        // real, real1, real2 중 하나라도 있으면 그 값 반환
//        return profiles.stream()
//                .filter(realProfiles::contains)
//                .findAny()
//                .orElse(defaultProfile);
//    }
//}

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
//테스트
@RestController
public class ProfileController {

    private final Environment env;

    public ProfileController(Environment env) {
        this.env = env;
    }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}