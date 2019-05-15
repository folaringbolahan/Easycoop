package com.sift.financial.member;



/**
 * EventProperty entity. @author MyEclipse Persistence Tools
 */

public class EventProperty  implements java.io.Serializable {


    // Fields    

     private Integer eventPptyId;
     private Event event;
     private String eventPptyNameDesc;
     private String eventPptyShort;
     private String eventPptyValue;
     private String delVal;
     private String eventPptyAmt;


    // Constructors

    /** default constructor */
    public EventProperty() {
    }

    
    /** full constructor */
    public EventProperty(Event event, String eventPptyNameDesc, String eventPptyShort, String eventPptyValue, String delVal,String eventPptyAmt) {
        this.event = event;
        this.eventPptyNameDesc = eventPptyNameDesc;
        this.eventPptyShort = eventPptyShort;
        this.eventPptyValue = eventPptyValue;
        this.delVal = delVal;
        this.eventPptyAmt=eventPptyAmt;
    }

   
    // Property accessors

    public Integer getEventPptyId() {
        return this.eventPptyId;
    }
    
    public void setEventPptyId(Integer eventPptyId) {
        this.eventPptyId = eventPptyId;
    }

    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEventPptyNameDesc() {
        return this.eventPptyNameDesc;
    }
    
    public void setEventPptyNameDesc(String eventPptyNameDesc) {
        this.eventPptyNameDesc = eventPptyNameDesc;
    }

    public String getEventPptyShort() {
        return this.eventPptyShort;
    }
    
    public void setEventPptyShort(String eventPptyShort) {
        this.eventPptyShort = eventPptyShort;
    }

    public String getEventPptyValue() {
        return this.eventPptyValue;
    }
    
    public void setEventPptyValue(String eventPptyValue) {
        this.eventPptyValue = eventPptyValue;
    }

    public String getDelVal() {
        return this.delVal;
    }
    
    public void setDelVal(String delVal) {
        this.delVal = delVal;
    }

    public String getEventPptyAmt() {
        return eventPptyAmt;
    }

    public void setEventPptyAmt(String eventPptyAmt) {
        this.eventPptyAmt = eventPptyAmt;
    }
   








}