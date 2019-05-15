/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.GendataService;
import com.sift.gl.GenericsiftException;
import com.sift.gl.model.Users;
import java.sql.*;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.*;

///import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;
//import biomet.ejb.PersonException;
//import javax.ejb.LocalBean;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Useraccountunlockdao implements Useraccountunlockinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Useraccountunlockdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   
    @Override 
    public void updateAccount(int id)  throws GenericsiftException {
      String sqlStatement = "update users set accountNonLocked = 1 where id = " + id;
      GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      boolean opsuccessfull = dbobj.updateTable(sqlStatement);
      String opsuccessfullmsg = "OK";
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
     } 
     
    }
   
    
    @Override 
    public Users retrieveAccount(int id) throws GenericsiftException {
        Users vaccountgroupDetails = new Users();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Users where id = " + id);
         while (rs.next()) {  
             vaccountgroupDetails.setId(rs.getInt("id"));
             vaccountgroupDetails.setUserName(rs.getString("username")); 
             vaccountgroupDetails.setUserId(rs.getString("userid"));
              SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("lastmodified"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setLastmodifiedstr(dateopenedx);
         }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
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
    public List<Users> retrieveAccounts(int branchid,int companyid) throws GenericsiftException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.* FROM users a where a.companyid  = " + companyid + " and a.branch = " + branchid + " and a.accountnonlocked = 0");  
         while (rs.next()) {
             Users vaccountgroupDetails = new Users();
             vaccountgroupDetails.setId(rs.getInt("id"));
             vaccountgroupDetails.setUserName(rs.getString("username")); 
             vaccountgroupDetails.setUserId(rs.getString("userid"));
              SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("lastmodified"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setLastmodifiedstr(dateopenedx);
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
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

 @Override 
    public List<Users> retrieveAccounts(int companyid) throws GenericsiftException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.*,b.branch_name FROM users a inner join branch b on a.branch = b.id where a.companyid  = " + companyid + " and a.accountnonlocked = 0");  
         while (rs.next()) {
             Users vaccountgroupDetails = new Users();
             vaccountgroupDetails.setId(rs.getInt("id"));
             vaccountgroupDetails.setUserName(rs.getString("username")); 
             vaccountgroupDetails.setUserId(rs.getString("userid"));
              SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("lastmodified"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setLastmodifiedstr(dateopenedx);
             vaccountgroupDetails.setBranch(rs.getInt("branch"));
             vaccountgroupDetails.setCompanyid(rs.getInt("companyid"));
             vaccountgroupDetails.setJobCode(rs.getString("branch_name"));
             allvaccountgroupDetails.add(vaccountgroupDetails);
           }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
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
 
  @Override 
    public List<Users> retrieveAccounts() throws GenericsiftException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.*,b.branch_name,c.name as coyname FROM users a inner join branch b on a.branch = b.id inner join company c on a.companyid = c.id where a.accountnonlocked = 0");  
         while (rs.next()) {
             Users vaccountgroupDetails = new Users();
             vaccountgroupDetails.setId(rs.getInt("id"));
             vaccountgroupDetails.setUserName(rs.getString("username")); 
             vaccountgroupDetails.setUserId(rs.getString("userid"));
              SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("lastmodified"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setLastmodifiedstr(dateopenedx);
             vaccountgroupDetails.setBranch(rs.getInt("branch"));
             vaccountgroupDetails.setCompanyid(rs.getInt("companyid"));
             vaccountgroupDetails.setJobCode(rs.getString("branch_name"));
             vaccountgroupDetails.setDepartment(rs.getString("coyname"));
             allvaccountgroupDetails.add(vaccountgroupDetails);
           }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
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


}

