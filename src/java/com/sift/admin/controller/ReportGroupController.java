package com.sift.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import com.sift.admin.model.ReportGroup;
import com.sift.admin.model.User;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ReportGroupService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.ReportGroupBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class ReportGroupController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;

@Autowired
private ReportGroupService reportGroupService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveReportGroup", method = RequestMethod.POST)
public ModelAndView saveReportGroup(@ModelAttribute("reportGroup")ReportGroupBean reportGroupBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("reportGroups",  prepareListofBean(reportGroupService.listReportGroup()));
	  	 model.put("reportGroup",  reportGroupBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addReportGroup":"editReportGroup";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 ReportGroup reportGroup = prepareModel(reportGroupBean);
	 reportGroupService.addReportGroup(reportGroup);

	 return new ModelAndView("redirect:/newReportGroup.htm");
 }

 @RequestMapping(value="/reportGroups", method = RequestMethod.GET)
 public ModelAndView listReportGroups() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("reportGroups",  prepareListofBean(reportGroupService.listReportGroup()));
	 return new ModelAndView("reportGroupList", model);
 }

 @RequestMapping(value = "/newReportGroup", method = RequestMethod.GET)
 public ModelAndView addReportGroup(@ModelAttribute("reportGroup")ReportGroupBean reportGroupBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("reportGroups",  prepareListofBean(reportGroupService.listReportGroup()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     model.put("reportGroup",reportGroupBean);     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 
     return new ModelAndView("addReportGroup", model);
 }

@RequestMapping(value = "/deleteReportGroup", method = RequestMethod.GET)
public ModelAndView deleteReportGroup(@ModelAttribute("reportGroup")ReportGroupBean reportGroupBean,BindingResult result,HttpServletRequest req) {
	    reportGroupService.deleteReportGroup(prepareModel(reportGroupBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("reportGroup", null);
		model.put("reportGroups",  prepareListofBean(reportGroupService.listReportGroup()));
		return new ModelAndView("addReportGroup", model);
 }

@RequestMapping(value = "/editReportGroup", method = RequestMethod.GET)
public ModelAndView editReportGroup(@ModelAttribute("reportGroup")ReportGroupBean reportGroupBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		ReportGroupBean obj= prepareReportGroupBean(reportGroupService.getReportGroup(reportGroupBean.getId()));
		model.put("reportGroup", obj);
		model.put("reportGroups",  prepareListofBean(reportGroupService.listReportGroup()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("reportGroupgroups", beanMapper.prepareListofReportGroupGroupBean(reportGroupGroupService.listReportGroupGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editReportGroup", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private ReportGroup prepareModel(ReportGroupBean reportGroupBean){
	    ReportGroup reportGroup = new ReportGroup();

		reportGroup.setId(reportGroupBean.getId());
		reportGroup.setCode(reportGroupBean.getCode());
		reportGroup.setDescription(reportGroupBean.getDescription());
		reportGroup.setFormatclass(reportGroupBean.getFormatclass());

	    return reportGroup;
 }

 private List<ReportGroupBean> prepareListofBean(List<ReportGroup> reportGroups){
        List<ReportGroupBean> beans = null;

        if(reportGroups != null && !reportGroups.isEmpty()){
        	beans = new ArrayList<ReportGroupBean>();
        	ReportGroupBean reportGroup = null;

        	for(ReportGroup item : reportGroups){
        		reportGroup = new ReportGroupBean();

        		reportGroup.setId(item.getId());
        		reportGroup.setCode(item.getCode());
        		reportGroup.setDescription(item.getDescription());
        		reportGroup.setFormatclass(item.getFormatclass());

			    beans.add(reportGroup);
		   }
	    }

        return beans;
 }

 private ReportGroupBean prepareReportGroupBean(ReportGroup reportGroup){
		  ReportGroupBean 	bean = new ReportGroupBean();

		  bean.setId(reportGroup.getId());
		  bean.setCode(reportGroup.getCode());
		  bean.setDescription(reportGroup.getDescription());
  		  bean.setFormatclass(reportGroup.getFormatclass());

		  return bean;
 }

 public ReportGroupService getReportGroupService(){
	return reportGroupService;
 }

 public void setReportGroupService(ReportGroupService reportGroupService){
	this.reportGroupService = reportGroupService;
 }
}