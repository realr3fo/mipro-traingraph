package com.mipro.traingraph.model;

import java.util.List;
import java.util.Objects;

public class TimeTableRow {
    private String stationShortCode;
    private int stationUICCode;
    private String countryCode;
    private String type;
    private boolean trainStopping;
    private boolean commercialStop;
    private String commercialTrack;
    private boolean cancelled;
    private String scheduledTime;
    private String liveEstimateTime;
    private String estimateSource;
    private boolean unknownDelay;
    private String actualTime;
    private long differenceInMinutes;
    private List<Cause> causes;

    //

    public TimeTableRow() {
    }

    public TimeTableRow(String stationShortCode, int stationUICCode, String countryCode, String type,
            boolean trainStopping, boolean commercialStop, String commercialTrack, boolean cancelled,
            String scheduledTime, String liveEstimateTime, String estimateSource, boolean unknownDelay,
            String actualTime, long differenceInMinutes, List<Cause> causes) {
        this.stationShortCode = stationShortCode;
        this.stationUICCode = stationUICCode;
        this.countryCode = countryCode;
        this.type = type;
        this.trainStopping = trainStopping;
        this.commercialStop = commercialStop;
        this.commercialTrack = commercialTrack;
        this.cancelled = cancelled;
        this.scheduledTime = scheduledTime;
        this.liveEstimateTime = liveEstimateTime;
        this.estimateSource = estimateSource;
        this.unknownDelay = unknownDelay;
        this.actualTime = actualTime;
        this.differenceInMinutes = differenceInMinutes;
        this.causes = causes;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTrainStopping() {
        return this.trainStopping;
    }

    public boolean getTrainStopping() {
        return this.trainStopping;
    }

    public void setTrainStopping(boolean trainStopping) {
        this.trainStopping = trainStopping;
    }

    public boolean isCommercialStop() {
        return this.commercialStop;
    }

    public boolean getCommercialStop() {
        return this.commercialStop;
    }

    public void setCommercialStop(boolean commercialStop) {
        this.commercialStop = commercialStop;
    }

    public String getCommercialTrack() {
        return this.commercialTrack;
    }

    public void setCommercialTrack(String commercialTrack) {
        this.commercialTrack = commercialTrack;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getScheduledTime() {
        return this.scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getLiveEstimateTime() {
        return this.liveEstimateTime;
    }

    public void setLiveEstimateTime(String liveEstimateTime) {
        this.liveEstimateTime = liveEstimateTime;
    }

    public String getEstimateSource() {
        return this.estimateSource;
    }

    public void setEstimateSource(String estimateSource) {
        this.estimateSource = estimateSource;
    }

    public boolean isUnknownDelay() {
        return this.unknownDelay;
    }

    public boolean getUnknownDelay() {
        return this.unknownDelay;
    }

    public void setUnknownDelay(boolean unknownDelay) {
        this.unknownDelay = unknownDelay;
    }

    public String getActualTime() {
        return this.actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public long getDifferenceInMinutes() {
        return this.differenceInMinutes;
    }

    public void setDifferenceInMinutes(long differenceInMinutes) {
        this.differenceInMinutes = differenceInMinutes;
    }

    public List<Cause> getCauses() {
        return this.causes;
    }

    public void setCauses(List<Cause> causes) {
        this.causes = causes;
    }

    public TimeTableRow stationShortCode(String stationShortCode) {
        setStationShortCode(stationShortCode);
        return this;
    }

    public TimeTableRow stationUICCode(int stationUICCode) {
        setStationUICCode(stationUICCode);
        return this;
    }

    public TimeTableRow countryCode(String countryCode) {
        setCountryCode(countryCode);
        return this;
    }

    public TimeTableRow type(String type) {
        setType(type);
        return this;
    }

    public TimeTableRow trainStopping(boolean trainStopping) {
        setTrainStopping(trainStopping);
        return this;
    }

    public TimeTableRow commercialStop(boolean commercialStop) {
        setCommercialStop(commercialStop);
        return this;
    }

    public TimeTableRow commercialTrack(String commercialTrack) {
        setCommercialTrack(commercialTrack);
        return this;
    }

    public TimeTableRow cancelled(boolean cancelled) {
        setCancelled(cancelled);
        return this;
    }

    public TimeTableRow scheduledTime(String scheduledTime) {
        setScheduledTime(scheduledTime);
        return this;
    }

    public TimeTableRow liveEstimateTime(String liveEstimateTime) {
        setLiveEstimateTime(liveEstimateTime);
        return this;
    }

    public TimeTableRow estimateSource(String estimateSource) {
        setEstimateSource(estimateSource);
        return this;
    }

    public TimeTableRow unknownDelay(boolean unknownDelay) {
        setUnknownDelay(unknownDelay);
        return this;
    }

    public TimeTableRow actualTime(String actualTime) {
        setActualTime(actualTime);
        return this;
    }

    public TimeTableRow differenceInMinutes(long differenceInMinutes) {
        setDifferenceInMinutes(differenceInMinutes);
        return this;
    }

    public TimeTableRow causes(List<Cause> causes) {
        setCauses(causes);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TimeTableRow)) {
            return false;
        }
        TimeTableRow timeTableRow = (TimeTableRow) o;
        return Objects.equals(stationShortCode, timeTableRow.stationShortCode)
                && stationUICCode == timeTableRow.stationUICCode
                && Objects.equals(countryCode, timeTableRow.countryCode) && Objects.equals(type, timeTableRow.type)
                && trainStopping == timeTableRow.trainStopping && commercialStop == timeTableRow.commercialStop
                && Objects.equals(commercialTrack, timeTableRow.commercialTrack) && cancelled == timeTableRow.cancelled
                && Objects.equals(scheduledTime, timeTableRow.scheduledTime)
                && Objects.equals(liveEstimateTime, timeTableRow.liveEstimateTime)
                && Objects.equals(estimateSource, timeTableRow.estimateSource)
                && unknownDelay == timeTableRow.unknownDelay && Objects.equals(actualTime, timeTableRow.actualTime)
                && differenceInMinutes == timeTableRow.differenceInMinutes
                && Objects.equals(causes, timeTableRow.causes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationShortCode, stationUICCode, countryCode, type, trainStopping, commercialStop,
                commercialTrack, cancelled, scheduledTime, liveEstimateTime, estimateSource, unknownDelay, actualTime,
                differenceInMinutes, causes);
    }

    @Override
    public String toString() {
        return "{" +
                " stationShortCode='" + getStationShortCode() + "'" +
                ", stationUICCode='" + getStationUICCode() + "'" +
                ", countryCode='" + getCountryCode() + "'" +
                ", type='" + getType() + "'" +
                ", trainStopping='" + isTrainStopping() + "'" +
                ", commercialStop='" + isCommercialStop() + "'" +
                ", commercialTrack='" + getCommercialTrack() + "'" +
                ", cancelled='" + isCancelled() + "'" +
                ", scheduledTime='" + getScheduledTime() + "'" +
                ", liveEstimateTime='" + getLiveEstimateTime() + "'" +
                ", estimateSource='" + getEstimateSource() + "'" +
                ", unknownDelay='" + isUnknownDelay() + "'" +
                ", actualTime='" + getActualTime() + "'" +
                ", differenceInMinutes='" + getDifferenceInMinutes() + "'" +
                ", causes='" + getCauses() + "'" +
                "}";
    }

}