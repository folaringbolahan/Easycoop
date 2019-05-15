package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.AddressItems;
import com.sift.admin.dao.AddressItemsDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("addressItemsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddressItemsServiceImpl implements AddressItemsService{
	 @Autowired
	 private AddressItemsDao addressItemsDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addAddressItems(AddressItems addressItems){
		 addressItemsDao.addAddressItems(addressItems);
	 }

	 public List<AddressItems> listAddressItems(){
	  return addressItemsDao.listAddressItems();
	 }

	 public AddressItems getAddressItems(int id){
	  return addressItemsDao.getAddressItems(id);
	 }

	 public void deleteAddressItems(AddressItems addressItems){
		 addressItemsDao.deleteAddressItems(addressItems);
	 }
}