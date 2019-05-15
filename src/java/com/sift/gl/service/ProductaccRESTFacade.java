/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import javax.persistence.EntityManager;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.GendataService;
import com.sift.gl.GenerateAccountno;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/** web service producer to handle account creation for member's products - deprecated 
 *
 * @author yomi-pc
 */
@Path("/glwsprdacno")
//@Autowire
public class ProductaccRESTFacade {
    String msgstr = "";
    /**
     *
     */
    public ProductaccRESTFacade() {
    }

    /**
     *
     * @param entity
     * @return
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @PreAuthorize("hasRole('ROLE_WEBSERVADMIN')")
    @Transactional
        
    public Response create(Accnowbs entity) {
        String retval = "";
        Response msg ;
        msgstr = "";
        String msgstrx = "";
        retval = this.generateacno(entity);
        if (retval.isEmpty() == false) {
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
    
    /**
     *
     * @param dEntity
     * @return
     */
    public String generateacno(Accnowbs dEntity){
        boolean updateSuccessfull= false;
        String newaccno = "";
        String newaccnofirst = "";
        GenerateAccountno daccnoent = new GenerateAccountno();
        
        newaccnofirst = daccnoent.createAccountno(dEntity.getProductcode(), dEntity.getCustomerno(), dEntity.getSubno(), dEntity.getBranchcode(), dEntity.getCompanycode(), dEntity.getBranchId(), dEntity.getCompany());
        if (newaccnofirst.isEmpty()==false) {
            if (performAllupdates(newaccnofirst,dEntity.getProductcode(),dEntity.getCompany(), dEntity.getBranchId(),dEntity.getSubno(),dEntity.getBranchcode(),dEntity.getCustomerno(),dEntity.getTimezone())== true) {
               msgstr = "OK. " + newaccnofirst;
               updateSuccessfull = true;
               newaccno = newaccnofirst;
            }
            else
            {
               msgstr = "Exception: Error Creating Account/AccountNo already exist";
               updateSuccessfull = false;
            }
            
        }
        else
        {
            msgstr = "Could not create a valid Account no";
            updateSuccessfull = false;
        }    
        //daccnoent
        
        return newaccno;
    }
    
