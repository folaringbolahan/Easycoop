package com.sift.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.AuthPermit;
import com.sift.admin.model.User;
import com.sift.admin.service.AuthPermitService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AuthPermitBean;
import com.sift.admin.bean.UserBean;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.MailBean;
import com.sift.loan.utility.PassEncoder;
import com.sift.loan.utility.RandomPasswordGenerator;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class AuthPermitController{

@Autowired
private AuthPermitService authPermitService;

@Autowired
private UserService userService;

@Autowired
private CurrentuserdisplayImpl currentuserdisplayService;

RandomPasswordGenerator passUTIL=new RandomPasswordGenerator();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveAuthPermit", method = RequestMethod.POST)
public ModelAndView saveAuthPermit(@ModelAttribute("authPermit")AuthPermitBean authPermitBean,BindingResult result,HttpServletRequest req) {
	 	if(result.hasErrors()){
		 	Map<String, Object> model = new HashMap<String, Object>();
	  	 	model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit()));

	  	 	String URI=req.getParameter("ACTION_ID").equals("1")? "addAuthPermit":"editAuthPermit";
	     	return new ModelAndView(URI, model);
	 	}
	 
		String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
	    
		String accessLevel=currentUser.getGroupId();
		String optionCode="";
		
	    if(Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel) || "Audit".equalsIgnoreCase(accessLevel) || "Acct".equalsIgnoreCase(accessLevel) || "Admin".equalsIgnoreCase(accessLevel)){
	    	optionCode="CA";
	    }else if("CAdmin".equalsIgnoreCase(accessLevel)){
	    	optionCode="BA";
	    }else if("BAdmin".equalsIgnoreCase(accessLevel)){
	    	optionCode="BU";
	    }else{
	    	optionCode="CA";
	    }

	 	AuthPermit authPermit = prepareModel(authPermitBean);	
	 	authPermit.setAccessLevelCode(optionCode);
	 	
	 	authPermitService.addAuthPermit(authPermit);
	 	return new ModelAndView("redirect:/doFeedback.htm?message=AuthPermit Info Update was successful&redirectURI=index.htm");
 }

