package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private boolean result;

    public ResponseDto(Boolean result) {
        this.result = result;
    }
}
