/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Accountgroupdao;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class AccountsetupImpl {
    Accountsetupdao AccountsetupInterface;
    Accountgroupdao AccountgroupInterface;
    Accountstructdao AccountstructInterface;
    //Cadre CadreInterface;
    //Salarylevel SalarylevelInterface;
    //private static Logger logger = Logger.getLogger(AccountgroupImpl.class.getPackage().getName());
    /**
     *
     */
    public AccountsetupImpl(){
        //try{
        // InitialContext initial = new InitialContext();
         AccountsetupInterface = new Accountsetupdao(); //) initial.lookup("payrollsalarylevelstep");
         AccountgroupInterface = new Accountgroupdao();
         AccountstructInterface = new Accountstructdao();
         //CadreInterface = (Cadre) initial.lookup("payrollcadre");
         //SalarylevelInterface = (Salarylevel) initial.lookup("payrollsalarylevel");
         //BasicConfigurator.configure();
        //}        
        //catch(javax.naming.NamingException dtex)
        //{
        // System.err.println("Exception: " + dtex.getMessage());
        //}
       
    }
    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @return
     */
    public String addAccount(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone){
            String retmess = "";
            try{
             retmess = AccountsetupInterface.addAccount(acno,name,acgrp,acstruct,currency, dateopened,baltype,cntrlac,cntrlacno,activeorclosed,blocked,branchid,companyid,ipaddr, username,timezone);
             return retmess;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountImpl;mth-addaccount;para-reportgroup:" + acno + ",-description:" + name + ",-classid:" + acgrp + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @return
     */
    public String removeAccount(String acno,int branchid,int companyid,String ipaddr, String username,String timezone){
            try{
             String returnmess = "";   
             returnmess = AccountsetupInterface.deleteAccount(acno,branchid,companyid, ipaddr, username, timezone);
             return returnmess;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountgroupImpl;mth-remoaccountgroup;para-code:" + acno + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @return
     */
    public String editAccount(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone){
            try{
             AccountsetupInterface.updateAccount(acno,name,acgrp,acstruct,currency, dateopened,baltype,cntrlac,cntrlacno,activeorclosed,blocked,branchid,companyid,ipaddr, username,timezone);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountImpl;mth-editaccount;para-code:" + acno  + ",-description:" + name + ",-cad:" + acgrp + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     */
    public List<Account> getAccounts(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountsetupInterface.retrieveAccounts(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     */
    public List<Account> getControlAccounts(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountsetupInterface.retrieveControlAccounts(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     */
    public List<Account> getAllaccounts(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountsetupInterface.retrieveallAccounts(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @return
     */
    public Account getAccount(String acno,int branchid,int companyid){
            Account dsalarylevelstep = new Account();
            try{
             dsalarylevelstep = AccountsetupInterface.retrieveAccount(acno,branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    /**
     *
     * @param companyid
     * @return
     */
    public List<Accountgroupdetail> getAccountgroups(int companyid){
            List allaccountgrpclasses = new ArrayList();
            try{
             allaccountgrpclasses = AccountgroupInterface.retrieveAccountgroups(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpclasses;
    }
    /**
     *
     * @param companyid
     * @return
     */
    public List<Accountgrprepdetails> getAccountstructs(Integer companyid){
            List allaccountgrpreps = new ArrayList();
            try{
             allaccountgrpreps = AccountstructInterface.retrieveAccountstructs(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpreps;
    }
    /**
     *
     * @param acno
     * @param acstr
     * @param companyid
     * @return
     */
    public String getAccountsegs(String acno,String acstr,int companyid) {
        String allaccountsegs = "";
            try{
             allaccountsegs = AccountsetupInterface.getAcSegmessage(acno, acstr, companyid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountsegs;
    }
}
