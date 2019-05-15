package com.sift.financial.member.services;

import java.util.List;
import java.util.Map;

import com.sift.financial.member.*;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.ManagementTempl;
import com.sift.financial.services.ReqReviewService;

public class MemberReviewImpl extends ManagementTempl {


	public Map<String, List> getReferenceData(String[] param) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean saveDetail(Member member, StatusFlow flow) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean acceptDetail(StatusFlow flow, Member member) {
		// TODO Auto-generated method stub
		return false;
	}


	public List<Object> listDetails(Member member) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean saveDocReviewFailureAuthDetails(Member member,
			StatusFlow flow) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean saveDocReviewSuccessAuthDetails(Member member,
			StatusFlow flow) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean sendReviewAuthSuccessNotification(String comment,
			Object eventObject, String mailTempl,
			MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean sendReviewAuthFailureNotification(String comment,
			Object eventObject, String mailTempl,
			MailNotificationImpl mailNotificationService) {
		// TODO Auto-generated method stub
		return false;
	}

}
