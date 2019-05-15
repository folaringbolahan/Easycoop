package com.sift.financial;

import java.util.HashMap;
import java.util.Map;


public class Result {
	
	private Map<String, String> message = new HashMap<String, String>();
	
	private String textMsg;
	
	public Map<String, String> getMessage() {
		return message;
	}

	public void setMessage(Map<String, String> message) {
		this.message = message;
	}

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

}
