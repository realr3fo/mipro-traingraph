package com.mipro.traingraph.model;

import java.util.List;
import java.util.Objects;

public class CustomLiveTrain {
    private long trainNumber;
    private List<String> scheduledStationCodes;
    private List<String> actualStationCodes;
    private List<String> scheduledTimes;
    private List<String> actualTimes;
    private String trainColor;

    public CustomLiveTrain() {
    }

    public CustomLiveTrain(long trainNumber, List<String> scheduledStationCodes, List<String> actualStationCodes,
            List<String> scheduledTimes, List<String> actualTimes, String trainColor) {
        this.trainNumber = trainNumber;
        this.scheduledStationCodes = scheduledStationCodes;
        this.actualStationCodes = actualStationCodes;
        this.scheduledTimes = scheduledTimes;
        this.actualTimes = actualTimes;
        this.trainColor = trainColor;
    }

    public long getTrainNumber() {
        return this.trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<String> getScheduledStationCodes() {
        return this.scheduledStationCodes;
    }

    public void setScheduledStationCodes(List<String> scheduledStationCodes) {
        this.scheduledStationCodes = scheduledStationCodes;
    }

    public List<String> getActualStationCodes() {
        return this.actualStationCodes;
    }

    public void setActualStationCodes(List<String> actualStationCodes) {
        this.actualStationCodes = actualStationCodes;
    }

    public List<String> getScheduledTimes() {
        return this.scheduledTimes;
    }

    public void setScheduledTimes(List<String> scheduledTimes) {
        this.scheduledTimes = scheduledTimes;
    }

    public List<String> getActualTimes() {
        return this.actualTimes;
    }

    public void setActualTimes(List<String> actualTimes) {
        this.actualTimes = actualTimes;
    }

    public String getTrainColor() {
        return this.trainColor;
    }

    public void setTrainColor(String trainColor) {
        this.trainColor = trainColor;
    }

    public CustomLiveTrain trainNumber(long trainNumber) {
        setTrainNumber(trainNumber);
        return this;
    }

    public CustomLiveTrain scheduledStationCodes(List<String> scheduledStationCodes) {
        setScheduledStationCodes(scheduledStationCodes);
        return this;
    }

    public CustomLiveTrain actualStationCodes(List<String> actualStationCodes) {
        setActualStationCodes(actualStationCodes);
        return this;
    }

    public CustomLiveTrain scheduledTimes(List<String> scheduledTimes) {
        setScheduledTimes(scheduledTimes);
        return this;
    }

    public CustomLiveTrain actualTimes(List<String> actualTimes) {
        setActualTimes(actualTimes);
        return this;
    }

    public CustomLiveTrain trainColor(String trainColor) {
        setTrainColor(trainColor);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CustomLiveTrain)) {
            return false;
        }
        CustomLiveTrain customLiveTrain = (CustomLiveTrain) o;
        return trainNumber == customLiveTrain.trainNumber
                && Objects.equals(scheduledStationCodes, customLiveTrain.scheduledStationCodes)
                && Objects.equals(actualStationCodes, customLiveTrain.actualStationCodes)
                && Objects.equals(scheduledTimes, customLiveTrain.scheduledTimes)
                && Objects.equals(actualTimes, customLiveTrain.actualTimes)
                && Objects.equals(trainColor, customLiveTrain.trainColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainNumber, scheduledStationCodes, actualStationCodes, scheduledTimes, actualTimes,
                trainColor);
    }

    @Override
    public String toString() {
        return "{" +
                " trainNumber='" + getTrainNumber() + "'" +
                ", scheduledStationCodes='" + getScheduledStationCodes() + "'" +
                ", actualStationCodes='" + getActualStationCodes() + "'" +
                ", scheduledTimes='" + getScheduledTimes() + "'" +
                ", actualTimes='" + getActualTimes() + "'" +
                ", trainColor='" + getTrainColor() + "'" +
                "}";
    }

}
