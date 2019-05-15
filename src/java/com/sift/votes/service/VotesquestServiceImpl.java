package com.sift.votes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.dao.LoanRequestDao;
import com.sift.votes.bean.VotVotequestsBean;
import com.sift.votes.dao.VotequestionsDao;
import com.sift.votes.model.VotVotequests;
import com.sift.votes.model.Votpagesession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 
 *
 */
@Service("votesquestService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotesquestServiceImpl implements VotesquestService{
         @Autowired
         private VotequestionsDao votequestionsDao;
         @Autowired
         private LoanRequestDao loanRequestDao;
         
         public Votpagesession buildVotpagession(Integer dagmid,Integer usrnoofvoteunits){
             Votpagesession votpgse = new Votpagesession();   
             Map<Integer, VotVotequests> votesqmap = new HashMap<Integer, VotVotequests>();
             List<VotVotequests> votequests =  votequestionsDao.listVotequestions(dagmid);
               int noofelectionpages = 0;
               Integer i = 0;
                 if (votequests!=null && votequests.size()>0) {
                     for (VotVotequests voteqobj: votequests)
                     {
                        if(voteqobj.getVotetypeid()==1)
                        {
                          noofelectionpages=noofelectionpages+1; 
                        }
                        //VotVotequestsBean vob = preparevotequestBean(voteqobj);
                        voteqobj.setVoteoptions(votequestionsDao.listVoteoptions(voteqobj.getId()));
                        i = i +1;
                        votesqmap.put(i, voteqobj);
                     }
                     
                 }
                 votpgse.setQuestaggregate(votesqmap);
                 votpgse.setPrevpagestart(noofelectionpages+1);
                 votpgse.setNoofpages(i);
                 votpgse.setNoofvoteunits(usrnoofvoteunits);
		 return votpgse;
	 }
         
       private VotVotequestsBean preparevotequestBean(VotVotequests vquests){
		  VotVotequestsBean bean = new VotVotequestsBean();
                  bean.setId(vquests.getId());
		   return bean;
   }
}