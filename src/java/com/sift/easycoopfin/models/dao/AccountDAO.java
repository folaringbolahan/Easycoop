/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;


import com.sift.easycoopfin.models.Custaccountdetails;
import java.util.List;
import com.sift.gl.model.Account;
import org.orm.*;

/**
 *
 * @author logzy
 */
public interface AccountDAO {

   
    
    public List<com.sift.gl.model.Account> getAllAccountsByQuery(String query);
    public Account getAccountByAccountNumber(String accountNumber, int branchId, int companyId);
    public Custaccountdetails getCustomerAccountByAccountNumber(String accountNumber, int branchId, int companyId);
}
