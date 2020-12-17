package com.SAR.buildingdrawing.models;

public class ThreeDModel {
    String id,threeDUrl,twoDUrl,garage;
    int floor,area;

    public ThreeDModel() {
    }

    public ThreeDModel(String id, String threeDUrl, String twoDUrl,
                       int area, String garage, int floor) {
        this.id = id;
        this.threeDUrl = threeDUrl;
        this.twoDUrl = twoDUrl;
        this.area = area;
        this.garage = garage;
        this.floor = floor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreeDUrl() {
        return threeDUrl;
    }

    public void setThreeDUrl(String threeDUrl) {
        this.threeDUrl = threeDUrl;
    }

    public String getTwoDUrl() {
        return twoDUrl;
    }

    public void setTwoDUrl(String twoDUrl) {
        this.twoDUrl = twoDUrl;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
