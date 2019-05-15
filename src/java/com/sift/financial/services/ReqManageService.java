package com.sift.financial.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sift.financial.member.ActivityLog;
import com.sift.financial.member.StatusFlow;

public abstract class ReqManageService extends ManagementTempl {
	

protected Map<String, String> messageMap= new HashMap<String, String>();
	
	public abstract Map<String,List> getReferenceData(String[] param) ;

	public abstract boolean addDetail(Object eventObject, StatusFlow flow, HttpServletRequest req);
	
	public abstract boolean editDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) ;
	
	public abstract boolean deleteDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) ;
	
	public abstract Object readDetail(Object eventObject) ;
	
	public abstract List<Object> listDetail(Object eventObject);
	
	public abstract List<Object> listDetail(Object eventObject, String status);
	
	public abstract boolean apprDetail(Object eventObject, StatusFlow flow, HttpServletRequest req);
	
	public abstract boolean apprBulkDetail(Object eventObject, StatusFlow flow, HttpServletRequest req);
	
	public abstract boolean sendSuccessNotification(String comment,Object eventObject, String mailTempl,  MailNotificationImpl mailNotificationService);
	
	public abstract boolean sendFailureNotification(String comment,Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService);
	
	public abstract boolean sendSuccessAuthNotification(String comment,Object eventObject, String mailTempl,  MailNotificationImpl mailNotificationService);
	
	public abstract boolean sendFailureAuthNotification(String comment,Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService);
	
	public abstract ActivityLog   getLogObject (Object eventObject, StatusFlow flow, HttpServletRequest req);

}
