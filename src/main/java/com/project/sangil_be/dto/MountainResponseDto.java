package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain100;
import lombok.Getter;

import java.util.List;

@Getter
public class MountainResponseDto {
    private Long mountainId;
    private String mountain;
    private int weather;
    private String weatherImgUrl;
    private String mountainImgUrl;
    private String mountainAddress;
    private String mountainInfo;
    private Float height;
    private String starAvr;
    private List<CourseListDto> courseLists;
    private CommentDto commentDto;

    public MountainResponseDto(Mountain100 mountain100, WeatherDto weatherDto, String starAvr, List<CourseListDto> courseLists, CommentDto commentDto) {
        this.mountainId = mountain100.getMountain100Id();
        this.mountain = mountain100.getMountain();
        this.weather = weatherDto.getWeather();
        this.weatherImgUrl = weatherDto.getWeatherImageUrl();
        this.mountainImgUrl = mountain100.getMountainImgUrl();
        this.mountainAddress = mountain100.getMountainAddress();
        this.mountainInfo = mountain100.getMountainInfo();
        this.height = mountain100.getHeight();
        this.starAvr = starAvr;
        this.courseLists = courseLists;
        this.commentDto = commentDto;
    }
}
