package com.sift.financial.benefits.controller;

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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.sift.financial.Result;
import com.sift.financial.benefits.services.DividendManageImpl;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.CompStockType;
import com.sift.financial.member.CompStockTypeDetail;
import com.sift.financial.member.Dividend;
import com.sift.financial.member.Company;
import com.sift.financial.member.DividendType;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.controller.MemberController;
import com.sift.financial.utility.customutil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerMapping;


@Controller
@SessionAttributes("dividend")
public class DividendController {
	
private static final Log log = LogFactory.getLog(DividendController.class);
	
@Autowired
private DividendManageImpl dividendService;
	
@Resource (name="divEvents")
 private Map<String, String> divEvents;

@Autowired
@Value("${divStart}")
private String startPos; 

@Autowired
@Qualifier("dividendValidator")
private Validator validator; 

@Autowired
private ConversionService conversionService;

 
@Autowired
private CurrentuserdisplayImpl user;

@Resource (name="divViewData")
private Map<String, Map<String, String>> divViewData;


@RequestMapping(value="/dividend/saveDividend", method = RequestMethod.POST)

public ModelAndView saveDividendInfo(@ModelAttribute("dividend") @Validated Dividend dividend,BindingResult result, SessionStatus status,HttpServletRequest req) 
{

    Result res = new Result();
    Map<String, String> theResult = new HashMap();
    
   String companyId=user.getCurruser().getCompanyid().toString();
   String branchId=user.getCurruser().getBranch().toString();
   String countryId=user.getCurrusercompany().getCountryId();

      
   dividend.setCompanyId(Integer.parseInt(companyId));
   dividend.setBranchId(Integer.parseInt(branchId));
   dividend.setCountryId(Integer.parseInt(countryId));

   //Company company = dividendService.getCompanyDAO().findById(Integer.valueOf(companyId));

    if (result.hasErrors()) 
    {
        return   new ModelAndView("/dividend/dividend", "dividend", dividend);
        //return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
    }
           
     dividendService.setUser(user);
    if(dividend.getAction().equals("ADD"))
    {
        System.out.println(" Divident type: "  + dividend.getDividendType().getDividendTypeId());
            DividendType divType = dividendService.getDividendTypeDAO().findById(dividend.getDividendType().getDividendTypeId());
            dividend.setDividendType(divType);
            String curEventShort = divEvents.get("ADD");

            Event curEvent = (Event)dividendService.getEventDAO().findByEventShort(curEventShort).get(0);

            Status initStatus = dividendService.getStatusDAO().getStatusFromShort(startPos) ;

            StatusFlow statusFlow = dividendService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);

            dividendService.addDetail(dividend, statusFlow, req);
    }

    if(dividend.getAction().equals("EDIT"))
    {
            String curEventShort = divEvents.get("EDIT");
            System.out.println("curEventShort :::" + curEventShort);
            Event curEvent = (Event)dividendService.getEventDAO().findByEventShort(curEventShort).get(0);
            System.out.println("curEvent.getEventName :::" + curEvent.getEventName());
             System.out.println("dividend.getStatus() :::" + dividend.getStatus());
            StatusFlow statusFlow = dividendService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, dividend.getStatus());

            dividendService.editDetail(dividend, statusFlow,req);
    }

    status.setComplete();
    theResult.put("message", dividendService.getMsg());
    res.setMessage(theResult);

    return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + dividendService.getMsg());

   }



@RequestMapping(value="/dividend/new", method = RequestMethod.GET)
public ModelAndView addDividend(@ModelAttribute("dividend") Dividend dividend, HttpServletRequest req){

   //CompStockType stockObj= null;
   Date today = new Date();
   String companyId=user.getCurruser().getCompanyid().toString();
   String branchId=user.getCurruser().getBranch().toString();
   String countryId=user.getCurrusercompany().getCountryId();

   String username=user.getCurruser().getUserName();

   Company company = dividendService.getCompanyDAO().findById(Integer.valueOf(companyId));

   dividend = new Dividend();
   dividend.setCreatedBy(username);
   dividend.setAction("ADD");
   dividend.setCompanyId(Integer.parseInt(companyId));
   dividend.setBranchId(Integer.parseInt(branchId));
   dividend.setCountryId(Integer.parseInt(countryId));

        //model.addAttribute("CompStockType", stockObj);

   return   new ModelAndView("/dividend/dividend", "dividend", dividend);
}


