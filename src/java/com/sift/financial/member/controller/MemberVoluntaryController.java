package com.sift.financial.member.controller;

import com.sift.financial.Result;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.services.MemberDisciplineImpl;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberVoluntaryController 
{
    
    private static final Log log = LogFactory.getLog(MemberVoluntaryController.class);
   
        @Autowired
        @Qualifier("conversionService")
	private ConversionService conversionService;

	@Resource (name="disckEvents")
	private Map<String, String> discEvents;
	
        @Autowired
        @Qualifier("memberDiscpService")
	private MemberDisciplineImpl discplineService;
        
        @Autowired
	private SiftFinancialUtil siftUtil;
	
        @Autowired
        private CurrentuserdisplayImpl user;
        
        @Autowired
        @Qualifier("memberDiscValidator")
        private Validator validator; 
        
        @Autowired
	@Value("${initResgMem}")
	private String initResgMem; 
        
        
        @Autowired
	@Value("${resgMem}")
	private String resgMem;
   
         
    @RequestMapping(value="/volunt/apprvMemberVoluntary", method = RequestMethod.POST)
     public ModelAndView apprvMemberVoluntary(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status,HttpServletRequest req) 
    {
         Result res = new Result();
            Map<String, String> theResult = new HashMap();
            
            if (result.hasErrors() && member.getAction().endsWith("RESIGN")) 
            {
                return   new ModelAndView("/members/apprResignPage", "member", member);
            }

             System.out.println("member.getAction() :: " + member.getAction());
            
            try
            {
                if(member.getMemberId()!=null)
		{
			log.info("getting the member here:" +  member.getMemberId());
			member = discplineService.getMemberDao().findById(member.getMemberId());
			member.setAction(req.getParameter("action"));
                }
                
                if(member.getAction()!=null  && !member.getAction().equals(""))
                {
                    String curEventShort = getDiscEvents().get(member.getAction());
                
                    System.out.println("curEventShort :: " + curEventShort);
                    discplineService.setUser(user);

                    Event curEvent = (Event)discplineService.getEventDAO().findByEventShort(curEventShort).get(0);
                    
                    System.out.println("curEvent :: " + curEvent.getEventName());
                    System.out.println("member.getStatus() :: " + member.getStatus());

                    StatusFlow statusFlow = discplineService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, member.getStatus());
                    System.out.println("statusFlow :: " + statusFlow.getStatusSuccessId());
                    discplineService.apprDetail(member, statusFlow, req);
                    
                    System.out.println("statusFlow ::getEventStockMove  :: " + statusFlow.getEvent().getEventStockMove());
                    
                    System.out.println("discplineService.getMsg() :: " + discplineService.getMsg());
    	 
                }
                else
                {
                    discplineService.setMsg("You have not specified the right parameters, please try again");
                }
                    status.setComplete();
                    theResult.put("message", discplineService.getMsg());
                    res.setMessage(theResult);
            }
            catch(Exception ex)
            {
            theResult.put("message", ex.getMessage());
            }
           
         return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + (String)theResult.get("message")); 
    }
     
        @RequestMapping(value={"/volunt/apprResignPage"}, method = RequestMethod.GET)
	public String getMember(ModelMap model, HttpServletRequest req)
	{
	    //Map<String ,Object> model = new HashMap<String, Object>();
            String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            //System.out.println("pattern :: " + pattern);
            // String pathValue = req.getServletPath(); 
            int pos1 = pattern.lastIndexOf("/");
            int pos2 = pattern.indexOf(".");
            String viewPage = pattern.substring(pos1+1, pos2);
            //System.out.println("pathValue :: " + pos1);
            //System.out.println("pathValue :: " + pos2);
            //int lenth = pos2-pos1;
            //System.out.println("pathValue :: " + lenth);
           
		 
		Member memberObj= null;
		Date today = new Date();
	
		String memberId = req.getParameter("memId");
		
		memberObj = new Member();
		//memberObj.setAction("EDIT");
		
		if(memberId!=null)
		{
                log.info("getting the member here:" +  memberId);
                memberObj = discplineService.getMemberDao().findByIds(Integer.valueOf(memberId), user.getCurruser().getCompanyid());
			
			//get Address Records
			//discplineService.setMemAddrType(memAddrType);
			//memberObj.setAddressEntriesList(discplineService.getMemberAddressEntries(memberId));
			
                        model.put("member", memberObj);
                       // return "/members/apprPage";
                        return "/members/" + viewPage;
			//memberObj.setAction("EDIT");
		}
                else
                {
                  return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                
                }

	}
     
    @RequestMapping(value = {"/volunt/pendingResignation","/volunt/resigned"}, method = RequestMethod.GET)
    public ModelAndView pendingReq (HttpServletRequest req, HttpServletResponse res){

        Map<String, Object> model = new HashMap<String, Object>();
        String vwName = "/members/";
        String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        int pos1 = pattern.lastIndexOf("/");
        int pos2 = pattern.indexOf(".");
        String viewPage = pattern.substring(pos1+1, pos2);
        vwName = vwName + viewPage;
        discplineService.setUser(user);
        String statusShort = null;
        
        if (viewPage.equalsIgnoreCase("pendingResignation"))
        {
           statusShort= initResgMem;
        }
        else if (viewPage.equalsIgnoreCase("resigned"))
        {
            statusShort= resgMem;
        }
        
        List<Object> requests =discplineService.listDetail(null,statusShort);

        model.put("listMember", requests);   	 

        return new ModelAndView(vwName, model);
     }
  
    
    
    @ModelAttribute("referenceList")
	public Map<String, Object> populateList(HttpServletRequest request,HttpServletResponse response) 
	{
		//Data referencing for web framework checkboxes
		String companyRef = null;
		String countryRef = null;
		String branchRef = null;
		

		try {
			companyRef = user.getCurruser().getCompanyid().toString();
			countryRef = user.getCurrusercompany().getCountryId();
			branchRef = user.getCurruser().getBranch().toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//companyRef = "4";
			//countryRef ="1";
			//branchRef  = "0003";
			
			e.printStackTrace();
		}
		
		Map<String,Object> referenceList = new HashMap<String, Object>();
		
		log.info("The company : " + companyRef);
		
		referenceList.put("identityDocList", discplineService.getAllIdentificationDocByCompanyRef(companyRef));
		referenceList.put("compList", discplineService.getCompanyList(companyRef));
		referenceList.put("branchList", discplineService.getBranchDAO().getBrachesByCompanyId(companyRef));
		referenceList.put("memberTypeList", discplineService.getMemberTypeDAO().findAll());
		referenceList.put("religionList", discplineService.getReligionDAO().getReligionByCountryId(countryRef));
		referenceList.put("addressList", discplineService.getCountryAddressFilterDAO().getCountryAddressByCountryId(countryRef));
		//referenceList.put("addressType", getMemAddrType());
		referenceList.put("taxGroupsList", discplineService.getTaxGroupsDAO().getTaxGroupsByCompanyId(companyRef));
		referenceList.put("banksList", discplineService.getBanksDAO().getBanksByCountry(countryRef));
		//referenceList.put("stockList", discplineService.getCompStockTypeDAO().getDefaultStockListByCompanyCol(companyRef,apprvdStockStat));
		
		return referenceList;
	}
        
        @InitBinder
           public void registerConversionServices(WebDataBinder dataBinder) {
	//       dataBinder.setConversionService(conversionService);
                dataBinder.setValidator(validator);
	}

    public Map<String, String> getDiscEvents() {
        return discEvents;
    }

    public void setMemEvents(Map<String, String> discEvents) {
        this.discEvents = discEvents;
    }

    public MemberDisciplineImpl getDiscplineService() {
        return discplineService;
    }

    public void setDiscplineService(MemberDisciplineImpl discplineService) {
        this.discplineService = discplineService;
    }
    
    public ConversionService getConversionService() {
		return conversionService;
    }
        
	public void setConversionService(ConversionService conversionService)
        {
		 this.conversionService=conversionService;
	}
     
   
}
