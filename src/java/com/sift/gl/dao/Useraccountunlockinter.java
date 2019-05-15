/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.GenericsiftException;
import com.sift.gl.model.Users;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Useraccountunlockinter {
    public void updateAccount(int id) throws GenericsiftException;
    public Users retrieveAccount(int id) throws GenericsiftException;
    public List<Users> retrieveAccounts(int branchid,int companyid) throws GenericsiftException;
    public List<Users> retrieveAccounts(int companyid) throws GenericsiftException;
    public List<Users> retrieveAccounts() throws GenericsiftException;
}
