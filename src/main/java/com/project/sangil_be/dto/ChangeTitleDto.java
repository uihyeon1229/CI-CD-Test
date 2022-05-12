package com.project.sangil_be.dto;

import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

@Getter
public class ChangeTitleDto {
    private Long userId;
    private String userTitle;

    public ChangeTitleDto(UserDetailsImpl userDetails, ChangeTitleRequestDto requestDto) {
        this.userId=userDetails.getUser().getUserId();
        this.userTitle= requestDto.getUserTitle();
    }
}
