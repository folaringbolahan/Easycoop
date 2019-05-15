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
import com.sift.admin.model.Reports;
import com.sift.admin.model.User;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ReportsService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.ReportsBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class ReportsController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;

@Autowired
private ReportsService reportsService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveReports", method = RequestMethod.POST)
public ModelAndView saveReports(@ModelAttribute("reports")ReportsBean reportsBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("reportss",  prepareListofBean(reportsService.listReports()));
	  	 model.put("reports",  reportsBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addReports":"editReports";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 Reports reports = prepareModel(reportsBean);
	 reportsService.addReports(reports);

	 return new ModelAndView("redirect:/newReports.htm");
 }

 @RequestMapping(value="/reportss", method = RequestMethod.GET)
 public ModelAndView listReportss() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("reportss",  prepareListofBean(reportsService.listReports()));
	 return new ModelAndView("reportsList", model);
 }

 @RequestMapping(value = "/newReports", method = RequestMethod.GET)
 public ModelAndView addReports(@ModelAttribute("reports")ReportsBean reportsBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("reportss",  prepareListofBean(reportsService.listReports()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     model.put("reports",reportsBean);     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 //model.put("reportsgroups", beanMapper.prepareListofReportsGroupBean(reportsGroupService.listReportsGroupsByCompanyId(currentUser.getCompanyId())));
	 
     return new ModelAndView("addReports", model);
}


@RequestMapping(value = "/deleteReports", method = RequestMethod.GET)
public ModelAndView deleteReports(@ModelAttribute("reports")ReportsBean reportsBean,BindingResult result,HttpServletRequest req) {
	    reportsService.deleteReports(prepareModel(reportsBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("reports", null);
		model.put("reportss",  prepareListofBean(reportsService.listReports()));
		return new ModelAndView("addReports", model);
 }

@RequestMapping(value = "/editReports", method = RequestMethod.GET)
public ModelAndView editReports(@ModelAttribute("reports")ReportsBean reportsBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		ReportsBean obj= prepareReportsBean(reportsService.getReports(reportsBean.getReportID()));
		model.put("reports", obj);
		model.put("reportss",  prepareListofBean(reportsService.listReports()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("reportsgroups", beanMapper.prepareListofReportsGroupBean(reportsGroupService.listReportsGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editReports", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Reports prepareModel(ReportsBean reportsBean){
	    Reports reports = new Reports();

	    reports.setReportID(reportsBean.getReportID());
		reports.setReportcode(reportsBean.getReportcode());
		reports.setReportFileName(reportsBean.getReportFileName());
		reports.setDescription(reportsBean.getDescription());
		reports.setReportrole(reportsBean.getReportrole());
		reports.setRangeCriteria(reportsBean.getRangeCriteria());
		reports.setSortCode(reportsBean.getSortCode());
		reports.setType(reportsBean.getType());
		reports.setReportgroup(reportsBean.getReportgroup());

		
		return reports;
 }

 private List<ReportsBean> prepareListofBean(List<Reports> reportss){
        List<ReportsBean> beans = null;

        if(reportss != null && !reportss.isEmpty()){
        	beans = new ArrayList<ReportsBean>();
        	ReportsBean reports = null;

        	for(Reports item : reportss){
        		reports = new ReportsBean();

        	    reports.setReportID(item.getReportID());
        		reports.setReportcode(item.getReportcode());
        		reports.setReportFileName(item.getReportFileName());
        		reports.setDescription(item.getDescription());
        		reports.setReportrole(item.getReportrole());
        		reports.setRangeCriteria(item.getRangeCriteria());
        		reports.setSortCode(item.getSortCode());
        		reports.setType(item.getType());
        		reports.setReportgroup(item.getReportgroup());
        		
        		beans.add(reports);
		   }
	    }

        return beans;
 }

 private ReportsBean prepareReportsBean(Reports reports){
	    ReportsBean 	bean = new ReportsBean();

	    bean.setReportID(reports.getReportID());
	    bean.setReportcode(reports.getReportcode());
	    bean.setReportFileName(reports.getReportFileName());
	    bean.setDescription(reports.getDescription());
	    bean.setReportrole(reports.getReportrole());
		bean.setRangeCriteria(reports.getRangeCriteria());
		bean.setSortCode(reports.getSortCode());
		bean.setType(reports.getType());
		bean.setReportgroup(reports.getReportgroup());
	  
	    return bean;
 }

 public ReportsService getReportsService(){
	return reportsService;
 }

public void setReportsService(ReportsService reportsService){
	this.reportsService = reportsService;
}
}