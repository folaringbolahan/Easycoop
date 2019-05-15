/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccounttxnsException;
import com.sift.gl.GendataService;
import com.sift.gl.model.Accounttxnsdetail;
import java.sql.*;
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
public class Accounttxnsdao implements Accounttxnsinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    double totaldebit = 0.0;
    double totalcredit = 0.0;
    double netmovement = 0.0;
    double bal = 0.0;
    public Accounttxnsdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
      
   
    @Override 
    public List<Accounttxnsdetail> retrieveAccounttxns(String accountno,int branchid,int companyid,java.util.Date startdate, java.util.Date enddate) throws AccounttxnsException {
        List allvaccounttxnsDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        netmovement = 0.0;
        totaldebit = 0.0;
        totalcredit = 0.0;
        bal = 0.0;
        String tempDate1 = null;
        String tempDate2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            tempDate1 = formatter.format(startdate);
            tempDate2 = formatter.format(enddate);
        } catch( NullPointerException nullEx ) {   }
        String datecrit = " and (Postdate >= {d '" + tempDate1 + "'} and Postdate <= {d '" + tempDate2 + "'})";
        
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select txnsheader.postdate,txnsheader.txndate,txns.* from txnsheader inner join txns on txnsheader.txntype = txns.txntype and txnsheader.txnserial = txns.txnserial and txnsheader.branchid = txns.branchid and txnsheader.companyid = txns.companyid where accountno = '" + accountno + "' and txns.companyid = " + companyid + " and txns.branchid = " + branchid + " " + datecrit + 
           "  union select txnsheader.postdate,txnsheader.txndate,txns.* from txnsheader inner join txns on txnsheader.txnserial = txns.txnserial and txnsheader.branchid = txns.branchid and txnsheader.companyid = txns.companyid where txns.txntype = 'PR' and accountno = '" + accountno + "' and txns.companyid = " + companyid + " and txns.branchid = " + branchid + " " + datecrit);  
         int i =0;
         while (rs.next()) {
             Accounttxnsdetail vaccounttxnsDetails = new Accounttxnsdetail();
             i = i + 1;
             vaccounttxnsDetails.setTxnId(i);
             vaccounttxnsDetails.setAccountno(accountno); 
             vaccounttxnsDetails.setUserId(rs.getString("UserId"));
             vaccounttxnsDetails.setNarrative(rs.getString("Narrative"));
             vaccounttxnsDetails.setCredit(rs.getDouble("Credit"));
             vaccounttxnsDetails.setDebit(rs.getDouble("Debit"));
             vaccounttxnsDetails.setEntryref(rs.getString("Txntype")+ "-" + rs.getString("Txnserial"));
             vaccounttxnsDetails.setPostdate(rs.getDate("Postdate").toString());
             vaccounttxnsDetails.setValuedate(rs.getDate("Txndate").toString());
             totaldebit = totaldebit + rs.getDouble("Debit");
             totalcredit = totalcredit + rs.getDouble("Credit");
             allvaccounttxnsDetails.add(vaccounttxnsDetails);
           }
          netmovement = totalcredit - totaldebit;
          
          rs =  dbobj.retrieveRecset("select balance from accounts where accountno = '" + accountno + "' and companyid = " + companyid + " and branch = " + branchid + "");  
          while (rs.next()) { 
             bal = rs.getDouble("balance");
          }
         
        } catch (SQLException ex) {
          throw new AccounttxnsException(ex.getMessage());
     } 
       if (dbobj != null) {
           dbobj.closecon();
        }
        dbobj = null; 

        return allvaccounttxnsDetails;
    }

    @Override 
    public double retrieveTotalcredit() throws AccounttxnsException {
        return this.totalcredit;
    }
    @Override 
    public double retrieveTotaldebit() throws AccounttxnsException {
        return this.totaldebit;
    }
    @Override 
    public double retrieveNetmvmt() throws AccounttxnsException {
        return this.netmovement;
    }
    @Override 
    public double retrieveBal() throws AccounttxnsException {
        return this.bal;
    }
}
