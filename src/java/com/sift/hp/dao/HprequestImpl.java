/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.dao;
import com.sift.gl.NotificationService;
import com.sift.hp.dao.*;
import com.sift.gl.dao.Accountgroupdao;
import com.sift.hp.model.Hprequestdetails;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.hp.model.Hpintcalcmtd;
import com.sift.hp.model.Hpoperands;
import com.sift.hp.model.Hpproduct;
import com.sift.hp.model.Hprepaymtfreq;
import com.sift.hp.model.Hprepymtschddetails;
import com.sift.hp.model.Hpvalidationrules;
import com.sift.hp.model.Member;
import com.sift.hp.model.Productdetails;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class HprequestImpl {
    Hprequestdao HprequestInterface;
    //Salarylevel SalarylevelInterface;
    //private static Logger logger = Logger.getLogger(AccountgroupImpl.class.getPackage().getName());
    public HprequestImpl(){
        HprequestInterface = new Hprequestdao(); //) initial.lookup("payrollsalarylevelstep");
    }
    public String addRequest(String memberno,String prod,double HPprice,double Cashprice,double Interestamt,String invoiceref,String Interestcalcmtd,double InterestRate,java.util.Date TxnDate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths, int NoofPayments, int branchid,int companyid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.addRequest(memberno,prod,HPprice,Cashprice,Interestamt,invoiceref,Interestcalcmtd,InterestRate,TxnDate,DownpaymentAmount,RepaymentFrequency,RepaymentPeriodinMonths,NoofPayments,branchid,companyid,ipaddr, username,timezone,mailsubject,notificationService);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-addrequest;para-:" + memberno + ",-product:" + prod + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
    public List<Member> getMembers(int branchid,int companyid){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveMembers(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
    public List<Hpproduct> getHpproducts(int branchid,int companyid){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveHPproducts(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
    public List<Hprepaymtfreq> getHprepymtfreqs(){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveHPrepymtfreqs();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
    public List<Hpintcalcmtd> getHpintcalcmtds(){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveHpintcalcmtd();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
    
    public List<Hpoperands> getHpoperands(){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveHpoperands();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
    
    public List<Hprequestdetails> getHpreqs4apprv(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.getHpreqs4apprv(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreqs4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
    public Hprequestdetails getHpreq4apprv(String ref,int companyid,int branchid,String userid){
        Hprequestdetails allrecords = new Hprequestdetails();    
          try{
             allrecords =HprequestInterface.getHpreq4apprv(ref,companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreq4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    } 
    
    public List<Hpvalidationrules> getHpvalrules(String code,int branchid,int companyid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.retrieveHpvalrules(code,branchid,companyid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpvalrules;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
    
    public String saveHpdetails(Hprequestdetails hpdet,List<Hprepymtschddetails> hpschdet,int companyid,int branchid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.saveHpdetails(hpdet,hpschdet,companyid,branchid,ipaddr,username,timezone, mailsubject, notificationService);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-saveHpdetails;" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String cancelHpdetails(String ref,int companyid,int branchid,String ipaddr, String username,String timezone){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.cancelHpdetails(ref,companyid,branchid,ipaddr,username,timezone);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-cancelHpdetails;" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
    public List<Hprequestdetails> getHpreqs4sale(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.getHpreqs4sale(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreqs4s;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
    public Hprequestdetails getHpreq4sale(String ref,int companyid,int branchid,String userid){
        Hprequestdetails allrecords = new Hprequestdetails();    
          try{
             allrecords =HprequestInterface.getHpreq4sale(ref,companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreq4s;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    } 
    public String postHpsales(Hprequestdetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.postHpsales(hpdet,companyid,branchid,ipaddr,username,timezone,dyr,dper,dpostdate);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HpsalesImpl;mth-saveHpdetails;" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
    public List<Hprequestdetails> getHpreqsactive(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.getHpreqsactive(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreqs4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }   
    
    public Hpproduct getDproduct(String code,int branchid,int companyid){
            Hpproduct dsalarylevelstep = new Hpproduct();
            try{
             dsalarylevelstep = HprequestInterface.retrieveProductdet(code,branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    
     public String addRule(String code,String desc,String prodcode,String valtype,String formula,String resultcond, int branchid,int companyid,String ipaddr, String username,String timezone){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.addRule(code,desc,prodcode,valtype,formula,resultcond,branchid,companyid,ipaddr,username,timezone);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-addrule;para-:" + code + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
     public List<Hpvalidationrules> getAllhpvalidationrules(int branchid,int companyid){
            List allobjs = new ArrayList();
            try{
             allobjs = HprequestInterface.retrieveHpvalrules(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    }
     
    public Hpvalidationrules getHpvalidationrule(int id,int branchid,int companyid){
             Hpvalidationrules allobjs = new  Hpvalidationrules();
            try{
             allobjs = HprequestInterface.retrieveHpvalrule(id,branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allobjs;
    } 
    
    public String edithpvalidationrules(int dcode,String ddesc,String dformula,String dprod,String dcond,int dbranch,int dcompany){
            try{
             HprequestInterface.updatehpvalidationrules(dcode,ddesc,dformula,dprod,dcond,dbranch,dcompany);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-edithpvalidationrules;para-code:" + dcode  + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
     public String removesetting(int code,int branchid,int companyid){
            try{
             HprequestInterface.removesetting(code,branchid,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-removesetting;para-:" + code + ";" ;
             System.out.println(errmess);
             return "notok";
            }        
    }
     
     public List<Hprequestdetails> getHpreqs4singpymt(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.getHpreqs4singpymt(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreqs4singpymt;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
   
     public List<Hprepymtschddetails> getHpscdhdet(String refid,int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.getHpscdhdet(refid,companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpscdhdet;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }  

     public List<List<String>> generaterepymtschdlasstr(List<Hprepymtschddetails> listofobj){
        List<List<String>> allrecords = new ArrayList();    
          try{
             allrecords =HprequestInterface.generaterepymtschdlasstr(listofobj);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpscdhdet;para-file:" + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }  
     public Hprequestdetails getHpreq4disp(String ref,int companyid,int branchid,String userid){
        Hprequestdetails allrecords = new Hprequestdetails();    
          try{
             allrecords =HprequestInterface.getHpreq4disp(ref,companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreq4disp;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    } 
     
     public Hprepymtschddetails getHpreq4paysing(String refid,int did,int companyid,int branchid,String userid){
        Hprepymtschddetails allrecords = new Hprepymtschddetails();    
          try{
             allrecords =HprequestInterface.getHpreq4paysing(refid,did,companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-getHpreq4paysing;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }  
     
     public String postsingHpsales(Hprepymtschddetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate){
            try{
             String retvalstr = "";   
             retvalstr = HprequestInterface.postsingHpsales(hpdet,companyid,branchid,ipaddr,username,timezone,dyr,dper,dpostdate);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-saveHprepaysing;" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
     
     public double determinepenalty(double hpdetPrincipal,double hpdetInterest,java.util.Date hpdetPaymentdate,java.util.Date hpdetRpymtdate, String productcode, int branchid, int companyid){
            try{
             double retvalstr = 0.0;   
             retvalstr = HprequestInterface.determinepenalty(hpdetPrincipal,hpdetInterest,hpdetPaymentdate,hpdetRpymtdate,productcode,branchid,companyid);
             return retvalstr;
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HprequestImpl;mth-determinepenalty;" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return 0.0;
             //log in log4 and rediect to error page
            }        
    }
}
