/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.dao;
import com.sift.gl.dao.*;
import com.sift.gl.NotificationService;
import com.sift.hp.dao.HPFileuploaddao;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Generictableviewbean;
import com.sift.hp.model.Hprepymtschddetails;
import java.util.*;
import java.util.concurrent.Future;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class HPFileuploadImpl {
    HPFileuploaddao HPFileuploadInterface;
    public HPFileuploadImpl(){
        HPFileuploadInterface = new HPFileuploaddao(); //) initial.lookup("payrollsalarylevelstep");
    }
    
    public String removeFile(FileUpload fileUpload){
            try{
             HPFileuploadInterface.removeFile(fileUpload);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-remofile;para-code:" + fileUpload.getBatchId() + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
     public String getSummaryreport(){
         String retstr = "";
            try{
             retstr = HPFileuploadInterface.getSummaryreport();
             return retstr;
            }
            catch(Exception dtex)
            {
             //System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-summaryreport;"  ;
             //System.out.println(errmess);
             //logger.error(errmess, dtex);
             return errmess;
             //log in log4 and rediect to error page
            }        
    }
     public String getDetailederrreport(String batchid,int companyid,int branchid){
         String retstr = "";   
         try{
             retstr = HPFileuploadInterface.getDetailederrreport(batchid, companyid, branchid);
             return retstr;
            }
            catch(Exception dtex)
            {
             //System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-detailederrreport;" ;
             //System.out.println(errmess);
             //logger.error(errmess, dtex);
             return errmess;
             //log in log4 and rediect to error page
            }        
    }
     
     public String addtxnFile(FileUpload fileUpload,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone){
            try{
             HPFileuploadInterface.addtxnFile(fileUpload,companyid,branchid,currency,dateopened,ipaddr,username,timezone);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-addxnfile;para-file:" + fileUpload.getBatchId() + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
            
     public List<FileUploadBean> getAccttxns4apprv(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HPFileuploadInterface.getAccttxns4apprv(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-getAccttxns4apprv;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
     public List<FileUploadBean> getAccttxns4view(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HPFileuploadInterface.getAccttxns4view(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-getAccttxns4view;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }  
     
     public List<Hprepymtschddetails> getAccttxns4dwn(int companyid,int branchid,String userid){
        List allrecords = new ArrayList();    
          try{
             allrecords =HPFileuploadInterface.getAccttxns4dwn(companyid,branchid,userid);
             }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-getAccttxns4dwn;para-file:" + companyid + ";" ;
             System.out.println(errmess);
             } 
          return allrecords;
    }    
     
     @Async
     public Future<String> runcheck(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String mailsubject,NotificationService notificationService){
           String returnmsg=""; 
           try{
             HPFileuploadInterface.addtxnFilecontents(batchid,companyid,branchid,currency,dateopened,ipaddr,username,timezone,mailsubject,notificationService);
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
             errmess = "cls-HPFileuploadImpl;mth-addtxnfilecontent;para-file:" + batchid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             returnmsg= "notok";
             //log in log4 and rediect to error page
            }   
            return new AsyncResult<String>(returnmsg);
    }
     
     public void rejectFile(String batchid,int companyid,int branchid){
            try{
             HPFileuploadInterface.rejectFile(batchid,companyid,branchid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;mth-rejectfile;para-file:" + batchid + ";" ;
             System.out.println(errmess);
             }        
    }
     
     public void writeListToExcel(List<Hprepymtschddetails> dlist, HttpServletResponse response,String filename) {
          try{
              HPFileuploadInterface.writeListToExcel(dlist, response, filename);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HPFileuploadImpl;writeListToExcel:" + ";" ;
             System.out.println(errmess);
             
            }
            
    } 
}
