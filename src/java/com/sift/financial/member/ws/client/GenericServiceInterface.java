package com.sift.financial.member.ws.client;


public interface GenericServiceInterface 
{
	
	public String convertObjectToXML(Object obj, String className) throws Throwable;
	
	public Object convertXMLToObject(String xml, String className) throws Throwable;
	
	public String convertObjectToJson(Object obj) throws Throwable;
	
	public Object convertJsonToObject(String json, String className) throws Throwable;
	

}
