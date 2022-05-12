package com.project.sangil_be.controller;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    //동호회 모임 만들기
    @PostMapping("/api/party/write")
    public PartyListDto writeParty(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PartyRequestDto partyRequestDto) throws IOException {
        return partyService.writeParty(userDetails, partyRequestDto);
    }

    //모든 동호회 찾아오기
    @GetMapping("/api/parties/{pageNum}")
    public PartyListResponseDto getAllParty(@PathVariable int pageNum) {
        return new PartyListResponseDto(partyService.getAllParty(pageNum-1));
    }

    //동호회 상세 페이지
    @GetMapping("/api/party/{partyId}")
    public PartyDetailDto findParty (@PathVariable Long partyId) {
        return partyService.findParty(partyId);
    }

    //동호회 모임 참가하기
    @PostMapping("/api/party/attend/{partyId}")
    public String attendParty (@PathVariable Long partyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return partyService.attendParty(partyId, userDetails);
    }

    //동호회 내용 수정
    @PutMapping("/api/party/{partyId}")
    public PartyDetailDto updateParty (@PathVariable Long partyId, @RequestBody PartyRequestDto partyRequestDto) {
        return partyService.updateParty(partyId, partyRequestDto);
    }

    //동호회 내용 삭제
    @DeleteMapping("/api/party/{partyId}")
    public void deleteParty (@PathVariable Long partyId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        partyService.deleteParty(partyId,userDetails);
    }
}
