/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.Settingcoop;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("Settingcoopdao")
public class SettingcoopDaoImpl implements Settingcoopdao {
@Autowired
private SessionFactory sessionFactory;
    @SuppressWarnings("unchecked")
 public List<Settingcoop> listSetting(){
    return (List<Settingcoop>) sessionFactory.getCurrentSession().createCriteria(Settingcoop.class).list();
 }
     @Override
    public boolean addOrUpdateSetting(Settingcoop setting) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().saveOrUpdate(setting);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
    
     public Settingcoop getSetting(int id){
  return (Settingcoop) sessionFactory.getCurrentSession().get(Settingcoop.class, id);
 }
     
      public void addSetting(Settingcoop setting) {
   sessionFactory.getCurrentSession().saveOrUpdate(setting);
 }
     
}
 
    
   

