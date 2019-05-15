package com.sift.admin.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.bean.FiscalPeriodBean;
import com.sift.admin.model.AppConfiguration;
import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.FiscalPeriodItem;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("fiscalPeriodDao")
public class FiscalPeriodDaoImpl implements FiscalPeriodDao{

@Autowired
private SessionFactory sessionFactory;
 HelperUtility helperUTIL=new HelperUtility(); 

 public void addFiscalPeriod(FiscalPeriod fiscalPeriod){
   sessionFactory.getCurrentSession().saveOrUpdate(fiscalPeriod);
 }
 
 public boolean addNewFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
	    boolean success=false;
	    
	    try{
			 sessionFactory.getCurrentSession().save(fiscalPeriod);
			 //sessionFactory.getCurrentSession().refresh(fiscalPeriod);
			 
			 if(fiscalPeriod.getId()==null){
				 FiscalPeriod newObj=getFiscalPeriod(fiscalPeriod.getCompanyId(),fiscalPeriod.getYear());		
				 fiscalPeriod.setId(newObj.getId());
			 }			 
			 
			 deleteFiscalPeriodItemsForAyear(fiscalPeriod);
			 
			 for(FiscalPeriodItem item: fpitems){
				item.setFiscalPeriodId(fiscalPeriod.getId());
			    sessionFactory.getCurrentSession().save(item);
			 }
			 
			 success=true;
		}catch(HibernateException e){
			 e.printStackTrace();
			 success=false;
		}
		
		return success;
 }
 
 public boolean addFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
	    boolean success=false;
	 
	    try{
			 sessionFactory.getCurrentSession().saveOrUpdate(fiscalPeriod);
			 sessionFactory.getCurrentSession().refresh(fiscalPeriod);
			 
			 deleteFiscalPeriodItemsForAyear(fiscalPeriod);
			 
			 for(FiscalPeriodItem item: fpitems){
				item.setFiscalPeriodId(fiscalPeriod.getId());
			    sessionFactory.getCurrentSession().saveOrUpdate(item);
			 }
			 success=true;
		}catch(HibernateException e){
			 e.printStackTrace();
			 success=false;
		}
		
		return success;
 }
 
 public void updateFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
	    boolean success=false;
	    try{
			 sessionFactory.getCurrentSession().saveOrUpdate(fiscalPeriod);
			 
			 if(fiscalPeriod.getId()==null){
				 FiscalPeriod newObj=getFiscalPeriod(fiscalPeriod.getCompanyId(),fiscalPeriod.getYear());		
				 fiscalPeriod.setId(newObj.getId());
			 }			 
			 
			 deleteFiscalPeriodItemsForAyear(fiscalPeriod);
			 
			 for(FiscalPeriodItem item: fpitems){
				item.setFiscalPeriodId(fiscalPeriod.getId());
			    sessionFactory.getCurrentSession().saveOrUpdate(item);
			 }
		} catch(HibernateException e) {
			 e.printStackTrace();
		}
}

 @SuppressWarnings("unchecked")
 public List<FiscalPeriod> listFiscalPeriod(){
  return (List<FiscalPeriod>) sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FiscalPeriod> listFiscalPeriodsByCompany(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FiscalPeriod> listFiscalPeriodsByBranch(String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return criteria.list();
 }

 @SuppressWarnings("unchecked")
 public List<FiscalPeriodBean> listFiscalPeriodsByBranchBean(String branchId){
	 return helperUTIL.getFiscalPeriodBeanList(sessionFactory,branchId);
 }
 
 public FiscalPeriod getFiscalPeriod(int id){
   return (FiscalPeriod) sessionFactory.getCurrentSession().get(FiscalPeriod.class, id);
 }
 
 public FiscalPeriod getFiscalPeriod(String company ,int year){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class);
	 criteria.add(Restrictions.eq("companyId",company));	 
	 criteria.add(Restrictions.eq("year",year));
	 
	 return (FiscalPeriod) criteria.list().get(0);
 }

 public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod) {
	 sessionFactory.getCurrentSession().createQuery("delete from FiscalPeriod where id = "+fiscalPeriod.getId()).executeUpdate();
 }
 
 public void deleteFiscalPeriodItemsForAyear(FiscalPeriod fiscalPeriod) {
	 sessionFactory.getCurrentSession().createQuery("delete from FiscalPeriodItem where fiscalPeriodId='"+fiscalPeriod.getId()+"' and  year = "+fiscalPeriod.getYear()).executeUpdate();
 }
}