package com.sift.admin.bean;

public class FeedbackBean{
    private String message;
    private String redirectURI;
    
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getRedirectURI(){
		return redirectURI;
	}
	public void setRedirectURI(String redirectURI){
		this.redirectURI = redirectURI;
	}
}
