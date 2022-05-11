package com.project.sangil_be.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class NearbyMountainDto {
    private List<NearbyMountainListDto> nearbyMountainDtos;
    private int totalPage;
    private int currentPage;

    public NearbyMountainDto(Page<NearbyMountainListDto> page) {
        this.nearbyMountainDtos=page.getContent();
        this.totalPage=page.getTotalPages();
        this.currentPage=page.getNumber();
    }
}
