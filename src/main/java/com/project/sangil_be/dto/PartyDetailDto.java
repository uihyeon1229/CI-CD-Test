package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PartyDetailDto {
    private Long partyId;
    private String username;
    private String userImgUrl;
    private String userTitle;
    private String title;
    private String mountain;
    private String address;
    private LocalDate partyDate;
    private String partyContent;
    private int maxPeople;
    private int curPeople;
    private LocalDateTime createdAt;
    private List<PartymemberDto> partymemberDto;

    public PartyDetailDto(Long partyId, String username, String userImageUrl,
                          String userTitle, String title, String mountain,
                          String address, LocalDate partyDate, int maxPeople,
                          int curPeople, String partyContent, LocalDateTime createdAt,List<PartymemberDto> partymemberDto) {
        this.partyId = partyId;
        this.username = username;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.createdAt = createdAt;
        this.partymemberDto = partymemberDto;
    }

    public PartyDetailDto(Long partyId, String username, String userImageUrl,
                          String userTitle, String title, String mountain,
                          String address, LocalDate partyDate, int maxPeople,
                          int curPeople, String partyContent, LocalDateTime createdAt) {

        this.partyId = partyId;
        this.username = username;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.createdAt = createdAt;
    }

}