@RequestMapping(value = "/approveAuthPermit", method = RequestMethod.POST)
public ModelAndView approveAuthPermit(@Valid @ModelAttribute("user")AuthPermitBean authPermitBean,BindingResult result,HttpServletRequest req){
	  AuthPermit authPermit = authPermitService.getAuthPermit(authPermitBean.getId());
	  User user=userService.getUserByEmail(authPermitBean.getEmail());
	  
	  String tempPass=passUTIL.generatePswd();
	  String encryptedPass=new PassEncoder().doEndcodePass(tempPass);
	  	  
	  user.setPassword(encryptedPass);
	  user.setMustChangePass("Y");
	  
	  authPermit.setRequestStatus(req.getParameter("approvalStatus"));
	  authPermit.setApprovalComment(authPermitBean.getApprovalComment());
	  authPermit.setApprovalBy(req.getRemoteUser());
	  
	  boolean approved="A".equalsIgnoreCase(authPermit.getRequestStatus());	  
	  Date curDate=eazyCoopUTIL.getTimeByZone(currentuserdisplayService.getCurrusercompany().getTimezone()); 	  
	  authPermit.setApprovalDate(curDate); 	
	  
	  boolean saved=false;
 	  boolean reset=false;
 	  
	  saved=authPermitService.addAuthPermit(authPermit);
 	  if(approved && saved){reset=userService.resetUserLogon(user);}
 	  
 	  boolean mailSent=false;
 	 
 	  if(saved && reset  & approved){		 
 		 try{
			 //mail bean setup
			 MailBean MB=eazyCoopUTIL.getMailConfig();
			 MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
			 MB.setToemail(user.getEmail());
			 
	    	 String uri="";
	    	 
	    	 try{
				uri=(String)new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
			 }catch(NamingException e){
				e.printStackTrace();
			 }
			 
		     String mailBody= "	<style type=\"text/css\">" +
					"	<!--" +
					"	.style2 {" +
					"		font-family: Verdana, Arial, Helvetica, sans-serif;" +
					"		font-size: 14px;" +
					"	}" +
					"	-->" +
					"	</style>" +
					"	 <p class=\"style2\">Hello "+user.getEmail()+", </p>" +
					"	<p class=\"style2\">Your EazyCoop logon password reset request was successful. <br>" +
					"   <strong>Below is your new logon details: </strong></p>" +
					"	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
					"	  <tr>" +
					"	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
					"	    <td width=\"50%\"><span class=\"style2\">" + user.getEmail() + "</span></td>" +
					"	  </tr>" +
					
					"	  <tr>" +
					"	    <td><span class=\"style2\">Temporary Password:</span></td>" +
					"	    <td><span class=\"style2\">" + tempPass + " </span></td>" +
					"	  </tr>" +
					
					"	  <tr>" +
					"	    <td><span class=\"style2\">Application URL:</span></td>" +
					"	    <td><span class=\"style2\">" + uri + " </span></td>" +
					"	  </tr>" +
	
				    "     <tr>" +
					"	    <td colspan=2><hr/></td>" +
					"	    " +
					"	  </tr>" +	
					
	                "     <tr>" +
					"	    <td colspan=2><hr/></td>" +
					"	    " +
					"	  </tr>" +        
					"	</table>" +
					"	<p class=\"style2\"><br>" +
					"	Thanks </p> " ;
	    
             MB.setMailBody(mailBody);	    	
 
			 //send email
			 eazyCoopUTIL.sendMail(MB);
			 mailSent=true;
		 }catch(RuntimeException e){
			e.printStackTrace();
		 }
 	  }

	  if(saved){
		  return new ModelAndView("redirect:/doFeedback.htm?message=Authentication Request Approval was successful&redirectURI=index.htm");
	  }else{
		  return new ModelAndView("redirect:/doErrork.htm?message=Authentication Request Approval Failed.&redirectURI=index.htm");
	  }
 }

 @RequestMapping(value="/authPermitsCA", method = RequestMethod.GET)
 public ModelAndView listAuthPermitsCA(){
	    Map<String ,Object> model = new HashMap<String, Object>();
	    model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit(currentuserdisplayService.getCurruser().getCompanyid(),currentuserdisplayService.getCurruser().getBranch(),"CA")));
	 
	    return new ModelAndView("listAuthPermit", model);
 }
 
 @RequestMapping(value="/authPermitsBA", method = RequestMethod.GET)
 public ModelAndView listAuthPermitsBA() {
	    Map<String ,Object> model = new HashMap<String, Object>();
	    model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit(currentuserdisplayService.getCurruser().getCompanyid(),currentuserdisplayService.getCurruser().getBranch(),"BA")));
	 
	    return new ModelAndView("listAuthPermit", model);
 }
 
 @RequestMapping(value="/authPermits", method = RequestMethod.GET)
 public ModelAndView listAuthPermits(){
	    Map<String ,Object> model = new HashMap<String, Object>();
	    model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit(currentuserdisplayService.getCurruser().getCompanyid(),currentuserdisplayService.getCurruser().getBranch(),"BU")));
	 
	    return new ModelAndView("listAuthPermit", model);
 }

 @RequestMapping(value = "/newAuthPermit", method = RequestMethod.GET)
 public ModelAndView addAuthPermit(@ModelAttribute("authPermit")AuthPermitBean authPermitBean, BindingResult result) {
	    Map<String, Object> model = new HashMap<String, Object>();
  	    model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit()));
     
  	    return new ModelAndView("addAuthPermit", model);
 }

