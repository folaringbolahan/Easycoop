/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl;
import com.sift.gl.model.Txnsheader;
import com.sift.gl.model.Entry;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author yomi
 */
public class ApplyEntrys {
    GendataService dbobj = new GendataService();
    java.sql.Connection myCon;
    Integer gAcctYear = 0;
    Integer gAcctPeriod = 0;
    Integer varSerialint;
    public String varSerial;
    Integer vCompany;
    Integer vBranch;
    boolean allEntrysvalid = true;
    java.util.List<Entry> allEntrys;
    Txnsheader dtxnHeader;
    TimeZone timeZone;
    //please reassign currentaccountingdetails class to variables
    public ApplyEntrys(java.util.List<Entry> vallEntrys, Txnsheader vdtxnHeader,Integer gYearx ,Integer gPeriodx,Integer branch,Integer companyid,String timezone) {
       try {
           dbobj.inimkcon();
           myCon = dbobj.getConnection();
           myCon.setAutoCommit(false);
           allEntrys = vallEntrys;
           dtxnHeader = vdtxnHeader;
           gAcctYear = gYearx;
           gAcctPeriod = gPeriodx;
           vBranch = branch;
           vCompany = companyid;
           timeZone = TimeZone.getTimeZone(timezone);
       }
       catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
        }
       
    }

    public boolean performAllupdates( ){
        boolean thisEntryvalid = false;
        boolean thisEntryvalid1 = false;
        boolean thisEntryvalid2 = false;
        boolean thisEntryvalid3 = false;
        thisEntryvalid = updateEntryHeader(dtxnHeader);
        if (thisEntryvalid == false ) {
            allEntrysvalid = false ;
        }
        System.out.println("thisEntryvalid " + thisEntryvalid);
        for (Entry comEntry : allEntrys) {
          //  System.out.println("here now 1" + thisEntryvalid);
            thisEntryvalid1 = updateEntrys(comEntry);
           // System.out.println("allEntrysvalid1-" + thisEntryvalid1);
            thisEntryvalid2 = updateAccounts(comEntry.getAccountNo(),comEntry.getAmount(),comEntry.getCcyAmount());
          //  System.out.println("allEntrysvalid2-" + thisEntryvalid2);
            thisEntryvalid3 = updateAccountbals(comEntry.getAccountNo(),comEntry.getAmount(),comEntry.getCcyAmount(),comEntry.getYear(),comEntry.getPeriod());
          //System.out.println("allEntrysvalid3-" + thisEntryvalid3);
         if ((thisEntryvalid == false) || (thisEntryvalid1 == false) || (thisEntryvalid2 == false) || (thisEntryvalid3 == false)) {
           allEntrysvalid = false ;
           
         }
        }
        //to take care of cases where no accounts exists or no accountbalances
        if ((thisEntryvalid == false) || (thisEntryvalid1 == false) || (thisEntryvalid2 == false) ) {
           allEntrysvalid = false ;
           
         }
        if (allEntrysvalid == false) {
            try
            {
              myCon.rollback();
              myCon.close();
            }
            catch ( SQLException sqlex) {
            System.err.println("SQLException: " + sqlex.getMessage());
            }
            finally {
            if (dbobj != null) {
             try {
               dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; // prevent any future access  
             }
            }
         else
         {
             try
             {
              myCon.commit();
              myCon.close();
              allEntrysvalid = true ;
             }
             catch ( SQLException sqlex) {
              System.err.println("SQLException: " + sqlex.getMessage());
             }
             finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; // prevent any future access  
           }
         }
       // System.out.println("allEntrysvalid4-" + allEntrysvalid);
        return allEntrysvalid;
    }

    public boolean updateAccounts(String acctNo, double amount,double ccyamount){
        boolean updateSuccessfull= false;
        ResultSet rsAccts=null;
        String mySQLString = "select * from accounts where accountno = '" + acctNo + "' and branch = " + vBranch + " and companyid = " + vCompany + "";
        String mySQLString2;
        try {
          System.out.println(mySQLString);
            rsAccts =  dbobj.retrieveRecset(mySQLString);
          if (rsAccts.first() == true)
           {
              //vNewSerial = rsSerials.getInt("Serial") + 1;
             mySQLString2 = "update accounts set ccyClearedBalance = ccyClearedBalance + " + ccyamount + ",ccyBalance = ccyBalance + " + ccyamount + ", ClearedBalance = ClearedBalance + " + amount + ", Balance = Balance + " + amount + " where accountno = '" + acctNo + "' and branch = " + vBranch + " and companyid = " + vCompany + "";
             System.out.println(mySQLString2);
             updateSuccessfull = dbobj.updateTablebatch(mySQLString2);
            // updateSuccessfull = true;
           }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         updateSuccessfull = false;
        }
        finally {
          try{  
           if (rsAccts!=null)
           {rsAccts.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          
       }
        return updateSuccessfull;
    }
    public boolean updateAccountbals(String acctNo, double amount,double ccyamount,Integer yr,Integer period){
        boolean updateSuccessfull= false;
        double ccycr = 0;
        double ccydr = 0;
        double cr = 0;
        double dr = 0;
        //gAcctPeriod = Company.getCurrentPeriod();
       // gAcctYear = Company.getCurrentYear();
        ResultSet rsAcctBals=null;
        String mySQLString = "select * from accountbalances where accountno = '" + acctNo + "' and year = " + yr + " and period = " + period + " and branch = " + vBranch + " and companyid = " + vCompany + "";
        String mySQLString2;

        if (amount < 0) {
           dr = amount*-1;
        }
        else
        {
            cr = amount;
        }
        if (ccyamount < 0) {
           ccydr = ccyamount*-1;
        }
        else
        {
           ccycr = ccyamount;
        }
        try {
          rsAcctBals =  dbobj.retrieveRecset(mySQLString);
          if (rsAcctBals.first() == true)
           {
             mySQLString2 = "update accountbalances set closingBalance = closingBalance + " + amount + ",closingccyBalance = closingccyBalance + " + ccyamount + ", drccyAmount = drccyAmount + " + ccydr + ", crccyAmount = crccyAmount + " + ccycr + ", drAmount = drAmount + " + dr + ", crAmount = crAmount + " + cr + " where accountno = '" + acctNo + "' and year = " + yr + " and period = " + period + " and branch = " + vBranch + " and companyid = " + vCompany + "";
             System.out.println(mySQLString2);
             updateSuccessfull = dbobj.updateTablebatch(mySQLString2);
             //updateSuccessfull = true;
           }
          else
          {
             mySQLString2 = "insert into accountbalances (accountno,year,period,closingBalance,closingccyBalance,drccyAmount,crccyAmount,drAmount,crAmount,branch,companyid) values('" + acctNo + "'," + yr + "," + period + "," + amount + "," + ccyamount + "," + ccydr + "," + ccycr + "," + dr + "," + cr + "," + vBranch + "," + vCompany + ")";
             System.out.println(mySQLString2);
             updateSuccessfull = dbobj.updateTablebatch(mySQLString2);
             //updateSuccessfull = true;
          }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         updateSuccessfull = false;
        }
         finally {
          try{  
           if (rsAcctBals!=null)
           {rsAcctBals.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          
       }
        return updateSuccessfull;
    }
    public boolean updateEntryHeader(Txnsheader dtxnHeader){
        boolean updateSuccessfull= false;
        String mySQLString2;
        NewSerialno vSerial = new NewSerialno();
        String tempDate = null;
        String tempDate1 = null;
        String tempDate2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        //formatter.setTimeZone(timeZone);
        formatter1.setTimeZone(timeZone);
        Calendar rightNow = Calendar.getInstance(timeZone);
        try {
         tempDate = formatter.format(dtxnHeader.getPostdate());
         tempDate1 = formatter.format(dtxnHeader.getTxnDate());
         tempDate2 = formatter1.format(rightNow.getTime());
        } catch( NullPointerException nullEx ) {   }
        if ((dtxnHeader.getTxnSerial()==null) || (dtxnHeader.getTxnSerial().equals("")) || (dtxnHeader.getTxnSerial().equals("1"))) {
         varSerialint =  vSerial.returnSerialno(dtxnHeader.getTxnType(),vBranch,vCompany);
         varSerial = Integer.toString(varSerialint);
         dtxnHeader.setTxnSerial(varSerial);
        }
        else
        {
         varSerial = dtxnHeader.getTxnSerial();  
        }
         try {
            mySQLString2 = "insert into txnsheader (txnSerial,txnType,txnDate,postdate,entrydate,docRef,headernarrative,source,userId,branchid,year,period,companyid) values('" + dtxnHeader.getTxnSerial() + "','" + dtxnHeader.getTxnType() + "',{d '" + tempDate1 + "'},{d '" + tempDate + "'},{d '" + tempDate2 + "'},'" + dtxnHeader.getDocRef() + "','" + dtxnHeader.getHeadernarrative() + "','" + dtxnHeader.getSource() + "','" + dtxnHeader.getUserId() + "','" + dtxnHeader.getBranchId() + "'," + dtxnHeader.getYear() + "," + dtxnHeader.getPeriod() + "," + vCompany + ")";
            //mySQLString2 = "insert into entryheaders (entrySerial,entryType,valueDate,postdate,entrydate,documentReference,remarks,userId,branch) values('" + dtxnHeader.getTxnSerial() + "','" + dtxnHeader.getTxnType() + "',{d '" + tempDate1 + "'},{d '" + tempDate + "'},{d '" + tempDate2 + "'},'" + dtxnHeader.getDocRef() + "','" + dtxnHeader.getHeadernarrative() + "','" + dtxnHeader.getUserId() + "','" + dtxnHeader.getBranchId() + "')";
            System.out.println(mySQLString2);
            updateSuccessfull = dbobj.updateTablebatch(mySQLString2);
            // updateSuccessfull = true;
          }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         updateSuccessfull = false;
        }

        return updateSuccessfull;
    }
    public boolean updateEntrys(Entry dEntry){
        boolean updateSuccessfull= false;
        String mySQLString2;
        try {
             mySQLString2 = "insert into txns (txnType,TransactionCode,accountno,docref,headerdocref,narrative,txnSerial,ccyDebit,ccyCredit,ccyAmount,debit,credit,amount,rate,userId,branchid,year,period,companyid) values('" + dEntry.getTxnType() + "','" + dEntry.getTxncode() + "','" + dEntry.getAccountNo() + "','" + dEntry.getDocref() + "','" +  dEntry.getHeaderdocref() + "','" + dEntry.getNarrative() + "','" + varSerial + "'," + dEntry.getCcyDebit() + "," + dEntry.getCcyCredit() + "," + dEntry.getCcyAmount() + "," + dEntry.getDebit() + "," + dEntry.getCredit() + "," + dEntry.getAmount() + "," + dEntry.getRate() + ",'" + dEntry.getUserId() + "','" + dEntry.getBranchId() + "'," + dEntry.getYear() + "," + dEntry.getPeriod() + "," + vCompany + ")";
             //mySQLString2 = "insert into entrys (entryType,reference,narrative,entrySerial,ccyDebit,ccyCredit,ccyAmount,debit,credit,amount,rate) values('" + dEntry.getTxnType() + "','" + dEntry.getDocref() + "','" + dEntry.getNarrative() + "','" + varSerial + "'," + dEntry.getCcyDebit() + "," + dEntry.getCcyCredit() + "," + dEntry.getCcyAmount() + "," + dEntry.getDebit() + "," + dEntry.getCredit() + "," + dEntry.getAmount() + "," + dEntry.getRate() + ")" ;
            System.out.println(mySQLString2);
             updateSuccessfull = dbobj.updateTablebatch(mySQLString2);
             //updateSuccessfull = true;
          }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         updateSuccessfull = false;
        }

        return updateSuccessfull;
    }


}
