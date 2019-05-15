package com.sift.financial.member.ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sift.financial.member.ws.client.MemberVote;
import com.sift.financial.member.ws.client.MemberVotes;
import com.sift.financial.stock.services.MemberHoldingsService;

@Service
public class VoteWsImpl implements GenericWsInterface {
	
	
	private MemberHoldingsService memberHoldingsService;

	@Override
	public MemberVote getSingleEntity(String bodyObj, String targetObj)
			throws Throwable {

		MemberVote memVote = new MemberVote();
				
		memVote = memberHoldingsService.findMemberVote(bodyObj, targetObj);
		

		return memVote;
	}

	@Override
	public MemberVotes getAllEntities(String bodyObj) throws Throwable {
		// TODO Auto-generated method stub
		
		MemberVotes memberVotes = new MemberVotes();
		
		memberVotes = memberHoldingsService.getCompanyMemberVote(bodyObj);
		
		return memberVotes;
	}

	@Override
	public Object getAllSubEntities(String bodyObj, String subObj,
			String targetObj) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public MemberHoldingsService getMemberHoldingsService() {
		return memberHoldingsService;
	}

	@Autowired
	public void setMemberHoldingsService(MemberHoldingsService memberHoldingsService) {
		this.memberHoldingsService = memberHoldingsService;
	}

}
