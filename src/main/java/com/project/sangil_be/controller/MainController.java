package com.project.sangil_be.controller;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MainService mainService;

    // 메인/마이페이지 예정된 등산 모임 임박한 순
    @GetMapping("/api/plan")
    public PlanResponseDto getPlan(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mainService.getPlan(userDetails);
    }

    // 메인페이지 최신 2개 모임
    @GetMapping("/api/main/parties")
    public TwoPartyListResponseDto getTwoParty(){
        return mainService.getTwoParty();
    }

    // 메인페이지 북마크 순으로 탑10
    @GetMapping("/api/main/mountains")
    public List<Top10MountainDto> get10Mountains(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mainService.get10Mountains(userDetails);
    }

    // 메인 페이지 피드 15개
    @GetMapping("/api/main/feeds/{pageNum}")
    public FeedListResponseDto mainfeeds (@PathVariable("pageNum")int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mainService.mainfeeds(pageNum-1,userDetails);
    }

    // 자기 주변 산
    @GetMapping("/api/main/nearby/{pageNum}")
    public NearbyMountainDto nearby(@RequestParam double lat,@RequestParam double lng, @PathVariable("pageNum")int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mainService.nearby(lat,lng,pageNum-1,userDetails);
    }
}
