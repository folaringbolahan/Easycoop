/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.model.Settingcoop;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface SettingcoopService {
   public List<Settingcoop> listSetting(); 
    public boolean addOrUpdateSetting(Settingcoop setting);
    public Settingcoop getSetting(int id); 
  public void addSetting(Settingcoop setting);


  
}
