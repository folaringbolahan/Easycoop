/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Holidatesdao;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Holidatesdet;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class HolidatesImpl {
    Holidatesdao HolidateInterface;
    public HolidatesImpl(){
         HolidateInterface = new Holidatesdao(); //) initial.lookup("payrollsalarylevelstep");
    }
   
    public String addHolidate(String ddesc, String ddate,int drecurring,int branchid,int companyid){
            try{
             HolidateInterface.addHolidate(ddesc,ddate,drecurring,branchid,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HolidateImpl;mth-addHolidate;para-ddate:" + ddate + ",-description:" + ddesc + ",-drecurring:" + drecurring + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String removeHolidate(int dcode,int branchid,int companyid){
            try{
             HolidateInterface.removeHolidate(dcode,branchid,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HolidateImpl;mth-remoholidate;para-code:" + dcode + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    
    public String editHolidate(int did,String ddesc, String ddate,int drecurring,int branchid,int companyid){
            try{
             HolidateInterface.updateHolidate(did,ddesc,ddate,drecurring,branchid,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-HolidateImpl;mth-editsetting;para-date:" + ddate  + ",-desc:" + ddesc + ",-id:" + did + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public List<Holidatesdet> getHolidates(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = HolidateInterface.retrieveHolidates(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    public Holidatesdet getHolidate(int did,int branchid,int companyid){
            Holidatesdet dsalarylevelstep = new Holidatesdet();
            try{
             dsalarylevelstep = HolidateInterface.retrieveHolidate(did,branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    
}
