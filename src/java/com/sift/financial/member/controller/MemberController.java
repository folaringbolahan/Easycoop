package com.sift.financial.member.controller;


/**
 * @author baydel200
 *
 */
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import java.text.*;
import java.util.*;

import com.sift.financial.Result;
import com.sift.financial.member.*;
import com.sift.financial.member.services.*;
import com.sift.financial.services.ManagementTempl;
import com.sift.financial.stock.services.*;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.webservice.utility.WebServiceUtility;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
//@RequestMapping("/crew") 
@SessionAttributes("member")
public class MemberController {
	
	private static final Log log = LogFactory.getLog(MemberController.class);

	@Autowired
	private MemberManageImpl memberService;
	
	//@Autowired
	private ConversionService conversionService;

	//@Resource (name="memEvents")
	private Map<String, String> memEvents;
	
	//@Resource (name="memAddrType")
	private Map<String, String> memAddrType;
	
	//@Autowired
	//@Value("${startPos}")
	private String startPos; 
        
        @Autowired
	@Value("${newMember}")
	private String newMember; 
	
	//@Autowired
	//@Value("${apprvdStockStat}")
	private String apprvdStockStat;
        
        @Autowired
	private CurrentuserdisplayImpl user;
        
        
        @Autowired
	@Value("${updMemberStat}")
	private String updMemberStat; 
        
        @Autowired
	@Value("${apprvMemberStatusShort}")
	private String apprvMemberStatusShort; 
       
        @Autowired
        @Qualifier("memberValidator")
        private Validator validator; 
        
        @Autowired
        @Value("${DEF-CON-PRODTYPE}")
        private String prodType;
        
