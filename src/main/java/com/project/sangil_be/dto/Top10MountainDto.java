package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class Top10MountainDto {
    private Long mountainId;
    private String mountainName;
    private String mountainImgUrl;
    private String mountainAddress;
    private String starAvr;
    private boolean bookmark;

    public Top10MountainDto(Mountain10ResponseDto mountain10, String starAvr, boolean bookMark2) {
        this.mountainId=mountain10.getMountainId();
        this.mountainName=mountain10.getMountainName();
        this.mountainImgUrl=mountain10.getMountainImgUrl();
        this.mountainAddress=mountain10.getMountainAddress();
        this.starAvr=starAvr;
        this.bookmark=bookMark2;
    }
}
