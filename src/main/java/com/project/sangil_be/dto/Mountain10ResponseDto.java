package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Getter;

@Getter
public class Mountain10ResponseDto {

    private Long mountainId;
    private String mountainName;
    private String mountainImgUrl;
    private String mountainAddress;
    private String starAvr;
    private boolean bookmark;
    private int bookMarkCnt;



    public Mountain10ResponseDto(Mountain mountain, String starAvr, Boolean bookmark, int bookMarkCnt) {
        this.mountainId = mountain.getMountainId();
        this.mountainName = mountain.getMountain();
        this.mountainImgUrl = mountain.getMountainImgUrl();
        this.mountainAddress = mountain.getMountainAddress();
        this.starAvr = starAvr;
        this.bookmark = bookmark;
        this.bookMarkCnt = bookMarkCnt;
    }
}
