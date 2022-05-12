package com.project.sangil_be.controller;

import com.project.sangil_be.dto.FeedListResponseDto;
import com.project.sangil_be.dto.FeedResponseDto;
import com.project.sangil_be.dto.GoodCheckResponseDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.FeedRepository;
import com.project.sangil_be.repository.GoodRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;
    private final FeedRepository feedRepository;

    //피드 작성
    @PostMapping("/api/feeds/write")
    public FeedResponseDto save(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("feedContent") String feedContent,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return feedService.saveFeed(feedContent, multipartFile, userDetails);
    }

    //피드 상세
    @GetMapping("api/feeds/detail/{feedId}")
    public FeedResponseDto detail(@PathVariable("feedId") Long feedId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return feedService.detail(feedId, user);
    }

    //나의 피드
    @GetMapping("api/myfeeds/{pageNum}")
    public FeedListResponseDto myfeeds (@PathVariable("pageNum") int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        return feedService.myfeeds(user, pageNum-1);
    }

    //피드 좋아요
    @PostMapping("/api/feeds/good/{feedId}")
    public GoodCheckResponseDto goodCheck(@PathVariable("feedId") Long feedId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return feedService.goodCheck(feedId, user);
    }

    //피드 삭제
    @Transactional
    @DeleteMapping("/api/feeds/delete/{feedId}")
    public void deleteFeed(@PathVariable("feedId") Long feedId){
        Optional<Feed> feed = feedRepository.findById(feedId);
        feedService.deletefeed(feedId, feed);
    }
}
