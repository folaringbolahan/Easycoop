/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.GenericsiftException;
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
/** implementation class for hp transactions posting interface
 *
 * @author ABAYOMI AWOSUSI
 */

public class PosttxnsImpl {
    Posttxnsdao PosttxnsInterface;
    /**
     *
     */
    public PosttxnsImpl(){
        PosttxnsInterface = new Posttxnsdao(); //) initial.lookup("payrollsalarylevelstep");
    }
   
    /** asynchronous posting of transactions
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     */
    @Async
     public void post(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) {
            try{
             PosttxnsInterface.post(refid,companyid,branchid,paraid,userid,dyr,dper,dtimezone,dpostdate,ipaddr);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PosttxnsImpl;mth-post;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             }        
    }
    
    /** asynchronous posting of hp transactions
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     */
    @Async
     public void posthp(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) {
            try{
             PosttxnsInterface.posthp(refid,companyid,branchid,paraid,userid,dyr,dper,dtimezone,dpostdate,ipaddr);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PosttxnsImpl;mth-posthp;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             }        
    }
    
    public void updatefileuploadtbl(String refid,int branchid,int companyid,String veid,int isverified) {
            try{
             PosttxnsInterface.updatefileuploadtbl(refid, branchid, companyid, veid, isverified);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PosttxnsImpl;mth-posthp;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             }        
    }
    public void updatefileuploadtblhp(String refid,int branchid,int companyid,String veid,int isverified) {
            try{
             PosttxnsInterface.updatefileuploadtblhp(refid, branchid, companyid, veid, isverified);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PosttxnsImpl;mth-posthp;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             }        
    }
    public boolean updatependingentrydtbl(String refid,int branchid,int companyid,String veid,int isverified,String txnserialref,String txntyperef, String timeZonestr) {
           boolean dresult=false; 
           try{
             dresult = PosttxnsInterface.updatependingentrydtbl(refid, branchid, companyid, veid, isverified,txnserialref,txntyperef,timeZonestr);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PosttxnsImpl;mth-posthp;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             }  
            return dresult;
    }
    
   // @Async
     public String postapprvjrnl(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,Date dpostdate,String ipaddr) {
            String returnmsg="";
            try{
              returnmsg=PosttxnsInterface.postapprvjrnl(refid,companyid,branchid,paraid,userid,dyr,dper,dtimezone,dpostdate,ipaddr);
              //System.out.println("check7 " + returnmsg);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-PostjrnltxnsImpl;mth-post;para-file:" + refid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             } 
            return returnmsg;
    }
    
    /*
     @Async
     public Future<String> runcheck(String batchid,int companyid,int branchid,String currency, Date dateopened,String ipaddr, String username,String timezone,String mailsubject){
           String returnmsg=""; 
           try{
             FileuploadInterface.addtxnFilecontents(batchid,companyid,branchid,currency,dateopened,ipaddr,username,timezone,mailsubject);
            // try {  
            //   System.out.println("start thread");
             // Thread.sleep(25000);
             // System.out.println("end thread");
             //} catch (InterruptedException e) {  
              // e.printStackTrace();  
            // }
             
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
     */
     
}
