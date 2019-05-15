/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.NotificationService;
import com.sift.gl.dao.Fileuploaddao;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import java.util.*;
import java.util.concurrent.Future;
import javax.naming.InitialContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class FileuploadImpl {
    Fileuploaddao FileuploadInterface;
    public FileuploadImpl(){
        FileuploadInterface = new Fileuploaddao(); //) initial.lookup("payrollsalarylevelstep");
    }
    public String addFile(FileUpload fileUpload){
            try{
             FileuploadInterface.addFile(fileUpload);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-addfile;para-file:" + fileUpload.getBatchId() + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
     public String addFilecontents(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone){
            try{
             FileuploadInterface.addFilecontents(batchid,companyid,branchid,currency,dateopened,ipaddr,username,timezone);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-addfilecontents;para-file:" + batchid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String removeFile(FileUpload fileUpload){
            try{
             FileuploadInterface.removeFile(fileUpload);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-remofile;para-code:" + fileUpload.getBatchId() + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
     public String getSummaryreport(){
         String retstr = "";
            try{
             retstr = FileuploadInterface.getSummaryreport();
             return retstr;
            }
            catch(Exception dtex)
            {
             //System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-summaryreport;"  ;
             //System.out.println(errmess);
             //logger.error(errmess, dtex);
             return errmess;
             //log in log4 and rediect to error page
            }        
    }
     public String getDetailederrreport(String batchid,int companyid,int branchid){
         String retstr = "";   
         try{
             retstr = FileuploadInterface.getDetailederrreport(batchid, companyid, branchid);
             return retstr;
            }
            catch(Exception dtex)
            {
             //System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-detailederrreport;" ;
             //System.out.println(errmess);
             //logger.error(errmess, dtex);
             return errmess;
             //log in log4 and rediect to error page
            }        
    }
     
     public String addtxnFile(FileUpload fileUpload,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String acctno, Date txndate){
            try{
             FileuploadInterface.addtxnFile(fileUpload,companyid,branchid,currency,dateopened,ipaddr,username,timezone,acctno,txndate);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-addxnfile;para-file:" + fileUpload.getBatchId() + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
            
     public List<FileUploadBean> getAccttxns4apprv(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =FileuploadInterface.getAccttxns4apprv(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-getAccttxns4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }      
    public List<FileUploadBean> getJournaltxns4apprv(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =FileuploadInterface.getJournaltxns4apprv(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-getJournaltxns4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }   
    public List<FileUploadBean> getAccttxns4view(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =FileuploadInterface.getAccttxns4view(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-getAccttxns4view;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }   
     
     @Async
     public Future<String> runcheck(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String mailsubject,NotificationService notificationService){
           String returnmsg=""; 
           try{
             FileuploadInterface.addtxnFilecontents(batchid,companyid,branchid,currency,dateopened,ipaddr,username,timezone,mailsubject,notificationService);
            /* try {  
               System.out.println("start thread");
              Thread.sleep(25000);
              System.out.println("end thread");
             } catch (InterruptedException e) {  
               e.printStackTrace();  
             }
             */ 
             returnmsg= "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-addtxnfilecontent;para-file:" + batchid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             returnmsg= "notok";
             //log in log4 and rediect to error page
            }   
            return new AsyncResult<String>(returnmsg);
    }
     
     public void rejectFile(String batchid,int companyid,int branchid){
            try{
             FileuploadInterface.rejectFile(batchid,companyid,branchid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-rejectfile;para-file:" + batchid + ";" ;
             System.out.println(errmess);
             }        
    }
     public void rejectPostingjrnl(String batchid,int companyid,int branchid,String userid, String timeZonestr){
            try{
             FileuploadInterface.rejectPostingjrnl(batchid,companyid,branchid,userid,timeZonestr);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-FileuploadImpl;mth-rejectPostingjrnl;para-id:" + batchid + ";" ;
             System.out.println(errmess);
             }        
    }
     
}
