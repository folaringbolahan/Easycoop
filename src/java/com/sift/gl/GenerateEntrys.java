/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Txnsheader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author yomi
 */
public class GenerateEntrys {
    private Integer txnId;
    public java.util.List<Entry> entryList;
    private String branchId;

    public GenerateEntrys() {
      entryList = new LinkedList<Entry>();
    }
   public void setEntrys(Entry indEntry) {
        this.entryList.add(indEntry) ;
    }
    public java.util.List<Entry> getEntrys() {
        return this.entryList;
    }
    public void setAllentrys(java.util.List<Entry> entryList) {
        //this.entryList.add(indEntry) ;
        this.entryList = entryList;
    }
    public boolean validateSumofAmount() {
        //double amtSum;
        BigDecimal amtSum = new BigDecimal("0.00");
        
        amtSum.setScale(2, BigDecimal.ROUND_HALF_UP );
        BigDecimal zero = new BigDecimal("0.00");
        //BigDecimal newAmt = new BigDecimal("0.0");
        
        //amtSum = 0;
        boolean xbool;
        for (Entry b : entryList) {
          //amtSum = amtSum + b.getAmount();
            //System.out.println("b.getAmount() :: " + b.getAmount());
            BigDecimal newAmt = new BigDecimal(b.getAmount());
            newAmt = newAmt.setScale(2, BigDecimal.ROUND_HALF_UP);            
            //System.out.println("newAmt :: " + newAmt);
            amtSum = amtSum.add(newAmt);
        }
        //if (amtSum == 0)
        if (amtSum.compareTo(zero) == 0)
        {
         xbool = true;
        }
        else
        {
         xbool = false;
        }
        //System.out.println("Amount sum :: " + amtSum);
        return xbool;
       }
    
    public boolean allAccountsvalid() {
        String curracc = "";
        Integer coy=0;
        Integer br=0;
        double clearedbalance =0.0;
        ResultSet rsAcct=null; 
        ResultSet rsAccts2=null; 
        boolean xacvalid=true;
        String mySQLString="";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        for (Entry b : entryList) {
         if (xacvalid==true) 
         {   
          curracc = b.getAccountNo();
          coy = b.getCompany();
          br = b.getBranchId();
          clearedbalance =0.0;
          mySQLString = "select accountno,clearedbalance from accounts where accountno = '" + curracc + "' and active = 1 and closed = 0 and blocked = 0 and controlaccount != 1 and branch = " + br + " and companyid  = " + coy;
          System.out.println("Sql: "+mySQLString);
          try { 
             
          rsAccts2 =  dbobj.retrieveRecset(mySQLString);
          if (rsAccts2.first() == false)
          {
               xacvalid = false;
          }
          else
          {
              clearedbalance = rsAccts2.getDouble("clearedbalance");
          }
           //check for contributions and savings ac and don't allow debit balance
           mySQLString = "select accountno,limitchecktype,accountlimit from custaccountdetails where accountno = '" + curracc + "' and branchid = " + br + " and companyid  = " + coy;
            rsAcct =  dbobj.retrieveRecset(mySQLString);
             System.out.println("Check for contributions and dont allow debit balance: "+mySQLString);
           while (rsAcct.next())
           {
             if ((rsAcct.getString("limitchecktype").equalsIgnoreCase("Y")==true)) {
               if ((clearedbalance+ b.getAmount())< rsAcct.getDouble("accountlimit")) {
                   xacvalid = false;
               }
             }
           }
           
           
          }
          catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
          }
         }
        }
        
        dbobj.closecon(); 
        return xacvalid;
       }
       
      public boolean datesvalid(Txnsheader header) {
        GendataService dbobj = new GendataService();
        String mySQLString;
        String tempDate = "";
        SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");

        boolean dateisvalid = true;
        tempDate = formatter3.format(header.getPostdate());
        mySQLString = "select a.* from fiscal_period_items a inner join fiscal_period b on a.fiscal_period_id = b.id where a.fp_start <= {d '" + tempDate + "'} and a.fp_end >= {d '" + tempDate + "'} and a.year = " + header.getYear() + " and a.period_id = " + header.getPeriod() + " and b.company_id = " + header.getCompanyId() + " and b.branch_id = " + header.getBranchId() + " and a.active = 'Y'";
       System.out.println("Sql 2: "+mySQLString);
        dbobj.inimkcon();
        ResultSet curRecSet;
        curRecSet = dbobj.retrieveRecset(mySQLString);
        try {
            if (dbobj.retrieveRecset(mySQLString).first() == false) {
                dateisvalid = false;
                 System.out.println("Date is not valid ");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            dateisvalid = false;
        } finally {
            try {
                if (curRecSet != null) {
                    curRecSet.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dbobj.closecon();
        }
        return dateisvalid;
    }
}
