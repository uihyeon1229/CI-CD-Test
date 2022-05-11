package com.project.sangil_be.dto;

import com.project.sangil_be.model.User;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UserResponseDto {
    private Long userId;
    private String username;
    private String userImageUrl;
    private String userTitle;

    public UserResponseDto(UserDetailsImpl userDetails) {
        this.userId = userDetails.getUser().getUserId();
        this.username = userDetails.getUsername();
        this.userImageUrl = userDetails.getUser().getUserImgUrl();
        this.userTitle = userDetails.getUser().getUserTitle();
    }

}
