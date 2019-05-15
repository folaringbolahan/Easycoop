/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgrouprepException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import java.sql.*;
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
public class Accountgrprepdao implements Accountgrouprepinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Accountgrprepdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    @Override
    public void addAccountgrouprep(String description,String code) throws AccountgrouprepException {
    
    try {
         InsertAccountgrouprepDetails(description,code);
      } catch (Exception ex) {
           throw new AccountgrouprepException("Transaction failed: " + ex.getMessage());
      }
   }

    
    public void deleteAccountgrouprep(int id) throws AccountgrouprepException {
     String sqlStatement = "delete from accountgrouprepgrp where id = " +
            id + "";
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new AccountgrouprepException("Transaction failed: " + ex.getMessage());
      }
    }
    
   

    @Override 
    public String updateAccountgrouprep(int id,String description, String code)  throws AccountgrouprepException {
     String sqlStatement = "update accountgrouprepgrp set description = '" + description + "',code = '" + code + "' where id = " +
            id + "";
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new AccountgrouprepException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    @Override 
    public Accountgrprepdetails retrieveAccountgrouprep(int id) throws AccountgrouprepException {
        Accountgrprepdetails vaccountgroupDetails = new Accountgrprepdetails();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM accountgrouprepgrp where id = " + id + "");
         while (rs.next()) {  
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setCode(rs.getString("code"));
             }  
        } catch (SQLException ex) {
          throw new AccountgrouprepException(ex.getMessage());
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
        return vaccountgroupDetails;
    }
    @Override 
    public List<Accountgrprepdetails> retrieveAccountgroupreps() throws AccountgrouprepException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM accountgrouprepgrp ");  
         while (rs.next()) {
             Accountgrpclassdetails vaccountgroupDetails = new Accountgrpclassdetails();
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setCode(rs.getString("code")); 
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountgrouprepException(ex.getMessage());
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
        return allvaccountgroupDetails;
    }

 
    
    private void InsertAccountgrouprepDetails(String description,String code) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
      String sqlStatement = "insert into accountgrouprepgrp (description,code) values ('" +
             description + "','" + code  +  "')";
      dbobj.updateTablebatch(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
   
}
