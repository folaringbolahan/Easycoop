/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.dao.Settingsdao;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Settingsdet;
import java.util.*;
import javax.naming.InitialContext;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

public class SettingsImpl {
    Settingsdao SettingInterface;
    Accountgrpclassdao SettingclassInterface;
    Accountgrprepdao SettingrepInterface;
    public SettingsImpl(){
         SettingInterface = new Settingsdao(); //) initial.lookup("payrollsalarylevelstep");
         SettingclassInterface = new Accountgrpclassdao();
         SettingrepInterface = new Accountgrprepdao();
         
    }
   
    public String editSetting(String dset, String dvalue,int branchid,int companyid){
            try{
             SettingInterface.updateSetting(dset,dvalue,branchid,companyid);
             return "ok";
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
             String errmess;
             errmess = "cls-SettingImpl;mth-editsetting;para-newval:" + dset  + ",-oldval:" + dvalue + ",-branchid:" + branchid + ";" ;
             //logger.error(errmess, dtex);
             System.out.println(errmess);
             return "notok";
             //log in log4 and rediect to error page
            }        
    }
    public List<Settingsdet> getSettings(int branchid,int companyid){
            List allsalarylevelsteps = new ArrayList();
            try{
             allsalarylevelsteps = SettingInterface.retrieveSettings(branchid,companyid);
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allsalarylevelsteps;
    }
    public Settingsdet getSetting(String dvalue,int branchid,int companyid){
            Settingsdet dsalarylevelstep = new Settingsdet();
            try{
             dsalarylevelstep = SettingInterface.retrieveSetting(dvalue,branchid,companyid);
              
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
          //   allaccountgrpclasses = SettingclassInterface.retrieveSettingclasses();
              
            }
            catch(Exception dtex)
            {
             System.err.println("ParseException: " + dtex.getMessage());
            }        
            return allaccountgrpclasses;
    }
    
}
