/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;

import com.sift.admin.model.EmployeeModel;
import com.sift.admin.service.EmployeeModelService;
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

/**
 *
 * @author Gbolahan.Folarin
 */
@Controller
public class EmployeeController {
    
@Autowired
public EmployeeModelService employeeModelService;

@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
public ModelAndView saveEmployee(@ModelAttribute("employeeType")EmployeeModel employeeModel,BindingResult result,HttpServletRequest req) {
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 employeeModel.setId(employeeModelService.getEmployeeModel(employeeModel.getId()).getId());
	 }
	 
//	 EmployeeModel employeeTypeModel = employeeModel(employeeModel);
	 employeeModelService.addEmployeeModel(employeeModel);

	 return new ModelAndView("redirect:/saveEmployee.htm");
 }
        
@RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView listEmployee(@ModelAttribute("employee")EmployeeModel employeeModel,BindingResult result,HttpServletRequest req) {
   Map<String, Object> model = new HashMap<String, Object>();

     model.put("employee",  employeeModelService.listEmployeeModel());
     
      return new ModelAndView("employee", model);
      
//   ModelAndView modelAndView = new ModelAndView("employee");
//   modelAndView.addObject("employee",employeeModelService.listEmployeeModel());
//   return modelAndView;
     


}



 private EmployeeModel prepareEmployeeBean(EmployeeModel employeeModel){
		  EmployeeModel 	bean = new EmployeeModel();

		  bean.setId(employeeModel.getId());
                          		  bean.setName(employeeModel.getName());
                                                            bean.setDesignation(employeeModel.getDesignation());
                                                            bean.setSalary(employeeModel.getSalary());  

		  return bean;
 }
    
}
