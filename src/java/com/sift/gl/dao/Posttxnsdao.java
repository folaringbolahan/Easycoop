/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.cli.GLJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.FileuploadException;
import com.sift.gl.GendataService;
import com.sift.gl.GenericsiftException;
import com.sift.gl.GetSetting;
import com.sift.gl.NewSerialno;
import com.sift.gl.NotificationService;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Txn;
import com.sift.gl.model.Txnsheader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** dao class for persistence and data operations file uploads transactions postings for GL and HP
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Posttxnsdao implements Posttxnsinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;
    Double amtrecsok = 0.0;
    java.util.Date dtxndate;
    java.util.List<Txn> dliref = new LinkedList<Txn>();
    String newref = "";
    /**
     *
     */
    public Posttxnsdao () {
    }
    
    /**
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     * @throws GenericsiftException
     */
    public void post(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate,String ipaddr) throws GenericsiftException{
        //retrieve list based on refid and companyid
        java.util.List<Txn> drlist = new LinkedList<Txn>();
        List<List<Txn>>  batchlist = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon(); 
        ResultSet rs = null;
        if (paraid == 1) {
            //call gettxndetails
            updatefileuploadtbl(refid,branchid,companyid, userid,-1);
            ////loop thru group of batch in file
            try {
                 rs = dbobj.retrieveRecset("SELECT distinct entrybatchno FROM accountstxnimportpending where reference = '" + refid + "' and companyid = " + companyid + " and branchid = " + branchid + "");
                 while (rs.next()) {
                     drlist =  gettxndetails(refid,rs.getString("entrybatchno"),companyid,branchid,userid);
                     batchlist.add(drlist);
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
            
        }
        boolean errorexists = false;
        //pass list to postservice
        int servres=0;
        int sizeoflist = 0;
        System.out.println("batchsize " + batchlist.size());
        for (List<Txn> drlistx : batchlist) {
          servres = callservice(drlistx,dtxndate,companyid,branchid,userid,dyr,dper,dtimezone,dpostdate);
          if (servres==201) {
               //updatefileuploadtbl(refid,branchid,companyid, userid,1);
          }
          else
          {
             errorexists=true;
          }  
        }
        if (errorexists==false) {
            updatefileuploadtbl(refid,branchid,companyid, userid,1);
        }
        auditlogcall(117,"GLATP",ipaddr,userid,dtimezone,refid,Integer.toString(servres),branchid,companyid);
    }
    
    /**
     *
     * @param refid
     * @param companyid
     * @param branchid
     * @param paraid
     * @param userid
     * @param dyr
     * @param dper
     * @param dtimezone
     * @param dpostdate
     * @param ipaddr
     * @throws GenericsiftException
     */
    public void posthp(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate,String ipaddr) throws GenericsiftException{
        //retrieve list based on refid and companyid
        java.util.List<Txn> drlist = new LinkedList<Txn>();
        
        String dpostdatestr = "";
        int servres = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
              dpostdatestr = formatter.format(dpostdate);
         } catch( NullPointerException nullEx ) {   }
        
        if (paraid == 1) {
            //call gettxndetails
            updatefileuploadtblhp(refid,branchid,companyid, userid,-1);
            drlist =  gettxndetailshp(refid,companyid,branchid,userid);
        }
        //pass list to postservice
         servres = callservicehp(drlist,dtxndate,companyid,branchid,userid,dyr,dper,dtimezone,dpostdate);
        if (servres==201) {
            
               updatefileuploadtblhp(refid,branchid,companyid, userid,1);
               updateshcdl(refid,companyid,branchid,dpostdatestr);
        }
        else
        {
              updatefileuploadtblhp(refid,branchid,companyid, userid,0);
        }  
        
        auditlogcall(122,"HPRTP",ipaddr,userid,dtimezone,refid,Integer.toString(servres),branchid,companyid);
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
     * @param batchid
     * @param companyid
     * @param branchid
     * @param userid
     * @return
     * @throws GenericsiftException
     */
    public java.util.List<Txn> gettxndetails(String batchid,String entrybatchno,int companyid,int branchid,String userid) throws GenericsiftException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        String addsql = "";
        String docref = "";
        String entryrefno = "";
        int kk = 0;
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and user_id != '" + userid + "'"; 
        }
        try {
         rs =  dbobj.retrieveRecset("SELECT a.accountno as corrac,a.txn_date,b.accountno,b.narration,b.amount,b.txntype,b.entryrefno,b.txndate FROM fileuploadacttxn a inner join accountstxnimportpending b on a.company_id = b.companyid and a.branch_id = b.branchid and a.reference_number = b.reference where a.is_verified != 1 and b.haserrors = 0 and a.reference_number = '" + batchid + "' and b.entrybatchno = '" + entrybatchno + "' and a.company_id = " + companyid + " and a.branch_id = " + branchid + addsql);
         
         while (rs.next()) {
            // System.out.println("kk " + kk);
             Txn dtxn1 = new Txn();
             dtxn1.setAccountno(rs.getString("accountno"));
             dtxn1.setNarrative(rs.getString("narration"));
             if (rs.getString("entryrefno").isEmpty()==false) {
              entryrefno = ":" + rs.getString("entryrefno");
             } 
             if (rs.getString("txntype").equalsIgnoreCase("C")) {
                 dtxn1.setAmount(rs.getDouble("amount"));
             }
             else {
                 dtxn1.setAmount(-1*rs.getDouble("amount"));
             }
             dtxn1.setReference(batchid + entryrefno);
             
             if ((rs.getString("txndate").isEmpty() == false)) {
                 try {
                     dtxndate =  formatter1.parse(rs.getString("txndate"));
                 } catch (ParseException pEx) {
                 } catch (NullPointerException nullEx) {
                 }
             }
             
             //dtxndate =  rs.getDate("txn_date");
             dli.add(dtxn1);
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
        return dli;
   }
    
    public java.util.List<Txn> getjrnltxndetails(String batchid,int companyid,int branchid,String userid) throws GenericsiftException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        String addsql = "";
        String docref = "";
        String entryrefno = "";
        int kk = 0;
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and a.userid != '" + userid + "'"; 
        }
        try {
         rs =  dbobj.retrieveRecset("SELECT a.txndate,b.accountno,b.narrative,b.amount,b.docref,b.rate FROM txnsheaderua a inner join txnsua b on a.companyid = b.companyid and a.branchid = b.branchid and a.txntype = b.txntype and a.txnserial = b.txnserial where a.approvedstatus = 0 and a.headerid = " + batchid + " and a.companyid = " + companyid + " and a.branchid = " + branchid + addsql);
         
         while (rs.next()) {
            // System.out.println("kk " + kk);
             Txn dtxn1 = new Txn();
             dtxn1.setAccountno(rs.getString("accountno"));
             dtxn1.setNarrative(rs.getString("narrative"));
             dtxn1.setAmount(rs.getDouble("amount"));
             dtxn1.setRate(rs.getDouble("rate"));
             dtxndate =  rs.getDate("txndate");
             try 
             {
              if (rs.getString("docref").isEmpty()==false) {
               entryrefno = rs.getString("docref");
              } 
             }
             catch(NullPointerException nex) 
             {
                 entryrefno = "";
             }    
             dtxn1.setReference(entryrefno);
             dli.add(dtxn1);
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
        return dli;
   }
    
    
    private Integer callservice(java.util.List<Txn> dtxnlist,java.util.Date dtxndate,int companyid,int branchid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate){
             Integer respo = 0;
             Integer branchcode = branchid;   //"02";
             Integer coyid = companyid;//currrentuserServicex.getCurruser().getCompanyid(); // "7";
             Integer coyperiod = dper;//5;
             Integer coyyear = dyr;//2015;
             String sourcemod = "GL";
             String txntype = "JV";
             java.util.Date txndate = dtxndate;
              String theerrmess;
              newref = "";
             
             TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
             Calendar rightNow = Calendar.getInstance(timeZone);
             java.util.Date entrydate = rightNow.getTime();
             double currrate = 1.0;
//var end
             Entrys ent = new Entrys();
             NewSerialno nvSerial = new NewSerialno();
             Integer varSerialint;
             varSerialint = nvSerial.returnSerialno("Dref", branchcode, coyid);
             String varSerial = Integer.toString(varSerialint);
             Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, dpostdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid,dtimezone);
             ent.setTxnsheader(txnhdr);

             java.util.LinkedList<Entry> dentlist;
             dentlist = new LinkedList<Entry>();
             double amt = 0.0;
             String txncode = "";
             for (Txn b : dtxnlist) {
                 if (b.getAmount() > 0){
                    txncode = "CRV";
                 }
                 else {
                    txncode = "DRV";
                 }
                 try
                 {
                   if (b.getRate() > 0){
                    currrate = b.getRate();
                   }
                 }
                 catch (NullPointerException nex) 
                 { 
                     currrate = 1.0;
                 }
                 Entry ent1 = new Entry(txntype, txncode, b.getAccountno(), "2000-10-10", b.getReference(), varSerial, b.getNarrative(),"1" , b.getAmount(), b.getAmount(), currrate, userid, branchcode, coyyear, coyperiod, coyid);
                 dentlist.add(ent1);
                 System.out.println(txncode);
                 System.out.println(b.getAccountno());
                 System.out.println(b.getReference());
                 System.out.println(b.getNarrative());
                 System.out.println(b.getAmount());
                 System.out.println(txntype);
             }

             ent.setEntrys(dentlist);       
             GLJerseyClient cliserv = new GLJerseyClient();
             respo = cliserv.calldservice(ent);
             
             theerrmess= cliserv.gettheerrmess();
             newref=cliserv.getthereference();
             return respo;
         }
    
    
    
    public void updatefileuploadtbl(String ref,int branchid,int companyid,String veid,int isverified)  throws GenericsiftException  {
     String sqlStatement = "update fileuploadacttxn set status = " + isverified + ",is_verified = " + isverified + " ,verifier_id = '" + veid + "' where reference_number = '" +
            ref + "' and company_id = " + companyid + " and branch_id = " + branchid ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new GenericsiftException("Transaction failed: " + ex.getMessage());
      }
    }
    
    public boolean updatependingentrydtbl(String ref,int branchid,int companyid,String veid,int isverified,String txnserialref,String txntyperef,String timeZonestr)  throws GenericsiftException  {
     boolean resultop = false;
     String tempDate = null;
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      TimeZone timeZone = TimeZone.getTimeZone(timeZonestr);
      Calendar rightNow = Calendar.getInstance(timeZone);
        try {
         tempDate = formatter.format(rightNow.getTime());
        } catch( NullPointerException nullEx ) {   }
        String sqlStatement = "update txnsheaderua set approvedstatus = " +  isverified + ", approveruserid = '" + veid + "', approvaldate = {d '" + tempDate + "'}, txnseriala = '" + txnserialref + "' , txntypea = '" + txntyperef + "' where headerid = " +
            ref + " and companyid = " + companyid + " and branchid = " + branchid ;
     //String sqlStatement = "update fileuploadacttxn set status = " + isverified + ",is_verified = " + isverified + " ,verifier_id = '" + veid + "' where reference_number = '" +
      //      ref + "' and company_id = " + companyid + " and branch_id = " + branchid ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
     resultop = true;
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new GenericsiftException("Transaction failed: " + ex.getMessage());
      }
     return resultop;
    }
    
    
    public String postapprvjrnl(String refid,int companyid,int branchid, int paraid,String userid,int dyr,int dper,String dtimezone,java.util.Date dpostdate,String ipaddr) throws GenericsiftException{
        //retrieve list based on refid and companyid
        String retval = ""; //format - JV:::0 (txntype:::txnserial)
        java.util.List<Txn> drlist = new LinkedList<Txn>();
        List<List<Txn>>  batchlist = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon(); 
        ResultSet rs = null;
        if (paraid == 1) {
            //call gettxndetails
            ////loop thru group of batch in file
            drlist =  getjrnltxndetails(refid,companyid,branchid,userid);
            batchlist.add(drlist);
        }
        boolean errorexists = false;
        //pass list to postservice
        int servres=0;
        int sizeoflist = 0;
       //System.out.println("batchsize " + batchlist.size());
        for (List<Txn> drlistx : batchlist) {
          servres = callservice(drlistx,dtxndate,companyid,branchid,userid,dyr,dper,dtimezone,dpostdate);
          //System.out.println("check1 " + servres);
          if (servres==201) {
              //Pattern p = Pattern.compile("\\{(.*)\\}");
              List<String> dcodes=new ArrayList<String>();
              String dRegex = "\\{.+?\\}";
              Pattern p = Pattern.compile(dRegex,Pattern.CASE_INSENSITIVE);
              Matcher m = p.matcher(newref);
              String newrefint = "0";
              //System.out.println("check2 " + newref);
              while (m.find()) {
                newrefint= m.group();
                //System.out.println("check3 " + newrefint);
               } 
              
              String newrefclean = newrefint.replaceAll("\\{|\\}", "");
              retval = "JV:::"+newrefclean;
              
          }
          else
          {
             errorexists=true;
             retval = "JV:::0";
             //System.out.println("check5 " + retval);
          }  
        }
        
        auditlogcall(117,"GLATP",ipaddr,userid,dtimezone,refid+"-::-"+retval,Integer.toString(servres),branchid,companyid);
        //System.out.println("check6 " + retval);
        return retval;
    }
    
    
    /**
     *
     * @param batchid
     * @param companyid
     * @param branchid
     * @param userid
     * @return
     * @throws GenericsiftException
     */
    public java.util.List<Txn> gettxndetailshp(String batchid,int companyid,int branchid,String userid) throws GenericsiftException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    dliref = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        ResultSet rs2=null;
        String loc = "";
        String addsql = "";
        int kk = 0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and user_id != '" + userid + "'"; 
        }
        try {
         String dnarrhpdwnpay = "";
         String narrationhpdown = "";
         rs2 =  dbobj.retrieveRecset("SELECT a.* FROM txncodes a where a.TransactionCode = 'HPP'");
          while (rs2.next()) {  
             dnarrhpdwnpay = rs2.getString("narrative");
           //System.out.println("dnarrhpdwnpay " + dnarrhpdwnpay); 
          }    
            
         rs =  dbobj.retrieveRecset("SELECT b.accountno,b.hprefid,b.amount,b.penalty,b.repaymentdate,d.product,d.instalno,d.interest,d.principal,q.fundsourceaccount from fileuploadhprpy a inner join hpreptxnimportpending b  on a.company_id = b.companyid and a.branch_id = b.branchid and a.reference_number = b.reference " +
                 " inner join qrynexthprepaymtscdl d on b.hprefid = d.refid and b.companyid = d.companyid and b.branchid = d.branchid " + 
                 " inner join qryproductaccounts q on d.product = q.code and d.companyid = a.company_id and d.branchid = q.branch_id " + 
                 " where a.is_verified != 1 and b.haserrors = 0 and a.reference_number = '" + batchid + "' and a.company_id = " + companyid + " and a.branch_id = " + branchid + addsql);
         while (rs.next()) {
             System.out.println("kk " + kk);
             Txn dtxn1 = new Txn();
             Txn dtxn2 = new Txn();
             Txn dtxn3 = new Txn();
             Txn dtxn4 = new Txn();
              narrationhpdown = String.format(dnarrhpdwnpay,rs.getString("product"),rs.getInt("instalno"),rs.getString("hprefid"));
            // System.out.println("narrationhpdown " + narrationhpdown);
             dtxn1.setAccountno(rs.getString("accountno"));
             dtxn2.setAccountno(rs.getString("fundsourceaccount"));
             dtxn3.setAccountno(rs.getString("accountno"));
             dtxn4.setAccountno(rs.getString("fundsourceaccount"));
             dtxn1.setNarrative("Principal: " + narrationhpdown);
             dtxn2.setNarrative("Principal: " + narrationhpdown);
             dtxn3.setNarrative("Interest: " + narrationhpdown);
             dtxn4.setNarrative("Interest: " + narrationhpdown);
             dtxn1.setAmount(rs.getDouble("principal"));
             dtxn2.setAmount(-1*rs.getDouble("principal"));
          
             dtxn3.setAmount(rs.getDouble("interest"));
             dtxn4.setAmount(-1*rs.getDouble("interest"));
             
             dtxn1.setReference(rs.getString("hprefid")+ ":" + rs.getInt("instalno"));
             dtxn2.setReference(rs.getString("hprefid")+ ":" + rs.getInt("instalno"));
             dtxn3.setReference(rs.getString("hprefid")+ ":" + rs.getInt("instalno"));
             dtxn4.setReference(rs.getString("hprefid")+ ":" + rs.getInt("instalno"));
             //dtxndate =  rs.getDate("txn_date");
             dli.add(dtxn1);
             dli.add(dtxn2);
             dli.add(dtxn3);
             dli.add(dtxn4);
             dliref.add(dtxn1);
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
    
    public void updatefileuploadtblhp(String ref,int branchid,int companyid,String veid,int isverified)  throws GenericsiftException  {
     String sqlStatement = "update fileuploadhprpy set status = " + isverified + ",is_verified = " + isverified + " ,verifier_id = '" + veid + "' where reference_number = '" +
            ref + "' and company_id = " + companyid + " and branch_id = " + branchid ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      
      //if batch successfull - look through details and match refid to corresponding install no - and set as processed with gpostdate
      // at excel importation earlier checks should have been done to ensure payment on excel = > next upprocessed install no
      dbobj = null;  
      } catch (Exception ex) {
           throw new GenericsiftException("Transaction failed: " + ex.getMessage());
      }
    }
    
    /**
     *
     * @param batchid
     * @param companyid
     * @param branchid
     * @param postdate
     * @return
     * @throws GenericsiftException
     */
    public java.util.List<Txn> updateshcdl(String batchid,int companyid,int branchid,String postdate) throws GenericsiftException {
    java.util.List<Txn> dli = new LinkedList<Txn>();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        String addsql = "";
        String dextref = "";
        int kk = 0;
        try {
          for (Txn b : dliref) {
           rs =  dbobj.retrieveRecset("SELECT b.instalno,a.refid FROM hprequests a inner join qrynexthprepaymtscdl b on a.companyid = b.companyid and a.branchid = b.branchid and a.refid = b.refid and a.noofpayments = b.instalno where a.refid = '" + b.getReference() + "' and a.companyid = " + companyid + " and a.branchid = " + branchid );
           boolean lastinstal = false;
           dextref = "";
           String[] arrofstr = b.getReference().split(":");
           dextref = arrofstr[0];
           dextref = dextref.trim();
           if (rs.first() == true)
           {
              lastinstal = true; 
           }   
            
           String sqlStatement = "update hprepaymentschedule a, qrynexthprepaymtscdl b set a.payed = " + 1 + ",a.paymentdate = {d '" + postdate + "'} where a.refid = b.refid and a.instalno = b.instalno " +
                   " and a.branchid = b.branchid and a.companyid = b.companyid and a.refid = '" +
            dextref + "' and a.accountno = '" + b.getAccountno() + "' and a.companyid = " + companyid + " and a.branchid = " + branchid ; 
          System.out.println(sqlStatement);
           dbobj.updateTable(sqlStatement);
           if (lastinstal == true)
           {
              sqlStatement = "update hprequest a set status = 'F' where a.refid = '" +
            dextref + "' and a.accountno = '" + b.getAccountno() + "' and a.companyid = " + companyid + " and a.branchid = " + branchid ; 
            dbobj.updateTable(sqlStatement); 
           } 
            
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
        return dli;
   }
}
