package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Getter;

@Getter
public class Mountain100Dto {
    private Long mountainId;
    private String mountain;
    private String mountainImgUrl;
    private String mountainAddress;
    private Double lat;
    private Double lng;

    public Mountain100Dto(Mountain mountain) {
        this.mountainId=mountain.getMountainId();
        this.mountain=mountain.getMountain();
        this.mountainImgUrl=mountain.getMountainImgUrl();
        this.mountainAddress=mountain.getMountainAddress();
        this.lat = mountain.getLat();
        this.lng = mountain.getLng();
    }
}
