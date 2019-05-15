/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;

/**
 *
 * @author Nelson Akpos
 */
public class VotMailBean {
    public VotMailBean(){
    }
        private String userid;
	private String mailsmtphost;
	private String mailsmtpport;
	private String sslortls;
	private String toemail;
	private String password;
	private String from;
	private String subject;
	private String mailBody;
	private String[] attachments;   

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the mailsmtphost
     */
    public String getMailsmtphost() {
        return mailsmtphost;
    }

    /**
     * @param mailsmtphost the mailsmtphost to set
     */
    public void setMailsmtphost(String mailsmtphost) {
        this.mailsmtphost = mailsmtphost;
    }

    /**
     * @return the mailsmtpport
     */
    public String getMailsmtpport() {
        return mailsmtpport;
    }

    /**
     * @param mailsmtpport the mailsmtpport to set
     */
    public void setMailsmtpport(String mailsmtpport) {
        this.mailsmtpport = mailsmtpport;
    }

    /**
     * @return the sslortls
     */
    public String getSslortls() {
        return sslortls;
    }

    /**
     * @param sslortls the sslortls to set
     */
    public void setSslortls(String sslortls) {
        this.sslortls = sslortls;
    }

    /**
     * @return the toemail
     */
    public String getToemail() {
        return toemail;
    }

    /**
     * @param toemail the toemail to set
     */
    public void setToemail(String toemail) {
        this.toemail = toemail;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the mailBody
     */
    public String getMailBody() {
        return mailBody;
    }

    /**
     * @param mailBody the mailBody to set
     */
    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    /**
     * @return the attachments
     */
    public String[] getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }
}
