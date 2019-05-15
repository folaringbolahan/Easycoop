/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class GendataService {
    public Connection con;
    public Statement Stmt=null;
    private String dbName = "jdbc/easycoopfin";
    //private static Logger logger = Logger.getLogger(GendataService.class.getPackage().getName());
    public GendataService () {
      //makeConnection();
    }
   public void inimkcon() {
      try {
         makeConnection();
      } catch (Exception ex) {
          System.out.println(ex.getMessage());
      }
     }
     private void makeConnection()  {
     try {
      Context ic = new InitialContext();
      Context envContext  = (Context)ic.lookup("java:/comp/env");
      DataSource ds = (javax.sql.DataSource) envContext.lookup(dbName);
      con =  ds.getConnection();
    //  System.out.println("Connected to database!");
     
      } catch (Exception ex) {
      System.out.println("Couldn't open connection to database: " + ex.getMessage());
     }       
   }
     
   public Connection getConnection(){
       return con;
   }   
   public void closecon() {
      try {
         Stmt.close();
         con.close();
       //  System.out.println("closing connection....");
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      }
       catch (NullPointerException nex) {
          System.out.println(nex.getMessage()+ " in connection closing");
      }
     }
   
   
   public ResultSet retrieveRecset(String mySqlStmt)   {
     ResultSet returnedrec;
     returnedrec = null;
       try {
         Stmt =  con.createStatement();
         returnedrec = Stmt.executeQuery(mySqlStmt);  
       } catch (SQLException ex) {
          System.out.println(ex.getMessage());
     } 
      return returnedrec;
    }
   
   public ResultSet retrieveupdatableRecset(String mySqlStmt)   {
     ResultSet returnedrec;
     returnedrec = null;
       try {
         Stmt =  con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
         returnedrec = Stmt.executeQuery(mySqlStmt);  
       } catch (SQLException ex) {
          System.out.println(ex.getMessage());
     } 
      return returnedrec;
    }
   
   public boolean updateTable (String mySqlStmt) throws SQLException   {
     //boolean updatesuccessful = true;
       boolean updatesuccessful = false;
     //  try {
         Stmt =  con.createStatement();
         /////System.out.println(mySqlStmt);
         Stmt.executeUpdate(mySqlStmt);
         updatesuccessful = true;
         //System.out.println("auto comm mode-ok " + con.getAutoCommit());
    /*   } catch (SQLException ex) {
          updatesuccessful = false; 
          String errmess;
          errmess = "Gendataserv;updatetable:" + mySqlStmt ;
          //logger.error(errmess, ex);
          System.out.println(errmess+ ": " + ex.getMessage());
         // System.out.println("auto comm mode - not ok " + con.getAutoCommit());
     }*/
     
        //closecon();
        return updatesuccessful;
    }
   
   public boolean updateTablebatch (String mySqlStmt) throws SQLException   {
     boolean updatesuccessful = true;
       try {
         //con.setAutoCommit(false);
         Stmt =  con.createStatement();
         ////System.out.println(mySqlStmt);
         Stmt.executeUpdate(mySqlStmt);
         
       } catch (SQLException ex) {
          updatesuccessful = false; 
          String errmess;
          errmess = "Gendataserv;updatetable:" + mySqlStmt ;
          //logger.error(errmess, ex);
          System.out.println(errmess + ": " + ex.getMessage());
     }
     
        //closecon();
        return updatesuccessful;
    }
  
}
