/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;
//import javax.swing.JFrame;
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Txnsheader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import org.apache.log4j.Logger;
/**
 *
 * @author yomi-pc
 */
public class EOPeriodprocessing {
    
/**
 *
 * @author ABAYOMI AWOSUSI
 */
      String tempDate = null;
      String tempDate2 = null;
      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
      Calendar rightNow = null; //Calendar.getInstance();
      GendataService dbobj;// = new GendataService();
      java.util.Date gEOMDate;
      java.util.Date gBOMDate;
      String mySQLString2;
      String mySQLString;
      String mySQLString3;
      String mySQLString4;
      String mySQLString5;
      String mySQLString6;
      Integer companyid;
      Integer branchid;
      Integer companycurryear;
      Integer companycurrper;
      Integer countryid;
      String guserid;
      String gprocessingmethod;
      String timezone;
      String Companyaccmtd = "";
      TimeZone timeZone;
      boolean endofperiod = false;
      boolean reaccountexists = false;
      boolean nextfiscalperiodexists = false;
      boolean currentfiscalperiodexists = false;
      String performprecheckerrmess = "";
      java.util.Date gpostdate;
      java.util.Date gcurrsysdate;
      private static Logger logger = Logger.getLogger(EOPeriodprocessing.class.getPackage().getName());
    /** class handles end of day and end of period processes
     *
     * @param companyid
     * @param branchid
     * @param companycurryear
     * @param companycurrper
     * @param gpostdate
     * @param gcurrsysdate
     * @param guserid
     * @param countryid
     * @param gcurr
     * @param processingmethod
     * @param timezone
     * @return
     * @throws EOPException
     */
    public boolean EOPeriodprocessinginit(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr,String processingmethod,String timezone) throws EOPException{
       int getconf;
      /// getconf = JOptionPane.showConfirmDialog (null,"Please backup your database before proceeding.Proceed with End of Month Processing? " ,"SiftERP",JOptionPane.YES_NO_OPTION);
      /// if (getconf == JOptionPane.YES_OPTION)
      ////  {
          boolean opsuccessful = false;
          dbobj = new GendataService();
         // dbobj.inimkcon();
          this.guserid = guserid;
          this.companyid = companyid;
          this.branchid = branchid;
          this.companycurryear = companycurryear;
          this.companycurrper = companycurrper;
          this.gpostdate = gpostdate;
          this.gcurrsysdate = gcurrsysdate;
          this.countryid = countryid;
          this.gprocessingmethod = processingmethod;
          this.timezone = timezone;
          timeZone = TimeZone.getTimeZone(timezone);
          formatter1.setTimeZone(timeZone);
          rightNow = Calendar.getInstance(timeZone);
          
          logger.info("------Logging Begins for " + this.companyid + " : " + this.branchid + " ; " + this.gpostdate + " ; " + this.gcurrsysdate + " ; " + this.companycurryear + " ; " + this.companycurrper + " " );
          logprocstartindb();
          GetSetting REA = new GetSetting();
          String Acctmtd = REA.GetSettingvalue("ACCOUNTINGMETHOD",branchid,companyid);
          endofperiod = isperiodend();
          Companyaccmtd = Acctmtd;
          //test loan accrual
         // testAccrueloansinterest();
          ////debug
         // try{
         //  dbobj.inimkcon();
        //  DatabaseMetaData dmd = dbobj.getConnection().getMetaData();
        //  String url = dmd.getURL();
        //   System.out.println("metadata info " + url);
        //  }
        //  catch(Exception ex){
        //      System.out.println("metadata exception " + ex);
         // }
          ////debug
          //end test loan accrual
          
          if ((processingmethod.equalsIgnoreCase("AUTO")!=true)  || ((processingmethod.equalsIgnoreCase("AUTO")==true) && (Newday()==true)))
          {
          
          if (Acctmtd.equalsIgnoreCase("ACCRUAL")==true) {
              Accruesavinterest(companyid,branchid,companycurryear,companycurrper,gpostdate, gcurrsysdate, guserid,countryid,gcurr);
              Accrueloansinterest(companyid,branchid,companycurryear,companycurrper,gpostdate, gcurrsysdate, guserid,countryid,gcurr);
              if (endofperiod==true) {
                Applysavinterest(companyid,branchid,companycurryear,companycurrper,gpostdate, gcurrsysdate, guserid,countryid,gcurr);
               ///   Applyloansinterest();
              }
              
          }
          Processehpinterestrepayment(companyid,branchid,gpostdate);
          
          calcAcctBal();
          if (endofperiod==true) {
              performprecheckerrmess = "";
             performprechecks();   
           if ((reaccountexists == true) && (nextfiscalperiodexists == true)) 
           {   
            closeperiod();
            updateCompany(gpostdate);
            opsuccessful = true;
           }
           else {
             logger.error(performprecheckerrmess);
             logerrindb(performprecheckerrmess);
             throw new EOPException(performprecheckerrmess);  
           }
          }
          else {
             updateCompany(gpostdate);
              if (processingmethod.equalsIgnoreCase("AUTO")==true) {
                 Calendar vCal = Calendar.getInstance(); 
                 vCal.setTime(gpostdate);
                 vCal.add(Calendar.DATE, 1);
                 Sodaystatus(vCal.getTime());
                 InitializeAPP(); 
              }
              opsuccessful = true;
          }
        } //if manual or auto and new day
          logproccompindb();
          return opsuccessful;
    }
    
    
    
    /**
     *
     * @return
     * @throws EOPException
     */
    public boolean isperiodend() throws EOPException{
      int pYear;
      int pPeriod;
      boolean itisperiodend=false;
      java.util.Date wkEOMDate;
      java.util.Date wkBOMDate;
      dbobj.inimkcon();
      mySQLString = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.year = " + companycurryear + " and period_id = " + companycurrper + " and b.company_id = " + companyid + " and b.branch_id = " + branchid;
     // System.out.println(mySQLString);
      ResultSet curRecSet;
      ResultSet curRecSet2=null;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       if (dbobj.retrieveRecset(mySQLString).first() == false)
         {
          System.out.println("Exception: Fiscal Calendar for current period not defined.");  
          logger.error("Exception: Fiscal Calendar for current period not defined.");
          logerrindb("Exception: Fiscal Calendar for current period not defined.");
          throw new EOPException("Exception: Fiscal Calendar for current period not defined.");
         }
       else
       {
        while (curRecSet.next()) {
         wkBOMDate = (curRecSet.getDate("fp_start"));
         wkEOMDate = (curRecSet.getDate("fp_end"));
         gBOMDate = (curRecSet.getDate("fp_start"));
         gEOMDate = (curRecSet.getDate("fp_end"));
         tempDate = formatter3.format(wkBOMDate);
         tempDate2 = formatter3.format(wkEOMDate);
         if (wkEOMDate.compareTo(gpostdate)==0) {
             itisperiodend=true;
         }
       }
        
       }
      }
      catch(SQLException ex) {
            //JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
           System.out.println("SQLException: " + ex.getMessage());
           logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
      return itisperiodend;
    }
    
