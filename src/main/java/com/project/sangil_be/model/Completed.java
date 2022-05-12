package com.project.sangil_be.model;

import com.project.sangil_be.dto.CompleteRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Completed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long mountainId;

    @Column(nullable = false)
    private Integer send;

    @Column(nullable = true)
    private Double totalDistance;

    @Column(nullable = true)
    private String totalTime;

    public Completed(Long mountainId, Integer send, Long userId) {
        this.mountainId =mountainId;
        this.send=send;
        this.userId=userId;
    }

    public void update(CompleteRequestDto completeRequestDto) {
        this.totalDistance=completeRequestDto.getTotalDistance();
        this.totalTime=completeRequestDto.getTotalTime();
    }
}
