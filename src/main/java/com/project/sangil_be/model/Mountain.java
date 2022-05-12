package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Mountain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mountainId;

    @Column(nullable = false)
    private String mountain;

    @Column(nullable = false)
    private String mountainInfo;

    @Column(nullable = false)
    private String mountainImgUrl;

    @Column(nullable = false)
    private String mountainAddress;

    @Column(nullable = false)
    private Float height;

    @Column
    private Double lat;

    @Column
    private Double lng;

    public void update(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
