/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import javax.persistence.EntityManager;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.GendataService;
import com.sift.gl.GenerateAccountno;
import com.sift.gl.model.Activitylog;
//import com.sun.jersey.api.spring.Autowire;
//import com.sun.jersey.api.spring.Autowire;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yomi-pc
 */
@Path("/glwsaudlog")
//@Autowire
public class AuditlogRESTFacade {
    String msgstr = "";
    public AuditlogRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    
    
    public Response create(Activitylog entity) {
        String retval = "";
        Response msg ;
        msgstr = "";
        String msgstrx = "";
        retval = this.createlog(entity);
        if (retval.isEmpty() == false) {
          msg = Response.created(URI.create(msgstrx)).entity(msgstr).build();
        }
        else {
           msg = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msgstr).build()  ;//serverError().build();
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
