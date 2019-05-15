package com.sift.admin.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.Usergrpmdl;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("usergrpmdlDao")
@Transactional
public class UsergrpmdlDaoImpl implements UsergrpmdlDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addUsergrpmdl(Usergrpmdl moduleMenu){
   sessionFactory.getCurrentSession().saveOrUpdate(moduleMenu);
 }
 
 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[]){	 
	Usergrpmdl usergrpmdl1=null;
	
	//we need to first delete current menus attached to this role
	sessionFactory.getCurrentSession().createQuery("delete from Usergrpmdl where usergroup='"+usergrpmdl.getUsergroup()+"'").executeUpdate();
	
	if(options!=null  && options.length>0){
		for(String item: options){
			usergrpmdl1=new Usergrpmdl();
			
			usergrpmdl1.setUsergroup(usergrpmdl.getUsergroup());
			usergrpmdl1.setCompanyid(usergrpmdl.getCompanyid());
			usergrpmdl1.setBranchid(usergrpmdl.getBranchid());
			usergrpmdl1.setMenu(item);
			
			//System.out.println("itemStr=:"+item);		
			sessionFactory.getCurrentSession().saveOrUpdate(usergrpmdl1);
		}
	}
 }
 
 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[],String reportMenus[]){
		Usergrpmdl usergrpmdl1=null;
		
		//we need to first delete current menus attached to this role
		sessionFactory.getCurrentSession().createQuery("delete from Usergrpmdl where branchid='"+usergrpmdl.getBranchid()+"' and companyid='"+usergrpmdl.getCompanyid()+"' and upper(usergroup)='"+usergrpmdl.getUsergroup().toUpperCase()+"'").executeUpdate();
		
		if(options!=null  && options.length>0){
			for(String item: options){
				usergrpmdl1=new Usergrpmdl();
				
				usergrpmdl1.setUsergroup(usergrpmdl.getUsergroup());
				usergrpmdl1.setCompanyid(usergrpmdl.getCompanyid());
				usergrpmdl1.setBranchid(usergrpmdl.getBranchid());
				usergrpmdl1.setMenu(item);
				
				//System.out.println("itemStr=:"+item);		
				sessionFactory.getCurrentSession().saveOrUpdate(usergrpmdl1);
			}
		}
		
		if(reportMenus!=null  && reportMenus.length>0){
			for(String elementStr: reportMenus){
				usergrpmdl1=new Usergrpmdl();
				
				usergrpmdl1.setUsergroup(usergrpmdl.getUsergroup());
				usergrpmdl1.setCompanyid(usergrpmdl.getCompanyid());
				usergrpmdl1.setBranchid(usergrpmdl.getBranchid());
				usergrpmdl1.setMenu(elementStr);
				
				//System.out.println("itemStr=:"+item);		
				sessionFactory.getCurrentSession().saveOrUpdate(usergrpmdl1);
			}
		} 		
 }

 @SuppressWarnings("unchecked")
 public List<Usergrpmdl> listUsergrpmdl(){
  return (List<Usergrpmdl>) sessionFactory.getCurrentSession().createCriteria(Usergrpmdl.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<Usergrpmdl> listUsergrpmdl(String usergroup){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usergrpmdl.class);
	 criteria.add(Restrictions.eq("usergroup",usergroup));
	 
	 return (List<Usergrpmdl>) criteria.list();
 }
 
 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usergrpmdl.class);
	 criteria.add(Restrictions.eq("companyid",companyId));
	 criteria.add(Restrictions.eq("branchid",branchId));
	 
	 return (List<Usergrpmdl>) criteria.list();
 }

 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId,String usergroup){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usergrpmdl.class);
	 criteria.add(Restrictions.eq("companyid",companyId));
	 criteria.add(Restrictions.eq("branchid",branchId));
	 criteria.add(Restrictions.eq("usergroup",usergroup));
	 
	 //criteria.addOrder(Order.asc("id"));
	 
	 return (List<Usergrpmdl>) criteria.list();
 }

 public Usergrpmdl getUsergrpmdl(String id){
     return (Usergrpmdl) sessionFactory.getCurrentSession().get(Usergrpmdl.class, id);
 }

 public void deleteUsergrpmdl(Usergrpmdl moduleMenu) {
	 sessionFactory.getCurrentSession().createQuery("delete from Usergrpmdl where id= "+moduleMenu.getId()).executeUpdate();
 }
}