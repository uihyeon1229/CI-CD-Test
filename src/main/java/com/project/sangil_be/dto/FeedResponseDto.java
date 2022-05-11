package com.project.sangil_be.dto;

import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.Good;
import com.project.sangil_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FeedResponseDto {

    private Long userId;

    private String username;

    private String userImgUrl;

    private String feedImgUrl;

    private String feedContent;

    private LocalDateTime createdAt;

    private int goodCnt;

    boolean goodStatus;

    public FeedResponseDto(User user, Feed feed, int l, boolean goodStatus) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.userImgUrl = user.getUserImgUrl();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.createdAt = feed.getCreatedAt();
        this.goodCnt = l;
        this. goodStatus = goodStatus;
    }

    public FeedResponseDto(Feed feed, int goodCnt, boolean goodStatus) {

        this.userId = feed.getUser().getUserId();
        this.username = feed.getUser().getUsername();
        this.userImgUrl = feed.getUser().getUserImgUrl();
        this.feedImgUrl = feed.getFeedImgUrl();
        this.feedContent = feed.getFeedContent();
        this.createdAt = feed.getCreatedAt();
        this.goodCnt = goodCnt;
        this. goodStatus = goodStatus;
    }
}
