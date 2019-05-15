/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.Settingcoop;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface Settingcoopdao {
    public void addSetting(Settingcoop setting);
   public List<Settingcoop> listSetting();
    public boolean addOrUpdateSetting(Settingcoop setting);
    public Settingcoop getSetting(int typeid);
    
     
     
   
  
   
}
