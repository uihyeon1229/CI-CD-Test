package com.project.sangil_be.utils;


import com.project.sangil_be.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Validator {


    public Page<PartyListDto> overPagesParty(List<PartyListDto> partiesList, int start, int end, Pageable pageable, int page) {
        Page<PartyListDto> pages = new PageImpl<>(partiesList.subList(start, end), pageable, partiesList.size());
        if(page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    public Page<FeedResponseDto> overPagesFeed(List<FeedResponseDto> feedsList, int start, int end, Pageable pageable, int page) {
        Page<FeedResponseDto> pages = new PageImpl<>(feedsList.subList(start, end), pageable, feedsList.size());
        if(page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    public void blankContent(PartyRequestDto partyRequestDto) {
        if(partyRequestDto.getPartyContent() == null){
            throw new IllegalArgumentException("내용을 입력해 주세요.");
        }
    }

    public void emptyMComment(MCommentRequestDto mCommentRequestDto) {
        if (mCommentRequestDto.getMountainComment() == null) {
            throw new IllegalArgumentException("댓글을 입력하세요");
        }
    }


}
