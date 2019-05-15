/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgroupclassException;
import com.sift.gl.GendataService;
import com.sift.gl.dao.Accountgroupclassinter;
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
public class Accountgrpclassdao implements Accountgroupclassinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Accountgrpclassdao () {
      //GendataService dbobj = new GendataService();
     // dbobj.inimkcon();
    }
    @Override
    public void addAccountgroupclass(String description,String code) throws AccountgroupclassException {
    
    try {
         InsertAccountgroupclassDetails(description,code);
      } catch (Exception ex) {
           throw new AccountgroupclassException("Transaction failed: " + ex.getMessage());
      }
   }

    
    public void deleteAccountgroupclass(int id) throws AccountgroupclassException {
     String sqlStatement = "delete from Accountgroupclass where id = " +
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
           throw new AccountgroupclassException("Transaction failed: " + ex.getMessage());
      }
    }
    
   

    @Override 
    public String updateAccountgroupclass(int id,String description, String code)  throws AccountgroupclassException {
     String sqlStatement = "update Accountgroupclass set description = '" + description + "',code = '" + code + "' where id = " +
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
          throw new AccountgroupclassException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    @Override 
    public Accountgrpclassdetails retrieveAccountgroupclass(int id) throws AccountgroupclassException {
        Accountgrpclassdetails vaccountgroupDetails = new Accountgrpclassdetails();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Accountgroupclass where id = " + id + "");
         while (rs.next()) {  
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setCode(rs.getString("code"));
             }  
        } catch (SQLException ex) {
          throw new AccountgroupclassException(ex.getMessage());
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
    public List<Accountgrpclassdetails> retrieveAccountgroupclasses() throws AccountgroupclassException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM Accountgroupclass ");  
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
          throw new AccountgroupclassException(ex.getMessage());
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

 
    
    private void InsertAccountgroupclassDetails(String description,String code) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
      String sqlStatement = "insert into Accountgroupclass (description,code) values ('" +
             description + "','" + code  +  "')";
      dbobj.updateTablebatch(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
   
}
