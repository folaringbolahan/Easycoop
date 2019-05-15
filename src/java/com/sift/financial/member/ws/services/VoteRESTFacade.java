package com.sift.financial.member.ws.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import org.springframework.web.bind.annotation.RestController;


import com.sift.financial.member.ws.client.MemberVote;
import com.sift.financial.member.ws.client.MemberVotes;


@Controller
@RequestMapping("/coopvote")
public class VoteRESTFacade {
	
	@Autowired
	private VoteWsImpl voteWsImpl;

	@RequestMapping(value = "/{compCode}", method = RequestMethod.GET, produces={"application/json","application/xml"})
	public @ResponseBody MemberVotes allMemberVote(@PathVariable("compCode") String compCode)
	{
		
		MemberVotes compVote = new MemberVotes();
	
		System.out.println("Here inside rest");
		
		try 
		{
			compVote = (MemberVotes)getVoteWsImpl().getAllEntities(compCode);
			
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
		
		return compVote;
	}
	
	
	@RequestMapping(value = "/{compCode}/{memCode}", method = RequestMethod.GET, produces={"application/json","application/xml"})
	public @ResponseBody MemberVote memberVote(@PathVariable("compCode") String compCode, @PathVariable("memCode") String memCode)
	{
		MemberVote memVote = new MemberVote();
			
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





	public VoteWsImpl getVoteWsImpl() {
		return voteWsImpl;
	}





	public void setVoteWsImpl(VoteWsImpl voteWsImpl) {
		this.voteWsImpl = voteWsImpl;
	}
	
	

}
