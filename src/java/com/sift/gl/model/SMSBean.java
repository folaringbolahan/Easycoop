/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yomi-pc
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SMSBean implements java.io.Serializable{
    @XmlElement(name = "cmd")
    private String cmd;
    
    @XmlElement(name = "sessionid")
	private String sessionid;
        
    @XmlElement(name = "owneremail")
	private String owneremail;
       
    @XmlElement(name = "subacct")
	private String subacct;
        
    @XmlElement(name = "subacctpwd")
	private String subacctpwd;
        
    @XmlElement(name = "message")
	private String message;
        
    @XmlElement(name = "sender")
	private String sender;
        
    @XmlElement(name = "sendto")
	private String sendto;
        
    @XmlElement(name = "msgtype")
    private String msgtype;
        
    @XmlElement(name = "sendtime")
	private String sendtime;
	
	//Constructors
	/** default constructor */
	public SMSBean(){
	}

	/** full constructor */
	public SMSBean(String cmd, String sessionid,
			String owneremail, String subacct, String subacctpwd,
			String message,String sender,String sendto,String msgtype, String sendtime) {
			this.cmd = cmd;
			this.sessionid = sessionid;
			this.owneremail = owneremail;
			this.subacct = subacct;
			this.subacctpwd = subacctpwd;
			this.message = message;
			this.sender = sender;
	        this.sendto = sendto;
			this.msgtype = msgtype;
			this.msgtype = msgtype;
	        this.sendtime = sendtime;
	}

	// Property accessors
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getOwneremail() {
		return owneremail;
	}

	public void setOwneremail(String owneremail) {
		this.owneremail = owneremail;
	}

	public String getSubacct() {
		return subacct;
	}

	public void setSubacct(String subacct) {
		this.subacct = subacct;
	}

	public String getSubacctpwd() {
		return subacctpwd;
	}

	public void setSubacctpwd(String subacctpwd) {
		this.subacctpwd = subacctpwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
}
