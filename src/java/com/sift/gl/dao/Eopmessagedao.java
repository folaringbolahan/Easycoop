/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;
import com.sift.gl.EOPException;
import com.sift.gl.EOPeriodprocessing;
import com.sift.gl.GendataService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.*;
import org.springframework.stereotype.Component;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
//import payhroll.ejb.*;
/**
 *
 * @author ABAYOMI AWOSUSI
 */

@Component 
public class Eopmessagedao {
    public int messageval(int companyid,int branchid,Date gpostdate)throws EOPException{
      int retval = 0;
      GendataService dbobj2;
      dbobj2 = new GendataService();
      SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
      dbobj2.inimkcon();
      String tempDate3 = formatter3.format(gpostdate);
      String mySQLString2x = "";
      ResultSet rs =  null;
      int idd = 0;
      try {
           mySQLString2x = "select max(id) as did from eodprocess where company_id = " + companyid +  " and branch_id = " + branchid + " and trx_date = " + 
                       "{d '" + tempDate3 + "'}"   ;
           rs =  dbobj2.retrieveRecset(mySQLString2x);
           while (rs.next()) {
             idd = rs.getInt("did");
           } 
           mySQLString2x = "select completed from eodprocess where id = " + idd  ;
           rs =  dbobj2.retrieveRecset(mySQLString2x);
           while (rs.next()) {
             retval = rs.getInt("completed");
           }  
      }
      catch(SQLException ex) {
       System.out.println("SQLException: " + ex.getMessage());
       throw new EOPException("SQLException: " + ex.getMessage());
      }
      finally {
        dbobj2.closecon();
       } 
      return retval;
    } 
}
