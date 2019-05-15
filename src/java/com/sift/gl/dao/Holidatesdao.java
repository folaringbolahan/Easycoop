/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.HolidatesException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Holidatesdet;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class Holidatesdao implements Holidatesinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Holidatesdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   
    
    @Override
    public void addHolidate(String ddesc, String ddate,int drecurring,int branchid,int companyid) throws HolidatesException {
    
    try {
         InsertDetails(ddesc,ddate,drecurring,branchid,companyid);
      } catch (Exception ex) {
           throw new HolidatesException("Transaction failed: " + ex.getMessage());
      }
   }

    
    public void removeHolidate(int did,int branchid,int companyid) throws HolidatesException {
     String sqlStatement = "delete from holidates where id = " + did ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new HolidatesException("Transaction failed: " + ex.getMessage());
      }
    }
    
    @Override 
    public String updateHolidate(int did,String ddesc, String ddate,int drecurring,int branchid,int companyid)  throws HolidatesException {
     SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
     java.util.Date realdate = new java.util.Date();
     SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
     String dateinstring2 = "";
     try {
         realdate = formatter1.parse(ddate);
     } catch (ParseException e) {
		//e.printStackTrace();
     }
     try {
        dateinstring2 = formatter2.format(realdate);
     } catch( NullPointerException nullEx ) {   }
        String sqlStatement = "update Holidates set description = '" + ddesc + "',recurring = " + drecurring + ", holidate = {d '" + dateinstring2 + "'} where id = " + did + " and branchid = " +
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
          throw new HolidatesException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    @Override 
    public Holidatesdet retrieveHolidate(int did,int branchid,int companyid) throws HolidatesException {
        Holidatesdet vDetails = new Holidatesdet();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Holidates where id = " + did + " and branchid = " + branchid + " and companyid = " + companyid);
         while (rs.next()) {  
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             java.util.Date realdate = new java.util.Date();
             SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
             String dateinstring2 = "";
             try {
               realdate = formatter2.parse(rs.getString("holidate"));
             } catch (ParseException e) {
		//e.printStackTrace();
             }
             try {
               dateinstring2 = formatter1.format(realdate);
             } catch( NullPointerException nullEx ) {   }
             
             vDetails.setHolidate(dateinstring2);
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setRecurring(rs.getInt("recurring"));
             vDetails.setBranch(rs.getInt("branchid"));
             vDetails.setCompanyid(rs.getInt("companyid"));
             vDetails.setId(rs.getInt("id"));
         }  
        } catch (SQLException ex) {
          throw new HolidatesException(ex.getMessage());
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
    public List<Holidatesdet> retrieveHolidates(int branchid,int companyid) throws HolidatesException {
        List allvDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM Holidates where companyid  = " + companyid + " and branchid = " + branchid + "");  
         SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
         java.util.Date realdate = new java.util.Date();
         SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
         String dateinstring2 = "";
         while (rs.next()) {
             Holidatesdet vDetails = new Holidatesdet();
            
             try {
               realdate = formatter2.parse(rs.getString("holidate"));
             } catch (ParseException e) {
		//e.printStackTrace();
             }
             try {
               dateinstring2 = formatter1.format(realdate);
             } catch( NullPointerException nullEx ) {   }
             
             vDetails.setHolidate(dateinstring2);
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setRecurring(rs.getInt("recurring"));
             vDetails.setBranch(rs.getInt("branchid"));
             vDetails.setCompanyid(rs.getInt("companyid"));
             vDetails.setId(rs.getInt("id"));
             allvDetails.add(vDetails);
           
         }  
        } catch (SQLException ex) {
          throw new HolidatesException(ex.getMessage());
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

     private void InsertDetails(String description,String ddate,int drecurring,int branchid,int companyid) throws SQLException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon(); 
     SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
     java.util.Date realdate = new java.util.Date();
     SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
     String dateinstring2 = "";
     try {
         realdate = formatter1.parse(ddate);
     } catch (ParseException e) {
		//e.printStackTrace();
     }
     try {
        dateinstring2 = formatter2.format(realdate);
     } catch( NullPointerException nullEx ) {   }
      String sqlStatement = "insert into holidates (description,holidate,recurring,branchid,companyid) values ('" +
             description + "',{d '" + dateinstring2 + "'}," + drecurring + "," + branchid + "," + companyid  +  ")";
      dbobj.updateTablebatch(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
    }
}
