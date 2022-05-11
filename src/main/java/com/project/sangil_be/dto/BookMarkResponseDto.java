package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain100;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkResponseDto {
    private Long mountain100Id;
    private String mountainName;
    private String mountainAddress;
    private String mountainImageUrl;
    private boolean bookMarkChk;
    private float starAvr;
    private Double distance;


    public BookMarkResponseDto(Mountain100 mountain100, boolean bookMarkChk, float starAvr, Double distance) {
        this.mountain100Id = mountain100.getMountain100Id();
        this.mountainName = mountain100.getMountain();
        this.mountainAddress = mountain100.getMountainAddress();
        this.mountainImageUrl = mountain100.getMountainImgUrl();
        this.bookMarkChk = bookMarkChk;
        this.starAvr = starAvr;
        this.distance = distance;
    }

    public BookMarkResponseDto(String mountain100) {
        this.mountainName=mountain100;
    }
}
