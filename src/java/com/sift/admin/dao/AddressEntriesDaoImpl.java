package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.AddressEntries;
import com.sift.admin.model.Branch;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("addressEntriesDao")
public class AddressEntriesDaoImpl implements AddressEntriesDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addAddressEntries(AddressEntries addressEntries){
   sessionFactory.getCurrentSession().saveOrUpdate(addressEntries);
 }

 @SuppressWarnings("unchecked")
 public List<AddressEntries> listAddressEntries(){
  return (List<AddressEntries>) sessionFactory.getCurrentSession().createCriteria(AddressEntries.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<AddressEntries> listAddressEntries(String keyId,String typeId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AddressEntries.class);
	 criteria.add(Restrictions.eq("keyId",keyId));
	 criteria.add(Restrictions.eq("typeId",typeId));
	 
	 return  criteria.list();
     //return (List<AddressEntries>) sessionFactory.getCurrentSession().createCriteria(AddressEntries.class).list();
 }

 public AddressEntries getAddressEntries(int id){
  return (AddressEntries) sessionFactory.getCurrentSession().get(AddressEntries.class, id);
 }

 public void updateAddressEntries(AddressEntries addressEntries) {
	 sessionFactory.getCurrentSession().update(addressEntries);
 }

 public void deleteAddressEntries(AddressEntries addressEntries) {
	 sessionFactory.getCurrentSession().createQuery("delete from AddressEntries where id = "+addressEntries.getId()).executeUpdate();
 }
}