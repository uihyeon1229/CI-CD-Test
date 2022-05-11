package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchDto {

    private Long mountain100Id;
    private String mountain;
    private String mountainAddress;
    private String mountainImgUrl;
    private String starAvr;
    private Double lat;
    private Double lng;

    public SearchDto(Long mountain100Id, String mountain, String mountainAddress, String mountainImgUrl, String starAvr, Double lat, Double lng) {
        this.mountain100Id = mountain100Id;
        this.mountain = mountain;
        this.mountainAddress = mountainAddress;
        this.mountainImgUrl = mountainImgUrl;
        this.starAvr = starAvr;
        this.lat = lat;
        this.lng = lng;
    }
}

