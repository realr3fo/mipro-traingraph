package com.mipro.traingraph.model;

import java.util.Objects;

public class TableRowEntry {
    private String stationShortCode;
    private String time;

    public TableRowEntry() {
    }

    public TableRowEntry(String stationShortCode, String hourMinuteTime) {
        this.stationShortCode = stationShortCode;
        this.time = hourMinuteTime;
    }

    public String getStationShortCode() {
        return this.stationShortCode;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String hourMinuteTime) {
        this.time = hourMinuteTime;
    }

    public TableRowEntry stationShortCode(String stationShortCode) {
        setStationShortCode(stationShortCode);
        return this;
    }

    public TableRowEntry hourMinuteTime(String hourMinuteTime) {
        setTime(hourMinuteTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TableRowEntry)) {
            return false;
        }
        TableRowEntry tableRowEntry = (TableRowEntry) o;
        return Objects.equals(stationShortCode, tableRowEntry.stationShortCode)
                && Objects.equals(time, tableRowEntry.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationShortCode, time);
    }

    @Override
    public String toString() {
        return "{" +
                " stationShortCode='" + getStationShortCode() + "'" +
                ", hourMinuteTime='" + getTime() + "'" +
                "}";
    }

}
