package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.TaxGroupItem;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("taxGroupItemDao")
@Transactional
public class TaxGroupItemDaoImpl implements TaxGroupItemDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addTaxGroupItem(TaxGroupItem moduleTaxId){
    sessionFactory.getCurrentSession().saveOrUpdate(moduleTaxId);
 }
 
 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String selectedoptions[]){	 
	TaxGroupItem taxGroupItem1=null;
	
	//we need to first delete current menus attached to this role
	sessionFactory.getCurrentSession().createQuery("delete from TaxGroupItem where companyId='"+taxGroupItem.getCompanyId()+"' and taxGroupId="+taxGroupItem.getTaxGroupId()+"").executeUpdate();
	
	if(selectedoptions!=null  && selectedoptions.length>0){
		for(String item: selectedoptions){
			taxGroupItem1=new TaxGroupItem();
			
			taxGroupItem1.setTaxGroupId(taxGroupItem.getTaxGroupId());
			taxGroupItem1.setCompanyId(taxGroupItem.getCompanyId());
			taxGroupItem1.setBranchId(taxGroupItem.getBranchId());
			taxGroupItem1.setTaxId(Integer.valueOf(item));
			
			sessionFactory.getCurrentSession().saveOrUpdate(taxGroupItem1);
		}
	}
 }
 
 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String options[],String reportTaxIds[]){
		TaxGroupItem taxGroupItem1=null;
		
		//we need to first delete current menus attached to this role
		sessionFactory.getCurrentSession().createQuery("delete from TaxGroupItem where companyId='"+taxGroupItem.getCompanyId()+"' and taxGroupId="+taxGroupItem.getTaxGroupId()+"").executeUpdate();
		
		if(options!=null  && options.length>0){
			for(String item: options){
				taxGroupItem1=new TaxGroupItem();
				
				taxGroupItem1.setTaxGroupId(taxGroupItem.getTaxGroupId());
				taxGroupItem1.setCompanyId(taxGroupItem.getCompanyId());
				taxGroupItem1.setBranchId(taxGroupItem.getBranchId());
				taxGroupItem1.setTaxId(Integer.valueOf(item));
				
				sessionFactory.getCurrentSession().saveOrUpdate(taxGroupItem1);
			}
		}
 }

 @SuppressWarnings("unchecked")
 public List<TaxGroupItem> listTaxGroupItem(){
  return (List<TaxGroupItem>) sessionFactory.getCurrentSession().createCriteria(TaxGroupItem.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<TaxGroupItem> listTaxGroupItem(String taxGroupId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TaxGroupItem.class);
	 criteria.add(Restrictions.eq("taxGroupId",Integer.parseInt(taxGroupId)));
	 
	 return (List<TaxGroupItem>) criteria.list();
 }

 public TaxGroupItem getTaxGroupItem(String id){
  return (TaxGroupItem) sessionFactory.getCurrentSession().get(TaxGroupItem.class, id);
 }

 public void deleteTaxGroupItem(TaxGroupItem item) {
	 sessionFactory.getCurrentSession().createQuery("delete from TaxGroupItem where id= "+item.getId()).executeUpdate();
 }
}