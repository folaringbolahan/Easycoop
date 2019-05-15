package com.sift.gl.model;

public class MailglBean {
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
		
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMailsmtphost() {
		return mailsmtphost;
	}
	public void setMailsmtphost(String mailsmtphost) {
		this.mailsmtphost = mailsmtphost;
	}
	public String getMailsmtpport() {
		return mailsmtpport;
	}
	public void setMailsmtpport(String mailsmtpport) {
		this.mailsmtpport = mailsmtpport;
	}
	public String getSslortls() {
		return sslortls;
	}
	public void setSslortls(String sslortls) {
		this.sslortls = sslortls;
	}
	public String getToemail() {
		return toemail;
	}
	public void setToemail(String toemail) {
		this.toemail = toemail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public String[] getAttachments() {
		return attachments;
	}
	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	} 
}
