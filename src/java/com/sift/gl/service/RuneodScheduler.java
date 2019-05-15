/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.gl.EOPException;
import com.sift.gl.EOPeriodprocessing;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 *
 * @author yomi-pc
 */
@Component
public class RuneodScheduler {
    
    /**
     *
     */
    public void run() {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     EOPeriodprocessing eop = new EOPeriodprocessing();
     String mySQLString = "select a.id,b.id as branchid,b.branch_code, b.currentyear,b.currentperiod,b.postdate,b.currentsystemdate,b.country_id,c.currency_code as basecurrency,c.timez  from company a inner join branch b on a.id = b.company_id inner join countries c on b.country_id = c.id where b.active = 'Y' and a.active = 'Y' order by a.id asc ,b.id asc";
     GetSetting procmtdsetg = new GetSetting();
     String procmtd = "";
     ResultSet curRecSet;
      curRecSet = dbobj.retrieveRecset(mySQLString);
     // int kkount = 0;
      try {
         while (curRecSet.next()) {
          if ((curRecSet!= null) ) {    
           procmtd = procmtdsetg.GetSettingvalue("PROCESSINGMETHOD",curRecSet.getInt("branchid"),curRecSet.getInt("id"));
           if (procmtd.equalsIgnoreCase("MANUAL")==false) 
           {
               try {
               //kkount = kkount + 1;
                 eop.EOPeriodprocessinginit(curRecSet.getInt("id"),curRecSet.getInt("branchid"),curRecSet.getInt("currentyear"),curRecSet.getInt("currentperiod"),curRecSet.getDate("postdate"),curRecSet.getDate("currentsystemdate"),"SYSGEN",curRecSet.getInt("country_id"),curRecSet.getString("basecurrency"),"AUTO",curRecSet.getString("timez"));
               }catch(EOPException ex) {
                System.out.println("EOPException: " + ex.getMessage());
                  //  System.out.println("EOPException kount: " + kkount);
               }
                 
           }
          } 
         }
      }
      catch(SQLException ex) {
           System.out.println("SQLException: " + ex.getMessage());
      }
     // catch(EOPException ex) {
     //      System.out.println("EOPException: " + ex.getMessage());
     //      System.out.println("EOPException kount: " + kkount);
     // }
      catch(NullPointerException ex) {
           System.out.println("NullPointerException: " + ex.getMessage());
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
    
}
