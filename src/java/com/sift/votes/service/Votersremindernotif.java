/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.gl.GendataService;
import com.sift.votes.dao.VotAgmDao;
import com.sift.votes.dao.VotecredDao;
import com.sift.votes.model.VotAgm;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author kola
 */
//@Service("votersremindernotifService")
@Component
public class Votersremindernotif {
    @Autowired
    private VotecredDao votecredDao;
    @Autowired
    private VoterscredgenService voterscredgenService;
    @Autowired
    private VoterNotificationService voterNotificationService;
    @Autowired
    private VotAgmDao votAgmDao;
    public void run() {
        processvalidagms();
        processpendingfirstnotif();
    }
   // loop through all non closed agms
     public void processvalidagms() {
         List<VotAgm> actagms = votecredDao.listActiveAgm();
         String  DBASE_URI = "";
         System.out.println("starting voting notification reminder...");
         if (actagms.size()>0) {
          try {
             javax.naming.Context ctx = new javax.naming.InitialContext();
             String uri = (String) ctx.lookup("java:comp/env/app.uri");
             DBASE_URI = uri + "/votes/login.htm";
          } catch (NamingException nx) {System.out.println("Error name exception" + nx.getMessage().toString());}
             VotersnotificationServiceImpl vnotserv = new VotersnotificationServiceImpl();
             //VoterNotificationService ntc = new VoterNotificationService();
         
         for(VotAgm dagm : actagms){
            List<Object[]> voterslist = votecredDao.getMembersofagmnotvoted(dagm.getId());
            int j = 0;
            for(Object[] validvotdet : voterslist)
                    {
		      //send mail
                      /// bulid model first
                        System.out.println("inside voting notification reminder...");
                        
                      j=j+1;
                      if (j>4) 
                      { 
                       j=0;   
                       try{
                        ///Thread.sleep(300000); //wait 5 min for Email server response - my mail server -only way or server-security treats as spam sending   
                       Thread.sleep(60000); //wait 1 min for Email server response
                       }
                       catch(InterruptedException iex){
                          System.out.println("Pausing Exception..."+ iex.getMessage() );  
                       }
		       System.out.println("Pausing..." );  
                      }  
                       Map model = new HashMap();
                       String surname = ((String)validvotdet[6]!=null?" " + (String)validvotdet[6]:"");
                       String mddlename  = ((String)validvotdet[5]!=null?" " + (String)validvotdet[5]:"");
                       String firstname = ((String)validvotdet[4]!=null?(String)validvotdet[4]:"");
                       model.put("getUsername", firstname + mddlename + surname);
                       model.put("getAgmdescription", (String)validvotdet[7]);
                       //model.put("uri", DBASE_URI);
                       model.put("uri", DBASE_URI+"?rf="+dagm.getId().toString());
                       model.put("getEmail", (String)validvotdet[1]);
                       model.put("getStartdate", validvotdet[8].toString());
                       model.put("getStarttime", validvotdet[9].toString());
                       model.put("getEnddate", validvotdet[10].toString());
                       model.put("getEndtime", validvotdet[11].toString());
          // System.out.println("inside voting notification reminder1...");
                       boolean res = vnotserv.firenotification(voterNotificationService,"voterreminduser.ftl",model,(String)validvotdet[1], "Easycoop E-voting - Voting Reminder");
                    }
             updateReminderdate(dagm.getId());
         }
        
     }
     }// end if
   // select the members of such agm
   // fire mail service
     
     public void processpendingfirstnotif() {
         List<VotAgm> actagms = votecredDao.listActiveAgm();
         if (actagms.size()>0) {
            for(VotAgm dagm : actagms){
               voterscredgenService.addvoterscred(dagm.getId());
            }
         }
     }
     
     public void updateReminderdate(Integer agmid) {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     String tempDate = null;
     Calendar rightNow = Calendar.getInstance();
     java.util.Date entrydate = rightNow.getTime();
     SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
     tempDate = formatter1.format(entrydate);
     String mySQLString = "update Vot_Agm set lastreminderdate={t '" + tempDate + "'} where id = "+ agmid;
     try {
         dbobj.updateTable(mySQLString);
      }
      catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
      }
      catch(NullPointerException ex) {
           System.out.println("NullPointerException: " + ex.getMessage());
      }
      finally {
         dbobj.closecon();
       }
    }
}
