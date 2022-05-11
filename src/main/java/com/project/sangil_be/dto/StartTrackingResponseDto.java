package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class StartTrackingResponseDto {
    private Long completedId;

    public StartTrackingResponseDto(Long completeId) {
        this.completedId=completeId;
    }
}
