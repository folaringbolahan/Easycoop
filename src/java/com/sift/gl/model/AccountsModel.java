/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/** model for accounts for zk component user interfaces
 *
 * @author yomi-pc
 */
public class AccountsModel {
 
    GendataService dbobj = new GendataService();
    java.sql.Connection myCon;
    Integer vCompany;
    Integer vBranch;
    /**
     *
     */
    public java.util.List<Account> accountList;
    
   
     private String branch;
     private String company;

    /**
     *
     * @param branch
     * @param companyid
     */
    public AccountsModel(Integer branch,Integer companyid,String searchkeyword) {
        dbobj.inimkcon();
        vBranch = branch;
        vCompany = companyid;
        String acctno;
        String acctname;
        String acctcurr;
        java.util.List<Account> accountListx = new LinkedList<Account>();
        ResultSet rsAccts=null;
        String mySQLString = "select * from accounts where controlaccount = 0 and branch = " + vBranch + " and companyid = " + vCompany + "";
        if (searchkeyword.isEmpty()==false && searchkeyword != null) {
            mySQLString = "select * from accounts where controlaccount = 0 and branch = " + vBranch + " and companyid = " + vCompany + " and (accountno like '%" + searchkeyword.trim() + "%' or name like '%" + searchkeyword.trim() + "%')";
        }
        
        String mySQLString2;
        try {
          //System.out.println(mySQLString);
            rsAccts =  dbobj.retrieveRecset(mySQLString);
          while (rsAccts.next()) {
              acctno = rsAccts.getString("accountno");
              acctname = rsAccts.getString("name");
              acctcurr = rsAccts.getString("currency");
              Account Acct = new Account();
              Acct.setAccountNo(acctno);
              Acct.setName(acctname);
              Acct.setCurrency(acctcurr);
              accountListx.add(Acct);

           }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
        
        }
        finally {
          try{  
           if (rsAccts!=null)
           {rsAccts.close();}
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
        
        accountList = accountListx;
    }

    /**
     *
     * @return
     */
    public java.util.List<Account> getaccountList(){
        
       return this.accountList; 
    }


}
