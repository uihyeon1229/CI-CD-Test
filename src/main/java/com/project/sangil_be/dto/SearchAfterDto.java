package com.project.sangil_be.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SearchAfterDto {
    private List<SearchDto> searchList;
    private int totalPage;
    private int currentPage;

    public SearchAfterDto(Page<SearchDto> searhMountain) {
        this.searchList = searhMountain.getContent();
        this.totalPage = searhMountain.getTotalPages();
        this.currentPage = searhMountain.getNumber();
    }
}
