package com.mipro.traingraph.model;

import java.util.List;
import java.util.Objects;

public class Train {
    private long trainNumber;
    private String departureDate;
    private int operatorUICCode;
    private String operatorShortCode;
    private String trainType;
    private String trainCategory;
    private String commuterLineID;
    private boolean runningCurrently;
    private boolean cancelled;
    private boolean deleted;
    private long version;
    private String timetableType;
    private String timetableAcceptanceDate;
    private List<TimeTableRow> timeTableRows;
    private TrainReady trainReady;

    // Getters and setters

    public Train() {
    }

    public Train(long trainNumber, String departureDate, int operatorUICCode, String operatorShortCode,
            String trainType, String trainCategory, String commuterLineID, boolean runningCurrently, boolean cancelled,
            boolean deleted, long version, String timetableType, String timetableAcceptanceDate,
            List<TimeTableRow> timeTableRows, TrainReady trainReady) {
        this.trainNumber = trainNumber;
        this.departureDate = departureDate;
        this.operatorUICCode = operatorUICCode;
        this.operatorShortCode = operatorShortCode;
        this.trainType = trainType;
        this.trainCategory = trainCategory;
        this.commuterLineID = commuterLineID;
        this.runningCurrently = runningCurrently;
        this.cancelled = cancelled;
        this.deleted = deleted;
        this.version = version;
        this.timetableType = timetableType;
        this.timetableAcceptanceDate = timetableAcceptanceDate;
        this.timeTableRows = timeTableRows;
        this.trainReady = trainReady;
    }

    public long getTrainNumber() {
        return this.trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public int getOperatorUICCode() {
        return this.operatorUICCode;
    }

    public void setOperatorUICCode(int operatorUICCode) {
        this.operatorUICCode = operatorUICCode;
    }

    public String getOperatorShortCode() {
        return this.operatorShortCode;
    }

    public void setOperatorShortCode(String operatorShortCode) {
        this.operatorShortCode = operatorShortCode;
    }

    public String getTrainType() {
        return this.trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainCategory() {
        return this.trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public String getCommuterLineID() {
        return this.commuterLineID;
    }

    public void setCommuterLineID(String commuterLineID) {
        this.commuterLineID = commuterLineID;
    }

    public boolean isRunningCurrently() {
        return this.runningCurrently;
    }

    public boolean getRunningCurrently() {
        return this.runningCurrently;
    }

    public void setRunningCurrently(boolean runningCurrently) {
        this.runningCurrently = runningCurrently;
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

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getTimetableType() {
        return this.timetableType;
    }

    public void setTimetableType(String timetableType) {
        this.timetableType = timetableType;
    }

    public String getTimetableAcceptanceDate() {
        return this.timetableAcceptanceDate;
    }

    public void setTimetableAcceptanceDate(String timetableAcceptanceDate) {
        this.timetableAcceptanceDate = timetableAcceptanceDate;
    }

    public List<TimeTableRow> getTimeTableRows() {
        return this.timeTableRows;
    }

    public void setTimeTableRows(List<TimeTableRow> timeTableRows) {
        this.timeTableRows = timeTableRows;
    }

    public TrainReady getTrainReady() {
        return this.trainReady;
    }

    public void setTrainReady(TrainReady trainReady) {
        this.trainReady = trainReady;
    }

    public Train trainNumber(long trainNumber) {
        setTrainNumber(trainNumber);
        return this;
    }

    public Train departureDate(String departureDate) {
        setDepartureDate(departureDate);
        return this;
    }

    public Train operatorUICCode(int operatorUICCode) {
        setOperatorUICCode(operatorUICCode);
        return this;
    }

    public Train operatorShortCode(String operatorShortCode) {
        setOperatorShortCode(operatorShortCode);
        return this;
    }

    public Train trainType(String trainType) {
        setTrainType(trainType);
        return this;
    }

    public Train trainCategory(String trainCategory) {
        setTrainCategory(trainCategory);
        return this;
    }

    public Train commuterLineID(String commuterLineID) {
        setCommuterLineID(commuterLineID);
        return this;
    }

    public Train runningCurrently(boolean runningCurrently) {
        setRunningCurrently(runningCurrently);
        return this;
    }

    public Train cancelled(boolean cancelled) {
        setCancelled(cancelled);
        return this;
    }

    public Train deleted(boolean deleted) {
        setDeleted(deleted);
        return this;
    }

    public Train version(long version) {
        setVersion(version);
        return this;
    }

    public Train timetableType(String timetableType) {
        setTimetableType(timetableType);
        return this;
    }

    public Train timetableAcceptanceDate(String timetableAcceptanceDate) {
        setTimetableAcceptanceDate(timetableAcceptanceDate);
        return this;
    }

    public Train timeTableRows(List<TimeTableRow> timeTableRows) {
        setTimeTableRows(timeTableRows);
        return this;
    }

    public Train trainReady(TrainReady trainReady) {
        setTrainReady(trainReady);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Train)) {
            return false;
        }
        Train train = (Train) o;
        return trainNumber == train.trainNumber && Objects.equals(departureDate, train.departureDate)
                && operatorUICCode == train.operatorUICCode
                && Objects.equals(operatorShortCode, train.operatorShortCode)
                && Objects.equals(trainType, train.trainType) && Objects.equals(trainCategory, train.trainCategory)
                && Objects.equals(commuterLineID, train.commuterLineID) && runningCurrently == train.runningCurrently
                && cancelled == train.cancelled && deleted == train.deleted && version == train.version
                && Objects.equals(timetableType, train.timetableType)
                && Objects.equals(timetableAcceptanceDate, train.timetableAcceptanceDate)
                && Objects.equals(timeTableRows, train.timeTableRows) && Objects.equals(trainReady, train.trainReady);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainNumber, departureDate, operatorUICCode, operatorShortCode, trainType, trainCategory,
                commuterLineID, runningCurrently, cancelled, deleted, version, timetableType, timetableAcceptanceDate,
                timeTableRows, trainReady);
    }

    @Override
    public String toString() {
        return "{" +
                " trainNumber='" + getTrainNumber() + "'" +
                ", departureDate='" + getDepartureDate() + "'" +
                ", operatorUICCode='" + getOperatorUICCode() + "'" +
                ", operatorShortCode='" + getOperatorShortCode() + "'" +
                ", trainType='" + getTrainType() + "'" +
                ", trainCategory='" + getTrainCategory() + "'" +
                ", commuterLineID='" + getCommuterLineID() + "'" +
                ", runningCurrently='" + isRunningCurrently() + "'" +
                ", cancelled='" + isCancelled() + "'" +
                ", deleted='" + isDeleted() + "'" +
                ", version='" + getVersion() + "'" +
                ", timetableType='" + getTimetableType() + "'" +
                ", timetableAcceptanceDate='" + getTimetableAcceptanceDate() + "'" +
                ", timeTableRows='" + getTimeTableRows() + "'" +
                ", trainReady='" + getTrainReady() + "'" +
                "}";
    }

}