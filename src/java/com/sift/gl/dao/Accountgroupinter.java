/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgroupException;
import com.sift.gl.model.Accountgroupdetail;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountgroupinter {

    public void addAccountgroup(String description,int acgrpid, String classid, String reportgroup,int companyid) throws AccountgroupException;

    public void deleteAccountgroup(int groupid,int companyid) throws AccountgroupException;

    public String updateAccountgroup(int groupid,int acgrpid,String description, String classid, String reportgroup,int companyid) throws AccountgroupException ;

    public Accountgroupdetail retrieveAccountgroup(int groupid,int companyid) throws AccountgroupException;
    //public List<String> getContents();
    public List<Accountgroupdetail> retrieveAccountgroups(int companyid) throws AccountgroupException;
   // public void remove();

}