     /**
     *
     * @throws EOPException
     */
    public void performprechecks() throws EOPException{
      //check if REAccount setting has valid accountno
      //check if current fiscal period exists
      //check if next fiscal period exists - has been setup   
         dbobj.inimkcon(); 
    int pperiod=0;
    int acctperiods=12;
    int pyr = 2010;
    int nperiod=0;
    int nyr = 2010;
    ResultSet curRecSet;
    boolean isyrend = false;
    boolean isprocsucess = false;
    String gREAcct = "";
    int getconf;
    int getconf2;
    
    
    //currentfiscalperiodexists;
    String mySQLString1="";
    
    GetSetting REA = new GetSetting();
    gREAcct = REA.GetSettingvalue("REAccount",branchid,companyid);
    if (gREAcct.equals("")) {
        reaccountexists = false ;
        performprecheckerrmess = performprecheckerrmess + "Retained Earnings Account <REACCOUNT> not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ";
    }
    else {
        
        mySQLString1 = "select accountno from accounts where accountno = '" + gREAcct + "' and active = 1 and companyid = " + companyid + " and branch = " + branchid;
        //curRecSet = dbobj.retrieveRecset(mySQLString1);
         try {
          if (dbobj.retrieveRecset(mySQLString1).first() == true)
          {    
            reaccountexists = true ;
          }
          else {
            reaccountexists = false ;
            performprecheckerrmess = performprecheckerrmess + "Retained Earnings Account <REACCOUNT> does not exist or is inactive for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ";
          }
         } 
         catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
            throw new EOPException("SQLException: " + ex.getMessage());
         }
      }
    
    
         pperiod = companycurrper;
         pyr = companycurryear;
         acctperiods = 12; //Company.getaccountingPeriods();
         nyr = pyr;
         nperiod = pperiod + 1;
         if (nperiod > acctperiods) {
             nperiod = 1;
             nyr = nyr + 1;
         }
         mySQLString1 = "select * from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.year = " +nyr + " and period_id = " + nperiod + " and b.company_id = " + companyid + " and b.branch_id = " + branchid;
         curRecSet = dbobj.retrieveRecset(mySQLString1);
         try {
             if (dbobj.retrieveRecset(mySQLString1).first() == true) {
                 while (curRecSet.next()) {
                     {
                         nextfiscalperiodexists = true;
                     }
                 }
             }
             else {
                 nextfiscalperiodexists = false;
                 performprecheckerrmess = performprecheckerrmess + "Exception: Fiscal Calendar for next period not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ";
                 //System.out.println("Exception: Fiscal Calendar for next period not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ");  
                 //logger.error("Exception: Fiscal Calendar for next period not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ");
                 
             }
         } catch (SQLException ex) {
             System.out.println("SQLException: " + ex.getMessage());
             logger.error("SQLException: " + ex.getMessage());
             throw new EOPException("SQLException: " + ex.getMessage());
         }
         finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    
    }
    //  3

    /**
     *
     * @throws EOPException
     */
    public void calcAcctBal() throws EOPException{
      /*

       */
      int pYear;
      int pPeriod;
      String wkAcct;
      boolean acmatched=false;
      java.util.Date wkEOMDate;
      java.util.Date wkBOMDate;
      Double CCyAmount;
      Double Amount;
      Double CCyDebit;
      Double Debit;
      Double CCyCredit;
      Double Credit;
      pYear = companycurryear;
      pPeriod = companycurrper - 1;
      if (pPeriod <= 0) {
        pPeriod = 13;
        pYear = companycurryear - 1;
      }
      mySQLString = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.year = " + companycurryear + " and period_id = " + companycurrper + " and b.company_id = " + companyid + " and b.branch_id = " + branchid;
      ResultSet curRecSet;
      ResultSet curRecSet2=null;
      ResultSet curRecSet3;
      ResultSet curRecSet5=null;
      //dbobj = new GendataService();
      dbobj.inimkcon();
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       if (dbobj.retrieveRecset(mySQLString).first() == false)
         {
          System.out.println("Exception: Fiscal Calendar for current period not defined.");  
          logger.error("Exception: Fiscal Calendar for current period not defined.");
          throw new EOPException("Exception: Fiscal Calendar for current period not defined.");
         }
       else
       {
        while (curRecSet.next()) {
         wkBOMDate = (curRecSet.getDate("fp_start"));
         wkEOMDate = (curRecSet.getDate("fp_end"));
         tempDate = formatter3.format(wkBOMDate);
         tempDate2 = formatter3.format(wkEOMDate);
         }
         //accounts
        mySQLString = "select * from accounts where branch = " + branchid + " and companyid = " + companyid + " order by accountno";
        mySQLString2 = "select b.accountno,sum(b.CCyDebit) as CCyDebit,sum(b.CCyCredit) as CCyCredit,sum(b.CCyAmount) as CCyAmount,sum(b.Debit)as debit,sum(b.Credit) as Credit,sum(b.Amount) as Amount " +
         "from Txnsheader as A inner join Txns as B on (A.TxnSerial = B.TxnSerial) AND (A.TxnType = B.TxnType) AND (A.Branchid = B.Branchid)  AND (A.companyid = B.companyid) " +
         "where b.branchid = '" + branchid + "' and b.companyid = " + companyid + " and ((postdate >= {d '" + tempDate + "'} and postdate <= {d '" + tempDate2 + "'} and txndate <= {d '" + tempDate2 + "'}) or (txndate >= {d '" + tempDate + "'} and txndate <= {d '" + tempDate2 + "'} and postdate <= {d '" + tempDate2 + "'})) group by accountno";
        curRecSet = dbobj.retrieveupdatableRecset(mySQLString);
        curRecSet2 = dbobj.retrieveRecset(mySQLString2);
         
        while (curRecSet.next()) {
           wkAcct = curRecSet.getString("Accountno");
           acmatched= false;
           CCyAmount=0.0;
           Amount=0.0;
           CCyDebit=0.0;
           Debit=0.0;
           CCyCredit=0.0;
           Credit=0.0;
           
          curRecSet2.beforeFirst();
           while (curRecSet2.next() && (acmatched == false)) {
              //System.out.println(acmatched + " wkAcct- " + wkAcct + " ac:" + curRecSet2.getString("Accountno") );
              if (wkAcct.equals(curRecSet2.getString("Accountno"))) {
                   acmatched = true;
                   CCyAmount=curRecSet2.getDouble("CCyAmount");
                   Amount=curRecSet2.getDouble("Amount");
                   CCyDebit=curRecSet2.getDouble("CCyDebit");
                   Debit=curRecSet2.getDouble("Debit");
                   CCyCredit=curRecSet2.getDouble("CCyCredit");
                   Credit=curRecSet2.getDouble("Credit");
               }
               else {
                 //curRecSet2.next();
                 //acmatched = false;
                
               } //end if
           } //end while (curRecSet2.next()....
           
           if (acmatched==true) { //act has txns dis period
           
           }

            mySQLString3 = "select * from accountbalances where accountno = '" + wkAcct + "' and year = " + companycurryear + " and period = " + companycurrper + " and branch = " + branchid + " and companyid = " + companyid ;
            if (dbobj.retrieveRecset(mySQLString3).first() == false)
            {
             mySQLString4 = "insert into accountbalances (accountno,year,period,closingBalance,closingccyBalance,drccyAmount,crccyAmount,drAmount,crAmount,branch,companyid) values('" + wkAcct + "'," + companycurryear + "," + companycurrper + ",0,0,0,0,0,0," + branchid + "," + companyid + ")";
             dbobj.updateTable(mySQLString4);
             mySQLString5 = "select * from accountbalances where accountno = '" + wkAcct + "' and year = " + pYear + " and period = " + pPeriod + " and branch = " + branchid + " and companyid = " + companyid ;
             curRecSet5 = dbobj.retrieveRecset(mySQLString5);
             if (dbobj.retrieveRecset(mySQLString5).first() == true)
              {
                 while (curRecSet5.next()) {
                     mySQLString6 = "update accountbalances set openingccybalance=" + curRecSet5.getDouble("closingccyBalance") + ",openingbalance=" + curRecSet5.getDouble("closingBalance") + " where accountno = '" + wkAcct + "' and year = " + companycurryear + " and period = " + companycurrper + " and branch = " + branchid + " and companyid = " + companyid ;
                     dbobj.updateTable(mySQLString6);
                 }// end while (curRecSet5.n...
              } // end if ...
            } // end if (dbobj.retrieve
            mySQLString6 = "update accountbalances set closingccybalance= openingccybalance + " + CCyAmount + ",closingbalance= openingbalance + " + Amount + ", drccyAmount= " + CCyDebit + ",crccyAmount= " + CCyCredit + ", drAmount= " + Debit + ",crAmount= " + Credit + " where accountno = '" + wkAcct + "' and year = " + companycurryear + " and period = " + companycurrper + " and branch = " + branchid + " and companyid = " + companyid ;
            dbobj.updateTable(mySQLString6);
            mySQLString5 = "select * from accountbalances where accountno = '" + wkAcct + "' and year = " + companycurryear + " and period = " + companycurrper + " and branch = " + branchid + " and companyid = " + companyid ;
            curRecSet5 = dbobj.retrieveRecset(mySQLString5);
            while (curRecSet5.next()) {
             curRecSet.updateDouble("CCyClearedBalance", curRecSet5.getDouble("closingccyBalance"));
             curRecSet.updateDouble("CCyBalance", curRecSet5.getDouble("closingccyBalance"));//should take care of future postdate
             curRecSet.updateDouble("ClearedBalance", curRecSet5.getDouble("closingBalance"));
             curRecSet.updateDouble("Balance", curRecSet5.getDouble("closingBalance"));//should take care of future postdate
             curRecSet.updateRow();
            }
         }  //while (curRecSet.nex
   
       }
      }
      catch(SQLException ex) {
            //JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
           System.out.println("SQLException: " + ex.getMessage());
           logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           if (curRecSet2!=null)
           {curRecSet2.close();}
           if (curRecSet5!=null)
           {curRecSet5.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    }

  // 5

    /**
     *
     * @param gYear
     * @param gPeriod
     * @return
     * @throws EOPException
     */
    public boolean closeAcctperiod(int gYear,int gPeriod) throws EOPException{
      ///mySQLString = "select * from company where branch = '" + Company.getBranch() + "'" ;
      //ResultSet curRecSet;
       dbobj.inimkcon();
      int pperiod=0;
      int acctperiods=12;
      int pyr = 2010;
      int nperiod=0;
      int nyr = 2010;
      boolean gSuccess = false;
      ResultSet curRecSet2=null;
      ResultSet curRecSet3=null;
      ///curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       ///while (curRecSet.next()) {
         //Company.getBranch();
         pperiod =  companycurrper;// companycurrper;//curRecSet.getInt("currentPeriod");
         pyr = companycurryear; //companycurryear;//curRecSet.getInt("currentYear");
         acctperiods = 12; // Company.getaccountingPeriods();//curRecSet.getInt("accountingPeriods");
       /// }
       nyr = pyr;
       nperiod = pperiod + 1;
       if (nperiod > acctperiods) {
           nperiod = 1;
           nyr = nyr + 1;
       }

       //create account balances for next period
       String dacno;
       mySQLString2 = "select * from accountbalances where branch = " + branchid + " and companyid = " + companyid + " and year = " + pyr + " and period = " + pperiod   ;
       curRecSet2 = dbobj.retrieveRecset(mySQLString2);
       while (curRecSet2.next()) {
         dacno = curRecSet2.getString("Accountno");
         mySQLString3 = "select * from accountbalances where accountno = '" + dacno + "' and year = " + nyr + " and period = " + nperiod + " and branch = " + branchid + " and companyid = " + companyid + "" ;
         curRecSet3 = dbobj.retrieveRecset(mySQLString3);
         
         if (dbobj.retrieveRecset(mySQLString3).first() == false)
           {
             mySQLString4 = "insert into accountbalances(AccountNo,Year,Period,DRCcyAmount,CRCcyAmount,DRAmount,CRAmount,DRQty,CRQty,OpeningCcyBalance,OpeningBalance,OpeningQtyBalance,ClosingCcyBalance,ClosingBalance,ClosingQtyBalance,Branch,companyid) " +
                     "values('" + dacno  + "'," + nyr + "," + nperiod + ",0,0,0,0,0,0,0,0,0,0,0,0," + branchid + "," + companyid + ")";
             dbobj.updateTable(mySQLString4);
             
           }
          mySQLString4 = "update accountbalances set OpeningCcyBalance=" + curRecSet2.getDouble("ClosingCcyBalance") + ",OpeningBalance=" + curRecSet2.getDouble("ClosingBalance") + ",OpeningQtyBalance=" + curRecSet2.getDouble("ClosingQtyBalance") + ",ClosingCcyBalance = OpeningCcyBalance + crccyamount - drccyamount,ClosingBalance= OpeningBalance + cramount - dramount,ClosingQtyBalance=openingqtybalance + crqty - drqty" +
                      " where accountno = '" + dacno + "' and year = " + nyr + " and period = " + nperiod + " and branch = " + branchid + " and companyid = " + companyid;
          dbobj.updateTable(mySQLString4);
          
       }//end of while
       //mySQLString4 = "update company set LastPeriod=" + pperiod + ",LastYear=" + pyr + ",CurrentPeriod=" + nperiod + ",CurrentYear=" + nyr +
       //               " where branch = '" + Company.getBranch() + "'";
       mySQLString4 = "update branch set LastPeriod=" + pperiod + ",LastYear=" + pyr + 
                 " where id = " + branchid + " and company_id = '" + companyid + "'";
       dbobj.updateTable(mySQLString4);
       
       mySQLString4 = "update fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id set a.active = 'N' where a.year = " + companycurryear + " and a.period_id = " + companycurrper + " and b.branch_id = " + branchid + " and b.company_id = " + companyid;
       dbobj.updateTable(mySQLString4);
       
       gSuccess = true;
      }
      catch(SQLException ex) {
           // JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
           System.out.println("SQLException: " + ex.getMessage());
           logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet3!=null)
           {curRecSet3.close();}
           if (curRecSet2!=null)
           {curRecSet2.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
      return gSuccess;
    }

//// 6
    /**
     *
     * @throws EOPException
     */
    public void initializeNewperiod() throws EOPException{
      dbobj.inimkcon();
     //// mySQLString = "select * from company where branch = '" + Company.getBranch() + "'" ;
     ///// ResultSet curRecSet;
      int currperiod=0;
      int acctperiods=12;
      int curryr = 2010;
     //// curRecSet = dbobj.retrieveRecset(mySQLString);
    try {
     ///////  while (curRecSet.next()) {
         currperiod = companycurrper;////curRecSet.getInt("currentPeriod");
         curryr = companycurryear;////curRecSet.getInt("currentYear");
         acctperiods = 12;////curRecSet.getInt("accountingPeriods");
      /////  }
       currperiod = currperiod + 1;
       if (currperiod > acctperiods) {
           currperiod = 1;
           curryr = curryr + 1;
       }
       mySQLString2 = "update branch  set currentPeriod = " + currperiod + ",currentYear = " + curryr + " where id = " + branchid + "";
       dbobj.updateTable(mySQLString2);
       initERP();
      }
      catch(SQLException ex) {
            //JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
          System.out.println("SQLException: " + ex.getMessage());
          logger.error("SQLException: " + ex.getMessage());
          throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          dbobj.closecon();
       }
    }
 
  // 7
  
    /**
     *
     * @throws EOPException
     */
    public void initERP() throws EOPException{
      dbobj.inimkcon();
      mySQLString = "select * from branch where id = " + branchid + "" ;
      ResultSet curRecSet;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      int currperiod=0;
      int curryr = 2010;
      try {
       while (curRecSet.next()) {
         companycurrper = curRecSet.getInt("currentPeriod");
         companycurryear = curRecSet.getInt("currentYear");
         //Company.setaccountingPeriods(curRecSet.getInt("accountingPeriods"));
         currperiod = curRecSet.getInt("currentPeriod") ;
         curryr = curRecSet.getInt("currentYear");
       }
       mySQLString = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where period_id = " + currperiod + " and a.year = " + curryr + " and b.company_id = " + companyid + " and b.branch_id = " + branchid;;
       curRecSet = dbobj.retrieveRecset(mySQLString);
        if (dbobj.retrieveRecset(mySQLString).first() == false)
         {
          System.out.println("Exception: Fiscal Calendar for current period not defined.");
          logger.error("Exception: Fiscal Calendar for current period not defined.");
          throw new EOPException("Exception: Fiscal Calendar for current period not defined.");
         }
      }
      catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
            throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    }


    /**
     *
     * @param ddate
     * @throws EOPException
     */
    public void updateCompany(java.util.Date ddate)throws EOPException{
        dbobj.inimkcon();
       try {
           tempDate = formatter1.format(rightNow.getTime());
           tempDate2 = formatter3.format(ddate);
           mySQLString2 = "update branch  set endofday = 1,lastpostdate = {d '" + tempDate2 + "'} where id = '" + branchid + "'";
           dbobj.updateTable(mySQLString2);

           }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       logger.error("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
        dbobj.closecon();
       }  
    }

   // 4
   
    /**
     *
     * @throws EOPException
     */
    public void closeperiod() throws EOPException{
    dbobj.inimkcon();   
    int pperiod=0;
    int acctperiods=12;
    int pyr = 2010;
    int nperiod=0;
    int nyr = 2010;
    ResultSet curRecSet;
    boolean isyrend = false;
    boolean isprocsucess = false;
    String gREAcct = "";
    int getconf;
    int getconf2;
       System.out.println("Close the current month and open the next period for posting - branch and coy " + branchid + " - " + companyid );
       
          if (companycurrper + 1 > acctperiods) {
             //getconf2 = JOptionPane.showConfirmDialog (null,"Closing this period will also close the accounting year. Do you still want to proceed with period close?" ,"SiftERP",JOptionPane.YES_NO_OPTION);
             System.out.println("Closing this period will also close the accounting year"  + branchid + " - " + companyid);
             //throw new EOPException("SQLException: " + ex.getMessage());
             isyrend = true;
             GetSetting REA = new GetSetting();
             gREAcct = REA.GetSettingvalue("REAccount",branchid,companyid);
          }
          else
          {
             System.out.println("Proceed with period close " );
           }
          
             if ((isyrend == true) && gREAcct.equals("")) {
                  System.out.println("Retained Earnings Account <REACCOUNT> not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator. ");
                  logger.error("Retained Earnings Account <REACCOUNT> not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator.");
                  throw new EOPException("Retained Earnings Account <REACCOUNT> not defined for company - branch : " + companyid + " - " + branchid + ". Close of Period Aborted. Please contact the systems administrator."); 
             }
              else
              {
                 isprocsucess = closeAcctperiod(companycurryear,companycurrper);
                 if (isprocsucess==true){
                     if (isyrend==true) {
                       closeacctyr();
                       System.out.println("Period and Year Closed successfully for company and branch : "  + companyid + " - " + branchid + ". Year - " + companycurryear + " Period- " + companycurrper + " .Preparing New year...");
                       logger.info("Period and Year Closed successfully for company and branch : "  + companyid + " - " + branchid + ". Year - " + companycurryear + " Period- " + companycurrper + " .Preparing New year...");
                     }
                     else {
                       System.out.println("Period Closed successfully for company and branch : "  + companyid + " - " + branchid + ". Year - " + companycurryear + " Period- " + companycurrper + " .Preparing New Period...");
                       logger.info("Period Closed successfully for company and branch : "  + companyid + " - " + branchid + ". Year - " + companycurryear + " Period- " + companycurrper + " .Preparing New Period...");
                     }
                     
                     //mark period as inactive
                    
                     initializeNewperiod();
                     java.util.Date mDate=gpostdate;
                     CalcDate cddate = new CalcDate();
                     //CalcDate cddate = new CalcDate(timezone);
                     //mDate = cddate.CalcValueDate(Company.getPostDate(), 1);
                     //mDate = CalcValueDate(gPostDate, 1)
                     //Company.getBranch();
                     pperiod = companycurrper;
                     pyr = companycurryear;
                     acctperiods = 12; //Company.getaccountingPeriods();
                     nyr = pyr;
                     nperiod = pperiod + 1;
                     if (nperiod > acctperiods) {
                         nperiod = 1;
                         nyr = nyr + 1;
                     }
                     mySQLString = "select * from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.year = " + pyr + " and period_id = " + pperiod + " and b.company_id = " + companyid + " and b.branch_id = " + branchid;
                     //System.out.println("dd: " + mySQLString);
                     dbobj.inimkcon();
                     curRecSet = dbobj.retrieveRecset(mySQLString);
                     try {
                     if (dbobj.retrieveRecset(mySQLString).first() == true)
                      while (curRecSet.next()) {
                       {
                        mDate = curRecSet.getDate("fp_start");
                       }
                      }
                     }
                     catch(SQLException ex) {
                       System.out.println("SQLException: " + ex.getMessage());
                       logger.error("SQLException: " + ex.getMessage());
                       throw new EOPException("SQLException: " + ex.getMessage());
                     }
                     finally {
                        try{  
                         if (curRecSet!=null)
                         {curRecSet.close();}

                         } catch (SQLException ex) {
                              ex.printStackTrace();
                         } 
                        dbobj.closecon();
                     }
                     rightNow.setTime(mDate);
                     rightNow.add(Calendar.DAY_OF_YEAR, -1);
                     mDate = rightNow.getTime();
                     if (gprocessingmethod.equalsIgnoreCase("MANUAL")==true) 
                     {    
                      mDate = cddate.CalcValueDate(mDate, 1,companyid,branchid);
                     }
                     else
                     {
                       rightNow.add(Calendar.DATE, 1);   
                       mDate = rightNow.getTime(); 
                     }    
                     Sodaystatus(mDate);
                     InitializeAPP();
                     System.out.println("Done with Period Processing");
                     logger.info("Done with Period Processing!");
                 }
              }
           
    }
   
   // 4a-1
    /**
     *
     * @throws EOPException
     */
    public void closeacctyr() throws EOPException {
       dbobj.inimkcon();
      GenerateEntrys txnEntrys = new  GenerateEntrys();
      Entry txnEntry1;
      Entry txnEntry2;
      ResultSet curRecSet=null;
      int pperiod=0;
      int acctperiods=12;
      int pyr = 2010;
      int nperiod=0;
      int nyr = 2010;
      String entrytype;
      String entrytype2;
      Double amount =0.0;
      boolean gSuccess = false;
      String wkserial;
      String gREAcct;
      ResultSet curRecSet2;
      int m=0;
      wkserial = "REA-" + companycurryear;
      try {
           GetSetting REA = new GetSetting();
           //dbobj.con.setAutoCommit(false);
           gREAcct = REA.GetSettingvalue("REAccount",branchid,companyid);
           mySQLString2 = "delete from txns where txntype = 'REA' and txnserial = '" + wkserial + "' and branchid = " + branchid + " and companyid = " + companyid;
           dbobj.updateTable(mySQLString2);
           mySQLString2 = "delete from txns where txntype = 'PC' and txnserial = '" + wkserial + "' and branchid = " + branchid + " and companyid = " + companyid;
           dbobj.updateTable(mySQLString2);
           mySQLString2 = "delete from txnsheader where txntype = 'REA' and txnserial = '" + wkserial + "' and branchid = "  + branchid + " and companyid = " + companyid;
           dbobj.updateTable(mySQLString2);
           int lastprd = 12 + 1;
           mySQLString2 = "delete from accountbalances where year = " + companycurryear + " and period = " + lastprd  + " and branch = "  + branchid + " and companyid = " + companyid;
           dbobj.updateTable(mySQLString2);
           pperiod = companycurrper;
           pyr = companycurryear;
           acctperiods = 12;
           nyr = pyr;
           nperiod = pperiod + 1;
           if (nperiod > acctperiods) {
            nperiod = 1;
            nyr = nyr + 1;
           }
           mySQLString2 = "INSERT INTO accountbalances (AccountNo, Year, Period, OpeningCcyBalance, OpeningBalance, DRCcyAmount, CRCcyAmount, DRAmount, CRAmount, ClosingCcyBalance, ClosingBalance, branch,companyid ) SELECT AccountNo, " + pyr + " as Year, " + (pperiod + 1) + " as Period, ClosingCcyBalance, ClosingBalance, 0 as drccyamount, 0 as CRCcyAmount, 0 as DRAmount, 0 as CRAmount, ClosingCcyBalance, ClosingBalance, Branch,companyid FROM AccountBalances where year = " + pyr + " and period = " + pperiod  + " and branch = " + branchid + " and companyid = " + companyid;
           dbobj.updateTable(mySQLString2);
           mySQLString = "select * from accounts a inner join accountgroups b on a.acgroup = b.acgroupid and a.Companyid = b.companyid where (a.clearedbalance != 0) and (a.clearedbalance IS NOT NULL) and a.branch = " + branchid + " and a.companyid = " + companyid + " and (b.classid = 'I' or b.classid = 'E')";
           curRecSet = dbobj.retrieveRecset(mySQLString);
           while (curRecSet.next()) {
               m=m+1;
               amount = curRecSet.getDouble("CCyclearedbalance");
               if (amount > 0){
                    txnEntry1 = new Entry("REA","REA",curRecSet.getString("Accountno") ,gpostdate.toString(),wkserial,wkserial, "Transfer of Balance to Retained Earnings", "1", -1 * Double.valueOf(amount),-1 * Double.valueOf(amount), 1, guserid,branchid,companycurryear,companycurrper+1,companyid);
                    txnEntry2 = new Entry("REA","REA", gREAcct,gpostdate.toString(),wkserial,wkserial, "Transfer of Balance to Retained Earnings-" + curRecSet.getString("Accountno") , "1", Double.valueOf(amount),Double.valueOf(amount), 1, guserid,branchid,companycurryear,companycurrper+1,companyid);
               }
                 else {
                   txnEntry1 = new Entry("REA","REA",curRecSet.getString("Accountno") ,gpostdate.toString(),wkserial,wkserial, "Transfer of Balance to Retained Earnings", "1", -1 * Double.valueOf(amount),-1 * Double.valueOf(amount), 1, guserid,branchid,companycurryear,companycurrper+1,companyid);
                   txnEntry2 = new Entry("REA","REA", gREAcct,gpostdate.toString(),wkserial,wkserial, "Transfer of Balance to Retained Earnings-" + curRecSet.getString("Accountno") , "1",Double.valueOf(amount),Double.valueOf(amount), 1, guserid,branchid,companycurryear,companycurrper+1,companyid);
               }

               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);
           }
           if (m>0) {
               if (txnEntrys.validateSumofAmount()== true){
                  Txnsheader thetxnheader = new Txnsheader(wkserial, "REA",companycurryear,companycurrper+1,gpostdate, gpostdate, gpostdate, wkserial, "Transfer of Balance to Retained Earnings:" + wkserial,"EOP", guserid, branchid,companyid,timezone);
                  ApplyEntrys dataUpdate =  new ApplyEntrys(txnEntrys.getEntrys(),thetxnheader,companycurryear,companycurrper,branchid,companyid,timezone);
                  if (dataUpdate.performAllupdates()== true) {
                    //
                  }
                  else
                  {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                    logerrindb("Exception: Error Updating - Entries cannot be posted.");
                    //dbobj.con.rollback();
                    throw new EOPException("Exception: Error Updating - Entries cannot be posted" );
                  }
               }
               else
               {
                 System.out.print("entrys not balanced");
                 logger.error("Exception: Entries not balanced");
                 logerrindb("Exception: Entries not balanced.");
                 //dbobj.con.rollback();
               }
           }
           mySQLString = "select * from accountbalances where year = " + companycurryear + " and period = " + (companycurrper + 1)  + " and branch = " + branchid + " and companyid = " + companyid;
           curRecSet = dbobj.retrieveRecset(mySQLString);
           while (curRecSet.next()) {
            mySQLString2 = "select * from accountbalances where accountno = '" + curRecSet.getString("Accountno") + "' and year = " + nyr + " and period = " + nperiod  + " and branch = " + branchid + " and companyid  = " + companyid;
            //curRecSet2 = dbobj.retrieveRecset(mySQLString2);
            if (dbobj.retrieveRecset(mySQLString2).first() == false)
             {
               mySQLString = "INSERT INTO accountbalances (AccountNo, Year, Period, OpeningCcyBalance, OpeningBalance, DRCcyAmount, CRCcyAmount, DRAmount, CRAmount, ClosingCcyBalance, ClosingBalance, branch,companyid ) values ('" +  curRecSet.getString("Accountno") + "'," + nyr + "," + nperiod + "," + curRecSet.getDouble("ClosingCcyBalance") + "," + curRecSet.getDouble("ClosingBalance") + ", 0, 0, 0, 0," + curRecSet.getDouble("ClosingCcyBalance") + "," + curRecSet.getDouble("ClosingBalance") + "," + branchid + "," + companyid + ")";
               dbobj.updateTable(mySQLString);
             }
            else
            {
               mySQLString = "update accountbalances set OpeningCcyBalance=" + curRecSet.getDouble("ClosingCcyBalance") + ", OpeningBalance=" + curRecSet.getDouble("ClosingBalance") + ",ClosingCcyBalance=" + curRecSet.getDouble("ClosingCcyBalance") + ", ClosingBalance=" + curRecSet.getDouble("ClosingBalance") + " where accountno = '" + curRecSet.getString("Accountno") + "' and year = " + nyr + " and period = " + nperiod  + " and branch = " + branchid + " and companyid = " + companyid;
               dbobj.updateTable(mySQLString);
            }//end if
           }//end while
           //dbobj.con.commit();
         }//try closing
      catch(SQLException ex) {
       //JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
       System.out.println("SQLException: " + ex.getMessage());
       logger.error("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    }
   
   // 6c
    /**
     *
     * @param ddate
     * @return
     * @throws EOPException
     */
    public boolean Sodaystatus(java.util.Date ddate) throws EOPException{
     boolean soday = false;
     dbobj.inimkcon();
     //String mySQLString2;
     try {
           tempDate = formatter1.format(rightNow.getTime());
           tempDate2 = formatter3.format(ddate);
           mySQLString2 = "update branch  set startofday = 1, endofday = 0,postdate = {d '" + tempDate2 + "'}, currentsystemdate = {d '" + tempDate + "'} where id = " + branchid + "";
           dbobj.updateTable(mySQLString2);
           soday = true;
           }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       logger.error("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          dbobj.closecon();
       }
    return soday;
    }
    
    //////////////////////////
    // 6b
    /**
     *
     * @throws EOPException
     */
    public void InitializeAPP() throws EOPException{
      dbobj.inimkcon();  
      String mySQLString;
      mySQLString = "select * from branch where id = " + branchid;
      ResultSet curRecSet;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       while (curRecSet.next()) {
           this.gpostdate = curRecSet.getDate("postDate");
           this.gcurrsysdate = curRecSet.getDate("currentsystemDate");
           this.companycurrper = curRecSet.getInt("currentPeriod");
           this.companycurryear = curRecSet.getInt("currentYear");
        }
      }
      catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
            throw new EOPException("SQLException: " + ex.getMessage());
      }
      if (Newday()==true){
        
        
       
      }//end if newday
    }
   // 6b-1
    /**
     *
     * @return
     */
    public boolean Newday(){
     boolean isnewday = false;
     Calendar rightNow2 = Calendar.getInstance(timeZone);
     java.util.Date dcurrdate;
     if (gcurrsysdate==null) {
        rightNow2.add(Calendar.DATE,-1);
        dcurrdate = rightNow2.getTime();
     }
     else
     {
        dcurrdate = gcurrsysdate;
     }
     rightNow2 = Calendar.getInstance(timeZone);
     tempDate = formatter3.format(dcurrdate);
     tempDate2 = formatter1.format(rightNow2.getTime());
     if (tempDate.equals(tempDate2)==false){
        isnewday = true;
        //System.out.println(tempDate2);
        //System.out.println(tempDate);
     }
     return isnewday;
    }
    
    
    
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param companycurryear
     * @param companycurrper
     * @param gpostdate
     * @param gcurrsysdate
     * @param guserid
     * @param countryid
     * @param gcurr
     * @throws EOPException
     */
    public void Accruesavinterest(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr) throws EOPException{
        dbobj.inimkcon();
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        ResultSet curRecSet6;
        Integer basedays = 365;
        GetSetting gsBasedays = new GetSetting();
        String strbasedays = gsBasedays.GetSettingvalue("INTERESTBASEDAYS", branchid, companyid);

        if (strbasedays.equals("365")==true) {
            if ((companycurryear % 4 ) == 0 ) {
                basedays = 366;
            } else {
                basedays = 365;
            }
        } else if (strbasedays.equals("360")==true) 
	{
            basedays = 360;
        }
	else if (strbasedays.equals("30")==true) 
	{
            basedays = 30;
        }  
	String dSQLString2 = "";
        String dSQLString1 = "";
        //
        String dSQLString = "select distinctrow a.AccountNo,a.Product,a.Title,b.Asegc,b.ClearedBalance,b.Active,b.Closed,b.Blocked," +
                " c.product_type_code, c.interest_rate, c.interestexpenseaccount, c.interestpayableaccount" +
                " FROM custaccountdetails a inner join accounts b on a.accountno = b.accountno and a.branchid = b.branch and a.companyid = b.companyid " +
                " left join qryproductaccounts c on a.product = c.code and a.branchid = c.branch_id and a.companyid = c.company_id " +
                " where product_type_code = 'S' and clearedbalance > 0 and a.companyid = " + companyid + " and a.branchid = " + branchid;
        ResultSet curRecSet;
        curRecSet = dbobj.retrieveRecset(dSQLString);
        //check 3
        System.out.println(dSQLString);
        String tempDate1 = formatter3.format(gpostdate);
        double intamt = 0.0;
        int noofdays = 1;
        Double dbnoofdays = 0.0;
        
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        
        String wkserial = "IAC-" + branchid.toString() + companyid.toString()+ tempDate1;
        
        Calendar postdateinst2 = Calendar.getInstance();
        postdateinst2.setTime(gpostdate);
        boolean prevaccexist = false;
        Calendar lastaccdate = Calendar.getInstance();
        
        try {
        /*    
         dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and postdate = {d '" + tempDate1 + "'} and txnref like 'IAC-%'" ;
         dbobj.updateTable(dSQLString1);    
          */ 
         dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txnref = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txnsheader where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'IAC' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txns where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'IAC' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);
         
        while (curRecSet.next()) {
          intamt = 0.0;
          intamt =  (curRecSet.getDouble("ClearedBalance")*(curRecSet.getDouble("interest_rate")/100)*noofdays)/basedays; 
          //
          
          String dSQLString6 = "select max(postdate) as lastaccdate from interestdailycomputation a " +
                " where a.accountno = '" + curRecSet.getString("accountno") + "' and a.txnref like 'IAC-%' and a.branchid = " + branchid + " and a.companyid=" + companyid ;
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
       prevaccexist = false;
       lastaccdate = Calendar.getInstance();
      /// set date to 1991-01-01 java.util.Date newdate = new Date()
       //lastaccdate.set(1999,0,1);
         
       while (curRecSet6.next()) {
           if (curRecSet6.getDate("lastaccdate")!=null) {
            lastaccdate.setTime(curRecSet6.getDate("lastaccdate"));
           }
       }
       dbnoofdays = 0.0;  
       if (lastaccdate.before(gpostdate)) {
           dbnoofdays = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
           noofdays = dbnoofdays.intValue();
       }
          
          dSQLString2 = "INSERT INTO interestdailycomputation(Year, Period, AccountNo, Name, Currency, CustomerNo, Product, " + 
                       "Producttype, PostDate, ValueDate, BalanceBF, Amount, BalanceCF, NoOfDays, CRInterestRate, CRInterestBaseDays, " +
                       "CRInterestcalcAmount, CRInterestCalculated, Branchid, Companyid, Appliedataccrual, Appliedatperiodend, Txnref) " +
                       " VALUES (" + companycurryear  + "," + companycurrper + ",'" + curRecSet.getString("accountno") + "','" + curRecSet.getString("Title") + "','" + gcurr + "','" 
                        + curRecSet.getString("Asegc") + "','" + curRecSet.getString("Product") + "','" + curRecSet.getString("product_type_code") + "',{d '" + tempDate1 + "'},{d '" + tempDate1 + "'}," + curRecSet.getDouble("ClearedBalance") + ",0,"
                      + curRecSet.getDouble("ClearedBalance") + "," + noofdays + "," + curRecSet.getDouble("interest_rate") + "," + basedays + ",round(" + intamt + ",2),1,"
                       + branchid + "," + companyid + ",1,0,'" + wkserial + "')" ;
          dbobj.updateTable(dSQLString2);
          //check 3
          System.out.println(dSQLString2);
          //set constraint on productaccounts -accounts
          // generate entrys and apply to Dr interest Expense and cr interest payable
          BigDecimal bgdnewintvalue = new BigDecimal(intamt);
          bgdnewintvalue = bgdnewintvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
          intamt = bgdnewintvalue.doubleValue();
          
               txnEntry1 = new Entry("IAC","IAC",curRecSet.getString("interestexpenseaccount") ,gpostdate.toString(),curRecSet.getString("Accountno"),wkserial, "Credit Interest Accrual :- " + curRecSet.getString("Accountno"), "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
               txnEntry2 = new Entry("IAC","IAC",curRecSet.getString("interestpayableaccount"),gpostdate.toString(),curRecSet.getString("Accountno"),wkserial, "Credit Interest Accrual :- " + curRecSet.getString("Accountno") , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
              
               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);
          // at application dr interest payable and cr memeber ac wth interest
          
         }
        
            if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "IAC", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, "Credit Interest Accrual :- " + wkserial, "EOD", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    //
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    dSQLString2 = "update interestdailycomputation set Appliedataccrual = 0 where txnref = '" + wkserial + "'" ;
                    dbobj.updateTable(dSQLString2);
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                    if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
                    {   
                     throw new EOPException("Exception: Error Updating - Entries cannot be posted");
                    } 
                }
            } else {
                logger.error("Exception: Entries not balanced/No records meet criteria");
                System.out.println("entrys not balanced/No records meet criteria");
            }
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
       }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    	 
    }
    
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param companycurryear
     * @param companycurrper
     * @param gpostdate
     * @param gcurrsysdate
     * @param guserid
     * @param countryid
     * @param gcurr
     * @throws EOPException
     */
    public void Applysavinterest(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr) throws EOPException{
        dbobj.inimkcon();
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        Integer basedays = 365;
        GetSetting gsBasedays = new GetSetting();
        String strbasedays = gsBasedays.GetSettingvalue("INTERESTBASEDAYS", branchid, companyid);

        if (strbasedays.equals("365")==true) {
            if ((companycurryear % 4 ) == 0 ) {
                basedays = 366;
            } else {
                basedays = 365;
            }
        } else if (strbasedays.equals("360")==true) 
	{
            basedays = 360;
        }
	else if (strbasedays.equals("30")==true) 
	{
            basedays = 30;
        }  
	String dSQLString2 = "";
        String dSQLString1 = "";
        //
        /*String dSQLString = "select a.*, b.Active,b.Closed,b.Blocked" +
                " FROM interestdailycomputation a inner join accounts b on a.accountno = b.accountno and a.branchid = b.branch and a.companyid = b.companyid " +
                " where b.producttype = 'S' and a.CRInterestCalculated = 1 and a.Appliedataccrual = 1 and a.Appliedatperiodend = 0 and period = " + companycurrper + " and year = " + companycurryear + " and a.companyid = " + companyid + " and a.branchid = " + branchid;
        */
         String dSQLString = "select a.*, b.Active,b.Closed,b.Blocked" +
                " FROM interestdailycomputation a inner join accounts b on a.accountno = b.accountno and a.branchid = b.branch and a.companyid = b.companyid " +
                " inner join custaccountdetails e on a.accountno = e.accountno and a.branchid = e.branchid and a.companyid = e.companyid " +
                " inner join qryproductaccounts c on e.product = c.code and e.branchid = c.branch_id and e.companyid = c.company_id " +
                " where product_type_code = 'S' and a.CRInterestCalculated = 1 and a.Appliedataccrual = 1 and a.Appliedatperiodend = 0 and period = " + companycurrper + " and year = " + companycurryear + " and a.companyid = " + companyid + " and a.branchid = " + branchid;
        
        ResultSet curRecSet;
        curRecSet = dbobj.retrieveRecset(dSQLString);
        
        String tempDate1 = formatter3.format(gpostdate);
        double intamt = 0.0;
        int noofdays = 1;
        
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        //BigDecimal bgdnewintvalue = new BigDecimal(".00");
        //bgdnewintvalue.setScale(2);
        String wkserial = "IAP-" + branchid.toString() + companyid.toString()+ tempDate1;
        
        
        try {
            
         dSQLString1 = "delete from txnsheader where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'IAP' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txns where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'IAP' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
            
        while (curRecSet.next()) {
          intamt = 0.0;
          intamt =  curRecSet.getDouble("CRInterestcalcAmount"); 
          txnEntry1 = new Entry("IAP","IAP",curRecSet.getString("interestpayableaccount") ,gpostdate.toString(),curRecSet.getString("Accountno"),wkserial, "Credit Interest Application :- " + curRecSet.getString("Accountno"), "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
          txnEntry2 = new Entry("IAP","IAP",curRecSet.getString("accountno"),gpostdate.toString(),curRecSet.getString("Accountno"),wkserial, "Credit Interest Application :- " + curRecSet.getString("Accountno") , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
          txnEntrys.setEntrys(txnEntry1);
          txnEntrys.setEntrys(txnEntry2);
          
          // at application dr interest payable and cr memeber ac wth interest
          
         }
        
            if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "IAP", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, "Credit Interest Application :- " + wkserial, "EOP", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    dSQLString2 = "update interestdailycomputation set Appliedatperiodend = 1 where b.producttype = 'S' and a.CRInterestCalculated = 1 and a.Appliedataccrual = 1 and a.Appliedatperiodend = 0 and period = " + companycurrper + " and year = " + companycurryear + " and a.companyid = " + companyid + " and a.branchid = " + branchid;
                    dbobj.updateTable(dSQLString2);
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    dSQLString2 = "update interestdailycomputation set Appliedatperiodend = 0 where txnref = '" + wkserial + "'" ;
                    dbobj.updateTable(dSQLString2);
                     logger.error("Applysavinterest Exception: Error Updating - Entries cannot be posted");
                     if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
                     { 
                        throw new EOPException("Exception: Error Updating - Entries cannot be posted");
                     }   
                }
            } else {
                System.out.print("entrys not balanced/No records meet criteria");
                 logger.error("Applysavinterest Exception: Entrys not balanced/No records meet criteria");
            }
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
           logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
       }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
    	 
    }
    
    
    public void Accrueloansinterest(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr) throws EOPException{
        dbobj.inimkcon();
        String dSQLString5 = "";
         String tempDate1 = formatter3.format(gpostdate);
         String tempDate2 = formatter3.format(gBOMDate);
         String tempDate3 = formatter3.format(gEOMDate);
         String tempDate4 = formatter3.format(gpostdate);
         String qInt = "";
         String qIntback = "";
         ResultSet curRecSet;
         ResultSet curRecSet2;
         ResultSet curRecSet6;
         java.util.Date date3;
         Double datediff=0.0;
         Double dailyintval=0.0;
         Double daysinperiod=0.0;
         Double accruedinttopostdate = 0.0;
         Double interestacc = 0.0;
         Calendar dateinst3 = Calendar.getInstance();
         Calendar prevdateinst2 = Calendar.getInstance();
         Calendar nextdateinst2 = Calendar.getInstance();
         Calendar bomdateinst2 = Calendar.getInstance();
         Calendar postdateinst2 = Calendar.getInstance();
         postdateinst2.setTime(gpostdate);
         bomdateinst2.setTime(gBOMDate);
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        
        //scan thru all loans for company and branch where loan interest type is not Flat
         String dSQLString0 = "";
      
        dSQLString0 = "select distinctrow a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.interest_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on trim(a.loan_account_number) = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and a.actual_commencement_rate <= {d '" + tempDate1 + "'}" ;
        
        //check 3
        //System.out.println(dSQLString0);
        curRecSet = dbobj.retrieveRecset(dSQLString0);   
        String repayfrequency ="";
        String loan_case_id ="";
        String interest_type ="";
        double intamt = 0.0;
        double totintacc = 0.0;
        int totintdays = 0;
        int noofdays = 1;
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        //BigDecimal bgdnewintvalue = new BigDecimal(".00");
        //bgdnewintvalue.setScale(2);
        String wkserial = "LIAP-" + branchid.toString() + companyid.toString()+ tempDate1;
        BigDecimal bgdnewintvalue = new BigDecimal(0.0);
        try {
         String dSQLString2 = "";
         String dSQLString1 = "";  
         //dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and postdate = {d '" + tempDate1 + "'} and txnref like 'LIAP-%'" ;
        dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txnref = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txnsheader where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LDD' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txns where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LDD' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1); 
           
             
        while (curRecSet.next()) {
         try {  
           interest_type = curRecSet.getString("interest_type");  
          if (curRecSet.getString("interestincomeaccount").isEmpty()==false && curRecSet.getString("interestreceivableaccount").isEmpty()==false) {
              //accrueloanrepaymentforperiod(curRecSet.getString("loan_case_id"),curRecSet.getString("loan_account_number"),companyid,branchid,gpostdate,curRecSet.getString("interestincomeaccount"),curRecSet.getString("interestreceivableaccount"),curRecSet.getString("repay_frequency"));
        if (interest_type.equals("RED")==true || interest_type.equals("REQ")==true) {
        loan_case_id = curRecSet.getString("loan_case_id");  
        repayfrequency = curRecSet.getString("repay_frequency");
         
        if (repayfrequency.equals("RPM")==true) {
            qInt = " INTERVAL 1 MONTH";
            qIntback = " INTERVAL -1 MONTH";
        } else if (repayfrequency.equals("RPW")==true) {
            qInt = " INTERVAL 1 WEEK";
            qIntback = " INTERVAL -1 WEEK";
        } else if (repayfrequency.equals("RPB")==true) {
            qInt = " INTERVAL 2 WEEK";
            qIntback = " INTERVAL -2 WEEK";
        } else if (repayfrequency.equals("RPD")==true) {
            qInt = " INTERVAL 1 DAY";
            qIntback = " INTERVAL -1 DAY";
        }
        
        //select max time interest was accrued - shld be last instalment drop date
        String dSQLString6 = "select max(valuedate) as lastaccdate from interestdailycomputation a " +
                "where a.accountno = '" + curRecSet.getString("loan_account_number").trim() + "' and a.branchid = " + branchid + " and a.companyid=" + companyid ;
        
        
       /* String dSQLString6 = "select max(postdate) as lastaccdate from txnsheader a inner join txns b on a.branchid = b.branchid and a.companyid = b.companyid " +
                " and a.txnserial = b.txnserial and a.txntype = b.txntype inner join loan_request c on b.docref = c.loan_account_number " +
                " and b.branchid = c.branch_id and b.companyid = c.company_id " +
                "where c.loan_case_id = '" + loan_case_id + "' and a.txnserial like 'LIAP-%' and a.branchid = " + branchid + " and a.companyid=" + companyid ;*/
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
       boolean prevaccexist = false;
       Calendar lastaccdate = Calendar.getInstance();
      /// set date to 1991-01-01 java.util.Date newdate = new Date()
       lastaccdate.set(1999,0,1);
         
       while (curRecSet6.next()) {
           if (curRecSet6.getDate("lastaccdate")!=null) {
           prevaccexist = true;
           lastaccdate.setTime(curRecSet6.getDate("lastaccdate"));
           }
       }
       
       //if (gBOMDate.before(lastaccdate.getTime())) {
           tempDate2 = formatter3.format(lastaccdate.getTime());
       //}
         
        dSQLString5 = "select a.id,a.loan_case_id,a.member_no,a.active,a.amount_interest,expected_repayment_date,DATE_ADD(expected_repayment_date, " + qIntback + " ) as prevdate,DATE_ADD(expected_repayment_date, " + qInt + " ) as nextdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and ((expected_repayment_date > {d '" + tempDate2 + "'} and expected_repayment_date <= {d '" + tempDate1 + "'}) " +
                " ) order by id asc";
        //take care of loans that span multiple monthe b/4 repayments
        //String dSQLString6 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number from loan_repayment_schedule a " +
        ///        "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and expected_repayment_date <= {d '" + tempDate2 + "'} and next_repayment_date <= {d '" + tempDate1 + "'} order by id asc";
        
        //check 3
        System.out.println(dSQLString5);
        curRecSet2 = dbobj.retrieveRecset(dSQLString5);        
        totintacc = 0.0;
        totintdays = 0;
        
       /* String dSQLString6 = "select max(expected_repayment_date) as endofrepaymtdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.branch_id = " + branchid + " and a.company_id=" + companyid ;
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
        */
        
        
        
       
        while (curRecSet2.next()) {
          //determine no of days btw last repayment and expected payment date
            dateinst3.setTime(curRecSet2.getDate("expected_repayment_date"));
            prevdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            nextdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            tempDate4 = formatter3.format(dateinst3.getTime());
            daysinperiod=0.0;
            if (repayfrequency.equals("RPM")==true) {
              prevdateinst2.add(Calendar.MONTH  , -1);
              nextdateinst2.add(Calendar.MONTH  , 1);
            } 
            if (repayfrequency.equals("RPW")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -1);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 1);
            }
            if (repayfrequency.equals("RPB")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -2);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 2);
            }
            if (repayfrequency.equals("RPD")==true) {
              prevdateinst2.add(Calendar.DATE  , -1);
              nextdateinst2.add(Calendar.DATE  , 1);
            }
            datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
            
            
            daysinperiod = datediff;
            
            
            
            if (curRecSet2.getDouble("amount_interest")!=0) {
                dailyintval =  curRecSet2.getDouble("amount_interest");
            }
            else
            {
                dailyintval =  0.0;  
            }
            System.out.println("datediff " + datediff + " " + datediff.intValue());
            System.out.println("dailyintval " + dailyintval + " " + dailyintval);
            
            
            interestacc = dailyintval;
            totintacc = interestacc;
            totintdays = daysinperiod.intValue();
            
            System.out.println("loanid " + curRecSet2.getString("loan_case_id") + " int accrued for inst days " + interestacc + " totintacc for period " + totintacc + " totintdays " + totintdays);
          
            
          
         
         dSQLString2 = "INSERT INTO interestdailycomputation(Year, Period, AccountNo, Name, Currency, CustomerNo, Product, " + 
                       "Producttype, PostDate, ValueDate, BalanceBF, Amount, BalanceCF, NoOfDays, DRInterestRate, DRInterestBaseDays, " +
                       "DRInterestcalcAmount, DRInterestCalculated, Branchid, Companyid, Appliedataccrual, Appliedatperiodend, Txnref) " +
                       " VALUES (" + companycurryear  + "," + companycurrper + ",'" + curRecSet.getString("loan_account_number").trim() + "','" + curRecSet.getString("name") + "','" + gcurr + "','" 
                        + curRecSet.getString("member_no") + "','" + curRecSet.getString("loan_type") + "','" + "L" + "',{d '" + tempDate1 + "'},{d '" + tempDate4 + "'}," + curRecSet.getDouble("ClearedBalance") + ",0,"
                      + curRecSet.getDouble("ClearedBalance") + "," + totintdays + "," + curRecSet.getDouble("product_rate") + "," + 365 + ",round(" + totintacc + ",2),1,"
                       + branchid + "," + companyid + ",1,0,'" + wkserial + "')" ;
         System.out.println(dSQLString2);   
         dbobj.updateTable(dSQLString2);
          //check 3
          
          bgdnewintvalue = new BigDecimal(totintacc);
          bgdnewintvalue = bgdnewintvalue.setScale(2,BigDecimal.ROUND_HALF_UP);
          //bgdnewintvalue.valueOf(totintacc);
          intamt = bgdnewintvalue.doubleValue();
          
          if (intamt!=0.0)
           {
               txnEntry1 = new Entry("LDD","LIA",curRecSet.getString("interestreceivableaccount") ,gpostdate.toString(),curRecSet.getString("loan_account_number"),loan_case_id, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number"), "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
               txnEntry2 = new Entry("LDD","LIA",curRecSet.getString("interestincomeaccount"),gpostdate.toString(),curRecSet.getString("loan_account_number"),loan_case_id, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number") , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
              
               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);        
           }       
          
           } //end loop for installments of a loan
          }  // if for only reducing balance method
          
          }
          else
          {
             logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
          }
         }
         catch(NullPointerException npex) {
            logger.error("Exception: Null in loan values;Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
            if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
            { 
               throw new EOPException("Exception: Null in loan values;Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id"));
            }   
         }    
        }
        
        if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "LDD", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, "Loan Interest Accrual :- " + wkserial, "EOD", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    //
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    dSQLString2 = "update interestdailycomputation set Appliedataccrual = 0 where txnref = '" + wkserial + "'" ;
                    dbobj.updateTable(dSQLString2);
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                    if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
                    { 
                       throw new EOPException("Exception: Error Updating - Entries cannot be posted");
                    }   
                }
            } else {
                logger.error("Exception: Entries not balanced/No records meet criteria");
                System.out.println("entrys not balanced/No records meet criteria");
            }   
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
       }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
        
        //pick current installment no - and pick the interest amt for the period
        
        //calculate the interest per day - no of pass days/total no of days * interestamt
        
        //post to accrual and integ ac
        
        
    }
    
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param companycurryear
     * @param companycurrper
     * @param gpostdate
     * @param gcurrsysdate
     * @param guserid
     * @param countryid
     * @param gcurr
     * @throws EOPException
     */
    public void Accrueloansinterestdaily(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr) throws EOPException{
        dbobj.inimkcon();
        String dSQLString5 = "";
         String tempDate1 = formatter3.format(gpostdate);
         String tempDate2 = formatter3.format(gBOMDate);
         String tempDate3 = formatter3.format(gEOMDate);
         String qInt = "";
         String qIntback = "";
         ResultSet curRecSet;
         ResultSet curRecSet2;
         ResultSet curRecSet6;
         java.util.Date date3;
         Double datediff=0.0;
         Double dailyintval=0.0;
         Double daysinperiod=0.0;
         Double accruedinttopostdate = 0.0;
         Double interestacc = 0.0;
         Calendar dateinst3 = Calendar.getInstance();
         Calendar prevdateinst2 = Calendar.getInstance();
         Calendar nextdateinst2 = Calendar.getInstance();
         Calendar bomdateinst2 = Calendar.getInstance();
         Calendar postdateinst2 = Calendar.getInstance();
         postdateinst2.setTime(gpostdate);
         bomdateinst2.setTime(gBOMDate);
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        
        //scan thru all loans for company and branch where loan interest type is not Flat
         String dSQLString0 = "";
        //
      /*  dSQLString0 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.loan_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on a.loan_account_number = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.interest_type != 'FFL' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and a.actual_commencement_rate <= {d '" + tempDate1 + "'}" ;
        */
        dSQLString0 = "select distinctrow a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.interest_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on trim(a.loan_account_number) = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and a.actual_commencement_rate <= {d '" + tempDate1 + "'}" ;
        
        //check 3
        System.out.println(dSQLString0);
        curRecSet = dbobj.retrieveRecset(dSQLString0);   
        String repayfrequency ="";
        String loan_case_id ="";
        double intamt = 0.0;
        double totintacc = 0.0;
        int totintdays = 0;
        int noofdays = 1;
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        //BigDecimal bgdnewintvalue = new BigDecimal(".00");
        //bgdnewintvalue.setScale(2);
        String wkserial = "LIAPD-" + branchid.toString() + companyid.toString()+ tempDate1;
        
        try {
         String dSQLString2 = "";
         String dSQLString1 = "";  
         //dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and postdate = {d '" + tempDate1 + "'} and txnref like 'LIAP-%'" ;
        dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txnref = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txnsheader where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LIAP' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txns where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LIAP' and txnserial = '" + wkserial + "'" ;
         dbobj.updateTable(dSQLString1); 
           
             
        while (curRecSet.next()) {
         try {   
          if (curRecSet.getString("interestincomeaccount").isEmpty()==false && curRecSet.getString("interestreceivableaccount").isEmpty()==false) {
              //accrueloanrepaymentforperiod(curRecSet.getString("loan_case_id"),curRecSet.getString("loan_account_number"),companyid,branchid,gpostdate,curRecSet.getString("interestincomeaccount"),curRecSet.getString("interestreceivableaccount"),curRecSet.getString("repay_frequency"));
        
        loan_case_id = curRecSet.getString("loan_case_id");  
        repayfrequency = curRecSet.getString("repay_frequency");
         
        if (repayfrequency.equals("RPM")==true) {
            qInt = " INTERVAL 1 MONTH";
            qIntback = " INTERVAL -1 MONTH";
        } else if (repayfrequency.equals("RPW")==true) {
            qInt = " INTERVAL 1 WEEK";
            qIntback = " INTERVAL -1 WEEK";
        } else if (repayfrequency.equals("RPB")==true) {
            qInt = " INTERVAL 2 WEEK";
            qIntback = " INTERVAL -2 WEEK";
        } else if (repayfrequency.equals("RPD")==true) {
            qInt = " INTERVAL 1 DAY";
            qIntback = " INTERVAL -1 DAY";
        }
        
        String dSQLString6 = "select max(postdate) as lastaccdate from txnsheader a inner join txns b on a.branchid = b.branchid and a.companyid = b.companyid " +
                " and a.txnserial = b.txnserial and a.txntype = b.txntype inner join loan_request c on b.docref = c.loan_account_number " +
                " and b.branchid = c.branch_id and b.companyid = c.company_id " +
                "where c.loan_case_id = '" + loan_case_id + "' and a.txnserial like 'LIAPD-%' and a.branchid = " + branchid + " and a.companyid=" + companyid ;
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
       boolean prevaccexist = false;
       Calendar lastaccdate = Calendar.getInstance();
      /// set date to 1991-01-01 java.util.Date newdate = new Date()
       lastaccdate.set(1999,0,1);
         
       while (curRecSet6.next()) {
           if (curRecSet6.getDate("lastaccdate")!=null) {
           prevaccexist = true;
           lastaccdate.setTime(curRecSet6.getDate("lastaccdate"));
           }
       }
       
       if (gBOMDate.before(lastaccdate.getTime())) {
           tempDate2 = formatter3.format(lastaccdate.getTime());
       }
         
        dSQLString5 = "select a.id,a.loan_case_id,a.member_no,a.active,a.amount_interest,expected_repayment_date,DATE_ADD(expected_repayment_date, " + qIntback + " ) as prevdate,DATE_ADD(expected_repayment_date, " + qInt + " ) as nextdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and ((expected_repayment_date >= {d '" + tempDate2 + "'} and expected_repayment_date <= {d '" + tempDate1 + "'}) " +
                " or (expected_repayment_date > {d '" + tempDate1 + "'} and DATE_ADD(expected_repayment_date, " + qIntback + " ) < {d '" + tempDate1 + "'}  and DATE_ADD(expected_repayment_date, " + qIntback + " ) >= {d '" + tempDate2 + "'}) " +
                " or (DATE_ADD(expected_repayment_date, " + qIntback + " ) < {d '" + tempDate2 + "'} and expected_repayment_date > {d '" + tempDate1 + "'} )) order by id asc";
        //take care of loans that span multiple monthe b/4 repayments
        //String dSQLString6 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number from loan_repayment_schedule a " +
        ///        "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and expected_repayment_date <= {d '" + tempDate2 + "'} and next_repayment_date <= {d '" + tempDate1 + "'} order by id asc";
        
        //check 3
        System.out.println(dSQLString5);
        curRecSet2 = dbobj.retrieveRecset(dSQLString5);        
        totintacc = 0.0;
        totintdays = 0;
        
       /* String dSQLString6 = "select max(expected_repayment_date) as endofrepaymtdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.branch_id = " + branchid + " and a.company_id=" + companyid ;
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
        */
        
        
        
       
        while (curRecSet2.next()) {
          //determine no of days btw last repayment and expected payment date
            dateinst3.setTime(curRecSet2.getDate("expected_repayment_date"));
            prevdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            nextdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            daysinperiod=0.0;
            if (repayfrequency.equals("RPM")==true) {
              prevdateinst2.add(Calendar.MONTH  , -1);
              nextdateinst2.add(Calendar.MONTH  , 1);
            } 
            if (repayfrequency.equals("RPW")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -1);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 1);
            }
            if (repayfrequency.equals("RPB")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -2);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 2);
            }
            if (repayfrequency.equals("RPD")==true) {
              prevdateinst2.add(Calendar.DATE  , -1);
              nextdateinst2.add(Calendar.DATE  , 1);
            }
            datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
            
            ////////////////            
            if ((prevaccexist == true)&&(bomdateinst2.before(lastaccdate))) {
                 // bomdateinst2.setTime(lastaccdate.getTime());
            }
            if ((prevaccexist == true)&&(prevdateinst2.before(lastaccdate))) {
                 //// prevdateinst2.setTime(lastaccdate.getTime());
            }
            /////////////
            
            
            
            if (repayfrequency.equals("RPD")==true) { /// repayment is less than a month
             if ((prevaccexist == false)||((prevaccexist == true)&&((gBOMDate.after(lastaccdate.getTime()))||(gBOMDate.after(lastaccdate.getTime()))))) { 
                               
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").before(gBOMDate)||curRecSet2.getDate("prevdate").equals(gBOMDate))) {
                  daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                ///debug  System.out.println("daysinper1a " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + bomdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                  daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
               ///debug    System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                  daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
               ///debug    System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
              }
              
              
              
             } 
             else if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime())))
             {
                 if (curRecSet2.getDate("expected_repayment_date").after(lastaccdate.getTime()))
                 {
                     if (curRecSet2.getDate("prevdate").before(lastaccdate.getTime())) {
                         
                         if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                            daysinperiod = (dateinst3.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                          ///debug   System.out.println("daysinper1ba " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + lastaccdate.getTime());
                         }
                         else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                             daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                          ///debug    System.out.println("daysinper1ca " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + lastaccdate.getTime());
                         }
                     }   
                     else {
                        if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                          daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                       ///debug    System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
                        }
                        else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                          daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                        ///debug   System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
                        } 
                     }
                 }       
             }  
             
             
            }
            /*
            if (repayfrequency.equals("RPD")==true) { /// repayment is less than a month
             if ((prevaccexist == false)||((prevaccexist == true)&&(gBOMDate.after(lastaccdate.getTime())))) {    
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").before(gBOMDate)||curRecSet2.getDate("prevdate").equals(gBOMDate))) {
                  daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
              else if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                  daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
              else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                  daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
            } 
            }  */
            else if (repayfrequency.equals("RPW")==true) { /// repayment is less than a month
             if ((prevaccexist == false)||((prevaccexist == true)&&((gBOMDate.after(lastaccdate.getTime()))||(gBOMDate.after(lastaccdate.getTime()))))) { 
              System.out.println("take1 start");                  
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").before(gBOMDate)||curRecSet2.getDate("prevdate").equals(gBOMDate))) {
                  daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                 ///debug  System.out.println("daysinper1a " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + bomdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                  daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                 ///debug  System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                  daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                ///debug   System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
              }
              
              ///debug System.out.println("take1 ends");    
              
             } 
             else if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime())))
             {  ///debug System.out.println("take2 start");    
                 if (curRecSet2.getDate("expected_repayment_date").after(lastaccdate.getTime()))
                 { ///debug  System.out.println("take3 start");    
                     if (curRecSet2.getDate("prevdate").before(lastaccdate.getTime())) {
                        ///debug  System.out.println("take4 start");    
                         if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                            daysinperiod = (dateinst3.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                          ///debug    System.out.println("daysinper1ba " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + lastaccdate.getTime());
                         }
                         else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                             daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                          ///debug    System.out.println("daysinper1ca " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + lastaccdate.getTime());
                         }
  ///debug  System.out.println("take4 end");          
                     }   
                     else {
                        if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                          daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                         ///debug  System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
                        }
                        else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                          daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                         ///debug  System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
                        } 
   ///debug  System.out.println("take3b ends");          
                     }
                 }       
   ///debug  System.out.println("take2 end");  
             }  
             
             
            }
           
           
            else if (repayfrequency.equals("RPB")==true) { /// repayment is less than a month
             if ((prevaccexist == false)||((prevaccexist == true)&&((gBOMDate.after(lastaccdate.getTime()))||(gBOMDate.after(lastaccdate.getTime()))))) { 
                               
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").before(gBOMDate)||curRecSet2.getDate("prevdate").equals(gBOMDate))) {
                  daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                 ///debug  System.out.println("daysinper1a " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + bomdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                  daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                ///debug   System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
              }
              else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                  daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
               ///debug    System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
              }
              
              
              
             } 
             else if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime())))
             {
                 if (curRecSet2.getDate("expected_repayment_date").after(lastaccdate.getTime()))
                 {
                     if (curRecSet2.getDate("prevdate").before(lastaccdate.getTime())) {
                         
                         if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                            daysinperiod = (dateinst3.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                           ///debug  System.out.println("daysinper1ba " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + lastaccdate.getTime());
                         }
                         else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                             daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                           ///debug   System.out.println("daysinper1ca " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + lastaccdate.getTime());
                         }
                     }   
                     else {
                        if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                          daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                         ///debug  System.out.println("daysinper1b " + daysinperiod.intValue() + " " + dateinst3.getTime() + " - " + prevdateinst2.getTime());
                        }
                        else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                          daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                        ///debug   System.out.println("daysinper1c " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
                        } 
                     }
                 }       
             }  
             
             
            }
            /*
            
            else if (repayfrequency.equals("RPB")==true) { /// repayment is less than a month
            if ((prevaccexist == false)||((prevaccexist == true)&&(gBOMDate.after(lastaccdate.getTime())))) {     
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").before(gBOMDate)||curRecSet2.getDate("prevdate").equals(gBOMDate))) {
                  daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
              else if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))&&(curRecSet2.getDate("prevdate").after(gBOMDate))){
                  daysinperiod = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
              else if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))){
                  daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
              }
             } 
            }
            */
            
            else if (repayfrequency.equals("RPM")==true) { /// repayment is monthly
              if ((curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate))) {
                ///  if ((prevaccexist == true)&&(gBOMDate.after(lastaccdate.getTime()))) {
                ///     daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                ///  }
                ////  if ((prevaccexist == false)) {
                     daysinperiod = ((dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0)+1;
                ////  }   
                  if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime()))) {
                    if (((curRecSet2.getDate("expected_repayment_date").before(lastaccdate.getTime())))) {  
                      daysinperiod = 0.0;
                    }
                    else {
                      daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                    }
                  }
                 System.out.println("daysinper1 " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + lastaccdate.getTime());
                  //pls confirm
                  //daysinperiod = daysinperiod + 1;
              }
              ///else {
              ///    daysinperiod = 0.0;
              ///}
              if ((curRecSet2.getDate("expected_repayment_date").after(gpostdate))&&(curRecSet2.getDate("prevdate").before(gpostdate))) {
                 if (curRecSet2.getDate("prevdate").before(gBOMDate)) { 
                  daysinperiod = ((postdateinst2.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0)+1;
                  if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime()))) {
                    daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                  }
                 }
                 else {
                    daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
                    if ((prevaccexist == true)&&(curRecSet2.getDate("prevdate").before(lastaccdate.getTime()))) {
                       daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                    }
                 }
                  //if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime()))) {
                  
                  System.out.println("daysinper2 " + daysinperiod.intValue() + " " + postdateinst2.getTime() + " - " + prevdateinst2.getTime());
                      
                  //daysinperiod = daysinperiod + 1;
              }
            } 
            else ///repayment spans more than a months interval
            {
               
               daysinperiod = ((postdateinst2.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0)+1;
               if ((prevaccexist == true)&&(gBOMDate.before(lastaccdate.getTime()))) {
                       daysinperiod = (postdateinst2.getTimeInMillis()-lastaccdate.getTimeInMillis())/1000.0/60.0/60.0/24.0;
               }
            }    
            
            if (curRecSet2.getDouble("amount_interest")!=0) {
                dailyintval =  curRecSet2.getDouble("amount_interest")/datediff.intValue();
            }
            else
            {
                dailyintval =  0.0;  
            }
            System.out.println("datediff " + datediff + " " + datediff.intValue());
            System.out.println("dailyintval " + dailyintval + " " + dailyintval);
            
            
            interestacc = daysinperiod * dailyintval;
            totintacc = totintacc + interestacc;
            totintdays = totintdays + daysinperiod.intValue();
            
            System.out.println("loanid " + curRecSet2.getString("loan_case_id") + " int accrued for inst days " + interestacc + " totintacc for period " + totintacc + " totintdays " + totintdays);
           /* 
            if (curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate)) {
                datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
                if (curRecSet2.getDouble("amount_interest")!=0) {
                 dailyintval =  curRecSet2.getDouble("amount_interest")/datediff.intValue();
                }
                else
                {
                  dailyintval =  0.0;  
                }    
                daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                //pls confirm
                daysinperiod = daysinperiod + 1;
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
            }
            else if (curRecSet2.getDate("expected_repayment_date").after(gpostdate))
            {
                datediff = (nextdateinst2.getTimeInMillis()-dateinst3.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                if (curRecSet2.getDouble("amount_interest")!=0) {
                 dailyintval =  curRecSet2.getDouble("amount_interest")/datediff.intValue();
                } 
                else
                {
                  dailyintval =  0.0;  
                } 
                if (prevdateinst2.before(bomdateinst2)) {
                   daysinperiod = (postdateinst2.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                else 
                {    
                   daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
            }    
            */
            
         }   
         
         dSQLString2 = "INSERT INTO interestdailycomputation(Year, Period, AccountNo, Name, Currency, CustomerNo, Product, " + 
                       "Producttype, PostDate, ValueDate, BalanceBF, Amount, BalanceCF, NoOfDays, DRInterestRate, DRInterestBaseDays, " +
                       "DRInterestcalcAmount, DRInterestCalculated, Branchid, Companyid, Appliedataccrual, Appliedatperiodend, Txnref) " +
                       " VALUES (" + companycurryear  + "," + companycurrper + ",'" + curRecSet.getString("loan_account_number").trim() + "','" + curRecSet.getString("name") + "','" + gcurr + "','" 
                        + curRecSet.getString("member_no") + "','" + curRecSet.getString("loan_type") + "','" + "L" + "',{d '" + tempDate1 + "'},{d '" + tempDate1 + "'}," + curRecSet.getDouble("ClearedBalance") + ",0,"
                      + curRecSet.getDouble("ClearedBalance") + "," + totintdays + "," + curRecSet.getDouble("product_rate") + "," + 365 + ",round(" + totintacc + ",2),1,"
                       + branchid + "," + companyid + ",1,0,'" + wkserial + "')" ;
         System.out.println(dSQLString2);   
         dbobj.updateTable(dSQLString2);
          //check 3
          
          BigDecimal bgdnewintvalue = new BigDecimal(totintacc);
          bgdnewintvalue = bgdnewintvalue.setScale(2,BigDecimal.ROUND_HALF_UP);
          //bgdnewintvalue.valueOf(totintacc);
          intamt = bgdnewintvalue.doubleValue();
          
          if (intamt!=0.0)
           {
               txnEntry1 = new Entry("LIAP","LIAP",curRecSet.getString("interestreceivableaccount") ,gpostdate.toString(),curRecSet.getString("loan_account_number"),wkserial, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number"), "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
               txnEntry2 = new Entry("LIAP","LIAP",curRecSet.getString("interestincomeaccount"),gpostdate.toString(), curRecSet.getString("loan_account_number"),wkserial, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number") , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
              
               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);        
           }       
          
          }
          else
          {
             logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
          } 
         }
         catch(NullPointerException npex) {
            logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
            if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
            { 
               throw new EOPException("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id"));
            }   
         }    
        }
        
        if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "LIAP", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, "Loan Interest Accrual :- " + wkserial, "EOD", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    //
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    dSQLString2 = "update interestdailycomputation set Appliedataccrual = 0 where txnref = '" + wkserial + "'" ;
                    dbobj.updateTable(dSQLString2);
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                    if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
                    { 
                       throw new EOPException("Exception: Error Updating - Entries cannot be posted");
                    }   
                }
            } else {
                logger.error("Exception: Entries not balanced/No records meet criteria");
                System.out.println("entrys not balanced/No records meet criteria");
            }   
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
       }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
        
        //pick current installment no - and pick the interest amt for the period
        
        //calculate the interest per day - no of pass days/total no of days * interestamt
        
        //post to accrual and integ ac
        
        
    }
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param companycurryear
     * @param companycurrper
     * @param gpostdate
     * @param gcurrsysdate
     * @param guserid
     * @param countryid
     * @param gcurr
     * @throws EOPException
     */
    public void Accrueloansinterestold(Integer companyid,Integer branchid,Integer companycurryear,Integer companycurrper,java.util.Date gpostdate,java.util.Date gcurrsysdate,String guserid,Integer countryid,String gcurr) throws EOPException{
        dbobj.inimkcon();
        String dSQLString5 = "";
         String tempDate1 = formatter3.format(gpostdate);
         String tempDate2 = formatter3.format(gBOMDate);
         String tempDate3 = formatter3.format(gEOMDate);
         String qInt = "";
         String qIntback = "";
         ResultSet curRecSet;
         ResultSet curRecSet2;
         ResultSet curRecSet6;
         java.util.Date date3;
         Double datediff=0.0;
         Double dailyintval=0.0;
         Double daysinperiod=0.0;
         Double accruedinttopostdate = 0.0;
         Double interestacc = 0.0;
         Calendar dateinst3 = Calendar.getInstance();
         Calendar prevdateinst2 = Calendar.getInstance();
         Calendar nextdateinst2 = Calendar.getInstance();
         Calendar bomdateinst2 = Calendar.getInstance();
         Calendar postdateinst2 = Calendar.getInstance();
         postdateinst2.setTime(gpostdate);
         bomdateinst2.setTime(gBOMDate);
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        
        //scan thru all loans for company and branch where loan interest type is not Flat
         String dSQLString0 = "";
        //
      /*  dSQLString0 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.loan_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on a.loan_account_number = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.interest_type != 'FFL' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and a.actual_commencement_rate <= {d '" + tempDate1 + "'}" ;
        */
        dSQLString0 = "select distinctrow a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.loan_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on trim(a.loan_account_number) = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and a.actual_commencement_rate <= {d '" + tempDate1 + "'}" ;
        
        //check 3
        System.out.println(dSQLString0);
        curRecSet = dbobj.retrieveRecset(dSQLString0);   
        String repayfrequency ="";
        String loan_case_id ="";
        double intamt = 0.0;
        double totintacc = 0.0;
        int totintdays = 0;
        int noofdays = 1;
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        //BigDecimal bgdnewintvalue = new BigDecimal(".00");
        //bgdnewintvalue.setScale(2);
        String wkserial = "LIAP-" + branchid.toString() + companyid.toString()+ tempDate1;
        
        try {
         String dSQLString2 = "";
         String dSQLString1 = "";  
         //dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and postdate = {d '" + tempDate1 + "'} and txnref like 'LIAP-%'" ;
         dSQLString1 = "delete from interestdailycomputation where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txnref like 'LIAP-%'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txnsheader where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LIAP'" ;
         dbobj.updateTable(dSQLString1);   
         dSQLString1 = "delete from txns where Year = " + companycurryear + " and Period = " + companycurrper + " and Branchid = " + branchid + " and Companyid = " +  companyid + " and txntype = 'LIAP'" ;
         dbobj.updateTable(dSQLString1); 
            
             
        while (curRecSet.next()) {
         try {   
          if (curRecSet.getString("interestincomeaccount").isEmpty()==false && curRecSet.getString("interestreceivableaccount").isEmpty()==false) {
              //accrueloanrepaymentforperiod(curRecSet.getString("loan_case_id"),curRecSet.getString("loan_account_number"),companyid,branchid,gpostdate,curRecSet.getString("interestincomeaccount"),curRecSet.getString("interestreceivableaccount"),curRecSet.getString("repay_frequency"));
        
        loan_case_id = curRecSet.getString("loan_case_id");  
        repayfrequency = curRecSet.getString("repay_frequency");
         
        if (repayfrequency.equals("RPM")==true) {
            qInt = " INTERVAL 1 MONTH";
            qIntback = " INTERVAL -1 MONTH";
        } else if (repayfrequency.equals("RPW")==true) {
            qInt = " INTERVAL 1 WEEK";
            qIntback = " INTERVAL -1 WEEK";
        } else if (repayfrequency.equals("RPB")==true) {
            qInt = " INTERVAL 2 WEEK";
            qIntback = " INTERVAL -2 WEEK";
        } else if (repayfrequency.equals("RPD")==true) {
            qInt = " INTERVAL 1 DAY";
            qIntback = " INTERVAL -1 DAY";
        }
         
         
        dSQLString5 = "select a.id,a.loan_case_id,a.member_no,a.active,a.amount_interest,expected_repayment_date,DATE_ADD(expected_repayment_date, " + qIntback + " ) as prevdate,DATE_ADD(expected_repayment_date, " + qInt + " ) as nextdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and ((expected_repayment_date >= {d '" + tempDate2 + "'} and expected_repayment_date <= {d '" + tempDate1 + "'}) " +
                " or (expected_repayment_date > {d '" + tempDate1 + "'} and DATE_ADD(expected_repayment_date, " + qIntback + " ) < {d '" + tempDate1 + "'}  and DATE_ADD(expected_repayment_date, " + qIntback + " ) >= {d '" + tempDate2 + "'}) " +
                " or (DATE_ADD(expected_repayment_date, " + qIntback + " ) <= {d '" + tempDate2 + "'} and expected_repayment_date > {d '" + tempDate1 + "'} )) order by id asc";
        //take care of loans that span multiple monthe b/4 repayments
        //String dSQLString6 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number from loan_repayment_schedule a " +
        ///        "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and expected_repayment_date <= {d '" + tempDate2 + "'} and next_repayment_date <= {d '" + tempDate1 + "'} order by id asc";
        
        //check 3
        System.out.println(dSQLString5);
        curRecSet2 = dbobj.retrieveRecset(dSQLString5);        
        totintacc = 0.0;
        totintdays = 0;
        
        String dSQLString6 = "select max(expected_repayment_date) as endofrepaymtdate from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.branch_id = " + branchid + " and a.company_id=" + companyid ;
       curRecSet6 = dbobj.retrieveRecset(dSQLString6); 
        
        while (curRecSet2.next()) {
          //determine no of days btw last repayment and expected payment date
            dateinst3.setTime(curRecSet2.getDate("expected_repayment_date"));
            prevdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            nextdateinst2.setTime(curRecSet2.getDate("expected_repayment_date"));
            
            if (repayfrequency.equals("RPM")==true) {
              prevdateinst2.add(Calendar.MONTH  , -1);
              nextdateinst2.add(Calendar.MONTH  , 1);
            } 
            if (repayfrequency.equals("RPW")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -1);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 1);
            }
            if (repayfrequency.equals("RPB")==true) {
              prevdateinst2.add(Calendar.WEEK_OF_YEAR  , -2);
              nextdateinst2.add(Calendar.WEEK_OF_YEAR  , 2);
            }
            if (repayfrequency.equals("RPD")==true) {
              prevdateinst2.add(Calendar.DATE  , -1);
              nextdateinst2.add(Calendar.DATE  , 1);
            }
            
            if (curRecSet2.getDate("expected_repayment_date").before(gpostdate)||curRecSet2.getDate("expected_repayment_date").equals(gpostdate)) {
                datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0; 
                if (curRecSet2.getDouble("amount_interest")!=0) {
                 dailyintval =  curRecSet2.getDouble("amount_interest")/datediff.intValue();
                }
                else
                {
                  dailyintval =  0.0;  
                }    
                daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                //pls confirm
                daysinperiod = daysinperiod + 1;
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
            }
            else if (curRecSet2.getDate("expected_repayment_date").after(gpostdate))
            {
                datediff = (nextdateinst2.getTimeInMillis()-dateinst3.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                if (curRecSet2.getDouble("amount_interest")!=0) {
                 dailyintval =  curRecSet2.getDouble("amount_interest")/datediff.intValue();
                } 
                else
                {
                  dailyintval =  0.0;  
                } 
                if (prevdateinst2.before(bomdateinst2)) {
                   daysinperiod = (postdateinst2.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                else 
                {    
                   daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
            }    
            
            
         }   
         
         dSQLString2 = "INSERT INTO interestdailycomputation(Year, Period, AccountNo, Name, Currency, CustomerNo, Product, " + 
                       "Producttype, PostDate, ValueDate, BalanceBF, Amount, BalanceCF, NoOfDays, DRInterestRate, DRInterestBaseDays, " +
                       "DRInterestcalcAmount, DRInterestCalculated, Branchid, Companyid, Appliedataccrual, Appliedatperiodend, Txnref) " +
                       " VALUES (" + companycurryear  + "," + companycurrper + ",'" + curRecSet.getString("loan_account_number").trim() + "','" + curRecSet.getString("name") + "','" + gcurr + "','" 
                        + curRecSet.getString("member_no") + "','" + curRecSet.getString("loan_type") + "','" + "L" + "',{d '" + tempDate1 + "'},{d '" + tempDate1 + "'}," + curRecSet.getDouble("ClearedBalance") + ",0,"
                      + curRecSet.getDouble("ClearedBalance") + "," + totintdays + "," + curRecSet.getDouble("product_rate") + "," + 365 + ",round(" + totintacc + ",2),1,"
                       + branchid + "," + companyid + ",1,0,'" + wkserial + "')" ;
         System.out.println(dSQLString2);   
         dbobj.updateTable(dSQLString2);
          //check 3
          
          BigDecimal bgdnewintvalue = new BigDecimal(totintacc);
          bgdnewintvalue = bgdnewintvalue.setScale(2,BigDecimal.ROUND_HALF_UP);
          //bgdnewintvalue.valueOf(totintacc);
          intamt = bgdnewintvalue.doubleValue();
          
               txnEntry1 = new Entry("PC","LIAP",curRecSet.getString("interestreceivableaccount") ,gpostdate.toString(),curRecSet.getString("loan_account_number"),wkserial, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number"), "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
               txnEntry2 = new Entry("PC","LIAP",curRecSet.getString("interestincomeaccount"),gpostdate.toString(), curRecSet.getString("loan_account_number"),wkserial, "Loan Interest Accrual :- " + curRecSet.getString("loan_account_number") , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
              
               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);        
                  
          
          }
          else
          {
             logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
          } 
         }
         catch(NullPointerException npex) {
            logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id")); 
            if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
            { 
               throw new EOPException("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product and Loans " +  curRecSet.getString("loan_type") + " - " + curRecSet.getString("loan_case_id"));
            }   
         }    
        }
        
        if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "LIAP", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, "Loan Interest Accrual :- " + wkserial, "EOD", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    //
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    dSQLString2 = "update interestdailycomputation set Appliedataccrual = 0 where txnref = '" + wkserial + "'" ;
                    dbobj.updateTable(dSQLString2);
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                    if (gprocessingmethod.equalsIgnoreCase("MANUAL"))
                    { 
                       throw new EOPException("Exception: Error Updating - Entries cannot be posted");
                    }   
                }
            } else {
                logger.error("Exception: Entries not balanced/No records meet criteria");
                System.out.println("entrys not balanced/No records meet criteria");
            }   
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
       }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
        
        //pick current installment no - and pick the interest amt for the period
        
        //calculate the interest per day - no of pass days/total no of days * interestamt
        
        //post to accrual and integ ac
        
        
    }
    
    
    /**
     *
     * @param loan_case_id
     * @param loanacno
     * @param companyid
     * @param branchid
     * @param gpostdate
     * @param incomeac
     * @param receivableac
     * @param repayfrequency
     */
    public void accrueloanrepaymentforperiod(String loan_case_id,String loanacno,Integer companyid,Integer branchid,java.util.Date gpostdate,String incomeac,String receivableac,String repayfrequency)
    {
      dbobj.inimkcon();
         String dSQLString5 = "";
         String tempDate1 = formatter3.format(gpostdate);
         String tempDate2 = formatter3.format(gBOMDate);
         String tempDate3 = formatter3.format(gEOMDate);
         String qInt = "";
         String qIntback = "";
         if (repayfrequency.equals("RPM")==true) {
            qInt = " INTERVAL 1 MONTH";
            qIntback = " INTERVAL -1 MONTH";
        } else if (repayfrequency.equals("RPW")==true) {
            qInt = " INTERVAL 1 WEEK";
            qIntback = " INTERVAL -1 WEEK";
        } else if (repayfrequency.equals("RPB")==true) {
            qInt = " INTERVAL 2 WEEK";
            qIntback = " INTERVAL -2 WEEK";
        } else if (repayfrequency.equals("RPD")==true) {
            qInt = " INTERVAL 1 DAY";
            qIntback = " INTERVAL -1 DAY";
        }
         
         
        dSQLString5 = "select a.id,a.loan_case_id,a.member_no,a.active,a.amount_interest,expected_repayment_date from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'A' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and ((expected_repayment_date >= {d '" + tempDate2 + "'} and expected_repayment_date <= {d '" + tempDate1 + "'}) " +
                " or (expected_repayment_date > {d '" + tempDate1 + "'} and DATE_ADD(expected_repayment_date, " + qIntback + " ) <= {d '" + tempDate1 + "'} ) " +
                " or (expected_repayment_date <= {d '" + tempDate2 + "'} and DATE_ADD(expected_repayment_date, " + qInt + " ) > {d '" + tempDate1 + "'} )) order by id asc";
        //take care of loans that span multiple monthe b/4 repayments
        String dSQLString6 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number from loan_repayment_schedule a " +
                "where a.loan_case_id = '" + loan_case_id + "' and a.active = 'Y' and a.branch_id = " + branchid + " and a.company_id=" + companyid + " and expected_repayment_date <= {d '" + tempDate2 + "'} and next_repayment_date <= {d '" + tempDate1 + "'} order by id asc";
        
        ResultSet curRecSet;
        curRecSet = dbobj.retrieveRecset(dSQLString5);        
        try {
         
         java.util.Date date3;
         Double datediff=0.0;
         Double dailyintval=0.0;
         Double daysinperiod=0.0;
         Double accruedinttopostdate = 0.0;
         Double interestacc = 0.0;
         Calendar dateinst3 = Calendar.getInstance();
         Calendar prevdateinst2 = Calendar.getInstance();
         Calendar nextdateinst2 = Calendar.getInstance();
         Calendar bomdateinst2 = Calendar.getInstance();
         Calendar postdateinst2 = Calendar.getInstance();
        while (curRecSet.next()) {
          //determine no of days btw last repayment and expected payment date
             //curRecSet.getDate("expected_repayment_date");
            //Calendar dateinst3 = Calendar.getInstance();
            //Calendar prevdateinst2 = Calendar.getInstance();
            //Calendar nextdateinst2 = Calendar.getInstance();
            //Calendar bomdateinst2 = Calendar.getInstance();
            //Calendar postdateinst2 = Calendar.getInstance();
            dateinst3.setTime(curRecSet.getDate("expected_repayment_date"));
            prevdateinst2.setTime(curRecSet.getDate("expected_repayment_date"));
            nextdateinst2.setTime(curRecSet.getDate("expected_repayment_date"));
            postdateinst2.setTime(gpostdate);
            if (repayfrequency.equals("RPM")==true) {
              prevdateinst2.add(Calendar.MONTH  , -1);
              nextdateinst2.add(Calendar.MONTH  , 1);
            } 
            
            if (curRecSet.getDate("expected_repayment_date").before(gpostdate)||curRecSet.getDate("expected_repayment_date").equals(gpostdate)) {
                datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                dailyintval =  curRecSet.getDouble("amount_interest")/datediff.intValue();
                daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                interestacc = daysinperiod * dailyintval;
            }
            else if (curRecSet.getDate("expected_repayment_date").after(gpostdate))
            {
                datediff = (nextdateinst2.getTimeInMillis()-dateinst3.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                dailyintval =  curRecSet.getDouble("amount_interest")/datediff.intValue();
                daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                interestacc = daysinperiod * dailyintval;
            }    
                     
         }
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
        }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }  
        
        
    }
    
    
     /**
     *
     * @param companyid
     * @param branchid
     * @param gpostdate
     */
    public void Processehpinterestrepayment(Integer companyid,Integer branchid,java.util.Date gpostdate)
    {
      dbobj.inimkcon();
         String dSQLString5 = "";
         String tempDate1 = formatter3.format(gpostdate);
         String tempDate2 = formatter3.format(gBOMDate);
         String dnarr = "";
         String dfillednarr="";
         int dinstno = 0;
         String cracc = "";
         String dracc = "";
         String drefid ="";
         GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        String wkserial = "HIAP-" + branchid.toString() + companyid.toString();
        String dSQLString = "select a.refid as hpref,a.accountno as hpaccountno,a.product,a.status,b.*,c.interestincomeaccount,c.interestreceivableaccount from hprequests a inner join hprepaymentschedule b on a.refid = b.refid and a.companyid = b.companyid and a.branchid = b.branchid " +
                " left join qryproductaccounts c on a.product = c.code and a.companyid = c.company_id and a.branchid = c.branch_id where a.status = 'S' and a.branchid = " + branchid + " and a.companyid=" + companyid + " and b.rpymtdate <= {d '" + tempDate1 + "'} and processed = 0 order by a.refid,instalno asc";
        
        ResultSet rs2 =  null;
        
        ResultSet curRecSet;
        curRecSet = dbobj.retrieveRecset(dSQLString);        
        try {
         
         rs2 =  dbobj.retrieveRecset("SELECT a.* FROM txncodes a where a.TransactionCode = 'HPI'");
          while (rs2.next()) {  
             dnarr = rs2.getString("narrative");
             //narrationhpdown = String.format(dnarrhpdwnpay,daccountno,hpref);
          }    
            
         double intamt = 0.0;
        while (curRecSet.next()) {
             dfillednarr = String.format(dnarr,curRecSet.getString("hpaccountno"),curRecSet.getString("hpref"));
             wkserial = "HIAP-" + branchid.toString() + companyid.toString()+"-"+curRecSet.getString("hpref");
             intamt = curRecSet.getDouble("interest");
             cracc = curRecSet.getString("interestincomeaccount");
             drefid = curRecSet.getString("hpref");
             dinstno = curRecSet.getInt("instalno");
             if (Companyaccmtd.equalsIgnoreCase("ACCRUAL")==true) {
                 dracc = curRecSet.getString("interestreceivableaccount");
             }
             else {
                 dracc = curRecSet.getString("hpaccountno");
             }        
             try {        
              if ((intamt!=0.0)&&(cracc.isEmpty()==false)&&(dracc.isEmpty()==false))
              {
               txnEntry1 = new Entry("HIAP","HIAP",dracc ,gpostdate.toString(),curRecSet.getString("hpaccountno"),wkserial, dfillednarr, "1", -1 * Double.valueOf(intamt),-1 * Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
               txnEntry2 = new Entry("HIAP","HIAP",cracc,gpostdate.toString(), curRecSet.getString("hpaccountno"),wkserial, dfillednarr , "1",Double.valueOf(intamt),Double.valueOf(intamt), 1, guserid,branchid,companycurryear,companycurrper,companyid);
              
               txnEntrys.setEntrys(txnEntry1);
               txnEntrys.setEntrys(txnEntry2);        
              }  
              else
              {
               logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product HP " +  curRecSet.getString("product") + " - " + curRecSet.getString("hprefid")); 
              }
             } 
             catch(NullPointerException npex) {
               logger.error("Exception: Invalid or non declared Interest Income or Receivable Accounts for Product HP " +  curRecSet.getString("product") + " - " + curRecSet.getString("hprefid")); 
             } 
            
            
           if ((txnEntrys.validateSumofAmount() == true) && (txnEntrys.getEntrys().size()>0)) {
                Txnsheader thetxnheader = new Txnsheader(wkserial, "HIAP", companycurryear, companycurrper, gpostdate, gpostdate, gpostdate, wkserial, dfillednarr + wkserial, "EOD", guserid, branchid, companyid,timezone);
                ApplyEntrys dataUpdate = new ApplyEntrys(txnEntrys.getEntrys(), thetxnheader, companycurryear, companycurrper, branchid, companyid,timezone);
                if (dataUpdate.performAllupdates() == true) {
                    String dSQLString2 = "update hprepaymentschedule set processed = 1,processdate = {d '" + tempDate1 + "'} where refid = '" + drefid + "' and instalno = " + dinstno + " and a.branchid = " + branchid + " and a.companyid=" + companyid ;
                    dbobj.updateTable(dSQLString2);
                } else {
                    System.out.println("Exception: Error Updating - Entries cannot be posted");
                    logger.error("Exception: Error Updating - Entries cannot be posted");
                       
                }
            } else {
                logger.error("Exception: Entries not balanced/No records meet criteria");
                System.out.println("entrys not balanced/No records meet criteria");
            }
         }
        
        }
        catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
            logger.error("SQLException: " + ex.getMessage());
        }
       finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }  
        
        
    }
    
    /**
     *
     * @throws EOPException
     */
    public void testAccrueloansinterest() throws EOPException{
        dbobj.inimkcon();
        String dSQLString5 = "";
        java.util.Date gpostdate;
        String tempDate2 = formatter3.format(gBOMDate);
         String tempDate3 = formatter3.format(gEOMDate);
         String qInt = "";
         String qIntback = "";
         ResultSet curRecSet;
         ResultSet curRecSet2;
         java.util.Date date3;
         Double datediff=0.0;
         Double dailyintval=0.0;
         Double daysinperiod=0.0;
         Double accruedinttopostdate = 0.0;
         Double interestacc = 0.0;
         Calendar dateinst3 = Calendar.getInstance();
         Calendar prevdateinst2 = Calendar.getInstance();
         Calendar nextdateinst2 = Calendar.getInstance();
         Calendar bomdateinst2 = Calendar.getInstance();
         Calendar postdateinst2 = Calendar.getInstance();
         
        //calculate daily interest rate and set applied to 0 store in interestdailycomputation;
        
        //scan thru all loans for company and branch where loan interest type is not Flat
         String dSQLString0 = "";
        //
        dSQLString0 = "select a.loan_case_id,a.member_no,a.loan_type,a.loan_status,a.loan_account_number,a.repay_frequency,a.product_rate,a.loan_type,b.interestincomeaccount,b.interestreceivableaccount,c.name,c.clearedbalance from loan_request a " +
                " inner join accounts c on a.loan_account_number = c.accountno and a.branch_id = c.branch and a.company_id = c.companyid " +
                " left join qryproductaccounts b on a.loan_type = b.code and a.branch_id = b.branch_id and a.company_id = b.company_id " +
                "where a.loan_status = 'D' and a.interest_type != 'FFL' and a.branch_id = " + branchid + " and a.company_id=" + companyid ;
        
        curRecSet = dbobj.retrieveRecset(dSQLString0);   
        String repayfrequency ="";
        String loan_case_id ="";
        double intamt = 0.0;
        double totintacc = 0.0;
        int totintdays = 0;
        int noofdays = 1;
        GenerateEntrys txnEntrys = new  GenerateEntrys();
        Entry txnEntry1;
        Entry txnEntry2;
        BigDecimal bgdnewintvalue = new BigDecimal(".00");
        bgdnewintvalue.setScale(2);
        
         loan_case_id = "433007201511236LON";  
        repayfrequency = "RPM";
         
        if (repayfrequency.equals("RPM")==true) {
            qInt = " INTERVAL 1 MONTH";
            qIntback = " INTERVAL -1 MONTH";
        } else if (repayfrequency.equals("RPW")==true) {
            qInt = " INTERVAL 1 WEEK";
            qIntback = " INTERVAL -1 WEEK";
        } else if (repayfrequency.equals("RPB")==true) {
            qInt = " INTERVAL 2 WEEK";
            qIntback = " INTERVAL -2 WEEK";
        } else if (repayfrequency.equals("RPD")==true) {
            qInt = " INTERVAL 1 DAY";
            qIntback = " INTERVAL -1 DAY";
        }
         
         
        totintacc = 0.0;
        totintdays = 0;
		int i=1;
		java.util.Date expected_repayment_date = null;
		java.util.Date bom_date =  null;
		java.util.Date eom_date =  null;
		java.util.Date post_date =  null;
                
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
               try {
                 expected_repayment_date = df.parse("2016-03-09");
                 bom_date = df.parse("2016-03-01");
                 eom_date = df.parse("2016-03-31");
                 post_date = df.parse("2016-03-15");
               } catch (ParseException e) {
                 e.printStackTrace();
               }
                gpostdate=post_date;
                postdateinst2.setTime(gpostdate);
                bomdateinst2.setTime(bom_date);
        while (i<=2) {
          //determine no of days btw last repayment and expected payment date
            dateinst3.setTime(expected_repayment_date);
            prevdateinst2.setTime(expected_repayment_date);
            nextdateinst2.setTime(expected_repayment_date);
            
            if (repayfrequency.equals("RPM")==true) {
              prevdateinst2.add(Calendar.MONTH  , -1);
              nextdateinst2.add(Calendar.MONTH  , 1);
            } 
            
            if (expected_repayment_date.before(gpostdate)||expected_repayment_date.equals(gpostdate)) {
                datediff = (dateinst3.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                dailyintval =  466.67/datediff.intValue();
                daysinperiod = (dateinst3.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                daysinperiod = daysinperiod + 1;
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
                
                logger.info("test: total int acc and days - run " + i + " --- " + totintacc + " " + totintdays );
                System.out.print("test: total int acc and days - run " + i + " --- " + totintacc + " " + totintdays);
            }
            else if (expected_repayment_date.after(gpostdate))
            {
                datediff = (nextdateinst2.getTimeInMillis()-dateinst3.getTimeInMillis())/1000.0/60.0/60.0/24.0;  
                dailyintval =  466.67/datediff.intValue();
                if (prevdateinst2.before(bomdateinst2)) {
                   daysinperiod = (postdateinst2.getTimeInMillis()-bomdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                else 
                {    
                   daysinperiod = (postdateinst2.getTimeInMillis()-prevdateinst2.getTimeInMillis())/1000.0/60.0/60.0/24.0;
                }
                interestacc = daysinperiod * dailyintval;
                totintacc = totintacc + interestacc;
                totintdays = totintdays + daysinperiod.intValue();
                
                logger.info("test: total int acc and days - run " + i + " --- " + totintacc + " " + totintdays );
                System.out.print("test: total int acc and days - run " + i + " --- " + totintacc + " " + totintdays);
                
            }    
            
            
            try {
                 expected_repayment_date = df.parse("2016-04-09");
                 
               } catch (ParseException e) {
                 e.printStackTrace();
               }
            i= i+1;
         }  
        
  }
    
    
     /**
     *
     * @param mess
     * @throws EOPException
     */
    public void logerrindb(String mess)throws EOPException{
       GendataService dbobj2;
       dbobj2 = new GendataService();
       dbobj2.inimkcon();
       String tempDate3 = formatter3.format(gpostdate);
       String mySQLString2x = "";
       try {
           mySQLString2x = "select max(id) as mid from eodprocess where company_id = " + companyid + " and branch_id = " + branchid + " and trx_date = {d '" + tempDate3 + "'}" ;
           ResultSet curRecSet;
           curRecSet = dbobj2.retrieveRecset(mySQLString2x);
           int lastid = 0;
           while (curRecSet.next()) {
               if (curRecSet.getInt("mid")>0) {
                   lastid = curRecSet.getInt("mid");
               }
           }
           
           mySQLString2x = "update eodprocess set haserror = 1 where id = " + lastid ;
           dbobj2.updateTable(mySQLString2x);
           
           mySQLString2x = "insert into eodprocerror (company_id, branch_id, curryear, currperiod, user_id, trx_date, error_message,ref_id) " + 
                       " VALUES (" + companyid + "," + branchid + "," + companycurryear  + "," + companycurrper + ",'" + guserid + "',{d '" + tempDate3 + "'},'" +  mess + "'," + lastid + ")" ;
           System.out.println("log in db " + mySQLString2x);
           dbobj2.updateTable(mySQLString2x);
      }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
        dbobj2.closecon();
       }  
    }
     
    /**
     *
     * @throws EOPException
     */
    public void logprocstartindb()throws EOPException{
      GendataService dbobj2;
      dbobj2 = new GendataService();
      dbobj2.inimkcon();
      String tempDate3 = formatter3.format(gpostdate);
      String mySQLString2x = "";
      try {
           mySQLString2x = "insert into eodprocess (company_id, branch_id, user_id, trx_date) " + 
                       " VALUES (" + companyid + "," + branchid + ",'" + guserid + "',{d '" + tempDate3 + "'}" + ")" ;
           dbobj2.updateTable(mySQLString2x);

      }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
        dbobj2.closecon();
       }  
    } 
    
    /**
     *
     * @throws EOPException
     */
    public void logproccompindb()throws EOPException{
         GendataService dbobj2;
         dbobj2 = new GendataService();
         dbobj2.inimkcon();
         String tempDate3 = formatter3.format(gpostdate);
         String mySQLString2x = "";
       try {
           mySQLString2x = "select max(id) as mid from eodprocess where company_id = " + companyid + " and branch_id = " + branchid + " and trx_date = {d '" + tempDate3 + "'}" ;
           ResultSet curRecSet;
           curRecSet = dbobj2.retrieveRecset(mySQLString2x);
           int lastid = 0;
           while (curRecSet.next()) {
               if (curRecSet.getInt("mid")>0) {
                   lastid = curRecSet.getInt("mid");
               }
           }
           
           mySQLString2x = "update eodprocess set completed = 1 where id = " + lastid ;
           dbobj2.updateTable(mySQLString2x);

           }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
        dbobj2.closecon();
       }  
    } 
    
    
    public int isdayclosed(int dcompanyid, int dbranchid) throws EOPException{
      int itisperiodend=0;
      if (dbobj==null) {
          dbobj = new GendataService();
      }
      dbobj.inimkcon();
      mySQLString = "select endofday from branch a where a.company_id = " + dcompanyid + " and a.id = " + dbranchid;
     // System.out.println(mySQLString);
      ResultSet curRecSet;
      ResultSet curRecSet2=null;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       if (dbobj.retrieveRecset(mySQLString).first() == false)
         {
          System.out.println("Exception: Branch/Company not defined.");  
          logger.error("Exception: Branch/Company not defined.");
          //logerrindb("Exception: Branch/Company not defined");
          throw new EOPException("Exception: Branch/Company not defined.");
         }
       else
       {
        while (curRecSet.next()) {
         if (curRecSet.getString("endofday").equals("1")) {
             itisperiodend=1;
         }
       }
        
       }
      }
      catch(SQLException ex) {
            //JOptionPane.showMessageDialog (null,"SQLException: " + ex.getMessage() ,"SiftERP",JOptionPane.ERROR_MESSAGE);
           System.out.println("SQLException: " + ex.getMessage());
           logger.error("SQLException: " + ex.getMessage());
           throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          dbobj.closecon();
       }
      return itisperiodend;
    }
}
