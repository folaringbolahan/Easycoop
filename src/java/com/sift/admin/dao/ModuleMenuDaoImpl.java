package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.MemberView;
import com.sift.admin.model.ModuleMenu;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("moduleMenuDao")
@Transactional
public class ModuleMenuDaoImpl implements ModuleMenuDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addModuleMenu(ModuleMenu moduleMenu){
   sessionFactory.getCurrentSession().saveOrUpdate(moduleMenu);
 }

 @SuppressWarnings("unchecked")
 public List<ModuleMenu> listModuleMenu(){
  return (List<ModuleMenu>) sessionFactory.getCurrentSession().createCriteria(ModuleMenu.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<ModuleMenu> listModuleMenu(String accessCode){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ModuleMenu.class);
	 criteria.add(Restrictions.in("accessLevelCode",new String[] {accessCode,"AL"}));
	 
	 criteria.addOrder(Order.asc("module"));	 
	 return  criteria.list();	 
 }

 public ModuleMenu getModuleMenu(String id){
  return (ModuleMenu) sessionFactory.getCurrentSession().get(ModuleMenu.class, id);
 }

 public void deleteModuleMenu(ModuleMenu moduleMenu) {
	 sessionFactory.getCurrentSession().createQuery("delete from ModuleMenu where id= "+moduleMenu.getId()).executeUpdate();
 }
}