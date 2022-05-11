package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class XYTransferDto {

    private Double lat;
    private Double lng;

    public XYTransferDto(Double lat, Double lng) {
        this.lat=lat;
        this.lng=lng;
    }
}
