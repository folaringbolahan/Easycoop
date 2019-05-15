/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import com.sift.hp.model.Hpvalidationrules;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author yomi
 */
public class Accrualutility {
    
     public double getLoanAccruedInterest(String loanacctno,String loanid,java.util.Date loancommencementdate, String branchId, String coyId,String intaccruedacct)  {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(debit) as totamt FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanacctno + "' and a.headerdocref = '" + loanid + "' and a.transactioncode = 'LIA' and b.postdate >= {d '" + tempDate +  "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset(sql);  
         while (rs.next()) {
             accrint = rs.getDouble("totamt"); 
          }  
        } catch (SQLException ex) {
          System.out.println("SQLException: " + ex.getMessage());
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
       return accrint;
    }
    
    public double getLoanAccruedInterestpaid(String loanid,java.util.Date loancommencementdate, String branchId, String coyId,String intaccruedacct)  {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(credit) as totamt FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid  where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanid + "' and a.txntype = 'LOR' and a.transactioncode = 'LIA' and b.postdate >= {d '" + tempDate +  "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset(sql);  
         while (rs.next()) {
             accrint = rs.getDouble("totamt"); 
          }  
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage()); 
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
        return accrint;
    }
    
    
    public double getDailyLoanAccruedInterest(String loanacctno,java.util.Date loancommencementdate, String branchId, String coyId,String intaccruedacct) throws GenericsiftException {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(debit) as totamt FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanacctno + "' and a.txntype = 'LIAP' and b.postdate >= {d '" + tempDate +  "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset(sql);  
         while (rs.next()) {
             accrint = rs.getDouble("totamt"); 
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
       return accrint;
    }
    
    public double getDailyLoanAccruedInterestpaid(String loanid,java.util.Date loancommencementdate, String branchId, String coyId,String intaccruedacct) throws GenericsiftException {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(credit) as totamt FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid  where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanid + "' and a.txntype = 'LOR' and a.transactioncode = 'LIA' and b.postdate >= {d '" + tempDate +  "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset(sql);  
         while (rs.next()) {
             accrint = rs.getDouble("totamt"); 
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
        return accrint;
    }
    
    
   
    
  
}
