/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import com.sift.gl.model.Activitylog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author yomi-pc
 */

public class AuditlogService {
    String msgstr = "";
    public AuditlogService() {
    }

    public String create(Activitylog entity) {
        String retval = "";
        String msg ;
        msgstr = "";
        String msgstrx = "";
        retval = this.createlog(entity);
        if (retval.isEmpty() == false) {
          msg = retval;
        }
        else {
           msg = "Exception: Error Creating Log"; 
        }
        return msg;
    }
    
    public String createlog(Activitylog dEntity){
        boolean updateSuccessfull= false;
        if (performAllupdates(dEntity)==true) {
               msgstr = "OK. ";
               updateSuccessfull = true;
        }
        else
        {
               msgstr = "Exception: Error Creating Log";
               updateSuccessfull = false;
        }
        return msgstr;
    }
    
     public boolean performAllupdates(Activitylog aent){
         boolean updatesuccess = false;
         //updates accounts table
         GendataService dbobj = new GendataService();
         dbobj.inimkcon();
         boolean active = true;
         boolean closed = false;
         TimeZone timeZone = TimeZone.getTimeZone(aent.getTimezone());
         SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
         formatter1.setTimeZone(timeZone);
         Calendar rightNow = Calendar.getInstance(timeZone);
         String datelogx = null;
         try {
           datelogx = formatter1.format(rightNow.getTime());
         } catch( NullPointerException nullEx ) {   }
         
         try 
         {  
           
             
       if (aent.getDescription().isEmpty()==true ) {      
       String mySQLString;
      //mySQLString = "select a.event_name,a.event_id,a.event_short from event a where a.event_id = '" + aent.getEvent() + "' or a.event_short = '" + aent.getAction() + "'"  ;
       mySQLString = "select a.event_name,a.event_id,a.event_short from event a where a.event_short = '" + aent.getAction() + "'"  ;
      System.out.println(mySQLString);
       ResultSet agRecSet;
      agRecSet = dbobj.retrieveRecset(mySQLString);
      
       while (agRecSet.next()) {
           aent.setDescription(agRecSet.getString("event_name"));
       }
       }
       
        String sqlStatement = "insert into activity_log (event_id, ipaddress, username, description, action_date, action, action_item, action_result, branch_id, company_id)" +
                   " values(" + aent.getEvent() + ",'" + aent.getIpaddress() + "','" + aent.getUsername() + "','" + aent.getDescription() + "',{t '" + datelogx + "'},'" + aent.getAction() + "','" + aent.getActionItem() + "','" + aent.getActionResult() + "'," + aent.getBranchid() + "," + aent.getCompanyid() + ")";
         //dbobj.con.commit();
         dbobj.updateTablebatch(sqlStatement);
       
         updatesuccess = true;
        
      }
       catch (SQLException ex) {
           updatesuccess = false;
          System.out.println(ex.getMessage());
      } 
        
       finally {
           if (dbobj != null) {
            dbobj.closecon();
           }
        } 
       
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
         //update custaccountstable
        return updatesuccess;
     }
}
