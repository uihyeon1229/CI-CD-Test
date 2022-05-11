package com.project.sangil_be.utils;

public class DistanceToUser {

    private final Double lat = 37.553877;
    private final Double lng = 126.971188;
//    private final Double MountainLat = 35.823212;
//    private final Double MountainLng = 128.114589;
//
//    //킬로미터(KiloMeter) 단위
//    public Double distanceKiloMeter = distance(lat, lng, MountainLat, MountainLng, "kilometer");

    /**
     * 두 지점간의 거리 계산
     * @param lat1 지점 1 위도
     * @param lng1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lng2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     **/
    public static double distance(double lat1, double lng1, double lat2, double lng2, String unit) {
        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist); dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }
        return (dist);
    }
    // This function converts decimal degrees to radians
     private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    // This function converts radians to decimal degrees
     private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}