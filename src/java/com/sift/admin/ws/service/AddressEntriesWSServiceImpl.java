package com.sift.admin.ws.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.AddressEntries;
import com.sift.admin.model.Country;
import com.sift.admin.dao.*;
import com.sift.admin.bean.*;
import com.sift.loan.utility.BeanMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;

@Service("addressEntriesWS")
public class AddressEntriesWSServiceImpl implements AddressEntriesWSService{
	@Autowired
    private AddressEntriesDao addressEntriesDao;

	@Override public String createOrSaveNewAddressEntries(AddressEntriesBean addressEntry){
		AddressEntries addressEntries = new AddressEntries();

	    addressEntries.setSerialPos(addressEntry.getSerialPos());
	    addressEntries.setAddrFieldValue(addressEntry.getAddrFieldValue());
	    addressEntries.setAddrFieldName(addressEntry.getAddrFieldName());
	    addressEntries.setActive(addressEntry.getActive());
	    addressEntries.setDeleted(addressEntry.getDeleted());
	    addressEntries.setCreatedBy(addressEntry.getCreatedBy());
	    addressEntries.setCreationDate(new java.util.Date());
	    addressEntries.setLastModifiedBy(addressEntry.getCreatedBy());
	    addressEntries.setLastModificationDate(new java.util.Date());

		addressEntriesDao.addAddressEntries(addressEntries);
		return null;
	}

	@Override public AddressEntriesBean getAddressEntryInfo(int id){
		AddressEntries addressEntries= addressEntriesDao.getAddressEntries(id);
		AddressEntriesBean addressEntry = new AddressEntriesBean();

		addressEntry.setSerialPos(addressEntries.getSerialPos());
		addressEntry.setAddrFieldValue(addressEntries.getAddrFieldValue());
		addressEntry.setAddrFieldName(addressEntries.getAddrFieldName());
		addressEntry.setActive(addressEntries.getActive());
		addressEntry.setDeleted(addressEntries.getDeleted());
		addressEntry.setCreatedBy(addressEntries.getCreatedBy());
		addressEntry.setLastModifiedBy(addressEntries.getCreatedBy());
		addressEntry.setId(addressEntries.getId());

		return addressEntry;
	}

	@Override public String updateAddressEntryInfo(AddressEntriesBean addressEntry){
		AddressEntries addressEntries = new AddressEntries();

	    addressEntries.setSerialPos(addressEntry.getSerialPos());
	    addressEntries.setAddrFieldValue(addressEntry.getAddrFieldValue());
	    addressEntries.setAddrFieldName(addressEntry.getAddrFieldName());
	    addressEntries.setActive(addressEntry.getActive());
	    addressEntries.setDeleted(addressEntry.getDeleted());
	    addressEntries.setCreatedBy(addressEntry.getCreatedBy());
	    addressEntries.setCreationDate(new java.util.Date());
	    addressEntries.setLastModifiedBy(addressEntry.getCreatedBy());
	    addressEntries.setLastModificationDate(new java.util.Date());
	    addressEntries.setId(addressEntry.getId());

	    addressEntriesDao.addAddressEntries(addressEntries);
		return null;
    }

	@Override public String deleteAddressEntryInfo(AddressEntriesBean addressEntry){
    	AddressEntries addressEntries = new AddressEntries();
    	addressEntries.setId(addressEntry.getId());

    	addressEntriesDao.deleteAddressEntries(addressEntries);
		return null;
    }

	@Override public AddressEntriesList getAllAddressEntries(){
		BeanMapperUtility mapper=new BeanMapperUtility();
		AddressEntriesList addressEntriesObj = new AddressEntriesList();
		
		List<AddressEntriesBean> beans=new ArrayList<AddressEntriesBean>();
		List<AddressEntries> models=addressEntriesDao.listAddressEntries();		
		
		beans=mapper.prepareListofAddressEntriesBean(models);
		
		addressEntriesObj.addressEntry=beans;		
		return addressEntriesObj;
    }
}