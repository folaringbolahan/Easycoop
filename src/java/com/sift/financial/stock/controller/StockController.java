package com.sift.financial.stock.controller;

import com.sift.financial.member.Company;
import com.sift.financial.member.Event;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;


import com.sift.financial.member.*;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.stock.services.StockManageImpl;
import com.sift.financial.utility.CompStockTypeBean;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("CompStockType")
public class StockController {
	
 private static final Log log = LogFactory.getLog(StockController.class);

 @Autowired
 private StockManageImpl stockService;

 @Resource (name="stckEvents")
 private Map<String, String> stckEvents;
 
@Autowired
@Value("${stockStart}")
private String startPos; 
 
@Autowired
@Value("${newStockStat}")
private String newPos; 

@Autowired
@Value("${apprvdStockStat}")
private String apprPos; 

@Autowired
@Value("${defTrdProdType}")
private String defProdType;

@Autowired
@Value("${updStockStat}")
private String updPos;
 
@Autowired
@Qualifier("stockValidator")
private Validator validator; 

 @Autowired
private ConversionService conversionService;
 
@Autowired
private CurrentuserdisplayImpl user;
 
 @RequestMapping(value="/stock/saveStock", method = RequestMethod.POST)
 //public View saveStock(@ModelAttribute("CompStockType") @Validated CompStockType compStockType,BindingResult result, SessionStatus status,HttpServletRequest req) 
 public ModelAndView saveStock(@ModelAttribute("CompStockType") @Validated CompStockType compStockType,BindingResult result, SessionStatus status,HttpServletRequest req)   
 {
	 
	   Map<String, String> theResult = new HashMap();
    	   System.out.println("Test Print: " + compStockType.getShortName() );

	   String companyId=user.getCurrusercompany().getId().toString();
  
	   String username= user.getCurruser().getUserName();
	   
	   Company company = getStockService().getCompanyDAO().findById(Integer.valueOf(companyId));
	   compStockType.setCompany(company);
           
	   System.out.println("Test Print: " + compStockType.getCompany().getShortName() );
           
                if (result.hasErrors()) {
    
                    return   new ModelAndView("/stocks/addStocks", "CompStockType", compStockType);
                    //return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
                }

                
	     if(compStockType.getAction().equals("ADD"))
	     {
	    	 String curEventShort = getStckEvents().get("ADD");
	    	 stockService.setDefProdType(defProdType);
                 stockService.setReqInfo(userInfo());
                 stockService.setUser(user);
                 
	    	 Event curEvent = (Event)stockService.getEventDAO().findByEventShort(curEventShort).get(0);
	    	 Status initStatus = stockService.getStatusDAO().getStatusFromShort(startPos) ;
	    	 
	    	 StatusFlow statusFlow = stockService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
	    	 
	    	 boolean Ok = stockService.addDetail(compStockType, statusFlow, req);
	    	 
	    	 if(Ok)
	    	 {
	    		 theResult.put("message", "Stock successfully added");
	    	 }
	    	 else
	    	 {
	    		 theResult.put("message", "Stock registration failed due to " + stockService.getMsg());
	    	 }
	    	 
	     }
             else if(compStockType.getAction().equals("EDIT"))
             {
                 System.out.println("Here Out in Edit");
                  
                 String curEventShort = getStckEvents().get("EDIT");
	    	 stockService.setDefProdType(defProdType);
                 stockService.setReqInfo(userInfo());
                 
                 Event curEvent = (Event)stockService.getEventDAO().findByEventShort(curEventShort).get(0);
	    	 Status editStatus = stockService.getStatusDAO().getStatusFromShort(compStockType.getStatus().getStatusShort()) ;
	    	 
	    	 StatusFlow statusFlow = stockService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, editStatus);
                 
                System.out.println("Here Before of Service");
                 boolean Ok = stockService.editDetail(compStockType, statusFlow, req);
	    	 System.out.println("Here Out of Service");
	    	 if(Ok)
	    	 {
	    		 theResult.put("message", "Stock successfully updated");
	    	 }
	    	 else
	    	 {
	    		 theResult.put("message", "Stock Update failed due to " + stockService.getMsg());
	    	 }
             }
	
