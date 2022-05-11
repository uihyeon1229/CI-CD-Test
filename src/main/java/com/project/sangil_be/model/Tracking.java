package com.project.sangil_be.model;


import com.project.sangil_be.dto.TrackingRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private Double distanceK;

    @Column(nullable = false)
    private Double distanceM;

    @Column(nullable = false)
    private Long completedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain100Id")
    private Mountain100 mountain100;

    public Tracking(Long completedId, TrackingRequestDto requestDto, User user) {
        this.completedId = completedId;
        this.lat = requestDto.getLat();
        this.lng = requestDto.getLng();
        this.user = user;
    }
}
