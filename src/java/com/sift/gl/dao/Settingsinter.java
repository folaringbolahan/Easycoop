/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.SettingsException;
import com.sift.gl.model.Settingsdet;
import java.util.List;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Settingsinter {

     public String updateSetting(String dsetting, String dvalue,int branchid,int companyid) throws SettingsException ;

    public Settingsdet retrieveSetting(String dsetting,int branchid,int companyid) throws SettingsException;
    //public List<String> getContents();
    public List<Settingsdet> retrieveSettings(int branchid,int companyid) throws SettingsException;
   // public void remove();

}
