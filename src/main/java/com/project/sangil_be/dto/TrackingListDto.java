package com.project.sangil_be.dto;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrackingListDto {
    private Long userId;
    private String username;
    private String userTitle;
    private String userTitleImgUrl;
    private Long completedId;
    private String mountain;
    private Double totalDistance;
    private String totalTime;
    private List<TrackingResponseDto> trackingList;

    public TrackingListDto(UserDetailsImpl userDetails, Long completedId, Mountain100 mountain100, Completed completed, List<TrackingResponseDto> trackingResponseDtoList) {
        this.userId = userDetails.getUser().getUserId();
        this.username = userDetails.getUser().getUsername();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.userTitleImgUrl =userDetails.getUser().getUserTitleImgUrl();
        this.completedId = completedId;
        this.mountain = mountain100.getMountain();
        this.totalDistance=completed.getTotalDistance();
        this.totalTime=completed.getTotalTime();
        this.trackingList = trackingResponseDtoList;
    }
}