@RequestMapping(value = "/deleteAuthPermit", method = RequestMethod.GET)
public ModelAndView deleteAuthPermit(@ModelAttribute("authPermit")AuthPermitBean authPermitBean,BindingResult result){
	    authPermitService.deleteAuthPermit(prepareModel(authPermitBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("authPermit", null);
		model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit()));
		return new ModelAndView("addAuthPermit", model);
 }

@RequestMapping(value = "/editAuthPermit", method = RequestMethod.GET)
public ModelAndView editAuthPermit(@ModelAttribute("authPermit")AuthPermitBean authPermitBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authPermit", prepareAuthPermitBean(authPermitService.getAuthPermit(authPermitBean.getId())));
		model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit()));
		return new ModelAndView("editAuthPermit", model);
}

@RequestMapping(value = "/viewAuthPermit", method = RequestMethod.GET)
public ModelAndView viewAuthPermit(@ModelAttribute("authPermit")AuthPermitBean authPermitBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authPermit", prepareAuthPermitBean(authPermitService.getAuthPermit(authPermitBean.getId())));
		//model.put("authPermits",  prepareListofBean(authPermitService.listAuthPermit()));
		return new ModelAndView("viewAuthPermit", model);
}

@InitBinder
public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private AuthPermit prepareModel(AuthPermitBean authPermitBean){
	    AuthPermit authPermit = new AuthPermit();

	    authPermit.setApprovalBy(authPermitBean.getApprovalBy());
	    authPermit.setApprovalDate(authPermitBean.getApprovalDate());
	    authPermit.setBranchId(authPermitBean.getBranchId());
	    authPermit.setCompanyId(authPermitBean.getCompanyId());
	    authPermit.setEmail(authPermitBean.getEmail());
	    authPermit.setRequestStatus(authPermitBean.getRequestStatus());
	    authPermit.setRequestDate(authPermitBean.getRequestDate());
	    authPermit.setAccessLevelCode(authPermitBean.getAccessLevelCode());
	    
	    authPermit.setId(authPermitBean.getId());

	    return authPermit;
 }

 private List<AuthPermitBean> prepareListofBean(List<AuthPermit> authPermits){
        List<AuthPermitBean> beans = null;

        if(authPermits != null && !authPermits.isEmpty()){
        	beans = new ArrayList<AuthPermitBean>();
        	AuthPermitBean authPermit = null;

        	for(AuthPermit authPermitBean : authPermits){
        		authPermit = new AuthPermitBean();

        	    authPermit.setApprovalBy(authPermitBean.getApprovalBy());
        	    authPermit.setApprovalDate(authPermitBean.getApprovalDate());
        	    authPermit.setBranchId(authPermitBean.getBranchId());
        	    authPermit.setCompanyId(authPermitBean.getCompanyId());
        	    authPermit.setEmail(authPermitBean.getEmail());
        	    authPermit.setRequestStatus(authPermitBean.getRequestStatus());
        	    authPermit.setRequestDate(authPermitBean.getRequestDate());
        	    authPermit.setAccessLevelCode(authPermitBean.getAccessLevelCode());
        	    authPermit.setId(authPermitBean.getId());

			    beans.add(authPermit);
		   }
	    }

        return beans;
 }

 private AuthPermitBean prepareAuthPermitBean(AuthPermit authPermit){
		    AuthPermitBean 	bean = new AuthPermitBean();

		    bean.setApprovalBy(authPermit.getApprovalBy());
		    bean.setApprovalDate(authPermit.getApprovalDate());
		    bean.setBranchId(authPermit.getBranchId());
		    bean.setCompanyId(authPermit.getCompanyId());
		    bean.setEmail(authPermit.getEmail());
		    bean.setRequestStatus(authPermit.getRequestStatus());
		    bean.setRequestDate(authPermit.getRequestDate());
		    bean.setAccessLevelCode(authPermit.getAccessLevelCode());

		    bean.setId(authPermit.getId());

		    return bean;
 }
}