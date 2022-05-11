package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PartyListResponseDto {
    private List<PartyListDto> partyList;
    private int totalPage;
    private int currentPage;

    public PartyListResponseDto(Page<PartyListDto> partyListDtoPage) {
        this.partyList = partyListDtoPage.getContent();
        this.totalPage = partyListDtoPage.getTotalPages();
        this.currentPage = partyListDtoPage.getNumber();
    }

}
