package com.project.sangil_be.utils;

import lombok.Getter;

@Getter
public class Location {

    private Double lat;
    private Double lng;

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
