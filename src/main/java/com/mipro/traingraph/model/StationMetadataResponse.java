package com.mipro.traingraph.model;

import java.util.Objects;

public class StationMetadataResponse {
    private boolean passengerTraffic;
    private String type;
    private String stationName;
    private String stationShortCode;
    private int stationUICCode;
    private String countryCode;
    private double longitude;
    private double latitude;

    public StationMetadataResponse() {
    }

    public StationMetadataResponse(boolean passengerTraffic, String type, String stationName, String stationShortCode,
            int stationUICCode, String countryCode, double longitude, double latitude) {
        this.passengerTraffic = passengerTraffic;
        this.type = type;
        this.stationName = stationName;
        this.stationShortCode = stationShortCode;
        this.stationUICCode = stationUICCode;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean isPassengerTraffic() {
        return this.passengerTraffic;
    }

    public boolean getPassengerTraffic() {
        return this.passengerTraffic;
    }

    public void setPassengerTraffic(boolean passengerTraffic) {
        this.passengerTraffic = passengerTraffic;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getStationUICCode() {
        return this.stationUICCode;
    }

    public void setStationUICCode(int stationUICCode) {
        this.stationUICCode = stationUICCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public StationMetadataResponse passengerTraffic(boolean passengerTraffic) {
        setPassengerTraffic(passengerTraffic);
        return this;
    }

    public StationMetadataResponse type(String type) {
        setType(type);
        return this;
    }

    public StationMetadataResponse stationName(String stationName) {
        setStationName(stationName);
        return this;
    }

    public StationMetadataResponse stationShortCode(String stationShortCode) {
        setStationShortCode(stationShortCode);
        return this;
    }

    public StationMetadataResponse stationUICCode(int stationUICCode) {
        setStationUICCode(stationUICCode);
        return this;
    }

    public StationMetadataResponse countryCode(String countryCode) {
        setCountryCode(countryCode);
        return this;
    }

    public StationMetadataResponse longitude(double longitude) {
        setLongitude(longitude);
        return this;
    }

    public StationMetadataResponse latitude(double latitude) {
        setLatitude(latitude);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StationMetadataResponse)) {
            return false;
        }
        StationMetadataResponse stationMetadataResponse = (StationMetadataResponse) o;
        return passengerTraffic == stationMetadataResponse.passengerTraffic
                && Objects.equals(type, stationMetadataResponse.type)
                && Objects.equals(stationName, stationMetadataResponse.stationName)
                && Objects.equals(stationShortCode, stationMetadataResponse.stationShortCode)
                && stationUICCode == stationMetadataResponse.stationUICCode
                && Objects.equals(countryCode, stationMetadataResponse.countryCode)
                && longitude == stationMetadataResponse.longitude && latitude == stationMetadataResponse.latitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerTraffic, type, stationName, stationShortCode, stationUICCode, countryCode,
                longitude, latitude);
    }

    @Override
    public String toString() {
        return "{" +
                " passengerTraffic='" + isPassengerTraffic() + "'" +
                ", type='" + getType() + "'" +
                ", stationName='" + getStationName() + "'" +
                ", stationShortCode='" + getStationShortCode() + "'" +
                ", stationUICCode='" + getStationUICCode() + "'" +
                ", countryCode='" + getCountryCode() + "'" +
                ", longitude='" + getLongitude() + "'" +
                ", latitude='" + getLatitude() + "'" +
                "}";
    }

    // getters and setters
}
