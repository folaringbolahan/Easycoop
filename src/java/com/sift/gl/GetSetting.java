/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl;
import java.sql.*;
/**
 *
 * @author yomi
 */
public class GetSetting {
    String rSettingvalue="";
    ResultSet rs=null;
  public GetSetting(){
      
  }
  public String GetSettingvalue(String vsetting,String vbranch) {
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
    String mySQLString = "select * from settings where setting = '" + vsetting + "' and branch = '" + vbranch + "'";
    try {
      rs =  dbobj.retrieveRecset(mySQLString);
      if (rs.first() == true)
       {
          rSettingvalue = rs.getString("value");
       }
     }
    catch (SQLException ex) {
            System.out.println("Record not retrieved : Error - " + ex.getMessage()) ;
        }
        finally {
          try{  
           if (rs!=null)
           {rs.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          dbobj.closecon();
        } 
    return rSettingvalue;
   }
  
  public String GetSettingvalue(String vsetting,Integer vbranch, Integer vcoy) {
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
    String mySQLString = "select * from settings where setting = '" + vsetting + "' and branch = " + vbranch + " and companyid = " + vcoy;
    try {
      rs =  dbobj.retrieveRecset(mySQLString);
      if (rs.first() == true)
       {
          rSettingvalue = rs.getString("value");
       }
     }
    catch (SQLException ex) {
            System.out.println("Record not retrieved : Error - " + ex.getMessage()) ;
        }
        finally {
          try{  
           if (rs!=null)
           {rs.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          dbobj.closecon();
        } 
    return rSettingvalue;
   }
}

  
