package com.project.sangil_be.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.User;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.GoogleUserService;
import com.project.sangil_be.service.KakaoUserService;
import com.project.sangil_be.service.NaverUserService;
import com.project.sangil_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;
    private final NaverUserService naverUserService;
    private final UserService userService;

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public SocialLoginDto kakaoLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code, response);
    }

    // 구글 로그인
    @GetMapping("/user/google/callback")
    public void googleLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws JsonProcessingException {
        googleUserService.googleLogin(code, response);
    }

    // 네이버 로그인
    @GetMapping("/user/naver/callback")
    public void naverLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response
    ) throws JsonProcessingException{
        naverUserService.naverLogin(code, state, response);
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseDto signup(@RequestBody SignUpRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    // 로그인 체크
    @GetMapping("/api/user/loginCheck")
    public UserResponseDto isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new UserResponseDto(userDetails);
    }
    //닉네임 중복체크
    @PostMapping("/api/mypages/usernameCheck")
    public String usernameCheck (@RequestBody UsernameRequestDto usernameRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.usernameCheck(usernameRequestDto,userDetails);
    }

    //username 수정
    @PutMapping("/api/mypages/profilename")
    public void editname(@RequestBody UsernameRequestDto usernameRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        userService.editname(usernameRequestDto, user);
    }

    //userimageUrl 수정
    @PutMapping("/api/mypages/profileUrl")
    public void editimage(@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        userService.editimage(multipartFile, user);
    }

    //마이페이지 즐겨찾기한 산
    @GetMapping("/api/mypages/bookmark")
    public List<BookMarkResponseDto> getBookMarkMountain (@RequestParam double lat,@RequestParam double lng,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getBookMarkMountain(lat,lng,userDetails);
    }


}