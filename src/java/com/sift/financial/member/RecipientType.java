package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;


/**
 * RecipientType entity. @author MyEclipse Persistence Tools
 */

public class RecipientType  implements java.io.Serializable {


    // Fields    

     private Integer recipientTypeId;
     private String recipientType;
     private Set mailRecipients = new HashSet(0);


    // Constructors

    /** default constructor */
    public RecipientType() {
    }

	/** minimal constructor */
    public RecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    
    /** full constructor */
    public RecipientType(String recipientType, Set mailRecipients) {
        this.recipientType = recipientType;
        this.mailRecipients = mailRecipients;
    }

   
    // Property accessors

    public Integer getRecipientTypeId() {
        return this.recipientTypeId;
    }
    
    public void setRecipientTypeId(Integer recipientTypeId) {
        this.recipientTypeId = recipientTypeId;
    }

    public String getRecipientType() {
        return this.recipientType;
    }
    
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public Set getMailRecipients() {
        return this.mailRecipients;
    }
    
    public void setMailRecipients(Set mailRecipients) {
        this.mailRecipients = mailRecipients;
    }
   








}