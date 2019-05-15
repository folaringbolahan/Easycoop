package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.AddressItems;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("addressItemsDao")
public class AddressItemsDaoImpl implements AddressItemsDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addAddressItems(AddressItems addressItems){
   sessionFactory.getCurrentSession().saveOrUpdate(addressItems);
 }

 public void updateAddressItems(AddressItems addressItems){
	   sessionFactory.getCurrentSession().update(addressItems);
 }

 @SuppressWarnings("unchecked")
 public List<AddressItems> listAddressItems(){
  return (List<AddressItems>) sessionFactory.getCurrentSession().createCriteria(AddressItems.class).list();
 }

 public AddressItems getAddressItems(int id){
  return (AddressItems) sessionFactory.getCurrentSession().get(AddressItems.class, id);
 }

 public void deleteAddressItems(AddressItems addressItems) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM ADDRESS_ITEMS WHERE id = "+addressItems.getId()).executeUpdate();
 }
}