	    // return new ModelAndView ("result", theResult) ;
             return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
   }
 
   @RequestMapping(value="/stock/apprvStock", method = RequestMethod.POST)
   public ModelAndView approveStock(@ModelAttribute("CompStockType") @Validated CompStockType compStockType,BindingResult result, SessionStatus status,HttpServletRequest req)   
    {
     
		Map<String, String> theResult = new HashMap();
		
		boolean success= false;
		
		System.out.println("req.action ::: " + req.getParameter("action"));
		
		if(compStockType.getCompStockTypeId()!=null)
		{
			compStockType = stockService.getCompStockTypeDAO().findById(compStockType.getCompStockTypeId());
		
			 stockService.setDefProdType(defProdType);
                         stockService.setReqInfo(userInfo());
                         System.out.println("action .....::" +  req.getParameter("action"));
                         compStockType.setAction(req.getParameter("action"));
                         
		 String curEventShort = getStckEvents().get("APPROVE");
		 Event curEvent = (Event)stockService.getEventDAO().findByEventShort(curEventShort).get(0);
		 System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
		 StatusFlow statusFlow = stockService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, compStockType.getStatus());
		 success = stockService.apprDetail(compStockType, statusFlow,req);
		 
			 if(success)
			 {
				if(compStockType.getAction().equals(ActivityInterface.ACTIONREJECT))
				{
					theResult.put("message", "Stock Record successfully rejected");
				}
				else if(compStockType.getAction().equals(ActivityInterface.ACTIONAPPROVE))
				{
					theResult.put("message", "Stock record successfully approved");
				}
			 }
			 else
			 {
				 
				 theResult.put("message", stockService.getMsg());
                         }
		status.setComplete();
	
		 return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
		}
		else
		{
		  return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed the page properly");
		}
	}
   
   
   
   @RequestMapping(value="/stock/bulkApprv", method = RequestMethod.POST)
   public ModelAndView approveBulkStock(@ModelAttribute("CompStockType") @Validated CompStockType compStockType,BindingResult result, SessionStatus status,HttpServletRequest req)   
    {
     
		Map<String, String> theResult = new HashMap();
		
		boolean success= false;
		
		System.out.println("req.action ::: " + req.getParameter("action"));
		
		if(compStockType.getCompStockTypeId()!=null)
		{
			compStockType = stockService.getCompStockTypeDAO().findById(compStockType.getCompStockTypeId());
		
			 stockService.setDefProdType(defProdType);
                         stockService.setReqInfo(userInfo());			
                         compStockType.setAction(req.getParameter("action"));
                         
		 String curEventShort = getStckEvents().get("APPROVE");
		 Event curEvent = (Event)stockService.getEventDAO().findByEventShort(curEventShort).get(0);
		 System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
		 StatusFlow statusFlow = stockService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, compStockType.getStatus());
		 success = stockService.apprDetail(compStockType, statusFlow,req);
		 
			 if(success)
			 {
				if(compStockType.getAction().equals(ActivityInterface.ACTIONREJECT))
				{
					theResult.put("message", "Stock Record successfully rejected");
				}
				else if(compStockType.getAction().equals(ActivityInterface.ACTIONAPPROVE))
				{
					theResult.put("message", "Stock record successfully approved");
				}
			 }
			 else
			 {
				 
				 theResult.put("message", stockService.getMsg());
                         }
		status.setComplete();
	
		 return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
		}
		else
		{
		  return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed the page properly");
		}
	}
	
 @RequestMapping(value = "/stock/listStock", method = RequestMethod.GET)