     /**
     *
     * @param acno
     * @param productcode
     * @param companyid
     * @param branchid
     * @param subno
     * @param branchcode
     * @param customerno
     * @param timezon
     * @return
     */
    public boolean performAllupdates(String acno,String productcode,Integer companyid,Integer branchid,String subno,String branchcode,String customerno,String timezon){
         boolean updatesuccess = false;
         boolean updatesqlok = true;
         boolean updatesqlok2 = true;
         //updates accounts table
         GendataService dbobj = new GendataService();
         dbobj.inimkcon();
         boolean active = true;
         boolean closed = false;
         TimeZone timeZone = TimeZone.getTimeZone(timezon);
         SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
         formatter1.setTimeZone(timeZone);
         Calendar rightNow = Calendar.getInstance(timeZone);
         String dateopenedx = null;
         try {
           dateopenedx = formatter1.format(rightNow.getTime());
         } catch( NullPointerException nullEx ) {   }
          String acseg1 = "";
           String acseg2 = "";
           String acseg3 = "";
           String acseg4 = "";
           String acseg5 = "";
           String acseg6 = "";
           String acseg7 = "";
           String acseg8 = "";
           String acseg9 = "";
           String acseg10 = "";
            String acsegc = "";
         
           String prodname="";
           Integer prodacgrp = 0;
           String cntrlacno="";
           String prodcurr="";
           String baltype = "D";
           String limitchecktype = "";
           String prodnamex = "";
         try 
       {  
           
        
           String mySQLString;
      //mySQLString = "select a.name,a.code,a.product_type_code,a.segment_code,b.currency_code,d.product_account_type_id,d.gl_account_number,f.acgroup from products a inner join currency b on a.currency_id = b.id inner join productaccount d on a.id = d.product_id inner join productaccounttype e on d.product_account_type_id = e.id inner join accounts f on d.gl_account_number = f.accountno and a.company_id = f.Companyid and a.branch_id = f.Branch where a.code = '" + productcode + "' and a.company_id = " + companyid + " and e.code = 'CTR'"  ;
      mySQLString = "select a.name,a.code,a.product_type_code,a.segment_code,b.currency_code,d.product_account_type_code,d.gl_account_number,f.acgroup from products a inner join currency b on a.currency_id = b.id inner join productaccount d on a.id = d.product_id inner join accounts f on d.gl_account_number = f.accountno and a.company_id = f.Companyid and a.branch_id = f.Branch where a.code = '" + productcode + "' and a.company_id = " + companyid + " and a.branch_id = " + branchid + " and d.product_account_type_code = 'CTR' and a.is_active = 1"  ;
      ResultSet agRecSet;
      agRecSet = dbobj.retrieveRecset(mySQLString);
      //System.out.println(mySQLString);
       while (agRecSet.next()) {
           acseg1 = agRecSet.getString("segment_code");
           prodcurr = agRecSet.getString("currency_code");
           prodname = agRecSet.getString("name").trim();
           prodacgrp = agRecSet.getInt("acgroup");
           cntrlacno= agRecSet.getString("gl_account_number");
           if ((agRecSet.getString("product_type_code").equalsIgnoreCase("C")==true) || (agRecSet.getString("product_type_code").equalsIgnoreCase("S")==true)) {
            baltype = "C";
            limitchecktype = "Y";
           }
        }
        
       mySQLString = "select a.first_name,a.middle_name,a.surname from member a where a.company_id = " + companyid + " and a.branch_id = " + branchid + " and member_no = '" + customerno + "' and status_id = 2"  ;
       agRecSet = dbobj.retrieveRecset(mySQLString);
       //System.out.println(mySQLString); 
       String memname = "";
       while (agRecSet.next()) {
           memname =  memname + agRecSet.getString("first_name").trim();
           memname =  memname.trim() + " " + agRecSet.getString("middle_name").trim();
           memname =  memname.trim() + " " + agRecSet.getString("surname").trim();
        }
       
       
       if ((prodcurr.isEmpty()==false)&&(prodname.isEmpty()==false)&&(prodacgrp!=0)&&(cntrlacno.isEmpty()==false)&&(memname.isEmpty()==false)) {    
        
        acseg2 = customerno;
        acseg3 = subno;
        acseg4 = branchcode;
        acsegc = customerno;   
        prodname = memname + " - " + prodname;
        prodnamex = prodname.replace("'","''");
        String sqlStatement = "insert into accounts (AccountNo,Name,AcGroup,AcStruct,Currency,ControlAccountno,subaccount,BalanceType,CCyBalance,CCyClearedBalance,Balance,ClearedBalance,Active,Closed,Blocked,branch,companyid,aseg1,aseg2,aseg3,aseg4,aseg5,aseg6,aseg7,aseg8,aseg9,aseg10,asegc,dateopened,accounttype)" +
                   " values('" + acno + "','" + prodnamex + "'," + prodacgrp + ",'" + "DEFAULTMEMBER" + "','" + prodcurr + "','" + cntrlacno + "',1,'" + baltype + "',0,0,0,0," + active + "," + closed + "," + 0 + "," + branchid + "," + companyid + ",'" +
                     acseg1 + "','" + acseg2 + "','" + acseg3 + "','" + acseg4 + "','" + acseg5 + "','" + acseg6 + "','" + acseg7 + "','" + acseg8 + "','" + acseg9 + "','" + acseg10 + "','" + acsegc + "',{d '" + dateopenedx + "'},'M')";
        //System.out.println(sqlStatement); 
        dbobj.con.setAutoCommit(false);
         updatesqlok = dbobj.updateTablebatch(sqlStatement);
         
        sqlStatement = "INSERT INTO custaccountdetails(AccountNo, Product, AccountSet, Title,  Branchid, Companyid,limitchecktype,accountlimit) " +
                   " values('" + acno + "','" + productcode + "','" + acseg1 + "','" + prodnamex + "'," + branchid + "," + companyid + ",'" + limitchecktype + "',0)" ;
        //System.out.println(sqlStatement);  
        updatesqlok2 = dbobj.updateTablebatch(sqlStatement);
         
         dbobj.con.commit();
         
         updatesuccess = true;
         if ((updatesqlok == false )||(updatesqlok2 == false)){
             updatesuccess = false;
             msgstr = "AccountNo already exist";
         }
       }  
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
