package com.sift.financial.member.ws.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sift.financial.member.ws.client.MemberVote;
import com.sift.financial.member.ws.client.MemberVotes;
import com.sift.financial.stock.services.MemberHoldingsService;
import com.sun.jersey.spi.inject.Inject;


@Component
@Path("/coopvote")
public class VoteJerseyRESTFacade {
	

   @Inject
   private VoteWsImpl voteWsImpl;
    
   /* public VoteRESTFacade()
    {
    	memberHoldingsService = new MemberHoldingsService();
    	
    }*/


	@GET
	@Path("{compCode}/{memCode}")
	@Produces(MediaType.APPLICATION_XML)
	
	public MemberVote memberVote(@PathParam("compCode") String compCode, @PathParam("memCode") String memCode)
	{
		MemberVote memVote = new MemberVote();
		
		//MemberHoldingsService memberHoldingsService = new MemberHoldingsService();
		/*MemberVote memVote = new MemberVote();
		memVote.setCompanyCode(compCode);
		memVote.setMemberCode(memCode);
		memVote.setMemberName("Adebayo Ojo");
		memVote.setVote("10");*/
		
		try 
		{
			
			memVote = (MemberVote)getVoteWsImpl().getSingleEntity(compCode, memCode);
			
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		
		//memberHoldingsService.findMemberVote(compCode, memCode);
	
		return memVote;
	}
	
	
	@GET
	@Path("{compCode}")
	@Produces(MediaType.APPLICATION_XML)
	public MemberVotes allMemberVote(@PathParam("compCode") String compCode)
	{
		
		MemberVotes compVote = new MemberVotes();
		
		//MemberHoldingsService memberHoldingsService = new MemberHoldingsService();
		/*MemberVotes compVote = new MemberVotes();
		
		compVote.setCompanyCode(compCode);
		
		
		List<MemberVote> list = new ArrayList<MemberVote>();
		
		MemberVote memVote = new MemberVote();
		memVote.setCompanyCode(compCode);
		memVote.setMemberCode("000001");
		memVote.setMemberName("Adebayo Ojo");
		memVote.setVote("10");
		
		list.add(memVote);
		
		MemberVote memVote2 = new MemberVote();
		memVote2.setCompanyCode(compCode);
		memVote2.setMemberCode("000002");
		memVote2.setMemberName("Abayomi Awosusi");
		memVote2.setVote("8");
		
		list.add(memVote2);
		
		compVote.setMemberVote(list);*/
		
		System.out.println("Here inside rest");
		
		//memberHoldingsService.getCompanyMemberVote(compCode)
		
		try 
		{
			compVote = (MemberVotes)getVoteWsImpl().getAllEntities(compCode);
			
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
		
		return null;
	}


	/**
	 * @return the memberHoldingsService
	
	public MemberHoldingsService getMemberHoldingsService() {
		return memberHoldingsService;
	}
	 */

	/**
	 * @param memberHoldingsService the memberHoldingsService to set
	
	
	public void setMemberHoldingsService(MemberHoldingsService memberHoldingsService) {
		this.memberHoldingsService = memberHoldingsService;
	}
	*/


	public VoteWsImpl getVoteWsImpl() {
		return voteWsImpl;
	}

	//@Autowired
	public void setVoteWsImpl(VoteWsImpl voteWsImpl) {
		this.voteWsImpl = voteWsImpl;
	}
	
}
