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
import com.sift.loan.service.BulkUploadErrorService;
import com.sift.loan.service.BulkUploadErrorService;
import com.sift.loan.service.BulkUploadService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;
import com.sift.loan.bean.FileUploadBean;
import com.sift.loan.bean.ProductBean;
import com.sift.loan.bean.RepaymentUploadErrorBean;
import com.sift.loan.bean.RepaymentUploadErrorBean;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class BulkUploadErrorController{
    private static final Logger logger = LoggerFactory
    .getLogger(BulkUploadErrorController.class);

	@Autowired
	private CompanyService companyService;
	
    @Autowired
	private HelperUtility helperUTIL;
		
	@Autowired
	private BulkUploadErrorService bulkUploadErrorService;
	
	@Autowired
	private UserService userService;
	
	BeanMapperUtility   beanMapper =new BeanMapperUtility();

	@RequestMapping(value = "/saveBulkUploadError", method = RequestMethod.POST)
	public ModelAndView saveBulkUploadError(@ModelAttribute("bulkuploaditem")RepaymentUploadErrorBean item,BindingResult result,HttpServletRequest req){
		 RepaymentUploadErrors fileUploadError = prepareModel(item);
		 bulkUploadErrorService.addBulkUploadError(fileUploadError);
		 return new ModelAndView("redirect:/newBulkUpload.htm");
	 }
	 
	 @RequestMapping(value="/listBulkUploadError", method = RequestMethod.GET)
	 public ModelAndView listBulkUploadError() {
		 Map<String ,Object> model = new HashMap<String, Object>();
		 model.put("uploads",  prepareListofBean(bulkUploadErrorService.listBulkUploadErrors()));
		 return new ModelAndView("listBulkUploadError", model);
	 }
	
	 @RequestMapping(value = "/newBulkRepaymentUploadError", method = RequestMethod.GET)
	 public ModelAndView addBulkUploadError(@ModelAttribute("bulkupload")FileUploadBean fileUploadBean, BindingResult result) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 //model.put("bulkuoploads",  prepareListofBean(bulkUploadService.listBulkUploads()));
	  	 //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	     
	  	 return new ModelAndView("newBulkRepaymentUpload", model);
	 }
	
	 @RequestMapping(value = "/deleteBulkUploadError", method = RequestMethod.GET)
	public ModelAndView deleteBulkUploadError(@ModelAttribute("bulkuploaderror")RepaymentUploadErrorBean item,BindingResult result) {
		    bulkUploadErrorService.deleteBulkUploadError(prepareModel(item));
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("fileUpload", null);
			model.put("fileUploads",  prepareListofBean(bulkUploadErrorService.listBulkUploadErrors()));
			return new ModelAndView("listBulkUploadError", model);
	}
	
	@RequestMapping(value = "/editBulkUploadError", method = RequestMethod.GET)
	public ModelAndView editBulkUploadError(@ModelAttribute("bulkuploaderror")RepaymentUploadErrorBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("fileUpload", prepareBulkUploadErrorBean(bulkUploadErrorService.getBulkUploadError(item.getId())));
			model.put("fileUploads",  prepareListofBean(bulkUploadErrorService.listBulkUploadErrors()));
			return new ModelAndView("editBulkUploadError", model);
	}	
	
	@RequestMapping(value = "/viewBulkUploadError", method = RequestMethod.GET)
	public ModelAndView viewBulkUploadError(@ModelAttribute("bulkuploaderror")RepaymentUploadErrorBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bulkuploaderror", prepareBulkUploadErrorBean(bulkUploadErrorService.getBulkUploadError(item.getId())));
			model.put("bulkuploaderrors",  prepareListofBean(bulkUploadErrorService.listBulkUploadErrors()));
			return new ModelAndView("viewBulkUploadErrorItem", model);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	private RepaymentUploadErrors prepareModel(RepaymentUploadErrorBean bean){
		 RepaymentUploadErrors item = new RepaymentUploadErrors();
	
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
		    item.setErrorMsg(bean.getErrorMsg());

    		return item;
	 }
	
	 private List<RepaymentUploadErrorBean> prepareListofBean(List<RepaymentUploadErrors> items){
	        List<RepaymentUploadErrorBean> beans = null;
	
	        if(items != null && !items.isEmpty()){
	        	beans = new ArrayList<RepaymentUploadErrorBean>();
	        	RepaymentUploadErrorBean bean = null;
	
	        	for(RepaymentUploadErrors item : items){
	        		bean = new RepaymentUploadErrorBean();
	
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
	        		bean.setErrorMsg(item.getErrorMsg());
	        		bean.setProcessingStatus("Failed");

		    		beans.add(bean);
			   }
		    }
	
	        return beans;
	  }
	
	  private RepaymentUploadErrorBean prepareBulkUploadErrorBean(RepaymentUploadErrors item){
		        RepaymentUploadErrorBean 	bean = new RepaymentUploadErrorBean();
	      		
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
        		bean.setErrorMsg(item.getErrorMsg());
        		bean.setProcessingStatus("Failed");
        		
	    		return bean;
	  }
}