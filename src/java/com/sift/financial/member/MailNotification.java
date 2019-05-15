package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;


/**
 * MailNotification entity. @author MyEclipse Persistence Tools
 */

public class MailNotification  implements java.io.Serializable {


    // Fields    

     private Integer emailId;
     private Event event;
     private String action;
     private String subject;
     private String template;
     private String delFlg;
     private Set mailRecipients = new HashSet(0);


    // Constructors

    /** default constructor */
    public MailNotification() {
    }

	/** minimal constructor */
    public MailNotification(Event event, String delFlg) {
        this.event = event;
        this.delFlg = delFlg;
    }
    
    /** full constructor */
    public MailNotification(Event event, String action, String subject, String template, String delFlg, Set mailRecipients) {
        this.event = event;
        this.action = action;
        this.subject = subject;
        this.template = template;
        this.delFlg = delFlg;
        this.mailRecipients = mailRecipients;
    }

   
    // Property accessors

    public Integer getEmailId() {
        return this.emailId;
    }
    
    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    public String getAction() {
        return this.action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }

    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return this.template;
    }
    
    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDelFlg() {
        return this.delFlg;
    }
    
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public Set getMailRecipients() {
        return this.mailRecipients;
    }
    
    public void setMailRecipients(Set mailRecipients) {
        this.mailRecipients = mailRecipients;
    }
   








}