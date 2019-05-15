package com.sift.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.AddressEntries;
import com.sift.admin.service.AddressEntriesService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AddressEntriesBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class AddressEntriesController{

@Autowired
private UserService userService;
	
@Autowired
private AddressEntriesService addressEntriesService;

@RequestMapping(value = "/saveAddrEntry", method = RequestMethod.POST)
public ModelAndView saveEmployee(@ModelAttribute("addressEntry")AddressEntriesBean addressEntriesBean,BindingResult result,HttpServletRequest req) {
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 addressEntriesBean.setCreatedBy(addressEntriesService.getAddressEntries(addressEntriesBean.getId()).getCreatedBy());
		 addressEntriesBean.setLastModifiedBy(req.getRemoteUser());
		 addressEntriesBean.setLastModificationDate(new java.util.Date());
	 }

	 AddressEntries addressEntries = prepareModel(addressEntriesBean);
	 addressEntriesService.addAddressEntries(addressEntries);

	 return new ModelAndView("redirect:/newAddrEntry.htm");
 }

 @RequestMapping(value="/addressEntriesList", method = RequestMethod.GET)
 public ModelAndView listAddressEntriess() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("addressEntriesList",  prepareListofBean(addressEntriesService.listAddressEntries()));
	 return new ModelAndView("addressEntriessList", model);
 }

 @RequestMapping(value = "/newAddrEntry", method = RequestMethod.GET)
 public ModelAndView addAddressEntries(@ModelAttribute("addressEntry")AddressEntriesBean addressEntriesBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("addressEntries",  prepareListofBean(addressEntriesService.listAddressEntries()));
     return new ModelAndView("addAddrEntry", model);
}

@RequestMapping(value = "/deleteAddrEntry", method = RequestMethod.GET)
public ModelAndView deleteAddressEntries(@ModelAttribute("addressEntry")AddressEntriesBean addressEntriesBean,BindingResult result) {
	    addressEntriesService.deleteAddressEntries(prepareModel(addressEntriesBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("addressEntries", null);
		model.put("addressEntriess",  prepareListofBean(addressEntriesService.listAddressEntries()));
		return new ModelAndView("addAddrEntry", model);
 }

@RequestMapping(value = "/editAddrEntry", method = RequestMethod.GET)
public ModelAndView editAddressEntries(@ModelAttribute("addressEntry")AddressEntriesBean addressEntriesBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("addressEntries", prepareAddressEntriesBean(addressEntriesService.getAddressEntries(addressEntriesBean.getId())));
		model.put("addressEntriess",  prepareListofBean(addressEntriesService.listAddressEntries()));
		return new ModelAndView("editAddrEntry", model);
 }

 private AddressEntries prepareModel(AddressEntriesBean addressEntriesBean){
	    AddressEntries addressEntries = new AddressEntries();

	    addressEntries.setSerialPos(addressEntriesBean.getSerialPos());
	    addressEntries.setAddrFieldValue(addressEntriesBean.getAddrFieldValue());
	    addressEntries.setAddrFieldName(addressEntriesBean.getAddrFieldName());
	    addressEntries.setActive(addressEntriesBean.getActive());
	    addressEntries.setDeleted(addressEntriesBean.getDeleted());
	    addressEntries.setCreatedBy(addressEntriesBean.getCreatedBy());
	    addressEntries.setCreationDate(addressEntriesBean.getCreationDate());
	    addressEntries.setLastModifiedBy(addressEntriesBean.getLastModifiedBy());
	    addressEntries.setLastModificationDate(addressEntriesBean.getLastModificationDate());
	    //addressEntriesBean.setId(null);

	    addressEntries.setId(addressEntriesBean.getId());

	    return addressEntries;
 }

 private List<AddressEntriesBean> prepareListofBean(List<AddressEntries> addressEntriess){
        List<AddressEntriesBean> beans = null;

        if(addressEntriess != null && !addressEntriess.isEmpty()){
        	beans = new ArrayList<AddressEntriesBean>();
        	AddressEntriesBean bean = null;

        	for(AddressEntries addressEntries : addressEntriess){
			    bean = new AddressEntriesBean();

			    bean.setSerialPos(addressEntries.getSerialPos());
			    bean.setAddrFieldName(addressEntries.getAddrFieldName());
			    bean.setAddrFieldValue(addressEntries.getAddrFieldValue());
			    bean.setId(addressEntries.getId());
			    bean.setActive(addressEntries.getActive());
			    bean.setDeleted(addressEntries.getDeleted());
			    bean.setCreatedBy(addressEntries.getCreatedBy());
			    bean.setCreationDate(addressEntries.getCreationDate());
			    bean.setLastModifiedBy(addressEntries.getLastModifiedBy());
			    bean.setLastModificationDate(addressEntries.getLastModificationDate());

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private AddressEntriesBean prepareAddressEntriesBean(AddressEntries addressEntries){
		  AddressEntriesBean 	bean = new AddressEntriesBean();

		  bean.setSerialPos(addressEntries.getSerialPos());
		  bean.setAddrFieldValue(addressEntries.getAddrFieldValue());
		  bean.setAddrFieldName(addressEntries.getAddrFieldName());
		  bean.setId(addressEntries.getId());
		  bean.setActive(addressEntries.getActive());
		  bean.setDeleted(addressEntries.getDeleted());
		  bean.setCreatedBy(addressEntries.getCreatedBy());
		  bean.setCreationDate(addressEntries.getCreationDate());
		  bean.setLastModifiedBy(addressEntries.getLastModifiedBy());
		  bean.setLastModificationDate(addressEntries.getLastModificationDate());

		  return bean;
 }
}