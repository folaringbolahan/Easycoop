/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgrouprepException;
import com.sift.gl.model.Accountgrprepdetails;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountgrouprepinter {

    public void addAccountgrouprep(String description, String code) throws AccountgrouprepException;

    public void deleteAccountgrouprep(int id) throws AccountgrouprepException;

    public String updateAccountgrouprep(int  id,String description, String code) throws AccountgrouprepException ;

    public Accountgrprepdetails retrieveAccountgrouprep(int id) throws AccountgrouprepException;
    //public List<String> getContents();
    public List<Accountgrprepdetails> retrieveAccountgroupreps() throws AccountgrouprepException;
   // public void remove();

}
