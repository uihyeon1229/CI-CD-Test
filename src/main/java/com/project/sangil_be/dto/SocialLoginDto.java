package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class SocialLoginDto {
    private String username;
    private String nickname;
    private Long socialId;

    public SocialLoginDto(String username, String nickname, Long socialId) {
        this.username = username;
        this.nickname = nickname;
        this.socialId = socialId;
    }
}
