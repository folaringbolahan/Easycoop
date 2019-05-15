/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yomi-pc
 */
public class Validateac4posting {
   boolean acok4posting = true;
    
    public void Validateac4postingchk(String acno,Integer coyid,Integer braid) {
      GendataService dbobj = new GendataService();
      String mySQLString = "select * from accounts where accountno = '" + acno + "' and companyid = " + coyid + " and branch = " + braid;
      ResultSet curRecSet;
      curRecSet = dbobj.retrieveRecset(mySQLString);
      try {
       while (curRecSet.next()) {
         acok4posting = true;  
         if (curRecSet.getInt("active")==0)
         {
            acok4posting = false; 
         }
         if (curRecSet.getInt("closed")==1)
         {
            acok4posting = false; 
         }
         if (curRecSet.getInt("blocked")==1)
         {
            acok4posting = false; 
         }
         if (curRecSet.getInt("controlaccount")==1)
         {
            acok4posting = false; 
         }
       }
      
      }
      catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
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
     
    public boolean acisok(){
        return acok4posting;
    }    
    /*
     
     AccountOK4Posting = False
If Not AcctRS.EOF Then
   If AcctRS!ControlAccount Then
      MsgBox ml_string(gUserlanguage, 1693, "Account is a control account. Posting not allowed."), vbExclamation, App.Title
      Exit Function
   End If
   If Not AcctRS!Active Then
      MsgBox ml_string(gUserlanguage, 1694, "Account is not active. Posting not allowed."), vbExclamation, App.Title
      Exit Function
   End If
   If AcctRS!BlockedforPostings Then
      MsgBox ml_string(gUserlanguage, 1695, "Account is blocked for postings. Posting not allowed."), vbExclamation, App.Title
      Exit Function
   End If
   If AcctRS!closed Then
      MsgBox ml_string(gUserlanguage, 1696, "Account is closed. Posting not allowed."), vbExclamation, App.Title
      Exit Function
   End If
   AccountOK4Posting = True
Else
   MsgBox ml_string(gUserlanguage, 968, "Account %0 not known. ", pAccountNo), vbExclamation, App.Title
End If
     * 
     * /
     */
}
