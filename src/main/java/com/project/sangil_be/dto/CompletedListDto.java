package com.project.sangil_be.dto;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedListDto {
    private Long completedId;
    private String mountain;
    private Double lat;
    private Double lng;
    private Double totalDistance;
    private String totalTime;

    public CompletedListDto(Completed complete, Mountain mountain) {
        this.completedId = complete.getCompleteId();
        this.mountain = mountain.getMountain();
        this.lat = mountain.getLat();
        this.lng = mountain.getLng();
        this.totalDistance = complete.getTotalDistance();
        this.totalTime = complete.getTotalTime();
    }
}
