/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountstructException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accountstructuresdetails;
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
public class Accountstructdao implements Accountstructinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Accountstructdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    @Override
    public void addAccountstruct(String description,String code,String delim, int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10,Integer company) throws AccountstructException {
    
    try {
         InsertAccountstructDetails(description,code,delim,seg1,seg2,seg3,seg4,seg5,seg6,seg7,seg8,seg9,seg10,company);
      } catch (Exception ex) {
           throw new AccountstructException("Transaction failed: " + ex.getMessage());
      }
   }

    
    public void deleteAccountstruct(int groupid,Integer companyid) throws AccountstructException {
     String sqlStatement = "delete from Accountstructures where acstrid = " +
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
           throw new AccountstructException("Transaction failed: " + ex.getMessage());
      }
    }
    
   

    @Override 
    public String updateAccountstruct(int groupid,String description, String code, String delim,int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10, Integer company)  throws AccountstructException {
     String sqlStatement = "update Accountstructures set description = '" + description + "',seg1 = " + seg1 + ",seg2 = " + seg2 + ",seg3 = " + seg3 + ",seg4 = " + seg4 + ",seg5 = " + seg5 + ",seg6 = " + seg6 + ",seg7 = " + seg7 + ",seg8 = " + seg8 + ",seg9 = " + seg9 + ",seg10 = " + seg10 + ",delimiter = '" + delim + "' where acstrid = " +
            groupid + " and companyid = " + company;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new AccountstructException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    @Override 
    public Accountstructuresdetails retrieveAccountstruct(int groupid,Integer companyid) throws AccountstructException {
        Accountstructuresdetails vaccountgroupDetails = new Accountstructuresdetails();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT a.* FROM Accountstructures a where AcStrID = " + groupid + " and a.companyid = " + companyid);
         while (rs.next()) {  
             vaccountgroupDetails.setAcStrId(rs.getInt("AcStrId"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setStructurecode(rs.getString("Structurecode")); 
             vaccountgroupDetails.setSeg1(rs.getInt("Seg1"));
             vaccountgroupDetails.setSeg2(rs.getInt("Seg2"));
             vaccountgroupDetails.setSeg3(rs.getInt("Seg3"));
             vaccountgroupDetails.setSeg4(rs.getInt("Seg4"));
             vaccountgroupDetails.setSeg5(rs.getInt("Seg5"));
             vaccountgroupDetails.setSeg6(rs.getInt("Seg6"));
             vaccountgroupDetails.setSeg7(rs.getInt("Seg7"));
             vaccountgroupDetails.setSeg8(rs.getInt("Seg8"));
             vaccountgroupDetails.setSeg9(rs.getInt("Seg9"));
             vaccountgroupDetails.setSeg10(rs.getInt("Seg10"));
            
         }  
        } catch (SQLException ex) {
          throw new AccountstructException(ex.getMessage());
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
    public List<Accountstructuresdetails> retrieveAccountstructs(Integer companyid) throws AccountstructException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.*,b1.name as segdesc1,b2.name as segdesc2,b3.name as segdesc3,b4.name as segdesc4," + 
                  "b5.name as segdesc5,b6.name as segdesc6,b7.name as segdesc7,b8.name as segdesc8,b9.name as segdesc9,b10.name as segdesc10 FROM Accountstructures a " + 
                 " left join accountsegments b1 on a.seg1 = b1.segmentid and a.companyid = b1.companyid " + 
                 " left join accountsegments b2 on a.seg2 = b2.segmentid and a.companyid = b2.companyid " + 
                 " left join accountsegments b3 on a.seg3 = b3.segmentid and a.companyid = b3.companyid " + 
                 " left join accountsegments b4 on a.seg4 = b4.segmentid and a.companyid = b4.companyid " +
                 " left join accountsegments b5 on a.seg5 = b5.segmentid and a.companyid = b5.companyid " + 
                 " left join accountsegments b6 on a.seg6 = b6.segmentid and a.companyid = b6.companyid " +
                 " left join accountsegments b7 on a.seg7 = b7.segmentid and a.companyid = b7.companyid " + 
                 " left join accountsegments b8 on a.seg8 = b8.segmentid and a.companyid = b8.companyid " +
                 " left join accountsegments b9 on a.seg9 = b9.segmentid and a.companyid = b9.companyid " + 
                 " left join accountsegments b10 on a.seg10 = b10.segmentid and a.companyid = b10.companyid " +
                 "where  a.companyid = " + companyid);
         while (rs.next()) {
             Accountstructuresdetails vaccountgroupDetails = new Accountstructuresdetails();
             vaccountgroupDetails.setAcStrId(rs.getInt("AcStrId"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setStructurecode(rs.getString("Structurecode")); 
             vaccountgroupDetails.setSeg1(rs.getInt("Seg1"));
             vaccountgroupDetails.setSeg2(rs.getInt("Seg2"));
             vaccountgroupDetails.setSeg3(rs.getInt("Seg3"));
             vaccountgroupDetails.setSeg4(rs.getInt("Seg4"));
             vaccountgroupDetails.setSeg5(rs.getInt("Seg5"));
             vaccountgroupDetails.setSeg6(rs.getInt("Seg6"));
             vaccountgroupDetails.setSeg7(rs.getInt("Seg7"));
             vaccountgroupDetails.setSeg8(rs.getInt("Seg8"));
             vaccountgroupDetails.setSeg9(rs.getInt("Seg9"));
             vaccountgroupDetails.setSeg10(rs.getInt("Seg10"));
              vaccountgroupDetails.setSegdesc1(rs.getString("Segdesc1"));
             vaccountgroupDetails.setSegdesc2(rs.getString("Segdesc2"));
             vaccountgroupDetails.setSegdesc3(rs.getString("Segdesc3"));
             vaccountgroupDetails.setSegdesc4(rs.getString("Segdesc4"));
             vaccountgroupDetails.setSegdesc5(rs.getString("Segdesc5"));
             vaccountgroupDetails.setSegdesc6(rs.getString("Segdesc6"));
             vaccountgroupDetails.setSegdesc7(rs.getString("Segdesc7"));
             vaccountgroupDetails.setSegdesc8(rs.getString("Segdesc8"));
             vaccountgroupDetails.setSegdesc9(rs.getString("Segdesc9"));
             vaccountgroupDetails.setSegdesc10(rs.getString("Segdesc10"));
             vaccountgroupDetails.setDelimiter(rs.getString("Delimiter"));
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountstructException(ex.getMessage());
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

 
    
    private void InsertAccountstructDetails(String description,String code,String delim, int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10,int company) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
      String sqlStatement = "insert into Accountstructures (description,structurecode,seg1,seg2,seg3,seg4,seg5,seg6,seg7,seg8,seg9,seg10,delimiter,companyid) values ('" +
             description + "','" + code + "'," + seg1 + "," + seg2 + "," + seg3 + "," + seg4 + "," + seg5 + "," + seg6 + "," + seg7 + "," + seg8 + "," + seg9 + "," + seg10 + ",'" + delim + "'," + company  +  ")";
      dbobj.updateTablebatch(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
   
}
