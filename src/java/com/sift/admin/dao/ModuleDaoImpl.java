package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.Module;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("moduleDao")
@Transactional
public class ModuleDaoImpl implements ModuleDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addModule(Module module){
   sessionFactory.getCurrentSession().saveOrUpdate(module);
 }

 @SuppressWarnings("unchecked")
 public List<Module> listModule(){
  return (List<Module>) sessionFactory.getCurrentSession().createCriteria(Module.class).list();
 }

 public Module getModule(String id){
  return (Module) sessionFactory.getCurrentSession().get(Module.class, id);
 }

 public void deleteModule(Module module) {
	 sessionFactory.getCurrentSession().createQuery("delete from Module where id= "+module.getId()).executeUpdate();
 }
}