@ModelAttribute("referenceList")
public Map<String, Object> populateList(HttpServletRequest request,HttpServletResponse response) 
{
	
    //Data referencing for web framework checkboxes
        String companyRef = null;
        String countryRef = null;
        String branchRef = null;
                
	Map<String,Object> referenceList = new HashMap<String, Object>();
        
        
        try {
			companyRef = user.getCurruser().getCompanyid().toString();
			countryRef = user.getCurrusercompany().getCountryId();
			branchRef = user.getCurruser().getBranch().toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	referenceList.put("divTypeList", dividendService.getDividendTypeDAO().findByProperty("delFlg", "N"));
	referenceList.put("operandList", dividendService.getGenericConfigDAO().getFormulaOperand("DIV"));
	referenceList.put("operatorList", dividendService.getGenericConfigDAO().getFormulaOperator());
	referenceList.put("banksList", dividendService.getBanksDAO().getBanksByCountry(countryRef));
        referenceList.put("controlAcctList", dividendService.getGenericConfigDAO().getControlAccounts(companyRef, branchRef));
	referenceList.put("nonControlAcctList", dividendService.getGenericConfigDAO().getGlAccounts(companyRef, branchRef));
        
	return referenceList;
}


@RequestMapping(value="/dividend/approveDividend", method = RequestMethod.POST)
public View approveDividendInfo(@ModelAttribute("dividend") Dividend dividend,BindingResult result, SessionStatus status, HttpServletRequest req) 
{
 
	Map<String, String> theResult = new HashMap();
	
	boolean success= false;
	
	System.out.println("req.action ::: " + req.getParameter("action"));
	
	if(dividend.getDividendId()!=null && req.getParameter("action")!=null && !req.getParameter("action").equals(""))
	{
		log.info("getting the dividend here:" +  dividend.getDividendId());
                
                dividendService.setUser(user);
                
		dividend = dividendService.getDividendDAO().findById(dividend.getDividendId());
	
		dividend.setAction(req.getParameter("action").toUpperCase());
		
		//dividendService.setApprvdStockStat(apprvdStockStat);re
                
                System.out.println("Dividend approval action::: " + req.getParameter("action"));
                
                log.info("Dividend approval action::: " + dividend.getAction());
    
                String curEventShort = getDivEvents().get(req.getParameter("action").toUpperCase());
	 
                Event curEvent = (Event)dividendService.getEventDAO().findByEventShort(curEventShort).get(0);
	 
                System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
	 
                StatusFlow statusFlow = dividendService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, dividend.getStatus());
	 
                success = dividendService.apprDetail(dividend, statusFlow,req);
	 
		 if(success)
		 {
			if(dividend.getAction().equals(ActivityInterface.ACTIONREJECT))
			{
				theResult.put("message", "Dividend record successfully rejected");
			}
			else if(dividend.getAction().equals(ActivityInterface.ACTIONAPPROVE))
			{
				theResult.put("message", "Dividend record successfully approved and will be applied shortly. Notification will be sent on completion");
			}
		 }
		 else
		 {
			 
			 theResult.put("message", dividendService.getMsg());
		 }
    	 
	status.setComplete();
	
	//return new ModelAndView ("result", theResult) ;

	 return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
	
	}
	else
	{
		//return new ModelAndView ("listPendMember") ;
	  return new RedirectView("/finResult/result.htm?textMsg=You have not accessed the page properly",true);
	}
	
}



@RequestMapping(value = "/dividend/listDividend", method = RequestMethod.GET)
public ModelAndView pendingDividendRequests(HttpServletRequest req, HttpServletResponse res){
	
	Map<String, Object> model = new HashMap<String, Object>();
	
	 //String companyId=(String)req.getSession().getAttribute("companyId");
         String companyId=user.getCurruser().getCompanyid().toString();
	 //companyId="4";
	 
	 //String branchId=(String)req.getSession().getAttribute("branchId");
          String branchId=user.getCurruser().getBranch().toString();
	 //branchId="4";
	 
	 String statusShort = req.getParameter("xstat");
	 String vwName = req.getParameter("nextpag")==null?"listDiviGen":req.getParameter("nextpag");
	 
	 List requests=dividendService.getDividendDAO().getDividendListByStatus(companyId,branchId,statusShort);
	 
	 model.put("listDividend", requests);   	 

  	 return new ModelAndView(vwName, model);

 }

