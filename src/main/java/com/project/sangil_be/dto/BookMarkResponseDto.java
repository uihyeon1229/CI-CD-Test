package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkResponseDto {
    private Long mountainId;
    private String mountainName;
    private String mountainAddress;
    private String mountainImageUrl;
    private boolean bookMarkChk;
    private float starAvr;
    private Double distance;


    public BookMarkResponseDto(Mountain mountain, boolean bookMarkChk, float starAvr, Double distance) {
        this.mountainId = mountain.getMountainId();
        this.mountainName = mountain.getMountain();
        this.mountainAddress = mountain.getMountainAddress();
        this.mountainImageUrl = mountain.getMountainImgUrl();
        this.bookMarkChk = bookMarkChk;
        this.starAvr = starAvr;
        this.distance = distance;
    }

}
