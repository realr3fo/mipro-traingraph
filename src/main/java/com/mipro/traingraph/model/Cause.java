package com.mipro.traingraph.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cause {
    private String description;
    private PassengerTerm passengerTerm;
    private String categoryCode;
    private String categoryName;
    private String validFrom;
    private String validTo;
    private Integer id;
    private String detailedCategoryCode;
    private String detailedCategoryName;
    private String thirdCategoryCode;
    private String thirdCategoryName;
    private Integer categoryCodeId;
    private Integer thirdCategoryCodeId;
    private Integer detailedCategoryCodeId;

    // Getters and setters

    public Cause() {
    }

    public Cause(String description, PassengerTerm passengerTerm, String categoryCode, String categoryName,
            String validFrom, String validTo, Integer id, String detailedCategoryCode, String detailedCategoryName,
            String thirdCategoryCode, String thirdCategoryName, Integer categoryCodeId, Integer thirdCategoryCodeId,
            Integer detailedCategoryCodeId) {
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

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getValidFrom() {
        return this.validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return this.validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetailedCategoryCode() {
        return this.detailedCategoryCode;
    }

    public void setDetailedCategoryCode(String detailedCategoryCode) {
        this.detailedCategoryCode = detailedCategoryCode;
    }

    public String getDetailedCategoryName() {
        return this.detailedCategoryName;
    }

    public void setDetailedCategoryName(String detailedCategoryName) {
        this.detailedCategoryName = detailedCategoryName;
    }

    public String getThirdCategoryCode() {
        return this.thirdCategoryCode;
    }

    public void setThirdCategoryCode(String thirdCategoryCode) {
        this.thirdCategoryCode = thirdCategoryCode;
    }

    public String getThirdCategoryName() {
        return this.thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    public Integer getCategoryCodeId() {
        return this.categoryCodeId;
    }

    public void setCategoryCodeId(Integer categoryCodeId) {
        this.categoryCodeId = categoryCodeId;
    }

    public Integer getThirdCategoryCodeId() {
        return this.thirdCategoryCodeId;
    }

    public void setThirdCategoryCodeId(Integer thirdCategoryCodeId) {
        this.thirdCategoryCodeId = thirdCategoryCodeId;
    }

    public Integer getDetailedCategoryCodeId() {
        return this.detailedCategoryCodeId;
    }

    public void setDetailedCategoryCodeId(Integer detailedCategoryCodeId) {
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

    public Cause categoryCode(String categoryCode) {
        setCategoryCode(categoryCode);
        return this;
    }

    public Cause categoryName(String categoryName) {
        setCategoryName(categoryName);
        return this;
    }

    public Cause validFrom(String validFrom) {
        setValidFrom(validFrom);
        return this;
    }

    public Cause validTo(String validTo) {
        setValidTo(validTo);
        return this;
    }

    public Cause id(Integer id) {
        setId(id);
        return this;
    }

    public Cause detailedCategoryCode(String detailedCategoryCode) {
        setDetailedCategoryCode(detailedCategoryCode);
        return this;
    }

    public Cause detailedCategoryName(String detailedCategoryName) {
        setDetailedCategoryName(detailedCategoryName);
        return this;
    }

    public Cause thirdCategoryCode(String thirdCategoryCode) {
        setThirdCategoryCode(thirdCategoryCode);
        return this;
    }

    public Cause thirdCategoryName(String thirdCategoryName) {
        setThirdCategoryName(thirdCategoryName);
        return this;
    }

    public Cause categoryCodeId(Integer categoryCodeId) {
        setCategoryCodeId(categoryCodeId);
        return this;
    }

    public Cause thirdCategoryCodeId(Integer thirdCategoryCodeId) {
        setThirdCategoryCodeId(thirdCategoryCodeId);
        return this;
    }

    public Cause detailedCategoryCodeId(Integer detailedCategoryCodeId) {
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