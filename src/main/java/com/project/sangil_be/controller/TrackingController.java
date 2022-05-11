package com.project.sangil_be.controller;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TrackingController {
    private final TrackingService trackingService;

    // 트래킹 시작
    @PostMapping("/api/tracking/{mountainId}")
    public StartTrackingResponseDto startMyLocation (
            @PathVariable Long mountainId,
            @RequestBody StartTrackingRequestDto startTrackingRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return trackingService.startMyLocation(mountainId,startTrackingRequestDto, userDetails);
    }

    // 맵 트래킹 5초 마다 저장
    @PostMapping("/api/mountain/tracking/{completedId}")
    public DistanceResponseDto saveMyLocation (@PathVariable Long completedId,@RequestBody TrackingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return trackingService.saveMyLocation(completedId, requestDto, userDetails);
    }

    // 트래킹 완료 후 저장
    @PutMapping("/api/tracking/{completedId}")
    public String saveTracking(@PathVariable Long completedId, @RequestBody CompleteRequestDto completeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return trackingService.saveTracking(completedId,completeRequestDto,userDetails);
    }

    // 맵트래킹 삭제 (10분 이하)
    @DeleteMapping("/api/tracking/{completedId}")
    public String deleteTracking(@PathVariable Long completedId){
        return trackingService.deleteTracking(completedId);
    }

    // 맵 트래킹 상세페이지
    @GetMapping("/api/tracking/detail/{completedId}")
    public TrackingListDto detailTracking (@PathVariable Long completedId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return trackingService.detailTracking(completedId, userDetails);
    }

    // 맵트래킹 마이페이지
    @GetMapping("/api/mypages/tracking")
    public List<CompletedListDto> myPageTracking(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return trackingService.myPageTracking(userDetails);
    }
}
