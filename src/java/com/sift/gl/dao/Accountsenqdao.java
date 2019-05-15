/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import java.sql.*;
//import java.sql.SQLException;
import java.util.*;

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
public class Accountsenqdao implements Accountsenqinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    /**
     *
     */
    public Accountsenqdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws Exception
     */
    @Override 
    public List<Account> retrieveAccounts(int branchid,int companyid) throws Exception {
        List allvaccountDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select accountno,name,balance FROM accounts where branch = " + branchid + " and companyid = " + companyid + " order by accountno asc ");  
         while (rs.next()) {
             Account vaccountDetails = new Account();
             vaccountDetails.setAccountNo(rs.getString("accountno")); 
             vaccountDetails.setName(rs.getString("name") + " - " + rs.getString("accountno")); 
             vaccountDetails.setBalance(rs.getDouble("balance")); 
             allvaccountDetails.add(vaccountDetails);
         }  
        } catch (SQLException ex) {
          throw new Exception(ex.getMessage());
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
        return allvaccountDetails;
    }
 
    
    @Override
    public List<Account> retrieveAccountswthoutcntrl(int branchid,int companyid) throws Exception {
        List allvaccountDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select accountno,name,balance FROM accounts where branch = " + branchid + " and companyid = " + companyid + " and controlaccount = false order by accountno asc ");  
         while (rs.next()) {
             Account vaccountDetails = new Account();
             vaccountDetails.setAccountNo(rs.getString("accountno")); 
             vaccountDetails.setName(rs.getString("name") + " - " + rs.getString("accountno")); 
             vaccountDetails.setBalance(rs.getDouble("balance")); 
             allvaccountDetails.add(vaccountDetails);
         }  
        } catch (SQLException ex) {
          throw new Exception(ex.getMessage());
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
        return allvaccountDetails;
    }
 
    @Override
    public List<Account> retrieveAccountsGenwthoutcntrl(int branchid,int companyid) throws Exception {
        List allvaccountDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select accountno,name,balance FROM accounts where branch = " + branchid + " and companyid = " + companyid + " and accounttype = 'G' and active = true and closed = false and blocked = false and controlaccount = false order by accountno asc ");  
         while (rs.next()) {
             Account vaccountDetails = new Account();
             vaccountDetails.setAccountNo(rs.getString("accountno")); 
             vaccountDetails.setName(rs.getString("name") + " - " + rs.getString("accountno")); 
             vaccountDetails.setBalance(rs.getDouble("balance")); 
             allvaccountDetails.add(vaccountDetails);
         }  
        } catch (SQLException ex) {
          throw new Exception(ex.getMessage());
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
        return allvaccountDetails;
    }
}
