/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.dao;

import com.sift.gl.dao.*;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.cli.GLJerseyClient;
import com.sift.cli.MembaccnoJerseyClient;
import com.sift.gl.AccountsetupException;
import com.sift.gl.AuditlogService;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import com.sift.gl.NewSerialno;
import com.sift.gl.NotificationService;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Account;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Txn;
import com.sift.gl.model.Txnsheader;
import com.sift.hp.HpgenericException;
import com.sift.hp.model.Hpintcalcmtd;
import com.sift.hp.model.Hpoperands;
import com.sift.hp.model.Hpproduct;
import com.sift.hp.model.Hprepaymtfreq;
import com.sift.hp.model.Hprepymtschddetails;
import com.sift.hp.model.Hprequestdetails;
import com.sift.hp.model.Hpvalidationrules;
import com.sift.hp.model.Member;
import java.sql.*;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.*;
import org.nfunk.jep.JEP;
import org.nfunk.jep.SymbolTable;
import org.nfunk.jep.Variable;
///import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;
//import biomet.ejb.PersonException;
//import javax.ejb.LocalBean;

/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Hprequestdao implements Hprequestinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    
    public Hprequestdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    @Override
    public String addRequest(String memberno,String prod,double HPprice,double Cashprice,double Interestamt,String invoiceref,String Interestcalcmtd,double InterestRate,java.util.Date TxnDate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths,int NoofPayments, int branchid,int companyid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService) throws HpgenericException {
    String retval ="";
    try {
         retval = InsertDetails(memberno,prod,HPprice,Cashprice,Interestamt,invoiceref,Interestcalcmtd,InterestRate,TxnDate,DownpaymentAmount,RepaymentFrequency,RepaymentPeriodinMonths,NoofPayments,branchid,companyid,ipaddr, username,timezone, mailsubject,notificationService);
      } catch (Exception ex) {
           throw new HpgenericException("Transaction failed: " + ex.getMessage());
      }
    return retval;
   }
   
    @Override
    public String addRule(String code,String desc,String prodcode,String valtype,String formula,String resultcond, int branchid,int companyid,String ipaddr, String username,String timezone) throws HpgenericException {
    String retval ="";
    try {
         retval = InsertRuleDetails(code,desc,prodcode,valtype,formula,resultcond,branchid,companyid,ipaddr,username,timezone);
      } catch (Exception ex) {
           throw new HpgenericException("Transaction failed: " + ex.getMessage());
      }
    return retval;
   }
    
    private String InsertDetails(String memberno,String prod,double HPprice,double Cashprice,double Interestamt,String invoiceref,String Interestcalcmtd,double InterestRate,java.util.Date TxnDate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths, int NoofPayments, int branchid,int companyid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService) throws SQLException,HpgenericException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean opsuccessfull = false;
     String retval = "";
     SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     String dateopenedx = null;
      try {
          dateopenedx = formatter1.format(TxnDate);

        } catch( NullPointerException nullEx ) {   }
      String myAcSeg = getMemname(memberno,companyid,branchid);
           
         Integer varSerialint;
         String varSerial; 
         NewSerialno vSerial = new NewSerialno();
         varSerial =  vSerial.returnSerialnostr("HPref",branchid,companyid);
         retval = varSerial;
     
         TimeZone timeZone = TimeZone.getTimeZone(timezone);
         //SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
         formatter1.setTimeZone(timeZone);
         Calendar rightNow = Calendar.getInstance(timeZone);
         String datelogx = null;
         try {
           datelogx = formatter1.format(rightNow.getTime());
         } catch( NullPointerException nullEx ) {   }
            
         
      String sqlStatement = "insert into hprequests(Refid,Product,Memberno,Membername,HPprice,Cashprice,Interestamt,invoiceref,Interestcalcmtd,InterestRate,TxnDate,TenorinMonths,DownpaymentAmount,loanbalance,RepaymentFrequency,RepaymentPeriodinMonths,NoofPayments,status,branchid,companyid,requestby,requestdate)" +
                   " values('" + varSerial + "','" + prod + "','" + memberno + "','" + myAcSeg + "'," + HPprice + "," + Cashprice + "," + Interestamt + ",'" + invoiceref + "','" + Interestcalcmtd + "'," + InterestRate + ",{d '" + dateopenedx + "'}," + RepaymentPeriodinMonths + "," + DownpaymentAmount + "," + (Cashprice-DownpaymentAmount) + ",'" + RepaymentFrequency + "'," + RepaymentPeriodinMonths + ","  + NoofPayments + ",'N'," + branchid + "," + companyid + ",'" + username + "',{d '" + datelogx + "'})";
      opsuccessfull = dbobj.updateTablebatch(sqlStatement);
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="HP Request creation Failed : " + retval;}
      else
      {
        retval = "ok: Reference - " + retval + ", HPPrice:" + HPprice + ", Interest : " +  Interestamt;
        GetSetting sendnoticeset = new GetSetting();
         String sendnotice = sendnoticeset.GetSettingvalue("ENABLEGLEMAILNOTIF", branchid, companyid);
         if (sendnotice.equalsIgnoreCase("ON") == true) {
          Map model = new HashMap();
          model.put("referenceid", varSerial);
          notificationService.createflowemailnotice("HP1", mailsubject, "hpreqapprvemail.ftl", branchid, companyid, model);
        }
      }    
      //testmemaccall();
      auditlogcall(113,"HPRQ",ipaddr,username,timezone,varSerial + "-" + memberno,opsuccessfullmsg,branchid,companyid);
      
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      return retval;
    }
   
    private String InsertRuleDetails(String code,String desc,String prodcode,String valtype,String formula,String resultcond, int branchid,int companyid,String ipaddr, String username,String timezone) throws SQLException,HpgenericException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean opsuccessfull = false;
     String retval = "";
     
         TimeZone timeZone = TimeZone.getTimeZone(timezone);
         
      String sqlStatement = "insert into hpvalidationrules(code, description,productcode, validationtype, formula,resultcond,  branchid, companyid)" +
                   " values('" + code + "','" + desc + "','" + prodcode + "','" + valtype + "','" + formula + "','" + resultcond + "'," + branchid + "," + companyid + ")";
      opsuccessfull = dbobj.updateTablebatch(sqlStatement);
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="HP Validation setup Failed : " + retval;}
      else
      {
        retval = "ok: Reference - " + code ;
      }    
      //testmemaccall();
      auditlogcall(123,"HPVAL",ipaddr,username,timezone,code,opsuccessfullmsg,branchid,companyid);
      
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      return retval;
    }
    
    public String getMemname(String no,int companyid,int branchid) throws HpgenericException{
      String mySQLString;
      mySQLString = "select a.surname,a.first_name,a.middle_name from member a where member_no  = '" + no + "' and a.company_id = " + companyid + " and a.branch_id = " + branchid;
     ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSeg = "";
      try {
       while (agRecSet.next()) {
         vAcSeg = agRecSet.getString("surname") + " " + agRecSet.getString("first_name") + " " + agRecSet.getString("middle_name");  
          vAcSeg = vAcSeg.trim();
       }
       } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return vAcSeg;
    }
    
    @Override 
    public List<Member> retrieveMembers(int branchid,int companyid) throws HpgenericException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.member_no,surname,first_name,middle_name FROM member a where status_id = 2 and  a.company_id  = " + companyid + " and a.branch_id = " + branchid + " order by surname asc");  
         while (rs.next()) {
             Member vaccountgroupDetails = new Member();
             vaccountgroupDetails.setMemberNo(rs.getString("member_no"));
             vaccountgroupDetails.setSurname(rs.getString("surname")); 
             vaccountgroupDetails.setFirstName(rs.getString("first_name"));
             vaccountgroupDetails.setMiddleName(rs.getString("middle_name"));
             String dnames = rs.getString("surname") + " " + rs.getString("first_name") + " " + rs.getString("middle_name");
             vaccountgroupDetails.setNames(dnames.trim());
             allvaccountgroupDetails.add(vaccountgroupDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
    public List<Hpproduct> retrieveHPproducts(int branchid,int companyid) throws HpgenericException {
        List allvobjDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select a.id,code,name FROM products a where product_type_code = 'P' and is_active = 1 and a.company_id  = " + companyid + " and a.branch_id = " + branchid + " order by name asc");  
         while (rs.next()) {
             Hpproduct vobjDetails = new Hpproduct();
             vobjDetails.setCode(rs.getString("code"));
             vobjDetails.setName(rs.getString("name")); 
             vobjDetails.setId(rs.getInt("id"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return allvobjDetails;
    }
    
    @Override 
    public List<Hprepaymtfreq> retrieveHPrepymtfreqs() throws HpgenericException {
        List allvobjDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select id,code,description FROM hprepymtfreq order by description asc");  
         while (rs.next()) {
             Hprepaymtfreq vobjDetails = new Hprepaymtfreq();
             vobjDetails.setCode(rs.getString("code"));
             vobjDetails.setDescription(rs.getString("description")); 
             vobjDetails.setId(rs.getInt("id"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return allvobjDetails;
    }
    
    @Override 
    public List<Hpintcalcmtd> retrieveHpintcalcmtd() throws HpgenericException {
        List allvobjDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select id,code,description FROM hpintcalcmtd order by description asc");  
         while (rs.next()) {
             Hpintcalcmtd vobjDetails = new Hpintcalcmtd();
             vobjDetails.setCode(rs.getString("code"));
             vobjDetails.setDescription(rs.getString("description")); 
             vobjDetails.setId(rs.getInt("id"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return allvobjDetails;
    }
    
    
    @Override 
    public List<Hpoperands> retrieveHpoperands() throws HpgenericException {
        List allvobjDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select id,code,description FROM hpoperands order by description asc");  
         while (rs.next()) {
             Hpoperands vobjDetails = new Hpoperands();
             vobjDetails.setCode(rs.getString("code"));
             vobjDetails.setDescription(rs.getString("description")); 
             vobjDetails.setId(rs.getInt("id"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return allvobjDetails;
    }
    
    @Override
    public List<Hprequestdetails> getHpreqs4apprv(int companyid,int branchid,String userid) throws HpgenericException {
    List allvobjDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and requestby != '" + userid + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id where a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'N') order by txndate desc");
         while (rs.next()) {  
             Hprequestdetails vobjDetails = new Hprequestdetails();
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return allvobjDetails;
    }
    
    @Override
    public Hprequestdetails getHpreq4apprv(String ref,int companyid,int branchid,String userid) throws HpgenericException {
    Hprequestdetails vobjDetails = new Hprequestdetails();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and requestby != '" + userid + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname,c.description as interestcalcmtddesc,d.description as repaymentfrequencydesc FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id left join hpintcalcmtd c on a.interestcalcmtd = c.code left join hprepymtfreq d on a.repaymentfrequency = d.code  where a.refid = '" + ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'N') order by txndate desc");
         while (rs.next()) {  
             
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("product"));
             vobjDetails.setProductname(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setDownpaymentamount(rs.getDouble("downpaymentamount"));
             vobjDetails.setInterestamt(rs.getDouble("interestamt"));
             vobjDetails.setInterestrate(rs.getDouble("interestrate"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             vobjDetails.setInvref(rs.getString("invoiceref"));
             vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtd"));
             vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequency"));
             vobjDetails.setLoanBalance(rs.getDouble("loanBalance"));
             //System.out.println("hreee" + vobjDetails.getRepaymentfrequency());
             //vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtddesc"));
             //vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequencydesc"));
             vobjDetails.setNoofpayments(rs.getInt("noofpayments"));
             vobjDetails.setRepaymentperiodinmonths(rs.getInt("repaymentperiodinmonths"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             if (rs.getString("status").equalsIgnoreCase("N")){
                 vobjDetails.setStatus("A");
             }
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return vobjDetails;
    }
    
    public List<Hpvalidationrules> retrieveHpvalrules(String code,int branchid,int companyid) throws HpgenericException {
        Hpvalidationrules vDetails = new Hpvalidationrules();
        List allvobjDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM hpvalidationrules a where a.productcode = '" + code + "' and a.companyid  = " + companyid + " and a.branchid = " + branchid + " order by description asc");  
         while (rs.next()) {
             vDetails = new Hpvalidationrules();
             vDetails.setCode(rs.getString("code"));
             vDetails.setDescription(rs.getString("description")); 
             vDetails.setProductcode(rs.getString("productcode"));
             vDetails.setValidationtype(rs.getString("validationtype"));
             vDetails.setFormula(rs.getString("formula"));
             vDetails.setResultcond(rs.getString("resultcond"));
             vDetails.setId(rs.getInt("id"));
             allvobjDetails.add(vDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return allvobjDetails;
    }
    
    @Override 
    public String saveHpdetails(Hprequestdetails hpdet,List<Hprepymtschddetails> hpschdet,int companyid,int branchid,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService)  throws HpgenericException {
     String retval = "";
     String daccountno = "";
     ///generate accountno
     daccountno = memaccall(hpdet.getMemberno(),hpdet.getProduct(),branchid,companyid, timezone);
     ///generate schedule
     System.out.println("daccountno.isEmpty()" + daccountno.isEmpty());
     if (daccountno.isEmpty()==false)
     {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     TimeZone timeZone = TimeZone.getTimeZone(timezone);
     formatter1.setTimeZone(timeZone);
     Iterator<Hprepymtschddetails> bditerator = hpschdet.iterator();
        int i = 0;
        String dnextrepaymentdate = null;
       String dateopenedx = null; 
       String sqlStatement2 = "";
       
      try { 
       if (hpdet.getStatus().equalsIgnoreCase("A")==true)
       {    
        while (bditerator.hasNext()) {
          Hprepymtschddetails objinn = bditerator.next();
          try {
            dateopenedx = formatter1.format(objinn.getRpymtdate());
          } catch( NullPointerException nullEx ) {   }
          if (i==0) {
              dnextrepaymentdate=dateopenedx;
          }
            sqlStatement2 = "insert into hprepaymentschedule(Refid, AccountNo, InstalNo, Rpymtdate, Instalment, Principal, PrincipalToDate, Interest, InterestToDate, ScheduleBalance, Branchid, Companyid)" +
            " values('" + hpdet.getRefid() + "','" + daccountno + "'," + objinn.getInstalNo() + ",{d '" + dateopenedx + "'}," + objinn.getInstalment() + "," + objinn.getPrincipal() + "," + objinn.getPrincipalToDate() + "," + objinn.getInterest() + "," + objinn.getInterestToDate() + "," + objinn.getScheduleBalance() + "," + branchid + "," + companyid + ")";
            dbobj.updateTable(sqlStatement2); 
        }
      }
     
         Calendar rightNow = Calendar.getInstance(timeZone);
         String datelogx = null;
         String dfinalmaturitydate = dateopenedx;
         
         try {
           datelogx = formatter1.format(rightNow.getTime());
         } catch( NullPointerException nullEx ) {   }
          
      
      String sqlStatement = "update hprequests set status = '" + hpdet.getStatus() + "',rejectcomment = '" + hpdet.getRejectcomment() + "',accountno = '" + daccountno + "',approvedby = '" + username +  "',approvaldate={d '" + datelogx + "'},finalmaturitydate = {d '" + dfinalmaturitydate + "'},nextinstalmentno = 1,nextrepaymentdate = {d '" + dnextrepaymentdate + "'} where refid = '" + hpdet.getRefid() + "' and branchid = " +
            branchid + " and companyid = " + companyid;
     
     
     
      //System.out.println(sqlStatement2);   
        
      boolean opsuccessfull = dbobj.updateTable(sqlStatement);
      
      if ((hpdet.getAnalysiscode1().isEmpty()!=true) && (hpdet.getAnalysiscode1()!=null)) {
           sqlStatement2 = "insert into hpapprvexception(Refid, Exception, Branchid, Companyid)" +
            " values('" + hpdet.getRefid() + "','" + hpdet.getAnalysiscode1() + "'," + branchid + "," + companyid + ")";
            dbobj.updateTable(sqlStatement2);
      }
      
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="HP Approval Failed";}
      else
      {
        retval = "ok: ";
         GetSetting sendnoticeset = new GetSetting();
         String sendnotice = sendnoticeset.GetSettingvalue("ENABLEGLEMAILNOTIF", branchid, companyid);
         if (sendnotice.equalsIgnoreCase("ON") == true) {
          Map model = new HashMap();
          model.put("referenceid", hpdet.getRefid());
          notificationService.createflowemailnotice("HP2", mailsubject, "hppursalemail.ftl", branchid, companyid, model);
        }
      } 
      auditlogcall(118,"HPAP",ipaddr,username,timezone,hpdet.getRefid(),opsuccessfullmsg,branchid,companyid);
     } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
     }finally {
         if (dbobj != null) {
          try {
            dbobj.closecon();
          } catch (Exception ignore) { }
         }
     dbobj = null;  
     }
     }
     return retval;
    }
    
    public String memaccall( String memberno,String prodcode,int branchid,int coyid, String timezone) throws HpgenericException{
       Accnowbs ent;
       ent = new Accnowbs();
       String theerrmess;        
       ent.setBranchId(branchid); 
       ent.setCompany(coyid); 
       ent.setCustomerno(memberno); 
       ent.setProductcode(prodcode);
       
       ent.setTimezone(timezone);
       
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
       ResultSet rs=null;
       ResultSet rs2=null;
       int kk=0;
       try {
         rs =  dbobj.retrieveRecset("SELECT a.code,b.branch_code FROM company a inner join branch b on a.id = b.company_id  where a.id = " + coyid + " and b.id = " + branchid );
         while (rs.next()) {  
             ent.setBranchcode(rs.getString("branch_code"));
             ent.setCompanycode(rs.getString("code"));
          } 
         
         rs2 =  dbobj.retrieveRecset("SELECT count(a.accountno) as kount FROM accounts a inner join custaccountdetails b on a.accountno = b.accountno and a.companyid = b.companyid  and a.branch = b.branchid where a.asegc = '" + memberno + "' and b.product = '" + prodcode + "' and a.branch = " + branchid + " and a.companyid = " + coyid);
         while (rs2.next()) {  
             if (rs2.getInt("kount")>0) {
              kk=rs2.getInt("kount");
             }
          } 
         
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
        } 
        
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (rs2 != null) {
              try {
                rs2.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
       kk = kk + 1;
       String kkstr = String.format ("%02d", kk);
       ent.setSubno(kkstr);
       
       MembaccnoJerseyClient cliserv = new MembaccnoJerseyClient();
       int respo = cliserv.calldservice(ent);
       
       System.out.println(respo);
       String drespstr = cliserv.gettheerrmess();
       String accno = cliserv.getdaccno();
       System.out.println("accno " + accno); 
       
       return accno;
    }
    
    @Override 
    public String cancelHpdetails(String refid,int companyid,int branchid,String ipaddr, String username,String timezone)  throws HpgenericException {
     String retval = "";
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     String sqlStatement = "update hprequests set status = 'C' where refid = '" + refid + "' and branchid = " +
            branchid + " and companyid = " + companyid;
     try {
       //System.out.println("1 " +sqlStatement);
      boolean opsuccessfull = dbobj.updateTable(sqlStatement);
      //System.out.println("2 " +sqlStatement);
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="HP Cancellation Failed";}
      else
      {
        retval = "ok: ";
         
      } 
      auditlogcall(119,"HPCA",ipaddr,username,timezone,refid,opsuccessfullmsg,branchid,companyid);
     } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
     }finally {
         if (dbobj != null) {
          try {
            dbobj.closecon();
          } catch (Exception ignore) { }
         }
     dbobj = null;  
     }
     
     return retval;
    }
    
    @Override
    public List<Hprequestdetails> getHpreqs4sale(int companyid,int branchid,String userid) throws HpgenericException {
    List allvobjDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and approvedby != '" + userid + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id where a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'A') order by txndate desc");
         while (rs.next()) {  
             Hprequestdetails vobjDetails = new Hprequestdetails();
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return allvobjDetails;
    }
    
    @Override
    public Hprequestdetails getHpreq4sale(String ref,int companyid,int branchid,String userid) throws HpgenericException {
    Hprequestdetails vobjDetails = new Hprequestdetails();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and approvedby != '" + userid + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname,c.description as interestcalcmtddesc,d.description as repaymentfrequencydesc FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id left join hpintcalcmtd c on a.interestcalcmtd = c.code left join hprepymtfreq d on a.repaymentfrequency = d.code  where a.refid = '" + ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'A') order by txndate desc");
         while (rs.next()) {  
             
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("product"));
             vobjDetails.setProductname(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setDownpaymentamount(rs.getDouble("downpaymentamount"));
             vobjDetails.setInterestamt(rs.getDouble("interestamt"));
             vobjDetails.setInterestrate(rs.getDouble("interestrate"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             vobjDetails.setInvref(rs.getString("invoiceref"));
             vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtd"));
             vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequency"));
             //System.out.println("hreee" + vobjDetails.getRepaymentfrequency());
             //vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtddesc"));
             //vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequencydesc"));
             vobjDetails.setNoofpayments(rs.getInt("noofpayments"));
             vobjDetails.setRepaymentperiodinmonths(rs.getInt("repaymentperiodinmonths"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
            // if (rs.getString("status").equalsIgnoreCase("N")){
                 vobjDetails.setStatus(rs.getString("Status"));
           //  }
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return vobjDetails;
    }
    
    @Override 
    public String postHpsales(Hprequestdetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate)  throws HpgenericException {
     String retval = "";
     String daccountno = "";
     Double deposit = 0.0;
     Double cashprice = 0.0;
     Double totalint = 0.0;
     Double hpprice = 0.0;
     String intcalcmtd = "";
     String hpref = "";  
     boolean allok = true;
     //get narrative for txncode
     //get accounts for txn
     //build txn list
     ResultSet rs=null;
     ResultSet rs2=null;
     ResultSet rs3=null;
     String addsql = "";
     String txn_date = "";
     java.util.Date txndate = null;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     TimeZone timeZone = TimeZone.getTimeZone(timezone);
     formatter1.setTimeZone(timeZone);
     int i = 0;
       String dnextrepaymentdate = null;
       String dateopenedx = null; 
       String sqlStatement2 = "";
       String dnarrhpsales = ""; 
       String dnarrhpdwnpay = "";
       String narrationhpsales= "";
       String narrationhpdown=  ""; 
       String cashbankaccount = "";
       String interestreceivableaccount= "";
       String salesaccount=  ""; 
       String productcode = "";
       int servres2 = 0;
      try { 
       if ((hpdet.getStatus().equalsIgnoreCase("A")==true)) // && (allok==true)) 
       {    
        
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and approvedby != '" + username + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       
        
        
         rs =  dbobj.retrieveRecset("SELECT a.* FROM hprequests a where a.refid = '" + hpdet.getRefid() + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'A') order by txndate desc");
         while (rs.next()) {  
             hpref = rs.getString("Refid"); 
             daccountno = rs.getString("accountno");
             hpprice = rs.getDouble("hpprice");
             cashprice = rs.getDouble("Cashprice");
             deposit = rs.getDouble("downpaymentamount");
             totalint = rs.getDouble("interestamt");
             intcalcmtd = rs.getString("interestcalcmtd");
             txn_date=formatter.format(rs.getDate("Txndate"));
             txndate = rs.getDate("Txndate");
             productcode = rs.getString("product");
          }     
           
          rs2 =  dbobj.retrieveRecset("SELECT a.* FROM txncodes a where a.TransactionCode = 'HPS'");
          while (rs2.next()) {  
             dnarrhpsales = rs2.getString("narrative");
             narrationhpsales = String.format(dnarrhpsales,daccountno,hpref);
          } 
          rs2 =  dbobj.retrieveRecset("SELECT a.* FROM txncodes a where a.TransactionCode = 'HPD'");
          while (rs2.next()) {  
             dnarrhpdwnpay = rs2.getString("narrative");
             narrationhpdown = String.format(dnarrhpdwnpay,daccountno,hpref);
          } 
          rs3 =  dbobj.retrieveRecset("SELECT a.* FROM qryproductaccounts a where a.code = '" + productcode + "' and a.company_id = " + companyid + " and a.branch_id = " + branchid );
          while (rs3.next()) {  
             cashbankaccount = rs3.getString("fundsourceaccount");
             interestreceivableaccount= rs3.getString("interestreceivableaccount");
             salesaccount= rs3.getString("hpsalesaccount"); 
          } 
          if (narrationhpdown.isEmpty()==true || (narrationhpsales.isEmpty()==true)||cashbankaccount.isEmpty()==true || (interestreceivableaccount.isEmpty()==true)||salesaccount.isEmpty()==true)
          {
              allok = false;
          }    
             java.util.List<Txn> dli = new LinkedList<Txn>();
             java.util.List<Txn> dli2 = new LinkedList<Txn>();
             Txn dtxn1 = new Txn();
             Txn dtxn2 = new Txn();
             Txn dtxn4 = new Txn();
             Txn dtxn5 = new Txn();
             dtxn1.setAccountno(daccountno);
             dtxn2.setAccountno(salesaccount);
             dtxn1.setNarrative(narrationhpsales);
             dtxn2.setNarrative(narrationhpsales);
             dtxn1.setReference(hpref);
             dtxn2.setReference(hpref);
             dtxn4.setAccountno(daccountno);
             dtxn5.setAccountno(cashbankaccount);
             dtxn4.setNarrative(narrationhpdown);
             dtxn5.setNarrative(narrationhpdown);
             dtxn4.setReference(hpref);
             dtxn5.setReference(hpref);
             dtxn4.setAmount(deposit);
             dtxn5.setAmount(-1*deposit);
             
          GetSetting REA = new GetSetting();
          String Acctmtd = REA.GetSettingvalue("ACCOUNTINGMETHOD",branchid,companyid);
          if (Acctmtd.equalsIgnoreCase("ACCRUAL")==true) {
             Txn dtxn3 = new Txn(); 
             dtxn3.setAccountno(interestreceivableaccount);
             dtxn3.setNarrative(narrationhpsales);
             dtxn3.setReference(hpref);
             dtxn1.setAmount(-1*hpprice);
             dtxn2.setAmount(cashprice);
             dtxn3.setAmount(totalint);
             dli.add(dtxn1);
             dli.add(dtxn2);
             dli.add(dtxn3);
          }
          else {
             dtxn1.setAmount(-1*cashprice);
             dtxn2.setAmount(cashprice);
             dli.add(dtxn1);
             dli.add(dtxn2);
          }
          dli2.add(dtxn4);
          dli2.add(dtxn5);
          if (allok==true) {
              int servres = callservice(dli,txndate,companyid,branchid,username,dyr,dper,timezone,dpostdate,"HPS");
             
              if (servres==201) {
                 servres2 = callservice(dli2,txndate,companyid,branchid,username,dyr,dper,timezone,dpostdate,"HPD");
                //updatefileuploadtbl(refid,branchid,companyid, userid,1);
             }
             else
             {
             
             }
          }
           
           
       }
     
         Calendar rightNow = Calendar.getInstance(timeZone);
         String datelogx = null;
         String dfinalmaturitydate = dateopenedx;
         
         try {
           datelogx = formatter1.format(rightNow.getTime());
         } catch( NullPointerException nullEx ) {   }
         boolean opsuccessfull = true;
         if (servres2==201) {
           String sqlStatement = "update hprequests set status = 'S',salesby = '" + username +  "',salesdate={d '" + datelogx + "'} where refid = '" + hpdet.getRefid() + "' and branchid = " +
            branchid + " and companyid = " + companyid;
     //System.out.println(sqlStatement2);   
        
          opsuccessfull = dbobj.updateTable(sqlStatement);
         }
      String opsuccessfullmsg = "OK";
      if (opsuccessfull==false) {opsuccessfullmsg="HP Sales posting Failed";}
      else
      {
        retval = "ok: ";
         
      } 
      auditlogcall(120,"HPSP",ipaddr,username,timezone,hpdet.getRefid(),opsuccessfullmsg,branchid,companyid);
     } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
     }finally {
          if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
           if (rs2 != null) {
              try {
                rs2.close();
              } catch (Exception ignore) { }
             }
           if (rs3 != null) {
              try {
                rs3.close();
              } catch (Exception ignore) { }
             }
         if (dbobj != null) {
          try {
            dbobj.closecon();
          } catch (Exception ignore) { }
         }
     dbobj = null;  
     }
    
     return retval;
    }
    
    private Integer callservice(java.util.List<Txn> dtxnlist,java.util.Date dtxndate,int companyid,int branchid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate,String txncode){
             Integer respo = 0;
             Integer branchcode = branchid;   //"02";
             Integer coyid = companyid;//currrentuserServicex.getCurruser().getCompanyid(); // "7";
             Integer coyperiod = dper;//5;
             Integer coyyear = dyr;//2015;
             String sourcemod = "HP";
             String txntype = txncode;
             java.util.Date txndate = dtxndate;
             String theerrmess;
             
             TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
             Calendar rightNow = Calendar.getInstance(timeZone);
             java.util.Date entrydate = rightNow.getTime();
             double currrate = 1.0;
             String valuedatestr=new java.text.SimpleDateFormat("yyyy-MM-dd").format(dpostdate);
             Entrys ent = new Entrys();
             NewSerialno nvSerial = new NewSerialno();
             Integer varSerialint;
             varSerialint = nvSerial.returnSerialno("Hsref", branchcode, coyid);
             String varSerial = Integer.toString(varSerialint);
             Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, dpostdate, entrydate, varSerial, "Hire Purchase Sales Posting:", sourcemod, userid, branchcode, coyid,dtimezone);
             ent.setTxnsheader(txnhdr);

             java.util.LinkedList<Entry> dentlist;
             dentlist = new LinkedList<Entry>();
             double amt = 0.0;
             //String txncode = "HPS";
             for (Txn b : dtxnlist) {
                 Entry ent1 = new Entry(txntype, txncode, b.getAccountno(), valuedatestr, b.getReference(), varSerial, b.getNarrative(),"1" , b.getAmount(), b.getAmount(), currrate, userid, branchcode, coyyear, coyperiod, coyid);
                 dentlist.add(ent1);
             }

             ent.setEntrys(dentlist);       
             GLJerseyClient cliserv = new GLJerseyClient();
             respo = cliserv.calldservice(ent);
             
             theerrmess= cliserv.gettheerrmess();
             return respo;
         }
    
    @Override
    public List<Hprequestdetails> getHpreqsactive(int companyid,int branchid,String userid) throws HpgenericException {
    List allvobjDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id where a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'S') order by txndate desc");
         while (rs.next()) {  
             Hprequestdetails vobjDetails = new Hprequestdetails();
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return allvobjDetails;
    }
    
    
    public Hpproduct retrieveProductdet(String code,int branchid,int companyid) throws HpgenericException {
        Hpproduct vobjDetails = new Hpproduct();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM products a where product_type_code = 'P' and code = '" + code + "' and is_active = 1 and a.company_id  = " + companyid + " and a.branch_id = " + branchid + " order by name asc");  
         while (rs.next()) {
             
             vobjDetails.setLoanDuration(rs.getInt("loan_duration"));
             vobjDetails.setInterestRate(rs.getDouble("interest_rate")); 
             vobjDetails.setLoanTypeCode(rs.getString("loan_type_code"));
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        return vobjDetails;
    }
    
    public List<Hpvalidationrules> retrieveHpvalrules(int branchid,int companyid) throws HpgenericException {
        List allvaccountgroupDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM hpvalidationrules a where a.companyid  = " + companyid + " and a.branchid = " + branchid + " order by description asc");  
         while (rs.next()) {
             Hpvalidationrules vaccountgroupDetails = new Hpvalidationrules();
             vaccountgroupDetails.setCode(rs.getString("code"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setProductcode(rs.getString("productcode"));
             vaccountgroupDetails.setValidationtype(rs.getString("validationtype"));
             vaccountgroupDetails.setFormula(rs.getString("formula"));
             vaccountgroupDetails.setResultcond(rs.getString("resultcond"));
             vaccountgroupDetails.setId(rs.getInt("id"));
             allvaccountgroupDetails.add(vaccountgroupDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
    
    public Hpvalidationrules retrieveHpvalrule(int id,int branchid,int companyid) throws HpgenericException {
        Hpvalidationrules vaccountgroupDetails = new Hpvalidationrules();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset("select * FROM hpvalidationrules a where a.id = " + id + " and a.companyid  = " + companyid + " and a.branchid = " + branchid + " order by description asc");  
         while (rs.next()) {
             vaccountgroupDetails = new Hpvalidationrules();
             vaccountgroupDetails.setCode(rs.getString("code"));
             vaccountgroupDetails.setDescription(rs.getString("description")); 
             vaccountgroupDetails.setProductcode(rs.getString("productcode"));
             vaccountgroupDetails.setValidationtype(rs.getString("validationtype"));
             vaccountgroupDetails.setFormula(rs.getString("formula"));
             vaccountgroupDetails.setResultcond(rs.getString("resultcond"));
             vaccountgroupDetails.setId(rs.getInt("id"));
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
    
    public String updatehpvalidationrules(int dcode,String ddesc,String dformula,String dprod,String dcond,int dbranch,int dcompany)  throws HpgenericException {
     
     String sqlStatement = "update hpvalidationrules set description = '" + ddesc + "',formula = '" + dformula +  "',resultcond='" + dcond + "',productcode = '" + dprod + "' where branchid = " +
            dbranch + " and companyid = " + dcompany + " and id = " + dcode;
     
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
      boolean opsuccessfull = dbobj.updateTable(sqlStatement);
      String opsuccessfullmsg = "OK";
      
     } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
     }
     finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
     return "OK";
    }
    
    public void removesetting(int code,int branchid,int companyid) throws HpgenericException {
        
     String sqlStatement = "delete from hpvalidationrules where id = " + code + " and branchid = " +
            branchid + " and companyid = " + companyid;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean candelete = true;
      
     try {
         dbobj.updateTable(sqlStatement);
         String opsuccessfullmsg = "OK";
      dbobj = null;  
      } catch (Exception ex) {
           throw new HpgenericException("Transaction failed: " + ex.getMessage());
      }
      finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
    }
    
    // hp single payment
    
     @Override
    public List<Hprequestdetails> getHpreqs4singpymt(int companyid,int branchid,String userid) throws HpgenericException {
    List allvobjDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
       // GetSetting usercanapprov = new GetSetting();
        //String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        //if (canapprove.equalsIgnoreCase("YES")==false) {
        //   addsql = " and requestby != '" + userid + "'"; 
       // }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id where a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'S') order by txndate desc");
         while (rs.next()) {  
             Hprequestdetails vobjDetails = new Hprequestdetails();
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return allvobjDetails;
    }
    
    @Override
    public List<Hprepymtschddetails> getHpscdhdet(String refid,int companyid,int branchid,String userid) throws HpgenericException {
    List allvobjDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT b.* FROM hprequests a inner join hprepaymentschedule b on a.refid = b.refid and a.branchid = b.branchid and a.companyid = b.companyid where a.refid = '" + refid + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'S') order by instalno desc");
         while (rs.next()) {  
             Hprepymtschddetails vobjDetails = new Hprepymtschddetails();
             i = i + 1;
             vobjDetails.setId(rs.getInt("Id"));
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setInstalNo(rs.getInt("Instalno"));
             vobjDetails.setInstalment(rs.getDouble("Instalment"));
             vobjDetails.setInterest(rs.getDouble("Interest"));
             vobjDetails.setPrincipal(rs.getDouble("Principal"));
             vobjDetails.setInterestToDate(rs.getDouble("InterestToDate"));
             vobjDetails.setPrincipalToDate(rs.getDouble("PrincipalToDate"));
             vobjDetails.setScheduleBalance(rs.getDouble("ScheduleBalance"));
             vobjDetails.setRpymtdate(rs.getDate("Rpymtdate"));
             vobjDetails.setPayed(rs.getShort("Payed"));
            // vobjDetails.setPaymentdate(rs.getDate("Paymentdate"));
             
             allvobjDetails.add(vobjDetails);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return allvobjDetails;
    }
     
    public List<List<String>> generaterepymtschdlasstr(List<Hprepymtschddetails> listofobj){
         List allvobjDetails = new ArrayList();
         SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
         String dateopenedx = null;
         Iterator<Hprepymtschddetails> bditerator = listofobj.iterator();
        int i = 0;
        
        while (bditerator.hasNext()) {
            
            Hprepymtschddetails objinn = bditerator.next();
            List allrowrecDetails = new ArrayList<String>();
             
            allrowrecDetails.add(objinn.getId().toString());
            
            allrowrecDetails.add(Integer.toString(objinn.getInstalNo()));
            allrowrecDetails.add(objinn.getInstalment().toString());
            allrowrecDetails.add(objinn.getPrincipal().toString());
            allrowrecDetails.add(objinn.getInterest().toString());
            allrowrecDetails.add(objinn.getInterestToDate().toString());
            allrowrecDetails.add(objinn.getPrincipalToDate().toString());
            allrowrecDetails.add(objinn.getScheduleBalance().toString());
            
           // allrowrecDetails.add(objinn.getPaymentdate().toString());
            try {
            dateopenedx = formatter1.format(objinn.getRpymtdate());
             } catch( NullPointerException nullEx ) {   }
            allrowrecDetails.add(dateopenedx);
            allrowrecDetails.add(objinn.getRefid());
            String valuepay = "Unpaid";
            if (objinn.getPayed()==1) {
                valuepay = "Paid";
            }
            allrowrecDetails.add(valuepay);
            
            allvobjDetails.add(i,allrowrecDetails);
            i = i + 1;
            //System.out.println("line no" + i +" " + objinn.getScheduleBalance().toString());
         }
         
        return allvobjDetails;
    }
    
    
    public Hprequestdetails getHpreq4disp(String ref,int companyid,int branchid,String userid) throws HpgenericException {
    Hprequestdetails vobjDetails = new Hprequestdetails();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT a.*,b.name as productname,c.description as interestcalcmtddesc,d.description as repaymentfrequencydesc FROM hprequests a inner join products b on a.product = b.code and a.branchid = b.branch_id and a.companyid = b.company_id left join hpintcalcmtd c on a.interestcalcmtd = c.code left join hprepymtfreq d on a.repaymentfrequency = d.code  where a.refid = '" + ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid + " order by txndate desc");
         while (rs.next()) {  
             i = i + 1;
             vobjDetails.setId(i);
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setMembername(rs.getString("membername"));
             vobjDetails.setMemberno(rs.getString("memberno"));
             vobjDetails.setProduct(rs.getString("product"));
             vobjDetails.setProductname(rs.getString("productname"));
             vobjDetails.setHpprice(rs.getDouble("hpprice"));
             vobjDetails.setCashprice(rs.getDouble("Cashprice"));
             vobjDetails.setDownpaymentamount(rs.getDouble("downpaymentamount"));
             vobjDetails.setInterestamt(rs.getDouble("interestamt"));
             vobjDetails.setInterestrate(rs.getDouble("interestrate"));
             vobjDetails.setCreateddate(rs.getDate("Requestdate"));
             vobjDetails.setCreatedby(rs.getString("Requestby"));
             vobjDetails.setInvref(rs.getString("invoiceref"));
             vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtd"));
             vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequency"));
             vobjDetails.setLoanBalance(rs.getDouble("loanBalance"));
             //System.out.println("hreee" + vobjDetails.getRepaymentfrequency());
             //vobjDetails.setInterestcalcmtd(rs.getString("interestcalcmtddesc"));
             //vobjDetails.setRepaymentfrequency(rs.getString("repaymentfrequencydesc"));
             vobjDetails.setNoofpayments(rs.getInt("noofpayments"));
             vobjDetails.setRepaymentperiodinmonths(rs.getInt("repaymentperiodinmonths"));
             txn_date=formatter.format(rs.getDate("Txndate"));
             vobjDetails.setTxndatestr(txn_date);
             vobjDetails.setTxndate(rs.getDate("Txndate"));
             
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return vobjDetails;
    }
    
    
    @Override
    public Hprepymtschddetails getHpreq4paysing(String refid,int did,int companyid,int branchid,String userid) throws HpgenericException {
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        int i=0;
        Hprepymtschddetails vobjDetails = new Hprepymtschddetails();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT b.*,a.product FROM hprequests a inner join hprepaymentschedule b on a.refid = b.refid and a.branchid = b.branchid and a.companyid = b.companyid where a.refid = '" + refid + "' and b.id = " + did + " and b.payed =0 and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (a.status = 'S') order by instalno desc");
         while (rs.next()) {  
             
             i = i + 1;
             vobjDetails.setId(rs.getInt("Id"));
             vobjDetails.setRefid(rs.getString("Refid")); 
             vobjDetails.setInstalNo(rs.getInt("Instalno"));
             vobjDetails.setInstalment(rs.getDouble("Instalment"));
             vobjDetails.setInterest(rs.getDouble("Interest"));
             vobjDetails.setPrincipal(rs.getDouble("Principal"));
             vobjDetails.setInterestToDate(rs.getDouble("InterestToDate"));
             vobjDetails.setPrincipalToDate(rs.getDouble("PrincipalToDate"));
             vobjDetails.setScheduleBalance(rs.getDouble("ScheduleBalance"));
             vobjDetails.setRpymtdate(rs.getDate("Rpymtdate"));
             String rpymtdatestr = formatter.format(rs.getDate("Rpymtdate"));
             vobjDetails.setRpymtdatestr(rpymtdatestr);
             vobjDetails.setPayed(rs.getShort("Payed"));
             vobjDetails.setPayed(rs.getShort("Payed"));
            // vobjDetails.setPaymentdate(rs.getDate("Paymentdate"));
             //payment date issue
            // double penl = determinepenalty(rs.getDouble("Principal"),rs.getDouble("Interest"),rs.getDate("Paymentdate"),rs.getDate("Rpymtdate"), rs.getString("product"),branchid,companyid);
           //  vobjDetails.setPenalty(penl);
           }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
        
       return vobjDetails;
    }
    
    @Override 
    public String postsingHpsales(Hprepymtschddetails hpdet,int companyid,int branchid,String ipaddr, String username,String timezone,int dyr,int dper,java.util.Date dpostdate)  throws HpgenericException {
     java.util.List<Txn> drlist = new LinkedList<Txn>();
        String drepydatestr = "";
        String retval = "";
        int servres = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
              drepydatestr = formatter.format(hpdet.getPaymentdate());
         } catch( NullPointerException nullEx ) {   }
        
         drlist =  gettxndetailshp(hpdet,companyid,branchid,username);
        
        //pass list to postservice
         servres = callservicehp(drlist,dpostdate,companyid,branchid,username,dyr,dper,timezone,dpostdate);
        if (servres==201) {
             updateshcdl(hpdet.getRefid(),hpdet.getId(),companyid,branchid,drepydatestr);
             retval = "ok";
        }
          
        return retval;
    }
    
    
    public java.util.List<Txn> gettxndetailshp(Hprepymtschddetails hpdet,int companyid,int branchid,String userid) throws HpgenericException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    java.util.List<Txn> dliref = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        ResultSet rs2=null;
        String loc = "";
        String addsql = "";
        int kk = 0;
        
        try {
         String dnarrhpdwnpay = "";
         String narrationhpdown = "";
         rs2 =  dbobj.retrieveRecset("SELECT a.* FROM txncodes a where a.TransactionCode = 'HPP'");
          while (rs2.next()) {  
             dnarrhpdwnpay = rs2.getString("narrative");
           //System.out.println("dnarrhpdwnpay " + dnarrhpdwnpay); 
          }    
            
         rs =  dbobj.retrieveRecset("SELECT a.accountno,a.refid,d.Instalment,d.Rpymtdate,a.product,d.instalno,d.interest,d.principal,q.fundsourceaccount,q.penaltyincomeaccount from hprequests a " +
                 " inner join hprepaymentschedule d on a.refid = d.refid and a.companyid = d.companyid and a.branchid = d.branchid " + 
                 " inner join qryproductaccounts q on a.product = q.code and d.companyid = a.companyid and d.branchid = q.branch_id " + 
                 " where d.refid = '" + hpdet.getRefid() + "' and d.id = " + hpdet.getId() + " and d.companyid = " + companyid + " and d.branchid = " + branchid );
         while (rs.next()) {
             System.out.println("kk " + kk);
             Txn dtxn1 = new Txn();
             Txn dtxn2 = new Txn();
             Txn dtxn3 = new Txn();
             Txn dtxn4 = new Txn();
             
              narrationhpdown = String.format(dnarrhpdwnpay,rs.getString("product"),hpdet.getInstalNo(),hpdet.getRefid());
            // System.out.println("narrationhpdown " + narrationhpdown);
             dtxn1.setAccountno(rs.getString("accountno"));
             dtxn2.setAccountno(rs.getString("fundsourceaccount"));
             dtxn3.setAccountno(rs.getString("accountno"));
             dtxn4.setAccountno(rs.getString("fundsourceaccount"));
             dtxn1.setNarrative("Principal: " + narrationhpdown);
             dtxn2.setNarrative("Principal: " + narrationhpdown);
             dtxn3.setNarrative("Interest: " + narrationhpdown);
             dtxn4.setNarrative("Interest: " + narrationhpdown);
             dtxn1.setAmount(hpdet.getPrincipal());
             dtxn2.setAmount(-1*hpdet.getPrincipal());
          
             dtxn3.setAmount(hpdet.getInterest());
             dtxn4.setAmount(-1*hpdet.getInterest());
             
             dtxn1.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
             dtxn2.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
             dtxn3.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
             dtxn4.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
             //dtxndate =  rs.getDate("txn_date");
             dli.add(dtxn1);
             dli.add(dtxn2);
             dli.add(dtxn3);
             dli.add(dtxn4);
             if (hpdet.getPenalty()>0.0) {
                 Txn dtxn5 = new Txn();
                 Txn dtxn6 = new Txn();
                 dtxn5.setAccountno(rs.getString("penaltyincomeaccount"));
                 dtxn6.setAccountno(rs.getString("fundsourceaccount"));
                 dtxn5.setNarrative("Penalty: " + narrationhpdown);
                 dtxn6.setNarrative("Penalty: " + narrationhpdown);
                 dtxn5.setAmount(hpdet.getPenalty());
                 dtxn6.setAmount(-1*hpdet.getPenalty());
                 dtxn5.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
                 dtxn6.setReference(hpdet.getRefid()+ ":" + hpdet.getInstalNo());
                 dli.add(dtxn5);
                 dli.add(dtxn6);
             }
             
             
             dliref.add(dtxn1);
          }  
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
        } 
        
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (rs2 != null) {
              try {
                rs2.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        return dli;
   }
    
    private Integer callservicehp(java.util.List<Txn> dtxnlist,java.util.Date dtxndate,int companyid,int branchid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate){
             Integer respo = 0;
             Integer branchcode = branchid;   //"02";
             Integer coyid = companyid;//currrentuserServicex.getCurruser().getCompanyid(); // "7";
             Integer coyperiod = dper;//5;
             Integer coyyear = dyr;//2015;
             String sourcemod = "HP";
             String txntype = "HPP";
             java.util.Date txndate = dpostdate; //dtxndate;
              String theerrmess;
             
             TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
             Calendar rightNow = Calendar.getInstance(timeZone);
             java.util.Date entrydate = rightNow.getTime();
             double currrate = 1.0;
//var end
             String valuedatestr=new java.text.SimpleDateFormat("yyyy-MM-dd").format(dpostdate);
             Entrys ent = new Entrys();
             NewSerialno nvSerial = new NewSerialno();
             Integer varSerialint;
             varSerialint = nvSerial.returnSerialno("HPsref", branchcode, coyid);
             String varSerial = Integer.toString(varSerialint);
             Txnsheader txnhdr = new Txnsheader(varSerial, txntype, coyyear, coyperiod, txndate, dpostdate, entrydate, varSerial, "Hire Purchase Sales Posting:", sourcemod, userid, branchcode, coyid,dtimezone);
             ent.setTxnsheader(txnhdr);

             java.util.LinkedList<Entry> dentlist;
             dentlist = new LinkedList<Entry>();
             double amt = 0.0;
             String txncode = "HPP";
             for (Txn b : dtxnlist) {
                 Entry ent1 = new Entry(txntype, txncode, b.getAccountno(),valuedatestr, b.getReference(), varSerial, b.getNarrative(),"1" , b.getAmount(), b.getAmount(), currrate, userid, branchcode, coyyear, coyperiod, coyid);
                 dentlist.add(ent1);
             }

             ent.setEntrys(dentlist);       
             GLJerseyClient cliserv = new GLJerseyClient();
             respo = cliserv.calldservice(ent);
             
             theerrmess= cliserv.gettheerrmess();
             return respo;
         }
    
    
    public void updateshcdl(String ref,int id,int companyid,int branchid,String repydate) throws HpgenericException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        String addsql = "";
        String dextref = "";
        int kk = 0;
        try {
          rs =  dbobj.retrieveRecset("SELECT b.instalno,a.refid FROM hprequests a inner join qrynexthprepaymtscdl b on a.companyid = b.companyid and a.branchid = b.branchid and a.refid = b.refid and a.noofpayments = b.instalno where a.refid = '" + ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid );
           boolean lastinstal = false;
           if (rs.first() == true)
           {
              lastinstal = true; 
           }   
            
           String sqlStatement = "update hprepaymentschedule a, qrynexthprepaymtscdl b set a.payed = " + 1 + ",a.paymentdate = {d '" + repydate + "'} where a.refid = b.refid and a.instalno = b.instalno " +
                   " and a.branchid = b.branchid and a.companyid = b.companyid and a.refid = '" +
            ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid ; 
          System.out.println(sqlStatement);
           dbobj.updateTable(sqlStatement);
           if (lastinstal == true)
           {
              sqlStatement = "update hprequests a set status = 'F' where a.refid = '" +
            ref + "' and a.companyid = " + companyid + " and a.branchid = " + branchid ; 
            dbobj.updateTable(sqlStatement); 
           } 
         
        } catch (SQLException ex) {
          throw new HpgenericException(ex.getMessage());
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
       // return dli;
   }
    
    public double determinepenalty(double hpdetPrincipal,double hpdetInterest,java.util.Date hpdetPaymentdate,java.util.Date hpdetRpymtdate, String productcode, int branchid, int companyid){
        double amt = 0.0;
        //select penalty formula and grace days,penalty rate from products table-
        /*
         * has_penalty = 1,penalty(rate),default_penalty_days, penalty_formula
        */
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String dformula = "";
        double defdays = 0.0;
        double datediff = 0;
        Calendar dateinst1 = Calendar.getInstance();
        Calendar dateinst2 = Calendar.getInstance();
        dateinst1.setTime(hpdetRpymtdate);
        dateinst2.setTime(hpdetPaymentdate);
        try {
          rs =  dbobj.retrieveRecset("SELECT penalty,default_penalty_days, penalty_formula FROM products a where code = '" + productcode + "' and a.has_penalty = 1 and a.penalty_formula != '' and (a.penalty_formula IS NOT NULL) and a.company_id = " + companyid + " and a.branch_id = " + branchid );
           //System.out.println("SELECT penalty,default_penalty_days, penalty_formula FROM products a where code = '" + productcode + "' and a.has_penalty = 1 and a.penalty_formula != '' and (a.penalty_formula IS NOT NULL) and a.company_id = " + companyid + " and a.branch_id = " + branchid);
          while (rs.next()) {
               //determine if days of default >= actualpaydate - repaymentdate + gracedays
              datediff = (dateinst2.getTimeInMillis()-dateinst1.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
               defdays = (datediff - rs.getInt("default_penalty_days"));
               System.out.println("defdays " + defdays );
               if (defdays>0) {
                   dformula = rs.getString("penalty_formula");
                  // System.out.println("defdays " + defdays);
                   JEP parser = new JEP();
                   parser.setAllowUndeclared(true);
                   parser.parseExpression(dformula);
                   SymbolTable symbolTable = parser.getSymbolTable();
                   for (Object variableObj : symbolTable.values()) {
                       Variable variable = (Variable) variableObj;// symbolTable.getVar(variableName.toString());
                       if (!variable.isConstant()) {
                           if (variable.getName().equals("OP")) {
                             parser.addVariable(variable.getName(), hpdetPrincipal);
                           }
                           if (variable.getName().equals("OI")) {
                             parser.addVariable(variable.getName(), hpdetInterest);
                           }
                           if (variable.getName().equals("PR")) {
                             parser.addVariable(variable.getName(), rs.getDouble("penalty")/100);
                           }
                           if (variable.getName().equals("NDD")) {
                             parser.addVariable(variable.getName(), defdays);
                           }
                       }
                   }
                  amt = parser.getValue();
                 // System.out.println(" amt " +  amt );
                }
               //if yes - evaluate formula and replace variables with values principal,interest
               //Overdue Principal OP,Overdue Interest OI,Penalty Rate PR,No Of Default Days NDD
           }   
        } catch (SQLException ex) {
          //throw new HpgenericException(ex.getMessage());
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
       return amt;
    }
    
    
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
    
    

}
