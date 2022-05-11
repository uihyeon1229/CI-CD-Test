package com.project.sangil_be.dto;

import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain100;
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

    public CompletedListDto(Completed complete, Mountain100 mountain100) {
        this.completedId = complete.getCompleteId();
        this.mountain = mountain100.getMountain();
        this.lat = mountain100.getLat();
        this.lng = mountain100.getLng();
        this.totalDistance = complete.getTotalDistance();
        this.totalTime = complete.getTotalTime();
    }
}
