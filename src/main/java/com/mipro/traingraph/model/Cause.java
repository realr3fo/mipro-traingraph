package com.mipro.traingraph.model;

import java.util.List;
import java.util.Objects;

public class Cause {
    private String description;
    private PassengerTerm passengerTerm;
    private List<String> categoryCode;
    private List<String> categoryName;
    private List<String> validFrom;
    private List<String> validTo;
    private List<String> id;
    private List<String> detailedCategoryCode;
    private List<String> detailedCategoryName;
    private List<String> thirdCategoryCode;
    private List<String> thirdCategoryName;
    private List<String> categoryCodeId;
    private List<String> thirdCategoryCodeId;
    private List<String> detailedCategoryCodeId;

    // Getters and setters

    public Cause() {
    }

    public Cause(String description, PassengerTerm passengerTerm, List<String> categoryCode, List<String> categoryName,
            List<String> validFrom, List<String> validTo, List<String> id, List<String> detailedCategoryCode,
            List<String> detailedCategoryName, List<String> thirdCategoryCode, List<String> thirdCategoryName,
            List<String> categoryCodeId, List<String> thirdCategoryCodeId, List<String> detailedCategoryCodeId) {
        this.description = description;
        this.passengerTerm = passengerTerm;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.id = id;
        this.detailedCategoryCode = detailedCategoryCode;
        this.detailedCategoryName = detailedCategoryName;
        this.thirdCategoryCode = thirdCategoryCode;
        this.thirdCategoryName = thirdCategoryName;
        this.categoryCodeId = categoryCodeId;
        this.thirdCategoryCodeId = thirdCategoryCodeId;
        this.detailedCategoryCodeId = detailedCategoryCodeId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PassengerTerm getPassengerTerm() {
        return this.passengerTerm;
    }

    public void setPassengerTerm(PassengerTerm passengerTerm) {
        this.passengerTerm = passengerTerm;
    }

    public List<String> getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(List<String> categoryCode) {
        this.categoryCode = categoryCode;
    }

    public List<String> getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(List<String> categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getValidFrom() {
        return this.validFrom;
    }

    public void setValidFrom(List<String> validFrom) {
        this.validFrom = validFrom;
    }

    public List<String> getValidTo() {
        return this.validTo;
    }

    public void setValidTo(List<String> validTo) {
        this.validTo = validTo;
    }

    public List<String> getId() {
        return this.id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getDetailedCategoryCode() {
        return this.detailedCategoryCode;
    }

    public void setDetailedCategoryCode(List<String> detailedCategoryCode) {
        this.detailedCategoryCode = detailedCategoryCode;
    }

    public List<String> getDetailedCategoryName() {
        return this.detailedCategoryName;
    }

    public void setDetailedCategoryName(List<String> detailedCategoryName) {
        this.detailedCategoryName = detailedCategoryName;
    }

    public List<String> getThirdCategoryCode() {
        return this.thirdCategoryCode;
    }

    public void setThirdCategoryCode(List<String> thirdCategoryCode) {
        this.thirdCategoryCode = thirdCategoryCode;
    }

    public List<String> getThirdCategoryName() {
        return this.thirdCategoryName;
    }

    public void setThirdCategoryName(List<String> thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    public List<String> getCategoryCodeId() {
        return this.categoryCodeId;
    }

    public void setCategoryCodeId(List<String> categoryCodeId) {
        this.categoryCodeId = categoryCodeId;
    }

    public List<String> getThirdCategoryCodeId() {
        return this.thirdCategoryCodeId;
    }

    public void setThirdCategoryCodeId(List<String> thirdCategoryCodeId) {
        this.thirdCategoryCodeId = thirdCategoryCodeId;
    }

    public List<String> getDetailedCategoryCodeId() {
        return this.detailedCategoryCodeId;
    }

    public void setDetailedCategoryCodeId(List<String> detailedCategoryCodeId) {
        this.detailedCategoryCodeId = detailedCategoryCodeId;
    }

    public Cause description(String description) {
        setDescription(description);
        return this;
    }

    public Cause passengerTerm(PassengerTerm passengerTerm) {
        setPassengerTerm(passengerTerm);
        return this;
    }

    public Cause categoryCode(List<String> categoryCode) {
        setCategoryCode(categoryCode);
        return this;
    }

    public Cause categoryName(List<String> categoryName) {
        setCategoryName(categoryName);
        return this;
    }

    public Cause validFrom(List<String> validFrom) {
        setValidFrom(validFrom);
        return this;
    }

    public Cause validTo(List<String> validTo) {
        setValidTo(validTo);
        return this;
    }

    public Cause id(List<String> id) {
        setId(id);
        return this;
    }

    public Cause detailedCategoryCode(List<String> detailedCategoryCode) {
        setDetailedCategoryCode(detailedCategoryCode);
        return this;
    }

    public Cause detailedCategoryName(List<String> detailedCategoryName) {
        setDetailedCategoryName(detailedCategoryName);
        return this;
    }

    public Cause thirdCategoryCode(List<String> thirdCategoryCode) {
        setThirdCategoryCode(thirdCategoryCode);
        return this;
    }

    public Cause thirdCategoryName(List<String> thirdCategoryName) {
        setThirdCategoryName(thirdCategoryName);
        return this;
    }

    public Cause categoryCodeId(List<String> categoryCodeId) {
        setCategoryCodeId(categoryCodeId);
        return this;
    }

    public Cause thirdCategoryCodeId(List<String> thirdCategoryCodeId) {
        setThirdCategoryCodeId(thirdCategoryCodeId);
        return this;
    }

    public Cause detailedCategoryCodeId(List<String> detailedCategoryCodeId) {
        setDetailedCategoryCodeId(detailedCategoryCodeId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cause)) {
            return false;
        }
        Cause cause = (Cause) o;
        return Objects.equals(description, cause.description) && Objects.equals(passengerTerm, cause.passengerTerm)
                && Objects.equals(categoryCode, cause.categoryCode) && Objects.equals(categoryName, cause.categoryName)
                && Objects.equals(validFrom, cause.validFrom) && Objects.equals(validTo, cause.validTo)
                && Objects.equals(id, cause.id) && Objects.equals(detailedCategoryCode, cause.detailedCategoryCode)
                && Objects.equals(detailedCategoryName, cause.detailedCategoryName)
                && Objects.equals(thirdCategoryCode, cause.thirdCategoryCode)
                && Objects.equals(thirdCategoryName, cause.thirdCategoryName)
                && Objects.equals(categoryCodeId, cause.categoryCodeId)
                && Objects.equals(thirdCategoryCodeId, cause.thirdCategoryCodeId)
                && Objects.equals(detailedCategoryCodeId, cause.detailedCategoryCodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, passengerTerm, categoryCode, categoryName, validFrom, validTo, id,
                detailedCategoryCode, detailedCategoryName, thirdCategoryCode, thirdCategoryName, categoryCodeId,
                thirdCategoryCodeId, detailedCategoryCodeId);
    }

    @Override
    public String toString() {
        return "{" +
                " description='" + getDescription() + "'" +
                ", passengerTerm='" + getPassengerTerm() + "'" +
                ", categoryCode='" + getCategoryCode() + "'" +
                ", categoryName='" + getCategoryName() + "'" +
                ", validFrom='" + getValidFrom() + "'" +
                ", validTo='" + getValidTo() + "'" +
                ", id='" + getId() + "'" +
                ", detailedCategoryCode='" + getDetailedCategoryCode() + "'" +
                ", detailedCategoryName='" + getDetailedCategoryName() + "'" +
                ", thirdCategoryCode='" + getThirdCategoryCode() + "'" +
                ", thirdCategoryName='" + getThirdCategoryName() + "'" +
                ", categoryCodeId='" + getCategoryCodeId() + "'" +
                ", thirdCategoryCodeId='" + getThirdCategoryCodeId() + "'" +
                ", detailedCategoryCodeId='" + getDetailedCategoryCodeId() + "'" +
                "}";
    }

}