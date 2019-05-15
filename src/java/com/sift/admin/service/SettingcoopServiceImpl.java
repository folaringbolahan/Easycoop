/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.dao.Settingcoopdao;
import com.sift.admin.model.Settingcoop;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("settingcoopService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SettingcoopServiceImpl implements SettingcoopService {
@Autowired
 private Settingcoopdao settingDao;
    
     public List<Settingcoop> listSetting(){
	  return settingDao.listSetting();
	 }
     @Override
    public boolean addOrUpdateSetting(Settingcoop setting) {
        return settingDao.addOrUpdateSetting(setting);
    
}
      public Settingcoop getSetting(int id){
	  return settingDao.getSetting(id);
	 }
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addSetting(Settingcoop setting) {
		 settingDao.addSetting(setting);
	 }
    
    
}