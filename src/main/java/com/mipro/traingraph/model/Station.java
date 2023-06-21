package com.mipro.traingraph.model;

import java.util.Objects;

public class Station {
    private String stationName;
    private String stationShortCode;
    private double longitude;
    private double latitude;

    // getters and setters

    public Station() {
    }

    public Station(String stationName, String stationShortCode, double longitude, double latitude) {
        this.stationName = stationName;
        this.stationShortCode = stationShortCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getStationName() {
        return this.stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationShortCode() {
        return this.stationShortCode;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Station stationName(String stationName) {
        setStationName(stationName);
        return this;
    }

    public Station stationShortCode(String stationShortCode) {
        setStationShortCode(stationShortCode);
        return this;
    }

    public Station longitude(double longitude) {
        setLongitude(longitude);
        return this;
    }

    public Station latitude(double latitude) {
        setLatitude(latitude);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Station)) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(stationName, station.stationName)
                && Objects.equals(stationShortCode, station.stationShortCode) && longitude == station.longitude
                && latitude == station.latitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName, stationShortCode, longitude, latitude);
    }

    @Override
    public String toString() {
        return "{" +
                " stationName='" + getStationName() + "'" +
                ", stationShortCode='" + getStationShortCode() + "'" +
                ", longitude='" + getLongitude() + "'" +
                ", latitude='" + getLatitude() + "'" +
                "}";
    }

}
