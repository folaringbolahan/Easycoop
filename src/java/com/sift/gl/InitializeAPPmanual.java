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
import java.text.ParseException;
import org.apache.log4j.Logger;
/**
 *
 * @author yomi-pc
 */
public class InitializeAPPmanual {
    
/**
 *
 * @author ABAYOMI AWOSUSI
 */
      String tempDate = null;
      String tempDate2 = null;
      SimpleDateFormat formatter1 = null;//new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
      Calendar rightNow = null;// Calendar.getInstance();
      GendataService dbobj = new GendataService();
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
      boolean endofperiod = false;
      java.util.Date gpostdate;
      java.util.Date gcurrsysdate;
      String tmz ;
      TimeZone timezone;
    
    public InitializeAPPmanual(String tmz){
       TimeZone timeZone = TimeZone.getTimeZone(tmz);
       timezone = timeZone; 
       formatter1 = new SimpleDateFormat("yyyy-MM-dd");
       formatter1.setTimeZone(timeZone);
       rightNow =  Calendar.getInstance(timeZone);
       
    }  
      
    public void InitializeAPP(){
      String mySQLString;
      mySQLString = "select * from branch where id = " + branchid;
      dbobj.inimkcon();
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
      }
      if (Newday()==true){
        
        
       
      }//end if newday
    }
    
    
    public boolean iscurrentdateclosed(Integer dbranch){
      String mySQLString;
      int currdayclosedval = 0;
      boolean currentdateclosed = false;
      mySQLString = "select * from branch where id = " + dbranch;
      dbobj.inimkcon();
      ResultSet curRecSet;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       while (curRecSet.next()) {
           currdayclosedval = curRecSet.getInt("endofday");
       }
      }
      catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
      }
      if (currdayclosedval==1){
        currentdateclosed = true;
      }
      return currentdateclosed;
    }
    
    
    public String startdaynow(String ddate,Integer dperiod,Integer  dyear,Integer  dcompany,Integer dbranch){
        String startok="ok";
        GendataService dbobj = new GendataService();
        String mySQLString;
        branchid = dbranch;
        dbobj.inimkcon();
        java.util.Date newpostdate = new java.util.Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
         newpostdate = formatter2.parse(ddate);
        } catch (ParseException e) {
          System.out.println("Parse Error" + e.getMessage());
        }
        tempDate = formatter1.format(rightNow.getTime());
        tempDate2 = formatter3.format(newpostdate);
        mySQLString = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.year = " + dyear + " and period_id = " + dperiod + " and b.company_id = " + dcompany + " and b.branch_id = " + dbranch + " and {d '" + tempDate2  + "'} between fp_start and fp_end";
        ResultSet rs =  null;
        //System.out.println(mySQLString);
        try {
          rs =  dbobj.retrieveRecset(mySQLString); 
          if (rs.first() == false)
            {
              System.out.println ("Exception: Current period not closed. Please close before initializing new day." );
              startok = "Exception: Current period not closed. Please close before initializing new day.";
              mySQLString = "update branch set currentsystemdate = {d '" + tempDate  + "'} where id = " + dbranch + " and company_id = '" + dcompany + "'"; ;
              dbobj.updateTable(mySQLString);
            }
           else
            {
              if (Sodaystatus(newpostdate)==true) {
                  //  Company.setPostDate(newpostdate);
              }
              else
              {
                 startok = "Exception in starting new day";  //GoTo CheckForNewDay
              }
           }
          }
          catch(SQLException ex) {
                System.out.println ("SQLException: " + ex.getMessage() );
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
      return startok;
    }
    
   // 6b-1
    public boolean Newday(){
     boolean isnewday = false;
     Calendar rightNow2 = Calendar.getInstance(timezone);
     java.util.Date dcurrdate;
     if (gcurrsysdate==null) {
        rightNow2.add(Calendar.DATE,-1);
        dcurrdate = rightNow2.getTime();
     }
     else
     {
        dcurrdate = gcurrsysdate;
     }
     rightNow2 = Calendar.getInstance(timezone);
     tempDate = formatter3.format(dcurrdate);
     tempDate2 = formatter1.format(rightNow2.getTime());
     if (tempDate.equals(tempDate2)==false){
        isnewday = true;
        System.out.println(tempDate2);
        System.out.println(tempDate);
     }
     return isnewday;
    }
    
 public boolean Sodaystatus(java.util.Date ddate) {
     boolean soday = false;
    String mySQLString2;
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
     try {
           tempDate = formatter1.format(rightNow.getTime());
           tempDate2 = formatter3.format(ddate);
           mySQLString2 = "update branch  set startofday = 1, endofday = 0,postdate = {d '" + tempDate2 + "'}, currentsystemdate = {d '" + tempDate + "'} where id = " + branchid + "";
           dbobj.updateTable(mySQLString2);
           soday = true;
           }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       }
      finally {
          dbobj.closecon();
       }
    return soday;
    }
}
