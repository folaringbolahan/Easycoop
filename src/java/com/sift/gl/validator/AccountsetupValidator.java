/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccountsetupValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
    /**
     *
     * @param clazz
     * @return
     */
    public boolean supports(Class clazz) {
    return Account.class.isAssignableFrom(clazz);
  }  
    /**
     *
     * @param coyid
     */
    public void setCoyid(Integer coyid){
      this.companyid = coyid;
  }
    /**
     *
     * @param wkmode
     */
    public void setMode(String wkmode){
      this.wkmode = wkmode;
  }
    /**
     *
     * @param target
     * @param errors
     */
    public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmpty(errors, "name","required.name", "Name is required.");
     if (wkmode.equals("E")==false) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNo","required.accountNo", "Account No is required.");
      ValidationUtils.rejectIfEmpty(errors, "acStruct","required.acStruct", "Account Structure is required.");
     }
     ValidationUtils.rejectIfEmpty(errors, "acGroup","required.acGroup", "Account Group is required.");
     Account toaddaccount = (Account) target;
     //String accountgroupclass = toaddaccount.getAcGroup();
     String accountno = toaddaccount.getAccountNo();
     String accountstruct = toaddaccount.getAcStruct();
     String accountname = toaddaccount.getName();
     String dateop = toaddaccount.getDateOpenedstr();
     boolean cntrl = toaddaccount.getControlAccount();
     //String cntrlaccountno = toaddaccount.getControlAccountno();
     String cntrlaccountno = "";
     int accountgroupid = 0;
     try {
      accountgroupid = toaddaccount.getAcGroup();
     }
     catch (NullPointerException npex) {
         accountgroupid = 0;
     }
     if ((accountgroupid == 0) ) {
         errors.rejectValue("acGroup","accountdet.acGroup" ,"Invalid Group Id;Enter appropriate Account Group.");
      }
     
      if (accountname.length() > 50) {
         errors.rejectValue("name","accountdet.name", "Invalid Name;length must be less than or equal to 50.");
      }
      
      if ((cntrlaccountno.length() > 0) && (cntrl == true) ) {
         errors.rejectValue("controlAccountno","accountdet.controlAccountno" ,"This Account cannot be a sub account to this control account;Unselect the account below or uncheck the control account option");
         errors.rejectValue("controlAccount","accountdet.controlAccount" ,"Invalid control account option selected;Unselect this account or uncheck the control account option above");
      }
      
      if (getAcSeg(accountno,accountstruct, companyid) != accountno.length()) {
         errors.rejectValue("accountNo","accountdet.accountNo", "Invalid Account no.;length must be same as the selected account structure");
       }
      if (wkmode.equals("E")==false) {
       
       
       SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("dateOpenedstr","accountdet.dateOpenedstr", "Invalid Date.;format must be dd/MM/yyyy");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("dateOpenedstr","accountdet.dateOpenedstr", "Invalid Date.;format must be dd/MM/yyyy");
        }
      }
       
      
  }
  
  
    /**
     *
     * @param vAcno
     * @param vAcStructcode
     * @param companyid
     * @return
     */
    public int getAcSeg(String vAcno,String vAcStructcode,int companyid) {
      String mySQLString;
      mySQLString = "select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg1 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg2 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg3 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg4 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg5 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg6 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg7 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg8 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg9 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg10 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid   ;
      ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      System.out.println(mySQLString);
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSeg = "";
      int asum=0;
      int i = vAcno.trim().length();
      int j= 0;
      int indseg;
      int delimlen = 0;
      java.util.List AcsegL =  new ArrayList();
      try {
       while (agRecSet.next()) {
        asum= agRecSet.getInt("length");
        delimlen = 0;
        if ((agRecSet.getString("delimiter")!=null)&& (agRecSet.getString("delimiter").isEmpty()==false)) {
           delimlen = agRecSet.getString("delimiter").trim().length();
           asum=asum+delimlen;
        }
        //AcsegL.add(vAcno.substring(j, j+(asum)));
        j=j+asum;
       }
       j = j - delimlen;
       } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      } 
        catch (NullPointerException nex) {
          System.out.println(nex.getMessage());
      }  
       finally {
          try{  
           if (agRecSet!=null)
           {agRecSet.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          if (dbobj != null) {
           dbobj.closecon();
          }
        } 
       // System.out.println("the acno len " + j);
        return j;
    }
}
