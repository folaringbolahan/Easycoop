/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import javax.persistence.EntityManager;
import com.sift.gl.model.SMSBean;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
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
import com.sift.loan.utility.SMSUtility;

/**
 *
 * @author chris-Faseun
 */
@Path("/glwssms")
//@Autowire
public class SMSRESTFacade {
    String msgstr = "";
    SMSUtility UTIL=new SMSUtility();
    
    public SMSRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    
    public Response create(SMSBean entity) {
        String retval = "";
        Response msg ;
        msgstr = "";
        String msgstrx = "";
        retval = this.createSMS(entity);
        if (retval.isEmpty() == false) {
          msg = Response.created(URI.create(msgstrx)).entity(msgstr).build();
        }
        else{
           msg = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msgstr).build()  ;//serverError().build();
        }
        
        return msg;
    }
    
    public String createSMS(SMSBean dEntity){
        boolean success= false;
        
        try {
			//if (UTIL.sendSMSPost(dEntity)!=null){
			if (UTIL.sendSMSPostNew(dEntity)!=null){
			       msgstr = "OK. ";
			       success = true;
			}
			else
			{
			       msgstr = "Exception: Error Sending SMS";
			       success = false;
			}
		} catch (Exception e) {
			msgstr = "Exception: Error Sending SMS";
		    success = false;
			e.printStackTrace();
		}
		
        return msgstr;
    }  
}