@RequestMapping(value={"/dividend/divObj", "/dividend/viewDivObj","/dividend/divObject"}, method = RequestMethod.GET)
public String getDivForApprovalOrView(ModelMap model, HttpServletRequest req)
{
	 
	Dividend divObj= null;
	Date today = new Date();

	String dividendId = req.getParameter("dividId");
	
        String companyId=user.getCurruser().getCompanyid().toString();
         
        String  branchId=user.getCurruser().getBranch().toString();
	
	divObj = new Dividend();
	
	if(dividendId!=null)
	{
		log.info("getting the didvidend here:" +  dividendId);
		
		divObj = dividendService.getDividendDAO().findByIdAndCompany(companyId, branchId,dividendId);
	}
	
	model.put("dividend", divObj);
        
    String viewPage= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		
    return "/dividend/" + viewPage;
}



    @RequestMapping(value = {"/dividend/listNew", "/dividend/listApproved","/dividend/listRejected","/dividend/listEdited","/dividend/listCancelled","/dividend/listPaid"}, method = RequestMethod.GET)
   
    public ModelAndView listDividend (HttpServletRequest req, HttpServletResponse res)
    {
        String batchStat = null;
        
        int companyRef = user.getCurruser().getCompanyid();
        
        Map<String, Object> model = new HashMap<String, Object>();
        
        String vwName = "";
        String vwTitle = "";
        String objVwLink = "";
        String objAprLink = "";
        String objLinkLabel= "";
        String objApprLabel= "";

               
        String viewPage= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
        Map<String, String> curData = divViewData.get(viewPage);
        
         batchStat = curData.get("status");
         vwTitle  = curData.get("title");
         objVwLink =  curData.get("objView");
         objLinkLabel =  curData.get("viewLabel");
         
         if(curData.get("objAppr")!=null && curData.get("apprLabel")!=null)
         {
            objAprLink= curData.get("objAppr");
            objApprLabel = curData.get("apprLabel");
         }
         
         //objCrumbs =  curData.get("crumbs");
         vwName  = curData.get("page");
         
        dividendService.setUser(user);
        
        List requests=dividendService.listDetail(companyRef, batchStat);

        model.put("listDividend", requests);  
        model.put("title", vwTitle);
        model.put("objView", objVwLink);
         model.put("objAprLink", objAprLink);
        model.put("objLinkLabel", objLinkLabel);
        model.put("objApprLabel", objApprLabel);

        return new ModelAndView(vwName, model);
    }
   
    @RequestMapping(value="/dividend/update", method = RequestMethod.GET)
    //public String editStock(ModelMap model, HttpServletRequest req)
    public ModelAndView editDividend(@ModelAttribute("dividend") Dividend dividend, HttpServletRequest req)
    {
            Dividend divObj= null;
            //Date today = new Date();

            String divTypeId = req.getParameter("dividId");

            //stockObj.setAction("EDIT");

            if(divTypeId!=null)
            {
                divObj = new Dividend();
                divObj.setDividendId(Integer.valueOf(divTypeId));

                dividendService.setUser(user);
                log.info("getting the Divdend here:" +  divTypeId);

                 divObj = (Dividend)dividendService.readDetail(divObj);
                
                if(divObj!=null)
                {
                    divObj.setAction("EDIT");
                    divObj.setModifiedBy(user.getCurruser().getUserId());
                    return   new ModelAndView("/dividend/dividend", "dividend", divObj);
                }
                else
                {
                    return new ModelAndView("redirect:/finResult/result.htm?textMsg=No record retrieved");
                }
            }
            else
            {
                return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed page properly");
            }
    }
   
    
    @RequestMapping(value="/dividend/reBuildDivPayable", method = RequestMethod.POST)
    public View reBuildDividend(@ModelAttribute("dividend") Dividend dividend,BindingResult result, SessionStatus status, HttpServletRequest req)
    {
        
        Map<String, String> theResult = new HashMap();
	
	boolean success= false;
	
	//System.out.println("req.action ::: " + req.getParameter("action"));
	
	if(dividend.getDividendId()!=null && req.getParameter("action")!=null && !req.getParameter("action").equals(""))
	{
		log.info("getting the dividend here:" +  dividend.getDividendId());
                dividendService.setUser(user);
		dividend = dividendService.getDividendDAO().findById(dividend.getDividendId());
		dividend.setAction(req.getParameter("action").toUpperCase());
                //System.out.println("Dividend approval action::: " + req.getParameter("action"));
                log.info("Dividend approval action::: " + dividend.getAction());
    
                String curEventShort = getDivEvents().get(req.getParameter("action").toUpperCase());
	 
                Event curEvent = (Event)dividendService.getEventDAO().findByEventShort(curEventShort).get(0);
	       //Event curEvent = (Event)dividendService.getEventDAO().findByEventShort("REBUILD-DIV").get(0);
               // System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
	 
                StatusFlow statusFlow = dividendService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, dividend.getStatus());
	 
                success = dividendService.reBuildDiv(dividend, statusFlow,req);
	 
		 if(success)
		 {
                        theResult.put("message", "Dividend Rebuild has been initiated");
		 }
		 else
                 {
			 theResult.put("message", dividendService.getMsg());
		 }
    	 
	status.setComplete();
	
	//return new ModelAndView ("result", theResult) ;
	 return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
	
	}
	else
	{
		//return new ModelAndView ("listPendMember") ;
	  return new RedirectView("/finResult/result.htm?textMsg=You have not accessed the page properly",true);
	}
        
    }


@ModelAttribute("dividend")
   public Dividend populateForm() {
       return new Dividend(); // populates form for the first time if its null
   }

public DividendManageImpl getDividendService() {
	return dividendService;
}

public void setDividendService(DividendManageImpl dividendService) {
	this.dividendService = dividendService;
}


public Map<String, String> getDivEvents() {
	return divEvents;
}


public void setDivEvents(Map<String, String> divEvents) {
	this.divEvents = divEvents;
}

public String getStartPos() {
	return startPos;
}

public void setStartPos(String startPos) {
	this.startPos = startPos;
}

public CurrentuserdisplayImpl getUser() {
		return user;
	}

public void setUser(CurrentuserdisplayImpl user) {

         this.user.setdCurruser(user.getCurruser());
}

public ConversionService getConversionService() {
      return conversionService;
  }

  public void setConversionService(ConversionService conversionService) {
      this.conversionService = conversionService;
  }
  
  
@InitBinder
 public void registerConversionServices(WebDataBinder dataBinder) {
      //dataBinder.setConversionService(conversionService);
      dataBinder.setValidator(validator);

 }

}
