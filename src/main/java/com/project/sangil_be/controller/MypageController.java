package com.project.sangil_be.controller;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.User;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.MypageService;
import com.project.sangil_be.service.TrackingService;
import com.project.sangil_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final TrackingService trackingService;
    private final UserService userService;
    private final MypageService mypageService;

    // 맵트래킹 마이페이지
    @GetMapping("/api/mypages/tracking")
    public List<CompletedListDto> myPageTracking(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return trackingService.myPageTracking(userDetails);
    }

    // 닉네임 중복체크
    @PostMapping("/api/mypages/usernameCheck")
    public String usernameCheck (@RequestBody UsernameRequestDto usernameRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.usernameCheck(usernameRequestDto,userDetails);
    }

    //username 수정
    @PutMapping("/api/mypages/profilename")
    public UserResponseDto editname(@RequestBody UsernameRequestDto usernameRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.editname(usernameRequestDto, userDetails);
    }

    //userimageUrl 수정
    @PutMapping("/api/mypages/profileUrl")
    public void editimage(@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        userService.editimage(multipartFile, user);
    }

    //마이페이지 즐겨찾기한 산
    @GetMapping("/api/mypages/bookmark")
    public List<BookMarkResponseDto> getBookMarkMountain (@RequestParam double lat, @RequestParam double lng, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getBookMarkMountain(lat,lng,userDetails);
    }

    // 칭호 리스트
    @GetMapping("/api/mypages/userTitle")
    public List<UserTitleDto> getUserTitle(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getUserTitle(userDetails);
    }

    // 칭호 변경
    @PutMapping("/api/mypages/userTitle")
    public ChangeTitleDto putUserTitle(@RequestBody ChangeTitleRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.putUserTitle(requestDto, userDetails);
    }
}
