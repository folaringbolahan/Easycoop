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

import com.sift.admin.model.AddressType;
import com.sift.admin.service.AddressTypeService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AddressTypeBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class AddressTypeController{

@Autowired
private UserService userService;
	
 @Autowired
 private AddressTypeService addressTypeService;

@RequestMapping(value = "/saveAddrType", method = RequestMethod.POST)
public ModelAndView saveEmployee(@ModelAttribute("addressType")AddressTypeBean addressTypeBean,BindingResult result,HttpServletRequest req) {
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 addressTypeBean.setCreatedBy(addressTypeService.getAddressType(addressTypeBean.getId()).getCreatedBy());
		 addressTypeBean.setLastModifiedBy(req.getRemoteUser());
		 addressTypeBean.setLastModificationDate(new java.util.Date());
	 }
	 
	 AddressType addressType = prepareModel(addressTypeBean);
	 addressTypeService.addAddressType(addressType);

	 return new ModelAndView("redirect:/newAddrType.htm");
 }

 @RequestMapping(value="/addressTypeList", method = RequestMethod.GET)
 public ModelAndView listAddressTypes() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("addressTypeList",  prepareListofBean(addressTypeService.listAddressType()));
	 return new ModelAndView("addressTypesList", model);
 }

 @RequestMapping(value = "/newAddrType", method = RequestMethod.GET)
 public ModelAndView addAddressType(@ModelAttribute("addressType")AddressTypeBean addressTypeBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("addressTypes",  prepareListofBean(addressTypeService.listAddressType()));
     return new ModelAndView("addAddrType", model);
 }

@RequestMapping(value = "/deleteAddrType", method = RequestMethod.GET)
public ModelAndView deleteAddressType(@ModelAttribute("addressType")AddressTypeBean addressTypeBean,BindingResult result) {
	    addressTypeService.deleteAddressType(prepareModel(addressTypeBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("addressType", null);
		model.put("addressTypes",  prepareListofBean(addressTypeService.listAddressType()));
		return new ModelAndView("addAddrType", model);
 }

@RequestMapping(value = "/editAddrType", method = RequestMethod.GET)
public ModelAndView editAddressType(@ModelAttribute("addressType")AddressTypeBean addressTypeBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("addressType", prepareAddressTypeBean(addressTypeService.getAddressType(addressTypeBean.getId())));
		model.put("addressTypes",  prepareListofBean(addressTypeService.listAddressType()));
		return new ModelAndView("editAddrType", model);
 }

 private AddressType prepareModel(AddressTypeBean addressTypeBean){
	    AddressType addressType = new AddressType();

	    addressType.setTypeName(addressTypeBean.getTypeName());
	    addressType.setActive(addressTypeBean.getActive());
	    addressType.setDeleted(addressTypeBean.getDeleted());
	    addressType.setCreatedBy(addressTypeBean.getCreatedBy());
	    addressType.setCreationDate(addressTypeBean.getCreationDate());
	    addressType.setLastModifiedBy(addressTypeBean.getLastModifiedBy());
	    addressType.setLastModificationDate(addressTypeBean.getLastModificationDate());
	    addressType.setId(addressTypeBean.getId());
	    //addressTypeBean.setId(null);

	    return addressType;
 }

 private List<AddressTypeBean> prepareListofBean(List<AddressType> addressTypes){
        List<AddressTypeBean> beans = null;

        if(addressTypes != null && !addressTypes.isEmpty()){
        	beans = new ArrayList<AddressTypeBean>();
        	AddressTypeBean bean = null;

        	for(AddressType addressType : addressTypes){
			    bean = new AddressTypeBean();

			    bean.setTypeName(addressType.getTypeName());
			    bean.setId(addressType.getId());
			    bean.setActive(addressType.getActive());
			    bean.setDeleted(addressType.getDeleted());
			    bean.setCreatedBy(addressType.getCreatedBy());
			    bean.setCreationDate(addressType.getCreationDate());
			    bean.setLastModifiedBy(addressType.getLastModifiedBy());
			    bean.setLastModificationDate(addressType.getLastModificationDate());

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private AddressTypeBean prepareAddressTypeBean(AddressType addressType){
		  AddressTypeBean 	bean = new AddressTypeBean();

		  bean.setTypeName(addressType.getTypeName());
		  bean.setId(addressType.getId());
		  bean.setActive(addressType.getActive());
		  bean.setDeleted(addressType.getDeleted());
		  bean.setCreatedBy(addressType.getCreatedBy());
		  bean.setCreationDate(addressType.getCreationDate());
		  bean.setLastModifiedBy(addressType.getLastModifiedBy());
		  bean.setLastModificationDate(addressType.getLastModificationDate());

		  return bean;
 }
}