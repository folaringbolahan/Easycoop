/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl;
import java.sql.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
/**
 *
 * @author yomi
 */
public class NewSerialno {
    Integer vNewSerial=0;
    String vNewSerialstr="";
    ResultSet rsSerials;
  public NewSerialno() {
    }  
  public Integer returnSerialno(String vSeries,Integer vBranch,Integer vCoy){
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
    String mySQLString = "select * from serials where series = '" + vSeries + "' and branch = " + vBranch + " and companyid = " + vCoy + "";
    System.out.println(mySQLString);
    String mySQLString2;
    try {
      rsSerials =  dbobj.retrieveRecset(mySQLString);
      if (rsSerials.first() == true)
       {
          vNewSerial = rsSerials.getInt("Serial") + 1;
          System.out.println("here now 2222");
       }
      mySQLString2 = "update serials set serial = " + vNewSerial + " where series = '" + vSeries + "' and branch = " + vBranch + " and companyid = " + vCoy + "";
      dbobj.updateTable(mySQLString2);
      //updateTable(mySQLString);

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
           dbobj = null;  
     }
    return vNewSerial;
   }
  public String returnSerialnostr(String vSeries,Integer vBranch,Integer vCoy){
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
    String mySQLString = "select * from serials where series = '" + vSeries + "' and branch = " + vBranch + " and companyid = " + vCoy + "";
    String mySQLString2;
    Integer seriallen = 1;
    try {
      rsSerials =  dbobj.retrieveRecset(mySQLString);
      if (rsSerials.first() == true)
       {
          vNewSerial = rsSerials.getInt("Serial") + 1;
          seriallen = rsSerials.getInt("Seriallength");
          StringBuffer padvar = new StringBuffer();
          for (Integer k=0; k < seriallen; k++) {
             padvar = padvar.append("0");
          }
          NumberFormat nf = new DecimalFormat(padvar.toString());
          //nf.format((rsSerials.getInt("Serial") + 1));
          vNewSerialstr = rsSerials.getString("Prefix") + nf.format((rsSerials.getInt("Serial") + 1));//Integer.toString((rsSerials.getInt("Serial") + 1));
       }
      mySQLString2 = "update serials set serial = " + vNewSerial + " where series = '" + vSeries + "' and branch = " + vBranch + " and companyid = " + vCoy + "";
      dbobj.updateTable(mySQLString2);
      //updateTable(mySQLString);

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
    return vNewSerialstr;
   }
}
