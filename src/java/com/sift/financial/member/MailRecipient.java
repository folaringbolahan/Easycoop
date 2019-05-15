package com.sift.financial.member;



/**
 * MailRecipient entity. @author MyEclipse Persistence Tools
 */

public class MailRecipient  implements java.io.Serializable {


    // Fields    

     private Integer recipientId;
     private RecipientType recipientType;
     private MailNotification mailNotification;
     private String recipient;


    // Constructors

    /** default constructor */
    public MailRecipient() {
    }

    
    /** full constructor */
    public MailRecipient(RecipientType recipientType, MailNotification mailNotification, String recipient) {
        this.recipientType = recipientType;
        this.mailNotification = mailNotification;
        this.recipient = recipient;
    }

   
    // Property accessors

    public Integer getRecipientId() {
        return this.recipientId;
    }
    
    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public RecipientType getRecipientType() {
        return this.recipientType;
    }
    
    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public MailNotification getMailNotification() {
        return this.mailNotification;
    }
    
    public void setMailNotification(MailNotification mailNotification) {
        this.mailNotification = mailNotification;
    }

    public String getRecipient() {
        return this.recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
   








}