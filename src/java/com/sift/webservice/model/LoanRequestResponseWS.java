/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nelson Akpos
 */
@XmlRootElement
public class LoanRequestResponseWS {

    @XmlElement
    private String memberId;
    @XmlElement
    private boolean loanEligibility;
    @XmlElement
    private int responseCode;
    @XmlElement
    private String responseMessage;

    public LoanRequestResponseWS() {
    }

    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the loanEligibility
     */
    public boolean isLoanEligibility() {
        return loanEligibility;
    }

    /**
     * @param loanEligibility the loanEligibility to set
     */
    public void setLoanEligibility(boolean loanEligibility) {
        this.loanEligibility = loanEligibility;
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
