package com.project.sangil_be.dto;

import com.project.sangil_be.model.User;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String userImageUrl;
    private String userTitle;

    public UserResponseDto(UserDetailsImpl userDetails) {
        this.userId = userDetails.getUser().getUserId();
        this.nickname = userDetails.getNickname();
        this.userImageUrl = userDetails.getUser().getUserImgUrl();
        this.userTitle = userDetails.getUser().getUserTitle();
    }

    public UserResponseDto(User user) {
        this.nickname=user.getNickname();
    }
}
