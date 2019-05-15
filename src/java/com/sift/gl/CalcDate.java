/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
/**
 *
 * @author yomi-pc
 */
public class CalcDate {
    String tempDate = null;
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    Calendar vCal = null;//Calendar.getInstance();
    Calendar vCal2 = null;//Calendar.getInstance();
    GendataService dbobj = new GendataService();
    String mySQLString;
    ResultSet curRecSet;
    /** class to jump holidays and weekends
     *
     */
    public CalcDate(){
      
      //TimeZone timeZone = TimeZone.getTimeZone(tmz);
      //formatter1.setTimeZone(timeZone);
      vCal = Calendar.getInstance();//Calendar.getInstance(timeZone);
      vCal2 = Calendar.getInstance();//Calendar.getInstance(timeZone);
  }
    /**
     *
     * @param ddate
     * @param pvaluedays
     * @param companyid
     * @param branchid
     * @return
     * @throws EOPException
     */
    public java.util.Date CalcValueDate(java.util.Date ddate,int pvaluedays,Integer companyid,Integer branchid) throws EOPException{
     java.util.Date vdate ;
     int i;
     boolean DateisHoliday;
     vdate = ddate;
     i = 1;
     int q=0;
     dbobj.inimkcon();
     try {
           tempDate = formatter1.format(vdate);
           while (i <= pvaluedays) {
             mySQLString = "select * from holidates where holidate = {d '" + tempDate + "'} and companyid = " + companyid + " and branchid = " + branchid ;
             curRecSet =  dbobj.retrieveRecset(mySQLString);
             DateisHoliday =  dbobj.retrieveRecset(mySQLString).first() ;
             vCal.setTime(vdate);
             vCal.add(Calendar.DATE, 1);
             vCal2 = vCal;
             boolean dateisendofperiod = false;
             while (((vCal.get(Calendar.DAY_OF_WEEK)==1)||(vCal.get(Calendar.DAY_OF_WEEK)==7)||(DateisHoliday==true))&&(dateisendofperiod == false)) {
               //vCal.add(Calendar.DATE, 1);
               //System.out.println(vCal.getTime().toString());
               //need to run eomonth no jumping   
              String mySQLString3 = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where b.company_id = " + companyid + " and b.branch_id = " + branchid;   
              ResultSet curRecSet3=null;
              java.util.Date wkEOMDate;
              curRecSet3 = dbobj.retrieveRecset(mySQLString3);   
              
              while (curRecSet3.next()) {
                  wkEOMDate = (curRecSet3.getDate("fp_end"));
                  if ((wkEOMDate.compareTo(vCal.getTime())==0)) {
                   dateisendofperiod=true;
                  }
              }   
              if (dateisendofperiod==false)
              {  
               tempDate = formatter1.format(vCal.getTime());
               mySQLString = "select * from holidates where holidate = {d '" + tempDate + "'} and companyid = " + companyid + " and branchid = " + branchid ;
               DateisHoliday =  dbobj.retrieveRecset(mySQLString).first() ;
               vCal2 = vCal;
               vCal.add(Calendar.DATE, 1);
             }
             }
             i = i + 1;
            }
           vdate = vCal2.getTime();
           //System.out.println("time returned " + vdate.toString());
           return vdate;
           }
      catch(SQLException ex) {
          
       System.out.println("SQLException: " + ex.getMessage());
       //throw new EOPException("SQLException: " + ex.getMessage());
       
      }
     finally {
          try{  
           if (curRecSet!=null)
           {curRecSet.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
           } 
          if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
            }
          dbobj = null;
    }
    return vdate;
    }
}