        @Autowired
        private BranchService branchService;
        WebServiceUtility webServiceUtility = new WebServiceUtility();
        @Autowired
        private CompanyService companyService;
        @Autowired
        private CountryService countryService;
        EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
        
	
	@RequestMapping(value="/crew/saveMember", method = RequestMethod.POST)
	//public View saveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status,HttpServletRequest req) 
	public ModelAndView saveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status,HttpServletRequest req) 
        {
	
            Result res = new Result();
            Map<String, String> theResult = new HashMap();
            memberService.setUser(user);
            
            if (result.hasErrors()) {
                
                    return   new ModelAndView("/members/member", "member", member);
                }
     
            if(member.getAction().equals("ADD"))
            {
                String curEventShort = getMemEvents().get(member.getAction());
                System.out.println("curEventShort :: " + curEventShort);
                memberService.setReqInfo(userInfo());
               
                Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);

                Status initStatus = memberService.getStatusDAO().getStatusFromShort(startPos) ;

                StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);

                memberService.setMemAddrType(memAddrType);

                memberService.setApprvdStockStat(apprvdStockStat);

                memberService.addDetail(member, statusFlow, req);
    	 
            }
     
            if(member.getAction().equals("EDIT"))
            {
                String curEventShort = getMemEvents().get(member.getAction());
                System.out.println("curEventShort :: " + curEventShort);
                Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
                System.out.println("curEvent :: " + curEvent.getEventShort());
                System.out.println("member.getStatus() :: " + member.getStatus());
                memberService.setReqInfo(userInfo());
                
                memberService.setMemAddrType(memAddrType);
                
                Status currStatus = memberService.getStatusDAO().getStatusFromShort(member.getState()) ;
                
                 StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, currStatus);

                memberService.editDetail(member, statusFlow,req);
            }
     
            status.setComplete();

            theResult.put("message", memberService.getMsg());
            res.setMessage(theResult);

            //return new RedirectView("/finResult/result.htm?textMsg=" + memberService.getMsg(),true);
         
         return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
	}

	
	@RequestMapping(value="/crew/approveMember", method = RequestMethod.POST)
	//public View approveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status, HttpServletRequest req) 
	public ModelAndView approveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status, HttpServletRequest req) 
        {
     
		Map<String, String> theResult = new HashMap();
		
		boolean success= false;
		
		System.out.println("req.action ::: " + req.getParameter("action"));
		
		if(member.getMemberId()!=null)
		{
			log.info("getting the member here:" +  member.getMemberId());
			member = memberService.getMemberDao().findById(member.getMemberId());
			
			//get Address Records
			memberService.setMemAddrType(memAddrType);
			member.setAddressEntriesList(memberService.getMemberAddressEntries(member.getMemberId().toString()));
			
                        member.setMembersExtrafldEntriesList(memberService.getMembersExtrafldEntries(member.getMemberId().toString()));
                        
			member.setAction(req.getParameter("action"));
			
		 memberService.setApprvdStockStat(apprvdStockStat);
                 memberService.setUser(user);
	     
	     log.info("Member approval action::: " + member.getAction());
	    
		 String curEventShort = getMemEvents().get("APPROVE");
		 
		 Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
		 
		 System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
		 
		 StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, member.getStatus());
		 
		 success = memberService.apprDetail(member, statusFlow,req);
		 
			 if(success)
			 {
				if(member.getAction().equals(ActivityInterface.ACTIONREJECT))
				{
					theResult.put("message", "Member record successfully rejected");
				}
				else if(member.getAction().equals(ActivityInterface.ACTIONAPPROVE))
				{
                                       //do EasyCoop Call
                                        //doEasyCoop(member);
                                        
					theResult.put("message", "Member record successfully approved");
				}
				
			 }
			 else
			 {
				 
				 theResult.put("message", memberService.getMsg());
			 }
	    	 
		status.setComplete();
		
		// return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
                return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
		
		}
		else
		{
		 // return new RedirectView("/finResult/result.htm?textMsg=You have not accessed the page properly",true);
                 return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed the page properly");
		}
		
	}

	@RequestMapping(value="/crew/approveBulkMember", method = RequestMethod.GET)
	//public View approveBulkMember(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status, HttpServletRequest req) 
	public ModelAndView approveBulkMember(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status, HttpServletRequest req) 

        {
		Map<String, String> theResult = new HashMap();
	
		boolean success= false;
	
		System.out.println("req.action ::: " + req.getParameter("action"));
		
		String [] memIds =req.getParameterValues("memId");
		
		 String curEventShort = getMemEvents().get("APPROVE");
		 
		 Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
		 
		 System.out.println("curEvent :::" + curEvent.getEventName() + "   ID::: " + curEvent.getEventId());
		 
		 StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, member.getStatus());
		 
		 if(memIds !=null)
		  {
						
						for(String mem : memIds)
						{
								log.info("getting the member here:" +  mem);
								
								member = memberService.getMemberDao().findById(Integer.parseInt(mem));
								
								//get Address Records
								memberService.setMemAddrType(memAddrType);
								
								member.setAddressEntriesList(memberService.getMemberAddressEntries(member.getMemberId().toString()));
								
								member.setAction(req.getParameter("action"));
								
								memberService.setApprvdStockStat(apprvdStockStat);
						     
						    			 
							 success = memberService.apprDetail(member, statusFlow,req);
							 
								 if(success)
								 {
									if(member.getAction().equals(ActivityInterface.ACTIONREJECT))
									{
										//theResult.put("message", "Member record successfully rejected");
										theResult.put(member.getEmailAdd1(), member.getEmailAdd1() + "-- Member record successfully rejected");
									
									}
									else if(member.getAction().equals(ActivityInterface.ACTIONAPPROVE))
									{
										//theResult.put("message", "Member record successfully approved");
										theResult.put(member.getEmailAdd1(), "Member record successfully approved");
									}
									
								 }
								 else
								 {
									 
									 theResult.put(member.getEmailAdd1(), memberService.getMsg());
									 
								 }
								 
							System.out.println(theResult.get(member.getEmailAdd1()));
						    	 
						}
					status.setComplete();
					 //return new RedirectView("/finResult/result.htm?textMsg=" + theResult.get("message"),true);
                                         return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + theResult.get("message"));
			}
			else
			{
				//return new RedirectView("/finResult/result.htm?textMsg=You have not accessed the page properly",true);
                                return new ModelAndView("redirect:/finResult/result.htm?textMsg=You have not accessed the page properly");
			}
	
	}

	@RequestMapping(value = "/crew/pendingMember", method = RequestMethod.GET)
	public ModelAndView pendingMemberRequests(HttpServletRequest req, HttpServletResponse res){
		
		Map<String, Object> model = new HashMap<String, Object>();
		String vwName = "/members/listMember4Approval";
		List<Object[]> requests=listMembers(newMember);
		 
		 model.put("listMember", requests);   	 

	  	 return new ModelAndView(vwName, model);
	
	 }
        
        @RequestMapping(value = "/crew/listMember", method = RequestMethod.GET)
	public ModelAndView listMemberRequests(HttpServletRequest req, HttpServletResponse res){
		
		Map<String, Object> model = new HashMap<String, Object>();
		//String statusShort = "";
		String vwName = "/members/pendingApproval";
		List<Object[]> requests=listMembers(newMember);
		 
		 model.put("listMember", requests);   	 

	  	 return new ModelAndView(vwName, model);
	
	 }
        
         @RequestMapping(value = "/crew/apprdMember", method = RequestMethod.GET)
	public ModelAndView apprdMemberRequests(HttpServletRequest req, HttpServletResponse res){
		
		Map<String, Object> model = new HashMap<String, Object>();
                String vwName = "/members/newMembers";
		List<Object[]> requests=listMembers(apprvMemberStatusShort);
		 
		 model.put("listMember", requests);   	 

	  	 return new ModelAndView(vwName, model);
	
	 }
        
        @RequestMapping(value = "/crew/updMember", method = RequestMethod.GET)
	public ModelAndView updMemberRequests(HttpServletRequest req, HttpServletResponse res){
		
		Map<String, Object> model = new HashMap<String, Object>();
                String vwName = "/members/updMembers";
		List<Object[]> requests=listMembers(updMemberStat);
		 
		 model.put("listMember", requests);   	 

	  	 return new ModelAndView(vwName, model);
	
	 }
        
      

	@RequestMapping(value={"/crew/new"} , method = {RequestMethod.GET})
	public ModelAndView addMember(@ModelAttribute("Member") Member member, HttpServletRequest req){
           
               System.out.println("here1");	
		//Member memberObj= null;
		Date today = new Date();
		System.out.println("here2");	
		member = new Member();
		member.setCreatedBy(user.getCurruser().getUserName());
		member.setCreatedDate(today);
		member.setAction("ADD");
                member.setBranch(memberService.getBranchDAO().findById(user.getCurruser().getBranch()));
		//model.addAttribute("member", memberObj);
		System.out.println("here3");		
	//return "member";
        //return "/members/member";
      // return new ModelAndView("/members/member", model);
        return   new ModelAndView("/members/member", "member", member);
	}
	
     
	@RequestMapping(value="/crew/getAppPage", method = RequestMethod.GET)
	public String getMember(ModelMap model, HttpServletRequest req)
	{
		 //Map<String ,Object> model = new HashMap<String, Object>();
		 
		Member memberObj= null;
		Date today = new Date();
	
		String memberId = req.getParameter("memId");
		
		memberObj = new Member();
		//memberObj.setAction("EDIT");
		
		if(memberId!=null)
		{
			log.info("getting the member here:" +  memberId);
			memberObj = memberService.getMemberDao().findById(Integer.valueOf(memberId));
			
			//get Address Records
			memberService.setMemAddrType(memAddrType);
			memberObj.setAddressEntriesList(memberService.getMemberAddressEntries(memberId));
			memberObj.setMembersExtrafldEntriesList(memberService.getMembersExtrafldEntries(memberId));
                        model.put("member", memberObj);
                         return "/members/approveMember";
			//memberObj.setAction("EDIT");
		}
                else
                {
                  return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                
                }

	}
        
        
        @RequestMapping(value="/crew/getDiscPage", method = RequestMethod.GET)
	public String getMemberForDisc(ModelMap model, HttpServletRequest req)
	{
		 //Map<String ,Object> model = new HashMap<String, Object>();
		 
		Member memberObj= null;
		Date today = new Date();
	
		String memberId = req.getParameter("memId");
		
		memberObj = new Member();
		//memberObj.setAction("EDIT");
		
		if(memberId!=null)
		{
			log.info("getting the member here:" +  memberId);
			memberObj = memberService.getMemberDao().findByIds(Integer.valueOf(memberId), user.getCurruser().getCompanyid());
			
			//get Address Records
			memberService.setMemAddrType(memAddrType);
			memberObj.setAddressEntriesList(memberService.getMemberAddressEntries(memberId));
			
                        memberObj.setMembersExtrafldEntriesList(memberService.getMembersExtrafldEntries(memberId));
                        
                        model.put("member", memberObj);
                         return "/members/thisMember";
		}
                else
                {
                  return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                
                }

	}
	
	
	@RequestMapping(value="/crew/update", method = RequestMethod.GET)
        
	//public ModelAndView editMember(@ModelAttribute("member") Member member, BindingResult result,HttpServletRequest req)
	public String editMember(ModelMap model, HttpServletRequest req)
	{
		 //Map<String ,Object> model = new HashMap<String, Object>();
		 
		Member memberObj= null;
		Date today = new Date();
	
		String memberId = req.getParameter("memId");
		
		memberObj = new Member();
		memberObj.setAction("EDIT");
		
		if(memberId!=null)
		{
			log.info("getting the member here:" +  memberId);
			memberObj = memberService.getMemberDao().findById(Integer.valueOf(memberId));
			
			//get Address Records
			memberService.setMemAddrType(memAddrType);
			memberObj.setAddressEntriesList(memberService.getMemberAddressEntries(memberId));
                        
                        memberObj.setMembersExtrafldEntriesList(memberService.getMembersExtrafldEntries(memberId));
			
			String curEventShort = getMemEvents().get("EDIT");
                        //Event addEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
                       // memberObj.setMemberContributions(memberService.getMemberContributions(Integer.parseInt(memberId)));
                        
           
			//new HashSet(list);
			//memberObj.setMemberHoldingsMovements(new HashSet(memberService.getMemberHoldingsMovementDAO().findByEventAndMember(addEvent, memberObj)));
			
			memberObj.setAction("EDIT");
                        model.put("member", memberObj);
                        
                        return "/members/member";
		}
                else
                {
                     return "redirect:/finResult/result.htm?textMsg=You have not accessed the page properly";
                }
		
	}
	
	
	//@InitBinder
	 //public void initBinder(WebDataBinder binder){
		
	 // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	  //dateFormat.setLenient(false);
	 // binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
      //binder.registerCustomEditor(IdentificationDoc.class, new IdentificationDocEditor(getMemberService()));
	//  binder.registerCustomEditor(Company.class, new CompanyEditor(getMemberService()));
	//  binder.registerCustomEditor(Branch.class, new BranchEditor(getMemberService()));
	//  binder.registerCustomEditor(MemberType.class, new MemberTypeEditor(getMemberService()));
	//  binder.registerCustomEditor(Religion.class, new ReligionEditor(getMemberService()));
	//}
	
	@InitBinder
           public void registerConversionServices(WebDataBinder dataBinder) {
	//       dataBinder.setConversionService(conversionService);
                dataBinder.setValidator(validator);
                
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
		//List<Map<String,Object>>  theKeys = getGenericConfigDAO().getProductsByType(member.getCompany().getId().toString(), member.getBranch().getId().toString(), prodType);
		referenceList.put("identityDocList", memberService.getAllIdentificationDocByCompanyRef(companyRef));
		referenceList.put("compList", memberService.getCompanyList(companyRef));
		referenceList.put("branchList", memberService.getBranchDAO().getBrachesByCompanyId(companyRef));
		referenceList.put("memberTypeList", memberService.getMemberTypeDAO().findAll());
		referenceList.put("religionList", memberService.getReligionDAO().getReligionByCountryId(countryRef));
		referenceList.put("addressList", memberService.getCountryAddressFilterDAO().getCountryAddressByCountryId(countryRef));
		referenceList.put("addressType", getMemAddrType());
		referenceList.put("taxGroupsList", memberService.getTaxGroupsDAO().getTaxGroupsByCompanyId(companyRef));
		referenceList.put("banksList", memberService.getBanksDAO().getBanksByCountry(countryRef));
		referenceList.put("stockList", memberService.getCompStockTypeDAO().getDefaultStockListByCompanyCol(companyRef,apprvdStockStat));
                referenceList.put("contrList", memberService.getGenericConfigDAO().getDefaultProductsByType(user.getCurruser().getCompanyid().toString(), user.getCurruser().getBranch().toString(), prodType));
                 
                //yomi amended 
                referenceList.put("extrafldList", memberService.getMemberExtrafldDAO().findByCompany(new Integer(user.getCurruser().getCompanyid())));
		referenceList.put("extrafldoptionList", memberService.getMemberExtrafldoptionDAO().getExtrafldByCompany(user.getCurruser().getCompanyid().toString()));
                
                
		return referenceList;
	}

	public MemberManageImpl getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberManageImpl memberService) {
		this.memberService = memberService;
	}

	public Map<String, String> getMemEvents() {
		return memEvents;
	}

	public void setMemEvents(Map<String, String> memEvents) {
		this.memEvents = memEvents;
	}

	public String getStartPos() {
		return startPos;
	}

	public void setStartPos(String startPos) {
		this.startPos = startPos;
	}

	public Map<String, String> getMemAddrType() {
		return memAddrType;
	}

	public void setMemAddrType(Map<String, String> memAddrType) {
		this.memAddrType = memAddrType;
	}


	public String getApprvdStockStat() {
		return apprvdStockStat;
	}


	public void setApprvdStockStat(String apprvdStockStat) {
		this.apprvdStockStat = apprvdStockStat;
	}
        
        //public CurrentuserdisplayImpl getUser() {
	//	return user;
	//}

	//public void setUser(CurrentuserdisplayImpl user) {
	
        //this.user.setdCurruser(user.getCurruser());
        ///    this.user=user;
	//}
        
        public CurrentuserdisplayImpl getUser() {
		return user;
	}


	public void setUser(CurrentuserdisplayImpl user) {
		
		 this.user.setdCurruser(user.getCurruser());
	}
        
        
        
        //conversionService
        
        public ConversionService getConversionService() {
		return conversionService;
	}
        
	public void setConversionService(ConversionService conversionService)
        {
		 this.conversionService=conversionService;
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
           // user.getCurrusercompany().getTimezone();
        return reqInfo;
        }
         
        private List listMembers(String statusShort)
        {
            //getMemberService().setReqInfo(userInfo());
            String companyId=user.getCurruser().getCompanyid().toString();
            List<Object[]> requests= memberService.getMemberDao().getMemberListByStatus(companyId, statusShort, user.getCurruser().getBranch().toString());
            return requests;
        }
        
        
        private void doEasyCoop(Member member)
        {
                //call easycoop web service
                    int dbranch = user.getCurruser().getBranch();
                    int dcompany = user.getCurruser().getCompanyid();
                    if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                            && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {
                        //System.out.println("Company connected to easycoop...");

                        String name = member.getSurname() + " " + member.getFirstName() + " " + member.getMiddleName();
                        String resource = "user";

                        com.sift.admin.model.Company coy = companyService.getCompany(dcompany);
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date regDate = member.getCreatedDate();
                        String registrationDate = formatter1.format(regDate);

                        try {
                            webServiceUtility.webserviceClient(resource, webServiceUtility.createMember(dcompany,
                                    dbranch, member.getEmailAdd1(),
                                    registrationDate,
                                    member.getMemberNo(), member.getPhoneNo1(), name.trim(), member.getEmailAdd1(), member.getAddressStr()));
                        } catch (IOException ex) {                            
                            ex.printStackTrace();
                        }

                    } else {
                       log.info("Company not connected to easycoop...");
                        //System.out.println("Is it Connected to EasyCoop : " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                    }

        }
        

}
