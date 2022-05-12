package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchDto {

    private Long mountainId;
    private String mountain;
    private String mountainAddress;
    private String mountainImgUrl;
    private String starAvr;
    private Double lat;
    private Double lng;

    public SearchDto(Long mountainId, String mountain, String mountainAddress, String mountainImgUrl, String starAvr, Double lat, Double lng) {
        this.mountainId = mountainId;
        this.mountain = mountain;
        this.mountainAddress = mountainAddress;
        this.mountainImgUrl = mountainImgUrl;
        this.starAvr = starAvr;
        this.lat = lat;
        this.lng = lng;
    }

    public SearchDto(String starAvr, Mountain100Dto mountain100Dto) {
        this.mountainId=mountain100Dto.getMountainId();
        this.mountain=mountain100Dto.getMountain();
        this.mountainAddress=mountain100Dto.getMountainAddress();
        this.mountainImgUrl=mountain100Dto.getMountainImgUrl();
        this.starAvr=starAvr;
        this.lat=mountain100Dto.getLat();
        this.lng=mountain100Dto.getLng();
    }
}

