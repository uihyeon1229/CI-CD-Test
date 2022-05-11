package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingResponseDto {
    private Double lat;
    private Double lng;


    public TrackingResponseDto(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;

    }
}
