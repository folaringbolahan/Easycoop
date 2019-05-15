/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountsegmentdetlist;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class AccountsegImpl {
    Accountsegdao AccountsegInterface;
    //private static Logger logger = Logger.getLogger(AccountgroupImpl.class.getPackage().getName());
    public AccountsegImpl(){
        //try{
        // InitialContext initial = new InitialContext();
         AccountsegInterface = new Accountsegdao(); //) initial.lookup("payrollsalarylevelstep");
         
    }
    public String addData(Accountsegmentdetlist acseglist,Integer dcompany){
           //System.out.println("here againbv - " + acseglist.getAccsegdets().size());
        try{
             AccountsegInterface.addData(acseglist,dcompany);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountsegImpl;mth-adddata;para-companyp:" + dcompany + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
    public List<Accountsegmentdetails> getAccountsegs(Integer companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountsegInterface.retrieveDatalist(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    public Accountsegmentdetails getAccountseg(Integer companyid){
            Accountsegmentdetails dsalarylevelstep = new Accountsegmentdetails();
            try{
             dsalarylevelstep = AccountsegInterface.retrieveData(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
   
}
