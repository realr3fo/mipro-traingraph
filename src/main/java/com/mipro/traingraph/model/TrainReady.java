package com.mipro.traingraph.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainReady {
    private String description;
    private String source;
    private boolean accepted;
    private String timestamp;

    // Getters and setters

    public TrainReady() {
    }

    public TrainReady(String description, String source, boolean accepted, String timestamp) {
        this.description = description;
        this.source = source;
        this.accepted = accepted;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public boolean getAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public TrainReady description(String description) {
        setDescription(description);
        return this;
    }

    public TrainReady source(String source) {
        setSource(source);
        return this;
    }

    public TrainReady accepted(boolean accepted) {
        setAccepted(accepted);
        return this;
    }

    public TrainReady timestamp(String timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TrainReady)) {
            return false;
        }
        TrainReady trainReady = (TrainReady) o;
        return Objects.equals(description, trainReady.description) && Objects.equals(source, trainReady.source)
                && accepted == trainReady.accepted && Objects.equals(timestamp, trainReady.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, source, accepted, timestamp);
    }

    @Override
    public String toString() {
        return "{" +
                " description='" + getDescription() + "'" +
                ", source='" + getSource() + "'" +
                ", accepted='" + isAccepted() + "'" +
                ", timestamp='" + getTimestamp() + "'" +
                "}";
    }

}