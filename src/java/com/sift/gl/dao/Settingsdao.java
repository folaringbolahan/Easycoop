/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.SettingsException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Settingsdet;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.*;
import javax.sql.*;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Settingsdao implements Settingsinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Settingsdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   

    @Override 
    public String updateSetting(String dset, String dvalue,int branchid,int companyid)  throws SettingsException {
     String sqlStatement = "update Settings set value = '" + dvalue + "' where setting = '" + dset + "' and branch = " +
            branchid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new SettingsException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    @Override 
    public Settingsdet retrieveSetting(String dsetting,int branchid,int companyid) throws SettingsException {
        Settingsdet vDetails = new Settingsdet();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Settings where setting = '" + dsetting + "' and branch = " + branchid + " and companyid = " + companyid);
         while (rs.next()) {  
             vDetails.setSetting(rs.getString("setting"));
             vDetails.setSettingval(rs.getString("value")); 
             vDetails.setDisplay(rs.getString("display"));
             vDetails.setBranch(rs.getInt("branch"));
             vDetails.setCompanyid(rs.getInt("companyid"));
             vDetails.setId(rs.getInt("id"));
         }  
        } catch (SQLException ex) {
          throw new SettingsException(ex.getMessage());
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
    @Override 
    public List<Settingsdet> retrieveSettings(int branchid,int companyid) throws SettingsException {
        List allvDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM Settings where companyid  = " + companyid + " and branch = " + branchid + " and display = 'Y'");  
         while (rs.next()) {
             Settingsdet vDetails = new Settingsdet();
             vDetails.setSetting(rs.getString("setting"));
             vDetails.setSettingval(rs.getString("value")); 
             vDetails.setDisplay(rs.getString("display"));
             vDetails.setBranch(rs.getInt("branch"));
             vDetails.setCompanyid(rs.getInt("companyid"));
             vDetails.setId(rs.getInt("id"));
             allvDetails.add(vDetails);
           
         }  
        } catch (SQLException ex) {
          throw new SettingsException(ex.getMessage());
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
        return allvDetails;
    }

    
}