//public ModelAndView listCompanyStock(@ModelAttribute("CompStockType") CompStockType stockType, BindingResult result,HttpServletRequest req)
 public ModelAndView listCompanyStock(HttpServletRequest req, HttpServletResponse response)
 {
		Map<String, Object> model = new HashMap<String, Object>(); 
		model.put("listStocks", listStocks(newPos));  
                //return new ModelAndView("listStock", model);
                return new ModelAndView("/stocks/listCompanyStocks", model);
		
}
 
 @RequestMapping(value = "/stock/pendingStocks", method = RequestMethod.GET)
//public ModelAndView listCompanyStock(@ModelAttribute("CompStockType") CompStockType stockType, BindingResult result,HttpServletRequest req)
 public ModelAndView pendingCompanyStock(HttpServletRequest req, HttpServletResponse response)
 {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("listStocks",listStocks(newPos));  
                return new ModelAndView("/stocks/pendingStocks", model);
}
 
 @RequestMapping(value = "/stock/approvedStocks", method = RequestMethod.GET)
//public ModelAndView listCompanyStock(@ModelAttribute("CompStockType") CompStockType stockType, BindingResult result,HttpServletRequest req)
 public ModelAndView approvedCompanyStock(HttpServletRequest req, HttpServletResponse response)
 {
		Map<String, Object> model = new HashMap<String, Object>();	 
		//model.put("listStocks", listStocks(apprPos));  
                model.put("listStocks", listStocks2(apprPos));                
                return new ModelAndView("/stocks/approvedStocks", model);	
}
 
  @RequestMapping(value = "/stock/updatedStocks", method = RequestMethod.GET)
    public ModelAndView updatedCompanyStock(HttpServletRequest req, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("listStocks", listStocks(updPos));
        return new ModelAndView("/stocks/updatedStocks", model);
  }
  
  
 @RequestMapping(value = "/stock/viewStockInfo", method = RequestMethod.GET)
 public String viewStock(ModelMap model,HttpServletRequest req)
 {
		CompStockType stockObj= null;
		//Date today = new Date();
		String stockTypeId = req.getParameter("stkId");
                
               
		if(stockTypeId!=null)
		{
                    stockObj= new CompStockType();
                    
                    stockObj.setCompStockTypeId(Integer.valueOf(stockTypeId));
                    
			log.info("getting the stockType here:" +  stockTypeId);
                        getStockService().setReqInfo(userInfo());
			//stockObj = getStockService().getCompStockTypeDAO().findByIdAndCompany(Integer.valueOf(stockTypeId), companyId);
                        stockObj = (CompStockType)getStockService().readDetail(stockObj);
                        
			System.out.println("stockObj.getCompStockTypeDetails().size() " + stockObj.getCompStockTypeDetails().size());
			 
                       	stockObj.setAction("VIEW");
                        
                        model.put("CompStockType", stockObj);
                        
                        return "/stocks/viewStock";
		}
                else
                {
                    return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                }    
}
 
 
    @RequestMapping(value = "/stock/apprvViewStock", method = RequestMethod.GET)
    public String apprViewStock(ModelMap model,HttpServletRequest req)
    {
		CompStockType stockObj= null;
		//Date today = new Date();
		String stockTypeId = req.getParameter("stkId");
                
		if(stockTypeId!=null)
		{
                    stockObj= new CompStockType();
                    
                    stockObj.setCompStockTypeId(Integer.valueOf(stockTypeId));
                    
			log.info("getting the stockType here:" +  stockTypeId);
                        getStockService().setReqInfo(userInfo());
			//stockObj = getStockService().getCompStockTypeDAO().findByIdAndCompany(Integer.valueOf(stockTypeId), companyId);
                        try
                        {
                            stockObj = (CompStockType)getStockService().readDetail(stockObj);
                        
                            System.out.println("stockObj.getCompStockTypeDetails().size() " + stockObj.getCompStockTypeDetails().size());
			 
                            stockObj.setAction("VIEW");
                        
                            model.put("CompStockType", stockObj);
                        
                            return "/stocks/verifyStock";
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                           return "redirect:/finResult/result.htm?textMsg= Issues encountered, kindly try again";
                        }
                        
			
		}
                else
                {
                    return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                }
                
    }
 
