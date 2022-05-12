package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PlanListDto {
    private Long partyId;
    private String title;
    private LocalDateTime createdAt;
    private int maxPoeple;
    private int curPoeple;
    private LocalDate partyDate;
    private String msg;

    public PlanListDto(Party party,String msg) {
        this.partyId = party.getPartyId();
        this.title = party.getTitle();
        this.createdAt = party.getCreatedAt();
        this.maxPoeple = party.getMaxPeople();
        this.curPoeple = party.getCurPeople();
        this.partyDate = party.getPartyDate();
        this.msg = msg;
    }

}
