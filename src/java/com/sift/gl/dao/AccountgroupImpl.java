/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Accountgroupdao;
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

public class AccountgroupImpl {
    Accountgroupdao AccountgroupInterface;
    Accountgrpclassdao AccountgroupclassInterface;
    Accountgrprepdao AccountgrouprepInterface;
    //Cadre CadreInterface;
    //Salarylevel SalarylevelInterface;
    //private static Logger logger = Logger.getLogger(AccountgroupImpl.class.getPackage().getName());
    public AccountgroupImpl(){
        //try{
        // InitialContext initial = new InitialContext();
         AccountgroupInterface = new Accountgroupdao(); //) initial.lookup("payrollsalarylevelstep");
         AccountgroupclassInterface = new Accountgrpclassdao();
         AccountgrouprepInterface = new Accountgrprepdao();
         //CadreInterface = (Cadre) initial.lookup("payrollcadre");
         //SalarylevelInterface = (Salarylevel) initial.lookup("payrollsalarylevel");
         //BasicConfigurator.configure();
        //}        
        //catch(javax.naming.NamingException dtex)
        //{
        // System.err.println("Exception: " + dtex.getMessage());
        //}
       
    }
    public String addAccountgroup(String ddescription,int acgrpid, String dclassid,String dreportgroup,int dcompany){
            try{
             AccountgroupInterface.addAccountgroup(ddescription,acgrpid,dclassid,dreportgroup,dcompany);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountgroupImpl;mth-addaccountgroup;para-reportgroup:" + dreportgroup + ",-description:" + ddescription + ",-classid:" + dclassid + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String removeAccountgroup(int dcode,int companyid){
            try{
             AccountgroupInterface.deleteAccountgroup(dcode,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountgroupImpl;mth-remoaccountgroup;para-code:" + dcode + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String editAccountgroup(int dcode,int acgrpid, String ddesc,String dcad,String dsal,int companyid){
            try{
             AccountgroupInterface.updateAccountgroup(dcode,acgrpid,ddesc,dcad,dsal,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountgroupImpl;mth-editaccountgroup;para-code:" + dcode  + ",-description:" + ddesc + ",-cad:" + dcad + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public List<Accountgroupdetail> getAccountgroups(int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountgroupInterface.retrieveAccountgroups(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    public Accountgroupdetail getAccountgroup(int code,int companyid){
            Accountgroupdetail dsalarylevelstep = new Accountgroupdetail();
            try{
             dsalarylevelstep = AccountgroupInterface.retrieveAccountgroup(code,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    public List<Accountgrpclassdetails> getAccountgrpclasses(){
            List allaccountgrpclasses = new ArrayList();
            try{
             allaccountgrpclasses = AccountgroupclassInterface.retrieveAccountgroupclasses();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpclasses;
    }
    public List<Accountgrprepdetails> getAccountgrpreps(){
            List allaccountgrpreps = new ArrayList();
            try{
             allaccountgrpreps = AccountgrouprepInterface.retrieveAccountgroupreps();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpreps;
    }   
}
