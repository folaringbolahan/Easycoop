/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.gl.model.Account;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;

/**
 *
 * @author logzy
 */
public class EasyCoopFinValidator {

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }
    public static boolean isThisDateValid(String dateToValidate, String dateFromat){
	 // System.out.println("checking for date validity:....... step1"+ dateToValidate);
		int MAX_VALID_YR = 9999;
                int MIN_VALID_YR = 1900;
                if(dateToValidate == null){
                  //   System.out.println("checking for date validity:....... step2"+ dateToValidate);
			return false;
		}
		// System.out.println("checking for date validity:....... step3"+dateToValidate);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
                     //System.out.println("checking for date validity:....... step4"+dateToValidate);
			Date date = sdf.parse(dateToValidate);
                        
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        if ((year>MAX_VALID_YR) ||(year<MIN_VALID_YR)) {
                           return false; 
                        }
                        //System.out.println("the date>>>>>>>"+date);
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
    public static boolean checkAccount(String accountNumber, int branchId, int companyId) throws PersistentException{
        boolean status = false;
       
        Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber,branchId,companyId );
        
       
        if(account==null){
           return false;
        }else{
            return true;
        }
       
                           
        
    }
    public static boolean checkIfAccountExists(String accountNumber, int branchId, int companyId) throws PersistentException{
        boolean status = false;
       
        Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber,branchId,companyId );
        
       
        if(account==null){
            status = false;
        }else{
            status = true;
        }
        return status;
                           
        
    }
   public static boolean isValidBranchAccount(String accountNumber, int branchId, int companyId){
       boolean status = false;
       Custaccountdetails account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getCustomerAccountByAccountNumber(accountNumber, branchId, companyId);
       if(account==null){
           status = false;
       }else{
           status = true;
       }
       return status;
   }
}
