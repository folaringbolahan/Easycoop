/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.gl.ApplyEntrys;
import javax.persistence.EntityManager;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.GendataService;
import com.sift.gl.GenerateEntrys;
import com.sift.gl.model.Txnsheader;
//import com.sun.jersey.api.spring.Autowire;
//import com.sun.jersey.api.spring.Autowire;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yomi-pc
 */
@Path("/glws")
//@Autowire
public class TxnsdetRESTFacade {
    String msgstr = "";
    public TxnsdetRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @PreAuthorize("hasRole('ROLE_WEBSERVADMIN')")
    @Transactional
    
    public Response create(Entrys entity) {
        boolean retval = false;
        Response msg ;
        msgstr = "";
        String msgstrx = "";
        retval = this.updateEntrys(entity);
        if (retval == true) {
         // msg = Response.created(URI.create(msgstrx)).build(); //"OK";  
            msg = Response.created(URI.create(msgstrx)).entity(msgstr).build();
        }
        else {
             //msg = Response.serverError().build(); //"500";
             msg = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msgstr).build()  ;//serverError().build();
             
        }
       // return Response.created(URI.create(entity.getAccountNo().toString())).build();
        return msg;
    }
    
    public boolean updateEntrys(Entrys dEntrys){
        GenerateEntrys dgenentrys = new GenerateEntrys();
        dgenentrys.setAllentrys(dEntrys.getEntrys());
        boolean updateSuccessfull= false;
        if ((dgenentrys.validateSumofAmount()== true)&&(dgenentrys.allAccountsvalid()== true)&&(dgenentrys.datesvalid(dEntrys.getTxnsheader())==true)){
              Txnsheader thetxnheader =  dEntrys.getTxnsheader();//new Txnsheader("1", "JV",Company.getCurrentYear(),Company.getCurrentPeriod(),jDateChooser1.getDate(), Company.getPostDate(), jDateChooser1.getDate().toString(), Integer.toString(varSerial), "Journal Posting:" + Integer.toString(varSerial),"GL" ,CurrentUser.getUserId(), CurrentUser.getBranch());
              ApplyEntrys dataUpdate =  new ApplyEntrys(dgenentrys.getEntrys(),thetxnheader,thetxnheader.getYear(),thetxnheader.getPeriod(),thetxnheader.getBranchId(),thetxnheader.getCompanyId(),thetxnheader.getTimezone());
              if (dataUpdate.performAllupdates()== true) {
               msgstr = "Transaction with Ref.no {" + dataUpdate.varSerial + "} Saved";
                updateSuccessfull = true;
              }
              else
              {
                msgstr = "Exception: Error Updating - Journal cannot be posted";
                updateSuccessfull = false;
              }
            }
            else
            {
             msgstr = "Entrys not balanced or Accounts not valid";
             updateSuccessfull = false;
            }
        return updateSuccessfull;
    }
    
    
    @GET
    @Path("/gt")
    @Produces({"application/xml", "application/json"})
    //@PreAuthorize("hasRole('ROLE_GEN')")
    @PreAuthorize("hasRole('ROLE_WEBSERVADMIN')")
    @Transactional
    public String find() {
        return "<message1>Here now</message1>";
    }
   
}
