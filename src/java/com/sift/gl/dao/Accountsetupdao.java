/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.cli.AuditlogJerseyClient;
import com.sift.cli.MembaccnoJerseyClient;
import com.sift.gl.AccountsetupException;
import com.sift.gl.AuditlogService;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Account;
import com.sift.gl.model.Activitylog;
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

/** dao class for persistence and data operations on accounts
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Accountsetupdao implements Accountsetupinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    /**
     *
     */
    public Accountsetupdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @throws AccountsetupException
     */
    @Override
    public String addAccount(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException {
    String operres = "";
    try {
        
        operres = InsertAccountDetails(acno,name,acgrp,acstruct,currency,dateopened,baltype,cntrlac,cntrlacno,activeorclosed,blocked,branchid,companyid,ipaddr,username,timezone);
      } catch (Exception ex) {
           throw new AccountsetupException("Transaction failed: " + ex.getMessage());
      }
    return operres;
   }

    
    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @throws AccountsetupException
     */
    public String deleteAccount(String acno,int branchid,int companyid,String ipaddr, String username,String timezone) throws AccountsetupException {
        
     String sqlStatement = "delete from Accounts where accountno = '" + acno + "' and branch = " +
            branchid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean candelete = true;
      String  mySQLString = "select t.* from txns t inner join accounts a on t.accountno = a.accountno and t.branchid = a.branch and t.companyid = a.companyid where a.accountno = '" + acno + "' and a.branch = " + branchid + " and a.companyid = " + companyid ;
      String opsuccessfullmsg = "OK";
     try {
        ResultSet rs =  dbobj.retrieveRecset(mySQLString);
         while (rs.next()) {
             candelete = false;
             opsuccessfullmsg = "Aborting...transactions exists";
         }  
         if (candelete == true) {
           dbobj.updateTable(sqlStatement);
           
           auditlogcall(3,"GLAAD",ipaddr,username,timezone,acno,opsuccessfullmsg,branchid,companyid);
         }
           if (dbobj != null) {
           dbobj.closecon();
       }
      dbobj = null;  
      } catch (Exception ex) {
           opsuccessfullmsg = "Transaction failed: " + ex.getMessage();
           throw new AccountsetupException("Transaction failed: " + ex.getMessage());
      }
     return opsuccessfullmsg;
    }
    
   

    /**
     *
     * @param acno
     * @param name
     * @param acgrp
     * @param acstruct
     * @param currency
     * @param dateopened
     * @param baltype
     * @param cntrlac
     * @param cntrlacno
     * @param activeorclosed
     * @param blocked
     * @param branchid
     * @param companyid
     * @param ipaddr
     * @param username
     * @param timezone
     * @return
     * @throws AccountsetupException
     */
    @Override 
    public String updateAccount(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone)  throws AccountsetupException {
     
     boolean active = true;
     boolean closed = false;
     boolean subacct = false;
    // if (cntrlacno.trim().length()>0) {
    //     subacct = true;
    // }
     if (activeorclosed.equalsIgnoreCase("c")==true) {
         active = false;
         closed = true;
     }
     if (activeorclosed.equalsIgnoreCase("a")==true) {
         active = true;
         closed = false;
     }
        //String sqlStatement = "update Accounts set name = '" + name + "',acgroup = " + acgrp + ",controlaccount = " + cntrlac + ",blocked = " + blocked + ",active = " + active + ",closed = " + closed + " where accountno = '" + acno + "' branch = " +
        //    branchid + " and companyid = " + companyid;
     String namex = name.replace("'","''");
          //String sqlStatement = "update Accounts set name = '" + namex + "',acgroup = " + acgrp + ",controlaccount = " + cntrlac + ",controlaccountno = '" + cntrlacno + "',acstruct = '" + acstruct +  "',subaccount=" + subacct + ",blocked = " + blocked + ",active = " + active + ",closed = " + closed + " where accountno = '" + acno + "' and branch = " +
          //  branchid + " and companyid = " + companyid;
     String sqlStatement = "update Accounts set name = '" + namex + "',acgroup = " + acgrp + ",controlaccount = " + cntrlac +  ",acstruct = '" + acstruct +  "',blocked = " + blocked + ",active = " + active + ",closed = " + closed + " where accountno = '" + acno + "' and branch = " +
            branchid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      boolean opsuccessfull = dbobj.updateTable(sqlStatement);
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="Account Edit Failed";}
      auditlogcall(2,"GLGAE",ipaddr,username,timezone,acno,opsuccessfullmsg,branchid,companyid);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
     } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
     } 
     return "OK";
    }
   
    
    /**
     *
     * @param acno
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    @Override 
    public Account retrieveAccount(String acno,int branchid,int companyid) throws AccountsetupException {
        Account vaccountgroupDetails = new Account();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Accounts where accountno = '" + acno + "' and branch = " + branchid + " and companyid = " + companyid);
         while (rs.next()) {  
             vaccountgroupDetails.setAccountNo(rs.getString("accountno"));
             vaccountgroupDetails.setName(rs.getString("name")); 
             vaccountgroupDetails.setAcType(rs.getString("accounttype"));
             vaccountgroupDetails.setAcStruct(rs.getString("acstruct"));
             vaccountgroupDetails.setAcGroup(rs.getInt("acgroup"));
             vaccountgroupDetails.setAcId(rs.getInt("acid"));
             vaccountgroupDetails.setControlAccount(rs.getBoolean("controlaccount"));
             vaccountgroupDetails.setControlAccountno(rs.getString("controlaccountno"));
             if (rs.getBoolean("active")==true) {
              vaccountgroupDetails.setActiveorclosed("a");
              vaccountgroupDetails.setActive(true);  
             }
             if (rs.getBoolean("closed")==true) {
              vaccountgroupDetails.setActiveorclosed("c");
              vaccountgroupDetails.setClosed(true); 
             }
             
             
             vaccountgroupDetails.setBalanceType(rs.getString("balancetype"));
             vaccountgroupDetails.setBlocked(rs.getBoolean("blocked"));
             vaccountgroupDetails.setCurrency(rs.getString("currency"));
             vaccountgroupDetails.setDateOpened(rs.getDate("dateopened"));
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("dateopened"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setDateOpenedstr(dateopenedx);
         }  
        } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
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
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    @Override 
    public List<Account> retrieveAccounts(int branchid,int companyid) throws AccountsetupException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.*,b.description,c.description as strucdescription FROM Accounts a left join accountgroups b on a.AcGroup = b.Acgroupid and a.companyid = b.companyid left join accountstructures c on a.companyid = c.companyid and a.AcStruct = c.StructureCode where a.companyid  = " + companyid + " and a.branch = " + branchid + " and a.accounttype = 'G'");  
         while (rs.next()) {
             Account vaccountgroupDetails = new Account();
             vaccountgroupDetails.setAccountNo(rs.getString("accountno"));
             vaccountgroupDetails.setName(rs.getString("name")); 
             vaccountgroupDetails.setAcType(rs.getString("accounttype"));
             vaccountgroupDetails.setAcGroup(rs.getInt("acgroup"));
             vaccountgroupDetails.setAcId(rs.getInt("acid"));
             vaccountgroupDetails.setAcStruct(rs.getString("acstruct"));
             vaccountgroupDetails.setControlAccount(rs.getBoolean("controlaccount"));
             vaccountgroupDetails.setControlAccountno(rs.getString("controlaccountno"));
             if (rs.getBoolean("active")==true) {
              vaccountgroupDetails.setActiveorclosed("a");
              vaccountgroupDetails.setActive(true);  
             }
             if (rs.getBoolean("closed")==true) {
              vaccountgroupDetails.setActiveorclosed("c");
              vaccountgroupDetails.setClosed(true); 
             }
             vaccountgroupDetails.setAnalysiscode1(rs.getString("description"));
             vaccountgroupDetails.setAnalysiscode2(rs.getString("strucdescription"));
             
             vaccountgroupDetails.setBalanceType(rs.getString("balancetype"));
             vaccountgroupDetails.setBlocked(rs.getBoolean("blocked"));
             vaccountgroupDetails.setCurrency(rs.getString("currency"));
             vaccountgroupDetails.setDateOpened(rs.getDate("dateopened"));
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("dateopened"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setDateOpenedstr(dateopenedx);
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
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

 
    
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    @Override 
    public List<Account> retrieveallAccounts(int branchid,int companyid) throws AccountsetupException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.* FROM Accounts a where controlaccount = false and  a.companyid  = " + companyid + " and a.branch = " + branchid + " order by name asc");  
         while (rs.next()) {
             Account vaccountgroupDetails = new Account();
             vaccountgroupDetails.setAccountNo(rs.getString("accountno"));
             vaccountgroupDetails.setName(rs.getString("name")); 
             vaccountgroupDetails.setAcType(rs.getString("accounttype"));
             vaccountgroupDetails.setAcGroup(rs.getInt("acgroup"));
             vaccountgroupDetails.setAcId(rs.getInt("acid"));
             vaccountgroupDetails.setAcStruct(rs.getString("acstruct"));
             vaccountgroupDetails.setControlAccount(rs.getBoolean("controlaccount"));
             vaccountgroupDetails.setControlAccountno(rs.getString("controlaccountno"));
             if (rs.getBoolean("active")==true) {
              vaccountgroupDetails.setActiveorclosed("a");
              vaccountgroupDetails.setActive(true);  
             }
             if (rs.getBoolean("closed")==true) {
              vaccountgroupDetails.setActiveorclosed("c");
              vaccountgroupDetails.setClosed(true); 
             }
             //vaccountgroupDetails.setAnalysiscode1(rs.getString("description"));
            // vaccountgroupDetails.setAnalysiscode2(rs.getString("strucdescription"));
             
             vaccountgroupDetails.setBalanceType(rs.getString("balancetype"));
             vaccountgroupDetails.setBlocked(rs.getBoolean("blocked"));
             vaccountgroupDetails.setCurrency(rs.getString("currency"));
             vaccountgroupDetails.setDateOpened(rs.getDate("dateopened"));
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("dateopened"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setDateOpenedstr(dateopenedx);
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
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
    
    
    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    @Override 
    public List<Account> retrieveControlAccounts(int branchid,int companyid) throws AccountsetupException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.* FROM Accounts a where controlaccount = true and  a.companyid  = " + companyid + " and a.branch = " + branchid + " order by name asc");  
         while (rs.next()) {
             Account vaccountgroupDetails = new Account();
             vaccountgroupDetails.setAccountNo(rs.getString("accountno"));
             vaccountgroupDetails.setName(rs.getString("name") + " - " + rs.getString("accountno")); 
             vaccountgroupDetails.setAcType(rs.getString("accounttype"));
             vaccountgroupDetails.setAcGroup(rs.getInt("acgroup"));
             vaccountgroupDetails.setAcId(rs.getInt("acid"));
             vaccountgroupDetails.setAcStruct(rs.getString("acstruct"));
             vaccountgroupDetails.setControlAccount(rs.getBoolean("controlaccount"));
             vaccountgroupDetails.setControlAccountno(rs.getString("controlaccountno"));
             if (rs.getBoolean("active")==true) {
              vaccountgroupDetails.setActiveorclosed("a");
              vaccountgroupDetails.setActive(true);  
             }
             if (rs.getBoolean("closed")==true) {
              vaccountgroupDetails.setActiveorclosed("c");
              vaccountgroupDetails.setClosed(true); 
             }
             //vaccountgroupDetails.setAnalysiscode1(rs.getString("description"));
            // vaccountgroupDetails.setAnalysiscode2(rs.getString("strucdescription"));
             
             vaccountgroupDetails.setBalanceType(rs.getString("balancetype"));
             vaccountgroupDetails.setBlocked(rs.getBoolean("blocked"));
             vaccountgroupDetails.setCurrency(rs.getString("currency"));
             vaccountgroupDetails.setDateOpened(rs.getDate("dateopened"));
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
             String dateopenedx = null;
             try {
               dateopenedx = formatter1.format(rs.getDate("dateopened"));

             } catch( NullPointerException nullEx ) {   }
             vaccountgroupDetails.setDateOpenedstr(dateopenedx);
             allvaccountgroupDetails.add(vaccountgroupDetails);
             //int i = allvstateDetails.indexOf(vstateDetails);
             
             //System.out.println (i + " debugging");
             //Statedetails aStatedetails = (Statedetails) allvstateDetails.get(i);
             //System.out.println (aStatedetails.getCode() + " - " + aStatedetails.getDescription() + " debugging");
         }  
        } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
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
    
    
    private String InsertAccountDetails(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacno,String activeorclosed,boolean blocked, int branchid,int companyid,String ipaddr, String username,String timezone) throws SQLException,AccountsetupException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean active = true;
     boolean closed = false;
     boolean opsuccessfull = false;
     boolean subacct = false;
     if (activeorclosed.equalsIgnoreCase("c")==true) {
         active = false;
         closed = true;
     }
     //if (cntrlacno.trim().length()>0) {
      //   subacct = true;
     //}
     
     if (activeorclosed.equalsIgnoreCase("a")==true) {
         active = true;
         closed = false;
     }
     SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     String dateopenedx = null;
      try {
          dateopenedx = formatter1.format(dateopened);

        } catch( NullPointerException nullEx ) {   }
      java.util.List myAcSeg = getAcSeg(acno, acstruct,companyid);
           String acseg1 = "";
           String acseg2 = "";
           String acseg3 = "";
           String acseg4 = "";
           String acseg5 = "";
           String acseg6 = "";
           String acseg7 = "";
           String acseg8 = "";
           String acseg9 = "";
           String acseg10 = "";
           if (myAcSeg.size()>=1){
               acseg1 = (String) myAcSeg.get(0);
           }
           if (myAcSeg.size()>=2){
               acseg2 = (String) myAcSeg.get(1);
           }
           if (myAcSeg.size()>=3){
               acseg3 = (String) myAcSeg.get(2);
           }
           if (myAcSeg.size()>=4){
               acseg4 = (String) myAcSeg.get(3);
           }
           if (myAcSeg.size()>=5){
               acseg5 = (String) myAcSeg.get(4);
           }
           if (myAcSeg.size()>=6){
               acseg6 = (String) myAcSeg.get(5);
           }
           if (myAcSeg.size()>=7){
               acseg7 = (String) myAcSeg.get(6);
           }
           if (myAcSeg.size()>=8){
               acseg8 = (String) myAcSeg.get(7);
           }
           if (myAcSeg.size()>=9){
               acseg9 = (String) myAcSeg.get(8);
           }
           if (myAcSeg.size()==10){
               acseg10 = (String) myAcSeg.get(9);
           }

           String namex = name.replace("'","''");
      String sqlStatement = "insert into accounts (AccountNo,Name,AcGroup,AcStruct,Currency,ControlAccount,ControlAccountno,subaccount,BalanceType,CCyBalance,CCyClearedBalance,Balance,ClearedBalance,Active,Closed,Blocked,branch,companyid,aseg1,aseg2,aseg3,aseg4,aseg5,aseg6,aseg7,aseg8,aseg9,aseg10,dateopened,accounttype)" +
                   " values('" + acno + "','" + namex + "'," + acgrp + ",'" + acstruct + "','" + currency + "'," + cntrlac + ",'" + "" + "'," + subacct + ",'" + baltype + "',0,0,0,0," + active + "," + closed + "," + blocked + "," + branchid + "," + companyid + ",'" +
                     acseg1 + "','" + acseg2 + "','" + acseg3 + "','" + acseg4 + "','" + acseg5 + "','" + acseg6 + "','" + acseg7 + "','" + acseg8 + "','" + acseg9 + "','" + acseg10 + "',{d '" + dateopenedx + "'},'G')";
      opsuccessfull = dbobj.updateTablebatch(sqlStatement);
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="Account creation Failed";}
      //testmemaccall();
      auditlogcall(1,"GLGAC",ipaddr,username,timezone,acno,opsuccessfullmsg,branchid,companyid);
      
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      return opsuccessfullmsg;
    }
   
    /**
     *
     * @param vAcno
     * @param vAcStructcode
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public java.util.List getAcSeg(String vAcno,String vAcStructcode,int companyid) throws AccountsetupException{
      String mySQLString;
      mySQLString = "select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg1 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg2 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg3 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg4 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg5 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg6 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg7 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg8 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg9 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg10 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid   ;
      ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSeg = "";
      int asum=0;
      int i = vAcno.trim().length();
      int j= 0;
      int indseg;
      int delimlen = 0;
      java.util.List AcsegL =  new ArrayList();
      try {
       while (agRecSet.next()) {
        asum= agRecSet.getInt("length");
        delimlen = 0;
        if ((agRecSet.getString("delimiter")!=null)&& (agRecSet.getString("delimiter").isEmpty()==false)) {
           delimlen = agRecSet.getString("delimiter").trim().length();
           asum=asum+delimlen;
        }
        AcsegL.add(vAcno.substring(j, j+(asum)));
        j=j+asum;
       }
       j = j - delimlen;
       } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
      } 
        
       finally {
          try{  
           if (agRecSet!=null)
           {agRecSet.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          if (dbobj != null) {
           dbobj.closecon();
          }
        } 
        return AcsegL;
    }
    
    
    /**
     *
     * @param vAcno
     * @param vAcStructcode
     * @param companyid
     * @return
     * @throws AccountsetupException
     */
    public String getAcSegmessage(String vAcno,String vAcStructcode,int companyid) throws AccountsetupException{
      String mySQLString;
      mySQLString = "select a.structurecode,a.delimiter,b1.length as len1,b1.name as segdesc1,b2.length as len2,b2.name as segdesc2,b3.length as len3,b3.name as segdesc3,b4.length as len4,b4.name as segdesc4," + 
                  "b5.length as len5,b5.name as segdesc5,b6.length as len6,b6.name as segdesc6,b7.length as len7,b7.name as segdesc7,b8.length as len8,b8.name as segdesc8,b9.length as len9,b9.name as segdesc9,b10.length as len10,b10.name as segdesc10 FROM Accountstructures a " + 
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
                 " where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid ;
      ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSegs = "";
      String delimchar = "";
      int asum=0;
      int i = vAcno.trim().length();
      int j= 0;
      int indseg;
      int delimlen = 0;
      java.util.List AcsegL =  new ArrayList();
      try {
       while (agRecSet.next()) {
       // asum= agRecSet.getInt("length");
        delimlen = 0;
        if ((agRecSet.getString("delimiter")!=null)&& (agRecSet.getString("delimiter").isEmpty()==false)) {
           delimlen = agRecSet.getString("delimiter").trim().length();
           delimchar = agRecSet.getString("delimiter").trim();
        }
        if ((agRecSet.getString("segdesc1")!=null)&& (agRecSet.getString("segdesc1").isEmpty()==false)) {
           asum= agRecSet.getInt("len1");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc1") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc2")!=null)&& (agRecSet.getString("segdesc2").isEmpty()==false)) {
           asum= agRecSet.getInt("len2");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc2") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        } 
        if ((agRecSet.getString("segdesc3")!=null)&& (agRecSet.getString("segdesc3").isEmpty()==false)) {
           asum= agRecSet.getInt("len3");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc3") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc4")!=null)&& (agRecSet.getString("segdesc4").isEmpty()==false)) {
           asum= agRecSet.getInt("len4");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc4") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc5")!=null)&& (agRecSet.getString("segdesc5").isEmpty()==false)) {
           asum= agRecSet.getInt("len5");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc5") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc6")!=null)&& (agRecSet.getString("segdesc6").isEmpty()==false)) {
           asum= agRecSet.getInt("len6");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc6") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc7")!=null)&& (agRecSet.getString("segdesc7").isEmpty()==false)) {
           asum= agRecSet.getInt("len7");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc7") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        } 
        if ((agRecSet.getString("segdesc8")!=null)&& (agRecSet.getString("segdesc8").isEmpty()==false)) {
           asum= agRecSet.getInt("len8");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc8") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc9")!=null)&& (agRecSet.getString("segdesc9").isEmpty()==false)) {
           asum= agRecSet.getInt("len9");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc9") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }
        if ((agRecSet.getString("segdesc10")!=null)&& (agRecSet.getString("segdesc10").isEmpty()==false)) {
           asum= agRecSet.getInt("len10");
           //asum=asum+delimlen;
           vAcSegs= vAcSegs + agRecSet.getString("segdesc10") + " : " +  vAcno.substring(j, j+(asum)) + " ";
           j=j+asum;
        }   
       }
       j = j - delimlen;
       } catch (SQLException ex) {
          throw new AccountsetupException(ex.getMessage());
      } 
       catch (Exception otherex) {
          vAcSegs= "Accountno does not match Account Structure";
      }  
       finally {
          try{  
           if (agRecSet!=null)
           {agRecSet.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          if (dbobj != null) {
           dbobj.closecon();
          }
        } 
        return vAcSegs;
    }
    
    /**
     *
     * @param eventid
     * @param eventactioncode
     * @param ipaddr
     * @param username
     * @param timezone
     * @param actionitem
     * @param result
     * @param branch
     * @param company
     */
    public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       //ent.setEvent(1);
       //ent.setAction("GLGAC");
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
        AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
    
    /**
     *
     */
    public void testmemaccall() {
       Accnowbs ent;
       ent = new Accnowbs();
       String theerrmess;        
       ent.setBranchId(3); 
       ent.setBranchcode("03");
       ent.setCompany(4); 
       ent.setCompanycode("004");
       ent.setCustomerno("000012"); 
       ent.setProductcode("SAV");
       ent.setSubno("07");
       ent.setTimezone("Africa/Lagos");
       MembaccnoJerseyClient cliserv = new MembaccnoJerseyClient();
       int respo = cliserv.calldservice(ent);
       System.out.println(respo);
       theerrmess= cliserv.gettheerrmess();
       System.out.println(theerrmess);
    }

    public String retrieveAccountstrfld(String acno,int branchid,int companyid,String fldname)  {
        String fldresult = "";
        Account vaccountgroupDetails = new Account();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM Accounts where accountno = '" + acno + "' and branch = " + branchid + " and companyid = " + companyid);
         while (rs.next()) {  
             fldresult = rs.getString(fldname);
         }  
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
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
        return fldresult;
    }
    
}
