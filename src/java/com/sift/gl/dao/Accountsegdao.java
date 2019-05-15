/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountsegException;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountsegmentdetlist;
import com.sift.gl.GendataService;
import com.sift.gl.dao.Accountseginter;
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
public class Accountsegdao implements Accountseginter {
    
    public Accountsegdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    @Override
    public void addData(Accountsegmentdetlist acseglist,Integer company) throws AccountsegException {
    try {
         InsertDetails(acseglist,company);
      } catch (Exception ex) {
           throw new AccountsegException("Transaction failed: " + ex.getMessage());
      }
   }

    
    public void deleteData(Integer companyid) throws AccountsegException {
     
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     PreparedStatement psmt = dbobj.con.prepareStatement("delete from accountsegments where companyid = ?");
     psmt.setInt(6, companyid);
     psmt.executeUpdate();
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new AccountsegException("Transaction failed: " + ex.getMessage());
      }
    }
    
   

    
    @Override 
    public Accountsegmentdetails retrieveData(Integer companyid) throws AccountsegException {
        Accountsegmentdetails vaccountgroupDetails = new Accountsegmentdetails();
       /* GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        try {
         ResultSet rs =  dbobj.retrieveRecset("SELECT * FROM Accountgroups where groupid = " + groupid + "");
         while (rs.next()) {  
             vaccountgroupDetails.setGroupId(rs.getInt("groupid"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setClassId(rs.getString("classid"));
             vaccountgroupDetails.setReportGroup(rs.getString("reportgroup"));
             vaccountgroupDetails.setAcGrpId(rs.getInt("acgroupid"));
         }  
        } catch (SQLException ex) {
          throw new AccountsegException(ex.getMessage());
     } */
        return vaccountgroupDetails;
    }
    @Override 
    public List<Accountsegmentdetails> retrieveDatalist(Integer companyid) throws AccountsegException {
        List allvaccountsegDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
         ResultSet rs = null;  
         ResultSet rs2 = null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM accountsegmenttypes");  
         //ResultSet rs2;
         boolean inuse  = false;
         Integer dlen = 0;
         String dname = "";
         while (rs.next()) {
            rs2 =  dbobj.retrieveRecset("select * FROM accountsegments where companyid = " + companyid + " and type = '" +rs.getString("Segmenttype") + "'");  
            inuse  = false;
            dlen = rs.getInt("Defaultlength");
            dname = rs.getString("description");
            while (rs2.next()) {
                inuse  = true;
                dlen = rs2.getInt("length");
                dname = rs2.getString("name");
            }
             Accountsegmentdetails vaccountsegDetails = new Accountsegmentdetails();
             vaccountsegDetails.setSegmentid(rs.getInt("Acsegtypeid"));
             vaccountsegDetails.setName(dname); 
             vaccountsegDetails.setType(rs.getString("Segmenttype")); 
             vaccountsegDetails.setPreDefined(rs.getBoolean("Predefined"));
             vaccountsegDetails.setLength(dlen);
             vaccountsegDetails.setMandatory(rs.getBoolean("Mandatory"));
             vaccountsegDetails.setInuse(inuse);
             allvaccountsegDetails.add(vaccountsegDetails);
             //Integer i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountsegException(ex.getMessage());
       } 
        finally {
          try{  
            if (rs2!=null)
             {rs2.close();
            }
            if (rs!=null)
             {rs.close();
            }
            if (dbobj != null) {
             dbobj.closecon();
            }
            dbobj = null;
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          
       }
        return allvaccountsegDetails;
    }
 
    public List<Accountsegmentdetails> retrieveDatalist4coy(Integer companyid) throws AccountsegException {
        List allvaccountsegDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
         ResultSet rs = null;  
        try {
         rs =  dbobj.retrieveRecset("select * FROM accountsegments where companyid = " + companyid + "");  
             //Accountsegmentdetails vaccountsegDetails = new Accountsegmentdetails();
             //vaccountsegDetails.setSegmentid(0);
             //vaccountsegDetails.setName(""); 
             //vaccountsegDetails.setType(""); 
             //allvaccountsegDetails.add(vaccountsegDetails);
         while (rs.next()) {
             Accountsegmentdetails vaccountsegDetails = new Accountsegmentdetails();
             vaccountsegDetails.setSegmentid(rs.getInt("segmentid"));
             vaccountsegDetails.setName(rs.getString("name")); 
             vaccountsegDetails.setType(rs.getString("type")); 
             allvaccountsegDetails.add(vaccountsegDetails);
             //Integer i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountsegException(ex.getMessage());
       } 
        finally {
          try{  
            if (rs!=null)
             {rs.close();
            }
            if (dbobj != null) {
             dbobj.closecon();
            }
            dbobj = null;
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          
       }
        return allvaccountsegDetails;
    }
    
    private void InsertDetails(Accountsegmentdetlist acseglist,Integer company) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon(); 
     //System.out.println("Here now again1 --" + acseglist.getAccsegdets().size());
    /* String sqlStatement = "delete from accountsegments where companyid = '" +
            company + "'";
     dbobj.updateTable(sqlStatement);
     //dbobj.con.setAutoCommit(false);
     //System.out.println("Here now again0");*/
     PreparedStatement psmt = dbobj.con.prepareStatement("delete from accountsegments where companyid = ?");
     psmt.setInt(1, company);
     //System.out.println("Here now again1");
     psmt.executeUpdate();
     //System.out.println("Here now again2 --" + acseglist.getAccsegdets().size());
     //PreparedStatement psmt = dbobj.con.prepareStatement("insert into Accountsegments(Name,Type,Length,Mandatory,PreDefined,Companyid) values (?,?,?,?,?,?)");
     psmt = dbobj.con.prepareStatement("insert into Accountsegments(Name,Type,Length,Mandatory,PreDefined,Companyid,segmentid) values (?,?,?,?,?,?,?)"); 
     for (Accountsegmentdetails acseg : acseglist.getAccsegdets()) {
      psmt.setString(1, acseg.getName());
      psmt.setString(2, acseg.getType());
      psmt.setInt(3, acseg.getLength());
      psmt.setBoolean(4, acseg.getMandatory());
      psmt.setBoolean(5, acseg.getPredefined());
      psmt.setInt(6, company);
      psmt.setInt(7, acseg.getSegmentid());
      System.out.println("Here now again");
      psmt.executeUpdate();
     } 
     
     String sqlStatement2 = "";
     
    /* 
    // dbobj.con.commit();
      System.out.println("Here now again3 - " + acseglist.getAccsegdets().size());
     for (Accountsegmentdetails acseg : acseglist.getAccsegdets()) {
         System.out.println("Here now again4");
     // psmt.setString(6, company);
      sqlStatement2 = "insert into Accountsegments(Name,Type,Length,Mandatory,PreDefined,Companyid) values ('" +
            acseg.getName() + "','" + acseg.getType() + "'," + acseg.getLength() + "," + acseg.getMandatory() + "," + acseg.getPredefined() + ",'" + company + "')";
      dbobj.updateTable(sqlStatement2);
      System.out.println("Here now again");
      
     }
     */
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
   
}
