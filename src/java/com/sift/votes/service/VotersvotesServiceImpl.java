/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.financial.member.MemberExtrafld;
import com.sift.financial.member.MembersExtrafldEntries;
import com.sift.votes.dao.VoteDao;
import com.sift.votes.dao.VotecredDao;
import com.sift.votes.model.VotCreds;
import com.sift.votes.model.VotVoteoptions;
import com.sift.votes.model.VotVoteresults;
import com.sift.votes.utility.OTPgenerator;
import com.sift.votes.utility.Voterspasswordencoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kola
 */

    
    // first method
    // run thru the database and pick all members whoise agmid tally with the one passed as parameter and do 
    // left join with cred table - if null in cred generate password and store details in  cred
    
    // this should run in controller or another service class when this is completed - spin another call yo firstnotification
    // service and 
@Service("votersvotesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotersvotesServiceImpl implements VotersvotesService {
	 @Autowired
	 private VoteDao voteDao;
         private Map<Integer, Integer> txtunits = new HashMap<Integer, Integer>();
         
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addvotes(Integer agmid,Integer coyid, Integer voterid,Integer unitsofvotes,HttpServletRequest request) {
             boolean result = false;
		ArrayList<VotVoteresults> lvotres = new ArrayList<VotVoteresults>();
                List<VotVoteoptions> votopts = reBuildvoteflds(request);
                if (votopts.size()>0) {
                    
                    for(VotVoteoptions votopt : votopts){
		       VotVoteresults votres = new VotVoteresults();
                       votres.setBranchid(agmid);
                       votres.setCompanyid(coyid);
                       Calendar rightNow = Calendar.getInstance();
                       java.util.Date entrydate = rightNow.getTime();
                       votres.setAnswerdate(entrydate);
                       votres.setMemberid(voterid);
                       votres.setUnitsofvote(unitsofvotes);
                       votres.setVoteoptionid(votopt.getId());
                       votres.setVotequestid(votopt.getVoteid());
                       lvotres.add(votres);
                     }
                    result= voteDao.addVotes(lvotres,agmid, voterid);
              }
                return result;
	 }
         
         
         
         private  List<VotVoteoptions> reBuildvoteflds(HttpServletRequest req)
	{
		  List<VotVoteoptions>  retrunVal = null;
		  List <String> parameterKeyList = new ArrayList<String>();
		  Map<String, String[]> parameterMap = req.getParameterMap();
		 
		 System.out.println("parameterMap Size:::" +  parameterMap.size());
		 List<VotVoteoptions> theList = new  ArrayList<VotVoteoptions>();
		         Map m = req.getParameterMap();
                         Set s = m.entrySet();
                         Iterator it = s.iterator();
  
                         while (it.hasNext()) {
                            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
                            String key = entry.getKey();
                            String[] value = entry.getValue();
                            if (value.length > 1) {
                              for (int i = 0; i < value.length; i++) {
                                  String parametername = key;
                                  String[] theArray = parametername.split(":::");
                                  if ((theArray.length==2)&&(parametername.startsWith("opt"))) 
                                  {    
                                   VotVoteoptions  addEntry = new VotVoteoptions();
                                   addEntry.setId(Integer.parseInt(value[i]));
                                   addEntry.setVoteid(Integer.parseInt(theArray[1]));
                                   theList.add(addEntry);
                                  }
                             }
                            } 
                            else 
                            {
                                String parametername = key;
                                  String[] theArray = parametername.split(":::");
                                  if ((theArray.length==2)&&(parametername.startsWith("opt"))) 
                                  {  
                                   VotVoteoptions  addEntry = new VotVoteoptions();
                                   addEntry.setId(Integer.parseInt(value[0]));
                                   addEntry.setVoteid(Integer.parseInt(theArray[1]));
                                   theList.add(addEntry);
                                  }
                            }
                          }
                         	retrunVal=theList;
				parameterKeyList= null;
				theList = null;
		
	 return retrunVal;
	}
    public boolean addvotessv(Integer agmid,Integer coyid, Integer voterid,Integer unitsofvotes,HttpServletRequest request) {
             boolean result = false;
             txtunits.clear();
		ArrayList<VotVoteresults> lvotres = new ArrayList<VotVoteresults>();
                List<VotVoteoptions> votopts = reBuildvotefldssv(request);
                if (votopts.size()>0) {
                    Integer dvotes = 0;
                    for(VotVoteoptions votopt : votopts){
		       VotVoteresults votres = new VotVoteresults();
                       votres.setBranchid(agmid);
                       votres.setCompanyid(coyid);
                       Calendar rightNow = Calendar.getInstance();
                       java.util.Date entrydate = rightNow.getTime();
                       votres.setAnswerdate(entrydate);
                       votres.setMemberid(voterid);
                       dvotes = 0;
                       try
                       {
                         dvotes = txtunits.get(votopt.getId());
                       }catch(NullPointerException nuex) {
                           dvotes = 0;
                       }  
                       if (dvotes==null){
                          dvotes = 0; 
                       }
                       votres.setUnitsofvote(dvotes);
                       votres.setVoteoptionid(votopt.getId());
                       votres.setVotequestid(votopt.getVoteid());
                       lvotres.add(votres);
                     }
                    result= voteDao.addVotes(lvotres,agmid, voterid);
              }
                return result;
	 }
         
         
         
         private  List<VotVoteoptions> reBuildvotefldssv(HttpServletRequest req)
	{
		  List<VotVoteoptions>  retrunVal = null;
		  List <String> parameterKeyList = new ArrayList<String>();
		  Map<String, String[]> parameterMap = req.getParameterMap();
		 
		 System.out.println("parameterMap Size:::" +  parameterMap.size());
		 List<VotVoteoptions> theList = new  ArrayList<VotVoteoptions>();
		         Map m = req.getParameterMap();
                         Set s = m.entrySet();
                         Iterator it = s.iterator();
  
                         while (it.hasNext()) {
                            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
                            String key = entry.getKey();
                            String[] value = entry.getValue();
                            if (value.length > 1) {
                              for (int i = 0; i < value.length; i++) {
                                  String parametername = key;
                                  String[] theArray = parametername.split(":::");
                                  if ((theArray.length==2)&&(parametername.startsWith("opt"))) 
                                  {    
                                   VotVoteoptions  addEntry = new VotVoteoptions();
                                   addEntry.setId(Integer.parseInt(value[i]));
                                   addEntry.setVoteid(Integer.parseInt(theArray[1]));
                                   theList.add(addEntry);
                                  }
                                  if ((theArray.length==3)&&(parametername.startsWith("txt"))) 
                                  {    
                                   txtunits.put(Integer.parseInt(theArray[2]),Integer.parseInt(value[i]));
                                  }
                             }
                            } 
                            else 
                            {
                                String parametername = key;
                                  String[] theArray = parametername.split(":::");
                                  if ((theArray.length==2)&&(parametername.startsWith("opt"))) 
                                  {  
                                   VotVoteoptions  addEntry = new VotVoteoptions();
                                   addEntry.setId(Integer.parseInt(value[0]));
                                   addEntry.setVoteid(Integer.parseInt(theArray[1]));
                                   theList.add(addEntry);
                                  }
                                  if ((theArray.length==3)&&(parametername.startsWith("txt"))) 
                                  {    
                                   txtunits.put(Integer.parseInt(theArray[2]),Integer.parseInt(value[0]));
                                  }
                            }
                          }
                         	retrunVal=theList;
				parameterKeyList= null;
				theList = null;
		
	 return retrunVal;
	}
}
