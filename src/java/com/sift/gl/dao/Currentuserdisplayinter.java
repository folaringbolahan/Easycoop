/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.CurrentuserdisplayException;
import com.sift.gl.model.Company;
import com.sift.gl.model.Moduledetails;
import com.sift.gl.model.Modulemenudetails;
import com.sift.gl.model.Users;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Currentuserdisplayinter {

    public List<Moduledetails> retrieveModules(String role,Integer branch,Integer company) throws CurrentuserdisplayException;
    //public List<String> getContents();
    public List<Modulemenudetails> retrieveModulemenus(String role,Integer branch,Integer company) throws CurrentuserdisplayException;
   // public void remove();
    public Users retrieveUserdet(String userid) throws CurrentuserdisplayException;
   // public void remove();
    public Company retrieveCompanydet(Integer branch,Integer company) throws CurrentuserdisplayException;
   
}
