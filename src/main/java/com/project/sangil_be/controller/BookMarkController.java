package com.project.sangil_be.controller;

import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookMarkController {

    private final BookMarkService bookMarkService;

    // 산 즐겨찾기
    @PostMapping("/api/mountain/bookmark/{mountainId}")
    public String myBookMark(@PathVariable Long mountainId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookMarkService.myBookMark(mountainId, userDetails);
    }

}
