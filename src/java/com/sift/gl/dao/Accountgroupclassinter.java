/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountgroupclassException;
import com.sift.gl.model.Accountgrpclassdetails;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountgroupclassinter {

    public void addAccountgroupclass(String description, String code) throws AccountgroupclassException;

    public void deleteAccountgroupclass(int id) throws AccountgroupclassException;

    public String updateAccountgroupclass(int  id,String description, String code) throws AccountgroupclassException ;

    public Accountgrpclassdetails retrieveAccountgroupclass(int id) throws AccountgroupclassException;
    //public List<String> getContents();
    public List<Accountgrpclassdetails> retrieveAccountgroupclasses() throws AccountgroupclassException;
   // public void remove();

}
