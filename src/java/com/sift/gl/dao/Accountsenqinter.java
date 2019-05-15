/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.model.Account;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountsenqinter {

    /**
     *
     * @param branchid
     * @param companyid
     * @return
     * @throws Exception
     */
    public List<Account> retrieveAccounts(int branchid,int companyid) throws Exception;
    public List<Account> retrieveAccountswthoutcntrl(int branchid,int companyid) throws Exception;
    public List<Account> retrieveAccountsGenwthoutcntrl(int branchid,int companyid) throws Exception;
      // public void remove();

}
