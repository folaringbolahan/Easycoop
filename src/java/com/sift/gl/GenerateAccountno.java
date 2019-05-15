/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author yomi
 */
public class GenerateAccountno {
    GendataService dbobj = new GendataService();
    java.sql.Connection myCon;
    Integer gAcctYear = 0;
    Integer gAcctPeriod = 0;
    Integer varSerialint;
    /**
     *
     */
    public String varSerial;
    Integer vCompany;
    Integer vBranch;
    boolean allEntrysvalid = true;
    /**class to generate the members product account number - does a validation check on validity of product code, branch and member numbers
     *
     */
    public GenerateAccountno (){
        //try {
           dbobj.inimkcon();
           //myCon = dbobj.getConnection();
           //myCon.setAutoCommit(false);
           
      /// }
      // catch ( SQLException sqlex) {
       //  System.err.println("SQLException: " + sqlex.getMessage());
       // }
    }
    //please reassign currentaccountingdetails class to variables
    /**
     *
     * @param productcode
     * @param customerno
     * @param subno
     * @param branchcode
     * @param companycode
     * @param branchid
     * @param companyid
     * @return
     */
    public String createAccountno(String productcode,String customerno,String subno, String branchcode, String companycode,Integer branchid,Integer companyid) {
       vBranch = branchid;
       vCompany = companyid;
        String newacn = "";
       if (performAllvalidations(customerno,branchcode,productcode)==true)
       {
           newacn = createAccno(productcode,customerno,subno,branchcode,companycode);
           //if allvalid
           //create account from accountstructure of product
          //save accounts in database
        }
       dbobj.closecon();
       return newacn;
    }

    /**
     *
     * @param custno
     * @param branchcode
     * @param productcode
     * @return
     */
    public boolean performAllvalidations(String custno,String branchcode,String productcode){
        boolean thisEntryvalid = false;
        boolean thisEntryvalid1 = false;
        boolean thisEntryvalid2 = false;
        boolean thisEntryvalid3 = false;
        //validate customer no
        thisEntryvalid1 = validCustomerno(custno);
        //validate branchcode and company
        thisEntryvalid2 = validBranch(branchcode);
        //validate product and product segmentcode
        thisEntryvalid3 = validProduct(productcode);
        if ((thisEntryvalid1 == false) || (thisEntryvalid2 == false) || (thisEntryvalid3 == false)) {
           allEntrysvalid = false ;
         }
       return allEntrysvalid;
    }

    /**
     *
     * @param No
     * @return
     */
    public boolean validCustomerno(String No){
        boolean found= false;
        ResultSet rsAccts=null;
        String mySQLString = "select * from member where member_no = '" + No + "' and branch_id = " + vBranch + " and company_id = " + vCompany + "";
        try {
         // System.out.println(mySQLString);
            rsAccts =  dbobj.retrieveRecset(mySQLString);
          if (rsAccts.first() == true)
           {
             found = true;
           }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         found = false;
        }
       /* catch ( Exception ex) {
         System.err.println("Exception: " + ex.getMessage());
         found = false;
        }*/
        finally {
          try{  
           if (rsAccts!=null)
           {rsAccts.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
       }
        return found;
    }
    
    /**
     *
     * @param branchcode
     * @return
     */
    public boolean validBranch(String branchcode){
        boolean found= false;
        ResultSet rsAccts=null;
        String mySQLString = "select a.branch_code,b.id from branch a inner join company b on a.company_id = b.id where a.branch_code = '" + branchcode + "' and a.company_id = " + vCompany + "";
        try {
          //System.out.println(mySQLString);
            rsAccts =  dbobj.retrieveRecset(mySQLString);
          if (rsAccts.first() == true)
           {
             found = true;
           }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         found = false;
        }
        finally {
          try{  
           if (rsAccts!=null)
           {rsAccts.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
       }
        return found;
    }
    
     /**
     *
     * @param productcode
     * @return
     */
    public boolean validProduct(String productcode){
        boolean found= false;
        ResultSet rsAccts=null;
        String mySQLString = "select a.code from products a where a.code = '" + productcode + "' and a.company_id = " + vCompany + " and a.branch_id = " + vBranch + " and segment_code != '' and segment_code is not null";
        try {
         // System.out.println(mySQLString);
            rsAccts =  dbobj.retrieveRecset(mySQLString);
          if (rsAccts.first() == true)
           {
             found = true;
           }
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
         found = false;
        }
        finally {
          try{  
           if (rsAccts!=null)
           {rsAccts.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
       }
        return found;
    }
    
    /**
     *
     * @param productcode
     * @param customerno
     * @param subno
     * @param branchcode
     * @param companycode
     * @return
     */
    public String createAccno(String productcode,String customerno,String subno, String branchcode, String companycode){
        String newaccountno = "";
        ResultSet rs=null;
        String mySQLString = "select * from products where code = '" + productcode + "' and company_id = " + vCompany + " and branch_id = " + vBranch;
        String mySQLString2;

        newaccountno = productcode.trim() + customerno.trim() + subno.trim() + branchcode.trim();
       /* try {
          rs =  dbobj.retrieveRecset(mySQLString);
          while (rs.next()) {  
              newaccountno = productcode.trim() + customerno.trim() + subno.trim() + branchcode.trim();
          }  
        }
        catch ( SQLException sqlex) {
         System.err.println("SQLException: " + sqlex.getMessage());
        
        }
         finally {
          try{  
           if (rs!=null)
           {rs.close();}
           } catch (SQLException ex) {
                ex.printStackTrace();
          } 
        }*/
        return newaccountno;
    }

}
