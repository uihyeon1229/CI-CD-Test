package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TwoPartyListResponseDto {

    private List<TwoPartyListDto> parties;

    public TwoPartyListResponseDto(List<TwoPartyListDto> partyListDtos) {

        this. parties = partyListDtos;
    }
}
