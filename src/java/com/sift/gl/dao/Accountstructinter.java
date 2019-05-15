/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AccountstructException;
import com.sift.gl.model.Accountstructuresdetails;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Accountstructinter {

    public void addAccountstruct(String description, String code,String delim, int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10,Integer company) throws AccountstructException;

    public void deleteAccountstruct(int groupid, Integer companyid) throws AccountstructException;

    public String updateAccountstruct(int groupid,String description,String delim, String code, int seg1, int seg2, int seg3, int seg4, int seg5,int seg6, int seg7, int seg8, int seg9, int seg10, Integer company) throws AccountstructException ;

    public Accountstructuresdetails retrieveAccountstruct(int groupid,Integer companyid) throws AccountstructException;
    //public List<String> getContents();
    public List<Accountstructuresdetails> retrieveAccountstructs(Integer companyid) throws AccountstructException;
   // public void remove();

}
