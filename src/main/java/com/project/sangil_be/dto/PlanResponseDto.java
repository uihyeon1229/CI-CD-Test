package com.project.sangil_be.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlanResponseDto {
    private List<PlanListDto> plans;

    public PlanResponseDto(List<PlanListDto> planListDtos) {
        this.plans = planListDtos;
    }
}
