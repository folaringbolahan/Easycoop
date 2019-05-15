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

import com.sift.admin.model.AddressItems;
import com.sift.admin.service.AddressItemsService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AddressItemsBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class AddressItemsController{

@Autowired
private UserService userService;
	
 @Autowired
 private AddressItemsService addressItemsService;

@RequestMapping(value = "/saveAddrItem", method = RequestMethod.POST)
 public ModelAndView saveAddressItems(@ModelAttribute("addressItem")AddressItemsBean addressItemsBean,BindingResult result,HttpServletRequest req) {
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 addressItemsBean.setCreatedBy(addressItemsService.getAddressItems(addressItemsBean.getId()).getCreatedBy());
		 addressItemsBean.setLastModifiedBy(req.getRemoteUser());
		 addressItemsBean.setLastModificationDate(new java.util.Date());
	 }
	 
	 AddressItems addressItems = prepareModel(addressItemsBean);
	 addressItemsService.addAddressItems(addressItems);

	 return new ModelAndView("redirect:/newAddrItem.htm");
 }

 @RequestMapping(value="/addressItemsList", method = RequestMethod.GET)
 public ModelAndView listAddressItemss() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("addressItemsList",  prepareListofBean(addressItemsService.listAddressItems()));
	 return new ModelAndView("addressItemssList", model);
 }

 @RequestMapping(value = "/newAddrItem", method = RequestMethod.GET)
 public ModelAndView addAddressItems(@ModelAttribute("addressItem")AddressItemsBean addressItemsBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("addressItems",  prepareListofBean(addressItemsService.listAddressItems()));
     return new ModelAndView("addAddrItem", model);
 }

@RequestMapping(value = "/deleteAddrItem", method = RequestMethod.GET)
public ModelAndView deleteAddressItems(@ModelAttribute("addressItem")AddressItemsBean addressItemsBean,BindingResult result) {
	    addressItemsService.deleteAddressItems(prepareModel(addressItemsBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("addressItems", null);
		model.put("addressItemss",  prepareListofBean(addressItemsService.listAddressItems()));
		return new ModelAndView("addAddrItem", model);
 }

@RequestMapping(value = "/editAddrItem", method = RequestMethod.GET)
public ModelAndView editAddressItems(@ModelAttribute("addressItem")AddressItemsBean addressItemsBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("addressItem",   prepareAddressItemBean(addressItemsService.getAddressItems(addressItemsBean.getId())));
		model.put("addressItems",  prepareListofBean(addressItemsService.listAddressItems()));

		return new ModelAndView("editAddrItem", model);
 }

 private AddressItems prepareModel(AddressItemsBean addressItemsBean){
	    AddressItems addressItems = new AddressItems();

	    addressItems.setAddrFieldName(addressItemsBean.getAddrFieldName());
	    addressItems.setActive(addressItemsBean.getActive());
	    addressItems.setDeleted(addressItemsBean.getDeleted());
	    addressItems.setCreatedBy(addressItemsBean.getCreatedBy());
	    addressItems.setCreationDate(addressItemsBean.getCreationDate());
	    addressItems.setLastModifiedBy(addressItemsBean.getLastModifiedBy());
	    addressItems.setLastModificationDate(addressItemsBean.getLastModificationDate());
	    addressItems.setId(addressItemsBean.getId());

	    return addressItems;
 }

 private List<AddressItemsBean> prepareListofBean(List<AddressItems> addressItemss){
        List<AddressItemsBean> beans = null;

        if(addressItemss != null && !addressItemss.isEmpty()){
        	beans = new ArrayList<AddressItemsBean>();
        	AddressItemsBean bean = null;

        	for(AddressItems addressItems : addressItemss){
			    bean = new AddressItemsBean();

			    bean.setAddrFieldName(addressItems.getAddrFieldName());
			    bean.setId(addressItems.getId());
			    bean.setActive(addressItems.getActive());
			    bean.setDeleted(addressItems.getDeleted());
			    bean.setCreatedBy(addressItems.getCreatedBy());
			    bean.setCreationDate(addressItems.getCreationDate());
			    bean.setLastModifiedBy(addressItems.getLastModifiedBy());
			    bean.setLastModificationDate(addressItems.getLastModificationDate());

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private AddressItemsBean prepareAddressItemBean(AddressItems addressItems){
		  AddressItemsBean 	bean = new AddressItemsBean();

		  bean.setAddrFieldName(addressItems.getAddrFieldName());
		  bean.setId(addressItems.getId());
		  bean.setActive(addressItems.getActive());
		  bean.setDeleted(addressItems.getDeleted());
		  bean.setCreatedBy(addressItems.getCreatedBy());
		  bean.setCreationDate(addressItems.getCreationDate());
		  bean.setLastModifiedBy(addressItems.getLastModifiedBy());
		  bean.setLastModificationDate(addressItems.getLastModificationDate());

		  return bean;
 }
}