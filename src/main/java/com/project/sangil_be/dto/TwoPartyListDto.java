package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TwoPartyListDto {

    private String title;
    private LocalDate partyDate;
    private int maxPeople;
    private int curPeople;
    private LocalDateTime createdAt;

    public TwoPartyListDto(Party party) {
        this.title=party.getTitle();
        this.partyDate = party.getPartyDate();
        this.maxPeople = party.getMaxPeople();
        this.curPeople = party.getCurPeople();
        this.createdAt = party.getCreatedAt();
    }
}
