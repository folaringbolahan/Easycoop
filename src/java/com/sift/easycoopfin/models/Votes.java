package com.sift.easycoopfin.models;

import java.io.Serializable;

public class Votes implements Serializable {

    public Votes() {
    }
    private int id;
    private int companyId;
    private int agmId;
    private String description;
    private String title;
    private java.util.Date voteDate;
    private String stringDate;
    private java.util.Date endDate;
    private String stringEndDate;
    private java.sql.Time startTime;
    private java.sql.Time endTime;
    private String stringStartTime;
    private String stringEndTime;

    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }

    public void setVoteDate(java.util.Date value) {
        this.voteDate = value;
    }

    public java.util.Date getVoteDate() {
        return voteDate;
    }

    public void setEndDate(java.util.Date value) {
        this.endDate = value;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setStartTime(java.sql.Time value) {
        this.startTime = value;
    }

    public java.sql.Time getStartTime() {
        return startTime;
    }

    public void setEndTime(java.sql.Time value) {
        this.endTime = value;
    }

    public java.sql.Time getEndTime() {
        return endTime;
    }

    public void setStringStartTime(String value) {
        this.stringStartTime = value;
    }

    public String getStringStartTime() {
        return stringStartTime;
    }

    public void setStringEndTime(String value) {
        this.stringEndTime = value;
    }

    public String getStringEndTime() {
        return stringEndTime;
    }

    public void setAgmId(int value) {
        this.agmId = value;
    }

    public int getAgmId() {
        return agmId;
    }

    public void setStringDate(String value) {
        this.stringDate = value;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringEndDate(String value) {
        this.stringEndDate = value;
    }

    public String getStringEndDate() {
        return stringEndDate;
    }

    public String toString() {
        return String.valueOf(getId());
    }
}