@RequestMapping(value="/stock/update", method = RequestMethod.GET)
 //public String editStock(ModelMap model, HttpServletRequest req)
 public ModelAndView editMember(@ModelAttribute("CompStockType") CompStockType compStockType, HttpServletRequest req)
      {
	 	CompStockType stockObj = null;
                //Date today = new Date();
                boolean isStockEditable = false;
                Map<String, String> theResult = new HashMap();
                Map model = new HashMap();
	
		String stockTypeId = req.getParameter("stkId");
		
		//stockObj.setAction("EDIT");
		
		if (stockTypeId != null) {
            stockObj = new CompStockType();
            stockObj.setCompStockTypeId(Integer.valueOf(stockTypeId));

            getStockService().setReqInfo(userInfo());
            log.info("getting the stockType here:" + stockTypeId);
            //stockObj = getStockService().getCompStockTypeDAO().findById(Integer.valueOf(stockTypeId));

            stockObj = (CompStockType) getStockService().readDetail(stockObj);

            isStockEditable = getStockService().getCompStockTypeDAO().canEditStock(stockObj.getCompany().getId(), stockObj.getCompStockTypeId());
            System.out.println("isStockEditable :: " + isStockEditable);

            for (Object detailObj : stockObj.getCompStockTypeDetails()) {
                CompStockTypeDetail detail = (CompStockTypeDetail) detailObj;
                //System.out.println("Name :: " + detail.getCompStockProperty().getStockPptyName());
                // System.out.println("Value :: " + detail.getCompStockPptyVal());
            }
            stockObj.setAction("EDIT");
            stockObj.setModifiedBy(user.getCurruser().getUserId());

            /*
                    if (isStockEditable){
                        //System.out.println("stockObj.getCompStockTypeDetails().size() " + stockObj.getCompStockTypeDetails().size());
			 
                        for(Object detailObj :stockObj.getCompStockTypeDetails())
                         {
                             CompStockTypeDetail detail = (CompStockTypeDetail)detailObj;
                             //System.out.println("Name :: " + detail.getCompStockProperty().getStockPptyName());
                            // System.out.println("Value :: " + detail.getCompStockPptyVal());
                         }
			stockObj.setAction("EDIT");
                        stockObj.setModifiedBy(user.getCurruser().getUserId());
                       return   new ModelAndView("/stocks/addStocks", "CompStockType", stockObj);
                    }else {
                       // System.out.println("Unable to edit stock with id :: " + stockObj.getCompStockTypeId());
				 String msg = "This shares can not be edited :: " + stockObj.getCompStockName();
				 theResult.put("message", msg);
                                 return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
                    }
             */
        }

        //model.put("stockObj", stockObj);
        // model.put("isStockEditable", isStockEditable);
        return new ModelAndView("/stocks/addStocks", "CompStockType", stockObj);
        //return   new ModelAndView("/stocks/addStocks", "CompStockType", model);
	}
 
 
        @RequestMapping(value="/stock/new", method = RequestMethod.GET)
	public ModelAndView addStock(@ModelAttribute("CompStockType") CompStockType compStockType, HttpServletRequest req){
	
	   Date today = new Date("yyyy-mm-dd");	   
           String companyId =user.getCurruser().getCompanyid().toString();	   
          String username= user.getCurruser().getUserId();	   
	   Company company = getStockService().getCompanyDAO().findById(Integer.valueOf(companyId));	   
	   compStockType = new CompStockType();
	   compStockType.setCreatedBy(username);
	   compStockType.setCreatedDate(today);
	   compStockType.setAction("ADD");
	   compStockType.setCompany(company);			
	return new ModelAndView("/stocks/addStocks", "CompStockType", compStockType);
	}
 
        
  /**
     * To delete stock
     *
     * @param compStockType
     * @param req
     * @return
     */
    @RequestMapping(value = "/stock/delete", method = RequestMethod.GET)
    public ModelAndView deleteStock(@ModelAttribute("CompStockType") CompStockType compStockType, HttpServletRequest req) {
        CompStockType stockObj = null;
        Map<String, String> theResult = new HashMap();

        boolean success = false;
        boolean canDeleteShares = false;

        String stockTypeId = req.getParameter("stkId");

        //stockObj.setAction("EDIT");
        if (stockTypeId != null) {
            // Check is the stock hasn't been used before. If it has be used, then its can't be deleted. 
            // canDeleteShares = 

            stockObj = new CompStockType();
            stockObj.setCompStockTypeId(Integer.valueOf(stockTypeId));

            getStockService().setReqInfo(userInfo());
            log.info("getting the stockType here:" + stockTypeId);
            //stockObj = getStockService().getCompStockTypeDAO().findById(Integer.valueOf(stockTypeId));

            stockObj = (CompStockType) getStockService().readDetail(stockObj);

            canDeleteShares = getStockService().getCompStockTypeDAO().canEditStock(stockObj.getCompany().getId(), stockObj.getCompStockTypeId());

            if (canDeleteShares) {
                //compStockType.setAction(req.getParameter("action"));
                stockObj.setAction("DELETE");
                stockObj.setModifiedBy(user.getCurruser().getUserId());

                // first check if the stock can be delete i.e if the stock hasn't been used before
                String curEventShort = getStckEvents().get("DELETE");
                Event curEvent = (Event) stockService.getEventDAO().findByEventShort(curEventShort).get(0);
                System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
                StatusFlow statusFlow = stockService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, stockObj.getStatus());

                // delete stock. This changes the status of the stock to delete.
                success = stockService.editDetail(stockObj, statusFlow, req);
                if (success) {
                    System.out.println("Success :: " + success);
                   // if (compStockType.getAction().equals(ActivityInterface.ACTIONDELETE)) {
                        theResult.put("message", "Stock Record successfully deleted");
                  //  }

                } else {
                    System.out.println("Unable to delete stock with id :: " + stockObj.getCompStockTypeId());

                    theResult.put("message", stockService.getMsg());
                }

            } else {
                //return new ModelAndView("redirect:/finResult/result.htm?textMsg=You can't delete this shares");

                String msg = "This shares can not be deleted :: " + stockObj.getCompStockName();
                theResult.put("message", msg);
                return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
            }

            return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));

        }

        return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed the page properly");
        //return   new ModelAndView("/stocks/addStocks", "CompStockType", stockObj);
    }      
        
 /* @InitBinder
 public void initBinder(WebDataBinder binder){
	
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
  dateFormat.setLenient(false);
  binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  binder.registerCustomEditor(Company.class, new CompanyEditor(getStockService()));
  
 binder.registerCustomEditor(IdentificationDoc.class, new IdentificationDocEditor(getMemberService()));
  binder.registerCustomEditor(Company.class, new CompanyEditor(getMemberService()));
  binder.registerCustomEditor(Branch.class, new BranchEditor(getMemberService()));
  binder.registerCustomEditor(MemberType.class, new MemberTypeEditor(getMemberService()));
  binder.registerCustomEditor(Religion.class, new ReligionEditor(getMemberService()));
}
 */
 
    @InitBinder
    public void registerConversionServices(WebDataBinder dataBinder) {
         //dataBinder.setConversionService(conversionService);
         dataBinder.setValidator(validator);
   
    }
 
        @ModelAttribute("referenceList")
	public Map<String, Object> populateList(HttpServletRequest request,HttpServletResponse response) 
	{
		String companyId=user.getCurrusercompany().getId().toString();
                String branchRef = user.getCurruser().getBranch().toString();
                
		Map<String,Object> referenceList = new HashMap<String, Object>();
		
		referenceList.put("stockPptyList", stockService.getCompStockPropertyDAO().findByProperty("delFlg", "N"));
		referenceList.put("operandList", stockService.getGenericConfigDAO().getFormulaOperand("STOCK"));
		referenceList.put("operatorList", stockService.getGenericConfigDAO().getFormulaOperator());
		referenceList.put("productList", stockService.getGenericConfigDAO().getBranchTradingProducts(companyId, branchRef, defProdType));
                referenceList.put("controlAcctList", stockService.getGenericConfigDAO().getControlAccounts(companyId, branchRef));
		referenceList.put("nonControlAcctList", stockService.getGenericConfigDAO().getGlAccounts(companyId, branchRef));
		return referenceList;
	}
        
        
        private List listStocks(String statusShort)
        {
            getStockService().setReqInfo(userInfo());
            String companyId=user.getCurruser().getCompanyid().toString();
            List<Object[]> requests= stockService.getCompStockTypeDAO().getStockListByCompany(companyId, statusShort);
            return requests;
        }
        
        private List listStocks2(String statusShort) {
        getStockService().setReqInfo(userInfo());
        String companyId = user.getCurruser().getCompanyid().toString();
        List<Object[]> requests = stockService.getCompStockTypeDAO().getStockListByCompany(companyId, statusShort);
       
        CompStockTypeBean cstBean = null;
        List<CompStockTypeBean> cmstBeanList = null;
       

        Iterator<Object[]> results = requests.iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (cmstBeanList == null) {
                cmstBeanList = new ArrayList<CompStockTypeBean>();
            }
            cstBean = new CompStockTypeBean();

            Object[] row = results.next();

            cstBean.setCompStockTypeId((Integer) row[i++]);
            cstBean.setCompanyName((String) row[i++]);
            cstBean.setCompStockName((String) row[i++]);
            cstBean.setCreatedDate((Date) row[i++]);
            cstBean.setCreatedBy((String) row[i++]);   
            cstBean.setIsUsedByMember(getStockService().getCompStockTypeDAO().canEditStock(user.getCurruser().getCompanyid(), cstBean.getCompStockTypeId())); 
           

            cmstBeanList.add(cstBean);
        }

        return cmstBeanList;
       }
        
        private Map<String, Object> userInfo()
        {
            Map<String, Object> reqInfo = new HashMap<String, Object>();
        
           reqInfo.put(ActivityInterface.REQINFOID, user.getCurruser().getUserId());
           reqInfo.put(ActivityInterface.REQINFOCOMP, user.getCurruser().getCompanyid());
           reqInfo.put(ActivityInterface.REQINFOBRAN, user.getCurruser().getBranch());
           reqInfo.put(ActivityInterface.REQINFOUSER, user.getCurruser().getUserName());
           reqInfo.put(ActivityInterface.REQINFOSUPR, user.getCurruser().getSupervisorId());
           reqInfo.put(ActivityInterface.REQINFOCOUN, user.getCurrusercompany().getCountryId());
           reqInfo.put(ActivityInterface.REQINFOCURR, user.getCurrusercompany().getBaseCurrency());
        return reqInfo;
        }
        
   @ModelAttribute("CompStockType")
   public CompStockType populateForm() {
       return new CompStockType(); // populates form for the first time if its null
   }
 
public StockManageImpl getStockService() {
	return stockService;
}

public void setStockService(StockManageImpl stockService) {
	this.stockService = stockService;
}

public Map<String, String> getStckEvents() {
	return stckEvents;
}

public void setStckEvents(Map<String, String> stckEvents) {
	this.stckEvents = stckEvents;
}

public ConversionService getConversionService() {
	return conversionService;
}


public void setConversionService(ConversionService conversionService) {
	this.conversionService = conversionService;
}

public CurrentuserdisplayImpl getUser() {
		return user;
	}

public void setUser(CurrentuserdisplayImpl user) {

         this.user.setdCurruser(user.getCurruser());
}



}
