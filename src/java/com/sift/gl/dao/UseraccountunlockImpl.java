/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.model.Users;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class UseraccountunlockImpl {
    Useraccountunlockdao UseraccountunlockInterface;
    public UseraccountunlockImpl(){
         UseraccountunlockInterface = new Useraccountunlockdao(); //) initial.lookup("payrollsalarylevelstep");
    }
    
    public String editAccount(int id){
            try{
             UseraccountunlockInterface.updateAccount(id);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-UseraccountunlockImpl;mth-editunlockaccount;para-code:" + id  ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public List<Users> getAccounts(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = UseraccountunlockInterface.retrieveAccounts(branchid,companyid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
   
    public List<Users> getAccounts(int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = UseraccountunlockInterface.retrieveAccounts(companyid);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    
    public List<Users> getAccounts(){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = UseraccountunlockInterface.retrieveAccounts();
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    
    public Users getAccount(int id){
            Users dsalarylevelstep = new Users();
            try{
             dsalarylevelstep = UseraccountunlockInterface.retrieveAccount(id);
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return dsalarylevelstep;
    }
    
}
