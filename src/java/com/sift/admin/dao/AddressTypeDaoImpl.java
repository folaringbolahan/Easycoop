package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.AddressType;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("addressTypeDao")
public class AddressTypeDaoImpl implements AddressTypeDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addAddressType(AddressType addressType){
   sessionFactory.getCurrentSession().saveOrUpdate(addressType);
 }

 @SuppressWarnings("unchecked")
 public List<AddressType> listAddressType(){
  return (List<AddressType>) sessionFactory.getCurrentSession().createCriteria(AddressType.class).list();
 }

 public AddressType getAddressType(int id){
  return (AddressType) sessionFactory.getCurrentSession().get(AddressType.class, id);
 }

 public void deleteAddressType(AddressType addressType) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM ADDRESS_TYPE WHERE ID = "+addressType.getId()).executeUpdate();
 }
}