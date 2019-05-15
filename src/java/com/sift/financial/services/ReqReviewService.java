/**
 * 
 */
package com.sift.financial.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sift.financial.member.StatusFlow;
import com.sift.financial.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author baydel200
 *
 */
public abstract class ReqReviewService extends ManagementTempl{
	
	
	protected Map<String, String> messageMap= new HashMap<String, String>();
	
	public abstract Map<String,List> getReferenceData(String[] param) ;

	public abstract boolean saveDetail(ReqReviewBean reqReviewBean, StatusFlow flow, HttpServletRequest req);
	
	public abstract boolean acceptDetail(StatusFlow flow, ReqReviewBean reqReviewBean, HttpServletRequest req) ;
	
	public abstract List<Object> listDetails(ReqReviewBean reqReviewBean);
	
	public abstract boolean saveDocReviewFailureAuthDetails(ReqReviewBean reqReviewBean,StatusFlow flow);
    
	public abstract boolean saveDocReviewSuccessAuthDetails(ReqReviewBean reqReviewBean,StatusFlow flow);
	
	public abstract boolean sendReviewAuthSuccessNotification(String comment,Object eventObject, String mailTempl,  MailNotificationImpl mailNotificationService);
	
	public abstract boolean sendReviewAuthFailureNotification(String comment,Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService);


}
