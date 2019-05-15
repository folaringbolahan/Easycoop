/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotecredDao;
import com.sift.votes.model.VotCreds;
import com.sift.votes.utility.OTPgenerator;
import com.sift.votes.utility.Voterspasswordencoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
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
@Service("voterscredgenService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VoterscredgenServiceImpl implements VoterscredgenService {
	 @Autowired
private VotecredDao votecredDao;
         @Autowired
         private TaskExecutor taskExecutor;
         @Autowired
         private VoterNotificationService voterNotificationService;
         //private String DBASE_URI="";

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addvoterscred(Integer agmid) {
		List<Object[]> validvoters  = votecredDao.getMembersofagmwithoutcred(agmid);
                String DBASE_URI = "";
                if (validvoters.size()>0) {
                    try {
                       javax.naming.Context ctx = new javax.naming.InitialContext();
                       String uri = (String) ctx.lookup("java:comp/env/app.uri");
                       //DBASE_URI = uri + "/votes/login.htm";
                       DBASE_URI = uri + "/votes/login.htm?rf="+agmid.toString();
                    } catch (NamingException nx) {System.out.println("Error name exception" + nx.getMessage().toString());}
                    VotersnotificationServiceImpl vnotserv = new VotersnotificationServiceImpl();
                    //VoterNotificationService voterNotificationService;// = new VoterNotificationService();
                    int j = 0;
                    for(Object[] validvotdet : validvoters){
		       //System.out.println(Arrays.toString(arr));
                        j=j+1;
                      if (j>4) 
                      //if (j>20) // for exchange server implementation     
                      { 
                       j=0;   
                       try{
                        ///Thread.sleep(300000); //wait 5 min for Email server response - my mail server -only way or server-security treats as spam sending   
                       Thread.sleep(60000); //wait 1 min for Email server response
                       //Thread.sleep(1000);//wait i millisecond for exchange server implementation
                       }
                       catch(InterruptedException iex){
                          System.out.println("Pausing Exception..."+ iex.getMessage() );  
                       }
		       System.out.println("Pausing..." ); 
                      } 
                       VotCreds votcred = new VotCreds();
                       votcred.setAgmid(agmid);
                       votcred.setCompanyid((Integer)validvotdet[2]);
                       votcred.setMemberid((Integer)validvotdet[0]);
                       votcred.setMembermail((String)validvotdet[1]);
                       //votcred.setSent(null);
                       ///String salt = OTPgenerator.getSalt(20);
                       ////votcred.setSalt(salt);
                       String otp = OTPgenerator.generateOTP();
                       ////votcred.setTok(otp);
                       votcred.setTok("");
                       Voterspasswordencoder vpe = new Voterspasswordencoder();
                       votcred.setTokencrpy(vpe.encodePass(otp));
                       votcred.setSent("N");
                       votcred.setLocked("0");
                       votcred.setDeleted("N");
                       Calendar rightNow = Calendar.getInstance();
                       java.util.Date entrydate = rightNow.getTime();
                       votcred.setCreatedate(entrydate);
                       votcred.setRole("ROLE_VT1");
                       votecredDao.addCred(votcred);
                       //send mail
                      /// bulid model first
                       Map model = new HashMap();
                       String surname = ((String)validvotdet[6]!=null?" " + (String)validvotdet[6]:"");
                       String mddlename  = ((String)validvotdet[5]!=null?" " + (String)validvotdet[5]:"");
                       String firstname = ((String)validvotdet[4]!=null?(String)validvotdet[4]:"");
                       model.put("getUsername", firstname + mddlename + surname);
                       model.put("getAgmdescription", (String)validvotdet[7]);
                       model.put("uri", DBASE_URI);
                       model.put("getEmail", (String)validvotdet[1]);
                       model.put("getClearpass", otp);
                       model.put("getStartdate", validvotdet[8].toString());
                       model.put("getStarttime", validvotdet[9].toString());
                       model.put("getEnddate", validvotdet[10].toString());
                       model.put("getEndtime", validvotdet[11].toString());
           
                       boolean res = vnotserv.firenotification(voterNotificationService,"voteractivateuser.ftl",model,(String)validvotdet[1], "Easycoop E-voting - Voter Activation mail");
                      if (res==true) {
                           //update cred and set sent attrib to 'Y' if the noticecall returns true where memberid and agmid = current
                          votecredDao.updatemailedCred(agmid,(Integer)validvotdet[0]);
                      }
	            }
              }
	 }
         
         
    public void setTaskExecutor(TaskExecutor taskExecutor) {
       this.taskExecutor = taskExecutor;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
    
    
     public class createvotcredTask implements Runnable {
      private Integer agmid;
       public createvotcredTask(Integer agmid) {
            this.agmid = agmid;
       }

        public void run() {
           addvoterscred(agmid);
        }
    }

    public void gencred(Integer dagmid) {
        taskExecutor.execute(
                new createvotcredTask(dagmid));
    }
    
    
    public void resetvoterscredstr(String membermail) {
		List<Object[]> validvoter  = votecredDao.getMemberbyemailofagmnotvoted(membermail);
                int row = 0;
                String DBASE_URI = "";
                if (validvoter.size()>0) {
                    try {
                       javax.naming.Context ctx = new javax.naming.InitialContext();
                       String uri = (String) ctx.lookup("java:comp/env/app.uri");
                       //DBASE_URI = uri + "/votes/login.htm";
                       DBASE_URI = uri + "/votes/login.htm?rf=";
                    } catch (NamingException nx) {System.out.println("Error name exception" + nx.getMessage().toString());}
                    VotersnotificationServiceImpl vnotserv = new VotersnotificationServiceImpl();
                    //VoterNotificationService ntc = new VoterNotificationService();
                    
                    for(Object[] validvotdet : validvoter){
		       //System.out.println(Arrays.toString(arr));
                      if (row ==0) {  
                       VotCreds votcred = new VotCreds();
                       votcred.setAgmid((Integer)validvotdet[3]);
                       votcred.setCompanyid((Integer)validvotdet[2]);
                       votcred.setMemberid((Integer)validvotdet[0]);
                       votcred.setMembermail((String)validvotdet[1]);
                       String otp = OTPgenerator.generateOTP();
                       votcred.setTok(otp);
                       Voterspasswordencoder vpe = new Voterspasswordencoder();
                       votcred.setTokencrpy(vpe.encodePass(otp));
                       votcred.setSent("N");
                       votcred.setLocked("0");
                       votcred.setDeleted("N");
                       Calendar rightNow = Calendar.getInstance();
                       java.util.Date entrydate = rightNow.getTime();
                       votcred.setCreatedate(entrydate);
                       votcred.setRole("ROLE_VT1");
                       votecredDao.updateCred(votcred);
                       //send mail
                      /// bulid model first
                       Map model = new HashMap();
                       String surname = ((String)validvotdet[6]!=null?" " + (String)validvotdet[6]:"");
                       String mddlename  = ((String)validvotdet[5]!=null?" " + (String)validvotdet[5]:"");
                       String firstname = ((String)validvotdet[4]!=null?(String)validvotdet[4]:"");
                       model.put("getUsername", firstname + mddlename + surname);
                       model.put("getAgmdescription", (String)validvotdet[7]);
                       model.put("uri", DBASE_URI+validvotdet[3]);
                       model.put("getEmail", (String)validvotdet[1]);
                       model.put("getClearpass", otp);
                       model.put("getStartdate", validvotdet[8].toString());
                       model.put("getStarttime", validvotdet[9].toString());
                       model.put("getEnddate", validvotdet[10].toString());
                       model.put("getEndtime", validvotdet[11].toString());
           
                       boolean res = vnotserv.firenotification(voterNotificationService,"voteractivateuser.ftl",model,(String)validvotdet[1], "Easycoop E-voting - Voter Activation mail");
                      if (res==true) {
                           //update cred and set sent attrib to 'Y' if the noticecall returns true where memberid and agmid = current
                          votecredDao.updatemailedCred((Integer)validvotdet[3],(Integer)validvotdet[0]);
                      }
	            }
                      row = row + 1;
                  }     
              }
	 }
    
    public void resetvoterscred(Integer memberid) {
		List<Object[]> validvoter  = votecredDao.getMemberbyemailofagmnotvoted(memberid);
                int row = 0;
               // System.out.println("check 0");
                String DBASE_URI = "";
                if (validvoter.size()>0) {
                    /////
                   // System.out.println("check 1");
                    try {
                       javax.naming.Context ctx = new javax.naming.InitialContext();
                       String uri = (String) ctx.lookup("java:comp/env/app.uri");
                       //DBASE_URI = uri + "/votes/login.htm";
                       DBASE_URI = uri + "/votes/login.htm?rf=";
                    } catch (NamingException nx) {System.out.println("Error name exception" + nx.getMessage().toString());}
                    VotersnotificationServiceImpl vnotserv = new VotersnotificationServiceImpl();
                    //VoterNotificationService ntc = new VoterNotificationService();
                    
                    for(Object[] validvotdet : validvoter){
		       //System.out.println(Arrays.toString(arr));
                      if (row ==0) {  
                       VotCreds votcred = new VotCreds();
                       votcred.setAgmid((Integer)validvotdet[3]);
                       votcred.setCompanyid((Integer)validvotdet[2]);
                       votcred.setMemberid((Integer)validvotdet[0]);
                       votcred.setMembermail((String)validvotdet[1]);
                       String otp = OTPgenerator.generateOTP();
                       votcred.setTok(otp);
                       Voterspasswordencoder vpe = new Voterspasswordencoder();
                       votcred.setTokencrpy(vpe.encodePass(otp));
                       votcred.setSent("N");
                       votcred.setLocked("0");
                       votcred.setDeleted("N");
                       Calendar rightNow = Calendar.getInstance();
                       java.util.Date entrydate = rightNow.getTime();
                       votcred.setCreatedate(entrydate);
                       votcred.setRole("ROLE_VT1");
                     //  System.out.println("check 3");
                       votecredDao.updateCred(votcred);
                       //send mail
                      /// bulid model first
                       Map model = new HashMap();
                       String surname = ((String)validvotdet[6]!=null?" " + (String)validvotdet[6]:"");
                       String mddlename  = ((String)validvotdet[5]!=null?" " + (String)validvotdet[5]:"");
                       String firstname = ((String)validvotdet[4]!=null?(String)validvotdet[4]:"");
                       model.put("getUsername", firstname + mddlename + surname);
                       model.put("getAgmdescription", (String)validvotdet[7]);
                       model.put("uri", DBASE_URI+validvotdet[3]);
                       model.put("getEmail", (String)validvotdet[1]);
                       model.put("getClearpass", otp);
                       model.put("getStartdate", validvotdet[8].toString());
                       model.put("getStarttime", validvotdet[9].toString());
                       model.put("getEnddate", validvotdet[10].toString());
                       model.put("getEndtime", validvotdet[11].toString());
           
                       boolean res = vnotserv.firenotification(voterNotificationService,"voteractivateuser.ftl",model,(String)validvotdet[1], "Easycoop E-voting - Voter Activation mail");
                      if (res==true) {
                           //update cred and set sent attrib to 'Y' if the noticecall returns true where memberid and agmid = current
                          votecredDao.updatemailedCred((Integer)validvotdet[3],(Integer)validvotdet[0]);
                      }
	            }
                      row = row + 1;
                  }     
              }
	 }
}
