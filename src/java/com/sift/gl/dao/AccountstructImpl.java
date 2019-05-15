/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Accountstructdao;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountstructuresdetails;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class AccountstructImpl {
    Accountstructdao AccountstructInterface;
    Accountgrpclassdao AccountstructclassInterface;
    Accountsegdao AccountsegInterface;
    //Cadre CadreInterface;
    //Salarylevel SalarylevelInterface;
    //private static Logger logger = Logger.getLogger(AccountstructImpl.class.getPackage().getName());
    public AccountstructImpl(){
        //try{
        // InitialContext initial = new InitialContext();
         AccountstructInterface = new Accountstructdao(); //) initial.lookup("payrollsalarylevelstep");
         AccountstructclassInterface = new Accountgrpclassdao();
         AccountsegInterface = new Accountsegdao();
         //CadreInterface = (Cadre) initial.lookup("payrollcadre");
         //SalarylevelInterface = (Salarylevel) initial.lookup("payrollsalarylevel");
         //BasicConfigurator.configure();
        //}        
        //catch(javax.naming.NamingException dtex)
        //{
        // System.err.println("Exception: " + dtex.getMessage());
        //}
       
    }
    public String addAccountstruct(String ddescription,String dcode,String delim,int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10,Integer dcompany){
            try{
             AccountstructInterface.addAccountstruct( ddescription,dcode,delim,seg1,seg2,seg3,seg4,seg5,seg6, seg7,seg8,seg9,seg10,dcompany);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountstructImpl;mth-addaccountgroup;para-reportgroup:" + dcode + ",-description:" + ddescription + ",-classid:" + dcompany + ";" ;
             System.out.println(errmess);
             //logger.error(errmess, dtex);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String removeAccountstruct(int dcode,Integer companyid){
            try{
             AccountstructInterface.deleteAccountstruct(dcode,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountstructImpl;mth-remoaccountgroup;para-code:" + dcode + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public String editAccountstruct(int did, String ddesc,String dcode,String delim,int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10,Integer dcompany){
            try{
             AccountstructInterface.updateAccountstruct(did, ddesc,dcode,delim,seg1,seg2,seg3,seg4,seg5,seg6, seg7,seg8,seg9,seg10,dcompany);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-AccountstructImpl;mth-editaccountstruct;para-code:" + dcode  + ",-description:" + ddesc + ",-coy:" + dcompany + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public List<Accountstructuresdetails> getAccountstructs(Integer companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = AccountstructInterface.retrieveAccountstructs(companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    public Accountstructuresdetails getAccountstruct(int code,Integer companyid){
            Accountstructuresdetails dsalarylevelstep = new Accountstructuresdetails();
            try{
             dsalarylevelstep = AccountstructInterface.retrieveAccountstruct(code,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    
    public List<Accountsegmentdetails> getAccountsegs(Integer dcompany){
            List allaccountgrpreps = new ArrayList();
            try{
             allaccountgrpreps = AccountsegInterface.retrieveDatalist4coy(dcompany);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpreps;
    }   
}
