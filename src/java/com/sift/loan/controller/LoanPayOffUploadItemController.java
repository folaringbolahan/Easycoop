package com.sift.loan.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.UserService;
import com.sift.loan.service.LoanPayOffUploadItemService;
//import com.sift.loan.service.LoanPayOffUploadService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;
import com.sift.loan.bean.FileUploadBean;
import com.sift.loan.bean.LoanPayOffUploadItemsBean;
import com.sift.loan.bean.ProductBean;
import com.sift.loan.bean.RepaymentUploadItemsBean;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class LoanPayOffUploadItemController{
    private static final Logger logger = LoggerFactory
    .getLogger(LoanPayOffUploadItemController.class);

	@Autowired
	private CompanyService companyService;
	
    @Autowired
	private HelperUtility helperUTIL;
		
	@Autowired
	private LoanPayOffUploadItemService loanPayOffUploadItemService;
	
	@Autowired
	private UserService userService;
	
	BeanMapperUtility   beanMapper =new BeanMapperUtility();

	 @RequestMapping(value = "/saveLoanPayOffUploadItem", method = RequestMethod.POST)
	 public ModelAndView saveLoanPayOffUploadItem(@ModelAttribute("bulkuploaditem")LoanPayOffUploadItemsBean item,BindingResult result,HttpServletRequest req){
		 LoanPayOffUploadItems fileUploadItem = prepareModel(item);
		 //loanPayOffUploadItemService.addLoanPayOffUploadItem(fileUploadItem);
		 return new ModelAndView("redirect:/newLoanPayOffUpload.htm");
	 }
	 
	 @RequestMapping(value="/listLoanPayOffUploadItems", method = RequestMethod.GET)
	 public ModelAndView listLoanPayOffUploadItems(HttpServletRequest req){
		 Map<String ,Object> model = new HashMap<String, Object>();
		 String batchId=req.getParameter("batchId")==null?"":req.getParameter("batchId");
		 
		 model.put("loanpayoffuploaditems",  prepareListofBean(loanPayOffUploadItemService.listSuccessLoanPayOffUploadItems(batchId)));
		 		 
		 return new ModelAndView("listLoanPayOffUploadItems", model);
	 }
	
	 @RequestMapping(value = "/newLoanPayOffUploadItem", method = RequestMethod.GET)
	 public ModelAndView addLoanPayOffUploadItem(@ModelAttribute("bulkupload")FileUploadBean fileUploadBean, BindingResult result,HttpServletRequest req){
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 //model.put("bulkuoploads",  prepareListofBean(loanPayOffUploadService.listLoanPayOffUploads()));
	  	 //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	     
	  	 return new ModelAndView("newLoanPayOffUpload", model);
	}
	
	@RequestMapping(value = "/deleteLoanPayOffUploadItem", method = RequestMethod.GET)
	public ModelAndView deleteLoanPayOffUploadItem(@ModelAttribute("bulkuploaditem")LoanPayOffUploadItemsBean item,BindingResult result){
		    loanPayOffUploadItemService.deleteLoanPayOffUploadItem(prepareModel(item));
			Map<String,Object> model = new HashMap<String,Object>();
			
			model.put("bulkuploaditem",   null);
			model.put("bulkuploaditems",  prepareListofBean(loanPayOffUploadItemService.listLoanPayOffUploadItems()));
			return new ModelAndView("addLoanPayOffUploadItem", model);
	}
	
	@RequestMapping(value = "/editLoanPayOffUploadItem", method = RequestMethod.GET)
	public ModelAndView editLoanPayOffUploadItem(@ModelAttribute("bulkuploaditem")LoanPayOffUploadItemsBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bulkuploaditem", prepareLoanPayOffUploadItemBean(loanPayOffUploadItemService.getLoanPayOffUploadItem(item.getId())));
			model.put("bulkuploaditems",  prepareListofBean(loanPayOffUploadItemService.listLoanPayOffUploadItems()));
			return new ModelAndView("editLoanPayOffUploadItem", model);
	 }

	@RequestMapping(value = "/viewLoanPayOffUploadItem", method = RequestMethod.GET)
	public ModelAndView viewLoanPayOffUploadItem(@ModelAttribute("bulkuploaditem")RepaymentUploadItemsBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bulkuploaditem", prepareLoanPayOffUploadItemBean(loanPayOffUploadItemService.getLoanPayOffUploadItem(item.getId())));
			model.put("bulkuploaditems",  prepareListofBean(loanPayOffUploadItemService.listLoanPayOffUploadItems()));
			
			return new ModelAndView("viewLoanPayOffUploadItem", model);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	 private LoanPayOffUploadItems prepareModel(LoanPayOffUploadItemsBean bean){
		 	LoanPayOffUploadItems item = new LoanPayOffUploadItems();
	
		    item.setCompanyId(bean.getCompanyId());
		    item.setId(bean.getId());
		    item.setBranchId(bean.getBranchId());
		    item.setAmount(bean.getAmount());
		    item.setLoanCaseId(bean.getLoanCaseId());
		    item.setUploadedBy(bean.getUploadedBy());
		    item.setUploadedDate(bean.getUploadedDate());
		    item.setMemberNo(bean.getMemberNo());
		    item.setBatchId(bean.getBatchId());
		    item.setActive(bean.getActive());
		    item.setPenalty(bean.getPenalty());
		    item.setProcessedStatus(bean.getProcessedStatus());
    		//item.setScheduleId(bean.getScheduleId());

    		return item;
	 }
	
	 private List<LoanPayOffUploadItemsBean> prepareListofBean(List<LoanPayOffUploadItems> items){
	        List<LoanPayOffUploadItemsBean> beans = null;
	
	        if(items != null && !items.isEmpty()){
	        	beans = new ArrayList<LoanPayOffUploadItemsBean>();
	        	LoanPayOffUploadItemsBean bean = null;
	
	        	for(LoanPayOffUploadItems item : items){
	        		bean = new LoanPayOffUploadItemsBean();
	
	        		bean.setCompanyId(item.getCompanyId());
	        		bean.setId(item.getId());
	        		bean.setBranchId(item.getBranchId());
	        		bean.setAmount(item.getAmount());
	        		bean.setLoanCaseId(item.getLoanCaseId());
	        		bean.setUploadedBy(item.getUploadedBy());
	        		bean.setUploadedDate(item.getUploadedDate());
	        		bean.setMemberNo(item.getMemberNo());
	        		bean.setBatchId(item.getBatchId());
	        		bean.setActive(item.getActive());
	        		bean.setPenalty(item.getPenalty());
	        		bean.setProcessedStatus(item.getProcessedStatus());
	        		//bean.setScheduleId(item.getScheduleId());

	    		    beans.add(bean);
			   }
		    }
	
	        return beans;
	  }
	
	  private LoanPayOffUploadItemsBean prepareLoanPayOffUploadItemBean(LoanPayOffUploadItems item){
		    LoanPayOffUploadItemsBean 	bean = new LoanPayOffUploadItemsBean();
      		
    		bean.setCompanyId(item.getCompanyId());
    		bean.setId(item.getId());
    		bean.setBranchId(item.getBranchId());
    		bean.setAmount(item.getAmount());
    		bean.setLoanCaseId(item.getLoanCaseId());
    		bean.setUploadedBy(item.getUploadedBy());
    		bean.setUploadedDate(item.getUploadedDate());
    		bean.setMemberNo(item.getMemberNo());
    		bean.setBatchId(item.getBatchId());
    		bean.setActive(item.getActive());
    		bean.setPenalty(item.getPenalty());
    		bean.setProcessedStatus(item.getProcessedStatus());
    		//bean.setScheduleId(item.getScheduleId());

    		return bean;
	  }
}