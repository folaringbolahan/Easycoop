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
import com.sift.loan.service.BulkUploadItemService;
import com.sift.loan.service.BulkUploadService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;
import com.sift.loan.bean.FileUploadBean;
import com.sift.loan.bean.ProductBean;
import com.sift.loan.bean.RepaymentUploadItemsBean;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class BulkUploadItemController{
    private static final Logger logger = LoggerFactory
    .getLogger(BulkUploadItemController.class);

	@Autowired
	private CompanyService companyService;
	
    @Autowired
	private HelperUtility helperUTIL;
		
	@Autowired
	private BulkUploadItemService bulkUploadItemService;
	
	@Autowired
	private UserService userService;
	
	BeanMapperUtility   beanMapper =new BeanMapperUtility();

	@RequestMapping(value = "/saveBulkUploadItem", method = RequestMethod.POST)
	public ModelAndView saveBulkUploadItem(@ModelAttribute("bulkuploaditem")RepaymentUploadItemsBean item,BindingResult result,HttpServletRequest req){
		 RepaymentUploadItems fileUploadItem = prepareModel(item);
		 bulkUploadItemService.addBulkUploadItem(fileUploadItem);
		 return new ModelAndView("redirect:/newBulkUpload.htm");
	 }
	 
	 @RequestMapping(value="/listBulkUploadItems", method = RequestMethod.GET)
	 public ModelAndView listBulkUploadItems(HttpServletRequest req){
		 Map<String ,Object> model = new HashMap<String, Object>();
		 String uT=req.getParameter("uT")==null?"LP":req.getParameter("uT");
		 String batchId=req.getParameter("batchId")==null?"":req.getParameter("batchId");
		 
		 model.put("bulkuploaditems",  prepareListofBean(bulkUploadItemService.listSuccessBulkUploadItems(batchId)));
		 
		 return new ModelAndView("listBulkUploadItems", model);
	 }
	 
	 @RequestMapping(value="/listBulkUploadErrors", method = RequestMethod.GET)
	 public ModelAndView listBulkUploadErrors(HttpServletRequest req){
		 Map<String ,Object> model = new HashMap<String, Object>();
		 String uT=req.getParameter("uT")==null?"LP":req.getParameter("uT");
		 String batchId=req.getParameter("batchId")==null?"":req.getParameter("batchId");
		 
		 model.put("bulkuploaderrors",  prepareListofBean(bulkUploadItemService.listFailedBulkUploadItems(batchId)));
		 
		 return new ModelAndView("listBulkUploadErrors", model);
	 }
	
	 @RequestMapping(value = "/newBulkRepaymentUploadItem", method = RequestMethod.GET)
	 public ModelAndView addBulkUploadItem(@ModelAttribute("bulkupload")FileUploadBean fileUploadBean, BindingResult result,HttpServletRequest req){
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 //model.put("bulkuoploads",  prepareListofBean(bulkUploadService.listBulkUploads()));
	  	 //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	     
	  	 return new ModelAndView("newBulkRepaymentUpload", model);
	 }
	
	 @RequestMapping(value = "/deleteBulkUploadItem", method = RequestMethod.GET)
	public ModelAndView deleteBulkUploadItem(@ModelAttribute("bulkuploaditem")RepaymentUploadItemsBean item,BindingResult result){
		    bulkUploadItemService.deleteBulkUploadItem(prepareModel(item));
			Map<String,Object> model = new HashMap<String,Object>();
			
			model.put("bulkuploaditem",   null);
			model.put("bulkuploaditems",  prepareListofBean(bulkUploadItemService.listBulkUploadItems()));
			return new ModelAndView("addBulkUploadItem", model);
	}
	
	@RequestMapping(value = "/editBulkUploadItem", method = RequestMethod.GET)
	public ModelAndView editBulkUploadItem(@ModelAttribute("bulkuploaditem")RepaymentUploadItemsBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bulkuploaditem", prepareBulkUploadItemBean(bulkUploadItemService.getBulkUploadItem(item.getId())));
			model.put("bulkuploaditems",  prepareListofBean(bulkUploadItemService.listBulkUploadItems()));
			return new ModelAndView("editBulkUploadItem", model);
	 }

	@RequestMapping(value = "/viewBulkUploadItem", method = RequestMethod.GET)
	public ModelAndView viewBulkUploadItem(@ModelAttribute("bulkuploaditem")RepaymentUploadItemsBean item,BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bulkuploaditem", prepareBulkUploadItemBean(bulkUploadItemService.getBulkUploadItem(item.getId())));
			model.put("bulkuploaditems",  prepareListofBean(bulkUploadItemService.listBulkUploadItems()));
			
			return new ModelAndView("viewBulkUploadItem", model);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	 private RepaymentUploadItems prepareModel(RepaymentUploadItemsBean bean){
		    RepaymentUploadItems item = new RepaymentUploadItems();
	
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
    		item.setScheduleId(bean.getScheduleId());

    		return item;
	 }
	
	 private List<RepaymentUploadItemsBean> prepareListofBean(List<RepaymentUploadItems> items){
	        List<RepaymentUploadItemsBean> beans = null;
	
	        if(items != null && !items.isEmpty()){
	        	beans = new ArrayList<RepaymentUploadItemsBean>();
	        	RepaymentUploadItemsBean bean = null;
	
	        	for(RepaymentUploadItems item : items){
	        		bean = new RepaymentUploadItemsBean();
	
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
	        		bean.setScheduleId(item.getScheduleId());

	    		    beans.add(bean);
			   }
		    }
	
	        return beans;
	  }
	
	  private RepaymentUploadItemsBean prepareBulkUploadItemBean(RepaymentUploadItems item){
	        RepaymentUploadItemsBean 	bean = new RepaymentUploadItemsBean();
      		
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
    		bean.setScheduleId(item.getScheduleId());

    		return bean;
	  }
}