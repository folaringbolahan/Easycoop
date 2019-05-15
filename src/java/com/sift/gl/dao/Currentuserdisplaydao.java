/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.CurrentuserdisplayException;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Company;
import com.sift.gl.model.Moduledetails;
import com.sift.gl.model.Modulemenudetails;
import com.sift.gl.model.Users;
import java.sql.*;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.*;
import javax.sql.*;
///import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;
//import biomet.ejb.PersonException;
//import javax.ejb.LocalBean;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Currentuserdisplaydao implements Currentuserdisplayinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Currentuserdisplaydao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   
    @Override 
     public List<Moduledetails> retrieveModules(String role,Integer branch,Integer company) throws CurrentuserdisplayException {
        List allDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        String moduleActive = "Y";
        try {
         //rs =  dbobj.retrieveRecset("select distinct c.* FROM usergrpmdl a inner join modulemenu b on a.menu = b.menucode inner join modules c on b.module = c.code where a.usergroup = '" + role + "' and a.branchid = " + branch + " and a.companyid = " + company  + " order by c.sortorder");  
         rs =  dbobj.retrieveRecset("select distinct c.* FROM usergrpmdl a inner join modulemenu b on a.menu = b.menucode inner join modules c on b.module = c.code where active = '" + moduleActive + "' and a.usergroup = '" + role + "' and a.branchid = " + branch + " and a.companyid = " + company  + " order by c.sortorder");  
         
            while (rs.next()) {
             Moduledetails vDetails = new Moduledetails();
             vDetails.setCode(rs.getString("code"));
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setIcon(rs.getString("icon")); 
             vDetails.setSortorder(rs.getInt("sortorder"));
             vDetails.setModulerole(rs.getString("modulerole"));
             allDetails.add(vDetails);
        
         }  
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return allDetails;
    }
    
    @Override 
    public List<Modulemenudetails> retrieveModulemenus(String role,Integer branch,Integer company) throws CurrentuserdisplayException {
        List allDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.menu,b.* FROM usergrpmdl a inner join modulemenu b on a.menu = b.menucode where a.usergroup = '" + role + "' and a.branchid = " + branch + " and a.companyid = " + company  + " order by b.module,b.menusortorder");  
         while (rs.next()) {
             Modulemenudetails vDetails = new Modulemenudetails();
             vDetails.setMenucode(rs.getString("menucode"));
             vDetails.setMenupath(rs.getString("menupath")); 
             vDetails.setDisplaytext(rs.getString("displaytext")); 
             vDetails.setMenusortorder(rs.getInt("menusortorder"));
             vDetails.setModule(rs.getString("module"));
             vDetails.setMenurole(rs.getString("menurole"));
             allDetails.add(vDetails);
        
         }  
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return allDetails;
    }
    @Override 
    public Users retrieveUserdet(String userid) throws CurrentuserdisplayException {
        Users allDetails = new Users();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.* FROM users a where a.userid = '" + userid + "'");  
         while (rs.next()) {
             allDetails.setUserName(rs.getString("UserName"));
             allDetails.setUserId(userid);
             allDetails.setAccessLevel(rs.getString("accesslevel"));
             allDetails.setCompanyid(rs.getInt("companyid"));
             allDetails.setBranch(rs.getInt("branch"));
             allDetails.setMustChangePassword(rs.getString("must_change_pass"));
          }  
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
     } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        } 
        return allDetails;
    }
    
    @Override 
    public Company retrieveCompanydet(Integer branch,Integer company) throws CurrentuserdisplayException {
        Company vDetails = new Company();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        
        try {
         rs =  dbobj.retrieveRecset("select a.name,a.short_name,a.code,a.Country_id,b.id,b.currentyear,b.currentperiod,b.postdate,b.currentsystemdate,b.entrydate,c.currency_code as basecurrency,c.timez from company a inner join branch b on a.id = b.company_id inner join countries c on b.country_id = c.id where a.id = " + company  + " and b.id = " + branch);  
         while (rs.next()) {
             vDetails.setName(rs.getString("name"));
             vDetails.setShortName(rs.getString("short_name")); 
             vDetails.setCurrentPeriod(rs.getInt("CurrentPeriod")); 
             vDetails.setCurrentYear(rs.getInt("CurrentYear"));
             vDetails.setPostDate(rs.getDate("PostDate"));
             vDetails.setCurrentsystemDate(rs.getDate("CurrentsystemDate"));
             vDetails.setEntryDate(rs.getDate("EntryDate"));
             vDetails.setBaseCurrency(rs.getString("BaseCurrency"));
             vDetails.setCountryId(Integer.toString(rs.getInt("Country_id")));
             vDetails.setTimezone(rs.getString("Timez"));
             vDetails.setId(company);
             vDetails.setCode(rs.getString("Code"));
              vDetails.setStartofDay("");
             GetSetting procmtdsetg = new GetSetting();
             String procmtd = "";
             procmtd = procmtdsetg.GetSettingvalue("PROCESSINGMETHOD",rs.getInt("id"),company);
             if (procmtd.equalsIgnoreCase("MANUAL")==true) 
              {
                 if (Newday(rs.getDate("CurrentsystemDate"),rs.getString("Timez"))==true) {   
                    vDetails.setStartofDay("Application has not been initialized for the current date");
                 }   
                    vDetails.setProcmethod(procmtd);
              }  
         }
        } catch (SQLException ex) {
          throw new CurrentuserdisplayException(ex.getMessage());
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return vDetails;
    }
    
    public boolean Newday(java.util.Date gcurrsysdate,String timezon){
     boolean isnewday = false;
     String tempDate = null;
     String tempDate2 = null;
    // String tempDate3 = null;
    // String tempDate4 = null;
     TimeZone timeZone = TimeZone.getTimeZone(timezon);
     SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     formatter1.setTimeZone(timeZone);
     SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
     //formatter2.setTimeZone(timeZone);
    // formatter2.setTimeZone(timeZone);
     Calendar rightNow2 = Calendar.getInstance(timeZone);
     //Calendar rightNow3 = Calendar.getInstance(timeZone);
     java.util.Date dcurrdate;
     //java.util.Date dcurrdate2;
     if (gcurrsysdate==null) {
        // System.out.println("cab u");
        rightNow2.add(Calendar.DATE,-1);
        dcurrdate = rightNow2.getTime();
        //diff timezone
        
     }
     else
     {
        dcurrdate = gcurrsysdate;
        //System.out.println(dcurrdate.toString());
     }
     rightNow2 = Calendar.getInstance(timeZone);
     // the stored value should not be timezoned else it will be wrong offset
     tempDate = formatter2.format(dcurrdate);
     tempDate2 = formatter1.format(rightNow2.getTime());
     //
     //rightNow3 = Calendar.getInstance(timeZone);
     ///tempDate4 = formatter2.format(rightNow3.getTime());
     ///System.out.println(tempDate4 + " time zone us:los ang");
     if (tempDate.equals(tempDate2)==false){
        isnewday = true;
        System.out.println(tempDate2);
        System.out.println(tempDate);
     }
     return isnewday;
    }
}
