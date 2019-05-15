package com.sift.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.InterestType;
import com.sift.admin.service.InterestTypeService;
import com.sift.admin.bean.InterestTypeBean;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class InterestTypeController{

@Autowired
private InterestTypeService interestTypeService;

@RequestMapping(value = "/saveIntType", method = RequestMethod.POST)
public ModelAndView saveInterestType(@Valid @ModelAttribute("interestT")InterestTypeBean interestTypeBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	 	 model.put("interestType",interestTypeBean);
	  	 model.put("interestTypes",  prepareListofBean(interestTypeService.listInterestTypes()));
	    
	  	 String URI=req.getParameter("ACTION_ID").equals("1")? "addIntType":"editIntType";
		 return new ModelAndView("addIntType",model);
	 }
	 
	 InterestType interestType = prepareModel(interestTypeBean);
	 interestTypeService.addInterestType(interestType);

	 return new ModelAndView("redirect:/newIntType.htm");
 }

 @RequestMapping(value="/interestTypes", method = RequestMethod.GET)
 public ModelAndView listInterestTypes() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("interestTypes",  prepareListofBean(interestTypeService.listInterestTypes()));
	 return new ModelAndView("interestTypesList", model);
 }

 @RequestMapping(value = "/newIntType", method = RequestMethod.GET)
 public ModelAndView addInterestType(@ModelAttribute("interestT")InterestTypeBean interestTypeBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("interestTypes",  prepareListofBean(interestTypeService.listInterestTypes()));
     return new ModelAndView("addIntType", model);
 }

@RequestMapping(value = "/deleteIntType", method = RequestMethod.GET)
public ModelAndView deleteInterestType(@ModelAttribute("interestT")InterestTypeBean interestTypeBean,BindingResult result) {
	    interestTypeService.deleteInterestType(prepareModel(interestTypeBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("interestType", null);
		model.put("interestTypes",  prepareListofBean(interestTypeService.listInterestTypes()));
		return new ModelAndView("addIntType", model);
 }

@RequestMapping(value = "/editIntType", method = RequestMethod.GET)
public ModelAndView editInterestType(@ModelAttribute("interestT")InterestTypeBean interestTypeBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("interestType", prepareInterestTypeBean(interestTypeService.getInterestType(interestTypeBean.getId())));
		model.put("interestTypes",  prepareListofBean(interestTypeService.listInterestTypes()));
		return new ModelAndView("editIntType", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private InterestType prepareModel(InterestTypeBean interestTypeBean){
	    InterestType interestType = new InterestType();

	    interestType.setActive(interestTypeBean.getActive());
	    interestType.setDeleted(interestTypeBean.getDeleted());
	    interestType.setTypeName(interestTypeBean.getTypeName());
	    interestType.setTypeCode(interestTypeBean.getTypeCode());
	    interestType.setCreatedBy(interestTypeBean.getCreatedBy());
	    interestType.setCreationDate(interestTypeBean.getCreationDate());
	    interestType.setLastModifiedBy(interestTypeBean.getLastModifiedBy());
	    interestType.setLastModificationDate(interestTypeBean.getLastModificationDate());
	    interestType.setId(interestTypeBean.getId());
	    //interestTypeBean.setId(null);

	    return interestType;
 }

 private List<InterestTypeBean> prepareListofBean(List<InterestType> interestTypes){
        List<InterestTypeBean> beans = null;

        if(interestTypes != null && !interestTypes.isEmpty()){
        	beans = new ArrayList<InterestTypeBean>();
        	InterestTypeBean interestType = null;

        	for(InterestType interestTypeBean : interestTypes){
        		interestType = new InterestTypeBean();

        		interestType.setId(interestTypeBean.getId());
			    interestType.setActive(interestTypeBean.getActive());
			    interestType.setDeleted(interestTypeBean.getDeleted());
			    interestType.setTypeCode(interestTypeBean.getTypeCode());
			    interestType.setTypeName(interestTypeBean.getTypeName());
			    interestType.setCreatedBy(interestTypeBean.getCreatedBy());
			    interestType.setCreationDate(interestTypeBean.getCreationDate());
			    interestType.setLastModifiedBy(interestTypeBean.getLastModifiedBy());
			    interestType.setLastModificationDate(interestTypeBean.getLastModificationDate());

			    beans.add(interestType);
		   }
	    }

        return beans;
 }

 private InterestTypeBean prepareInterestTypeBean(InterestType interestType){
		  InterestTypeBean 	bean = new InterestTypeBean();

		  bean.setId(interestType.getId());
		  bean.setActive(interestType.getActive());
		  bean.setDeleted(interestType.getDeleted());
		  bean.setTypeCode(interestType.getTypeCode());
		  bean.setTypeName(interestType.getTypeName());
		  bean.setCreatedBy(interestType.getCreatedBy());
		  bean.setCreationDate(interestType.getCreationDate());
		  bean.setLastModifiedBy(interestType.getLastModifiedBy());
		  bean.setLastModificationDate(interestType.getLastModificationDate());

		  return bean;
 }
}