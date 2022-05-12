package com.project.sangil_be.dto;

import com.project.sangil_be.model.Party;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class PartyListDto {
    private Long partyId;
    private String nickname;
    private String title;
    private String partyContent;
    private String mountain;
    private String address;
    private LocalDate partyDate;
    private LocalTime partyTime;
    private int maxPeople;
    private int curPeople;
    private boolean completed;
    private LocalDateTime createdAt;

    public PartyListDto(Party party, boolean completed) {
        this.partyId = party.getPartyId();
        this.nickname = party.getUser().getNickname();
        this.title = party.getTitle();
        this.partyContent = party.getPartyContent();
        this.mountain = party.getMountain();
        this.address = party.getAddress();
        this.partyDate = party.getPartyDate();
        this.partyTime = party.getPartyTime();
        this.maxPeople = party.getMaxPeople();
        this.curPeople = party.getCurPeople();
        this.completed = completed;
        this.createdAt = party.getCreatedAt();
    }
}
