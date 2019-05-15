/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgroupException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accountgroupdetail;
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
public class Accountgroupdao implements Accountgroupinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    /**
     *
     */
    public Accountgroupdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    /**
     *
     * @param description
     * @param acgrpid
     * @param classid
     * @param reportgroup
     * @param company
     * @throws AccountgroupException
     */
    @Override
    public void addAccountgroup(String description,int acgrpid,String classid,String reportgroup,int company) throws AccountgroupException {
    
    try {
         InsertAccountgroupDetails(description,acgrpid,classid,reportgroup,company);
      } catch (Exception ex) {
           throw new AccountgroupException("Transaction failed: " + ex.getMessage());
      }
   }

    
    /**
     *
     * @param groupid
     * @param companyid
     * @throws AccountgroupException
     */
    public void deleteAccountgroup(int groupid,int companyid) throws AccountgroupException {
     String sqlStatement = "delete from Accountgroups where groupid = " +
            groupid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new AccountgroupException("Transaction failed: " + ex.getMessage());
      }
    }
    
   

    /**
     *
     * @param groupid
     * @param acgrpid
     * @param description
     * @param classid
     * @param reportgroup
     * @param companyid
     * @return
     * @throws AccountgroupException
     */
    @Override 
    public String updateAccountgroup(int groupid,int acgrpid,String description, String classid, String reportgroup,int companyid)  throws AccountgroupException {
     String sqlStatement = "update Accountgroups set description = '" + description + "',acgroupid = " + acgrpid + ",classid = '" + classid + "',reportgroup = '" + reportgroup + "' where groupid = " +
            groupid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new AccountgroupException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    /**
     *
     * @param groupid
     * @param companyid
     * @return
     * @throws AccountgroupException
     */
    @Override 
    public Accountgroupdetail retrieveAccountgroup(int groupid,int companyid) throws AccountgroupException {
        Accountgroupdetail vaccountgroupDetails = new Accountgroupdetail();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Accountgroups where groupid = " + groupid + " and companyid = " + companyid);
         while (rs.next()) {  
             vaccountgroupDetails.setGroupId(rs.getInt("groupid"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setClassId(rs.getString("classid"));
             vaccountgroupDetails.setReportGroup(rs.getString("reportgroup"));
             vaccountgroupDetails.setAcGrpId(rs.getInt("acgroupid"));
         }  
        } catch (SQLException ex) {
          throw new AccountgroupException(ex.getMessage());
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
    /**
     *
     * @param companyid
     * @return
     * @throws AccountgroupException
     */
    @Override 
    public List<Accountgroupdetail> retrieveAccountgroups(int companyid) throws AccountgroupException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.*,b.description as classiddesc,c.description as reportgroupdesc FROM Accountgroups a left join accountgroupclass b on a.classid = b.code left join accountgrouprepgrp c on a.reportgroup = c.code where a.companyid  = " + companyid + " order by groupid asc");  
         while (rs.next()) {
             Accountgroupdetail vaccountgroupDetails = new Accountgroupdetail();
             vaccountgroupDetails.setGroupId(rs.getInt("groupid"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             //vaccountgroupDetails.setClassId(rs.getString("classid")); 
             //vaccountgroupDetails.setReportGroup(rs.getString("reportgroup"));
              vaccountgroupDetails.setClassId(rs.getString("classiddesc")); 
             vaccountgroupDetails.setReportGroup(rs.getString("reportgroupdesc"));
             vaccountgroupDetails.setAcGrpId(rs.getInt("acgroupid"));
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountgroupException(ex.getMessage());
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
        //dbobj = null; 
        return allvaccountgroupDetails;
    }

 
    
    private void InsertAccountgroupDetails(String description,int acgrpid,String classid,String reportgroup,int company) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
      String sqlStatement = "insert into Accountgroups (description,acgroupid,classid,reportgroup,companyid) values ('" +
             description + "'," + acgrpid + ",'" + classid + "','" + reportgroup + "'," + company  +  ")";
      dbobj.updateTablebatch(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
   
}
