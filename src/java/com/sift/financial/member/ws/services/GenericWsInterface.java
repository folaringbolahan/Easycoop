package com.sift.financial.member.ws.services;


public interface GenericWsInterface {
	
	public Object getSingleEntity (String bodyObj, String targetObj) throws Throwable;
	
	public Object getAllEntities (String bodyObj) throws Throwable;
	
	public Object getAllSubEntities (String bodyObj, String subObj, String targetObj) throws Throwable;
	
}
