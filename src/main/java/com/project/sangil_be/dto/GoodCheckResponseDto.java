package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoodCheckResponseDto {

    boolean goodStatus;

    public GoodCheckResponseDto(boolean goodStatus) {

        this.goodStatus = goodStatus;
    }
}
