package com.sift.admin.controller;

import java.io.IOException;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.AuthPermit;
import com.sift.admin.model.User;
import com.sift.admin.service.AuthPermitService;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.UserGroupService;
import com.sift.admin.service.UserRoleService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.CompanyBean;
import com.sift.admin.bean.UserBean;
import com.sift.admin.bean.UserGroupBean;
import com.sift.admin.bean.UserRoleBean;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.MailBean;
import com.sift.loan.utility.RandomPasswordGenerator;
import com.sift.loan.utility.PassEncoder;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentuserdisplayImpl currentuserdisplayService;

    @Autowired
    private AuthPermitService authPermitService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private HelperUtility helperUTIL;

    @Autowired
    private Configuration freemarkerConfiguration;
    @Autowired
    private TaskExecutor taskExecutor;

    BeanMapperUtility beanMapper = new BeanMapperUtility();
    RandomPasswordGenerator passUTIL = new RandomPasswordGenerator();
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        System.out.println("logonUser=" + logonUser);
        User currentUser = userService.getUserByEmail(logonUser);

        String accessLevel = currentUser.getGroupId();
        String actionId = req.getParameter("ACTION_ID");

        String operand = "NE";
        String accessId = "-1";
        boolean sendMailToAdmin = false;

        if (Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel)) {
            operand = "NE";
            accessId = "-1";

            if ("1".equals(actionId)) {
                sendMailToAdmin = true;
            }
        } else if ("Audit".equalsIgnoreCase(accessLevel)) {
            operand = "NE";
            accessId = "-1";
        } else if ("Acct".equalsIgnoreCase(accessLevel)) {
            operand = "GT";
            accessId = "999";
        } else if ("Admin".equalsIgnoreCase(accessLevel)) {
            operand = "EQ";
            accessId = "0";
        } else if ("CAdmin".equalsIgnoreCase(accessLevel)) {
            operand = "EQ";
            accessId = "1";
        } else if ("BAdmin".equalsIgnoreCase(accessLevel)) {
            operand = "GT";
            accessId = "1";
        } else {
            operand = "GT";
            accessId = "999";
        }

        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("user", userBean);

            String URI = "";
            UserGroupBean bean = new UserGroupBean();
            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();

            if (Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel)) {
                bean.setDescription("Cooperative Admin");
                bean.setCode("CAdmin");
                model.put("accessL", "CAdmin");
                listX.add(bean);
                model.put("userGroups", listX);
                //URI="newUserCA";
                URI = "addUser";
            } else if ("Audit".equalsIgnoreCase(accessLevel)) {
                bean.setDescription("Cooperative Admin");
                bean.setCode("CAdmin");
                model.put("accessL", "CAdmin");
                listX.add(bean);
                model.put("userGroups", listX);
                //URI="newUserCA";
                URI = "addUser";
            } else if ("Acct".equalsIgnoreCase(accessLevel)) {
                bean.setDescription("Cooperative Admin");
                bean.setCode("CAdmin");
                model.put("accessL", "CAdmin");
                listX.add(bean);
                model.put("userGroups", listX);
                //URI="newUserCA";
                URI = "addUser";
            } else if ("Admin".equalsIgnoreCase(accessLevel)) {
                bean.setDescription("Cooperative Admin");
                bean.setCode("CAdmin");
                model.put("accessL", "CAdmin");
                listX.add(bean);
                model.put("userGroups", listX);
                //URI="newUserCA";
                URI = "addUser";
            } else if ("CAdmin".equalsIgnoreCase(accessLevel)) {
                bean.setDescription("Branch Admin");
                bean.setCode("BAdmin");
                model.put("accessL", "BAdmin");
                listX.add(bean);
                model.put("userGroups", listX);
                //URI="newUserBA";
                URI = "addUser";
            } else if ("BAdmin".equalsIgnoreCase(accessLevel)) {
                model.put("userGroups", beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(), "2")));
                URI = "addUser";
            }

            //model.put("userGroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroups(accessId,operand)));
            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));

            String URIX = req.getParameter("ACTION_ID").equals("1") ? URI : "editUser";
            return new ModelAndView(URI, model);
        }

        Activitylog activity = new Activitylog();
        String tempPass = passUTIL.generatePswd();
        boolean validEmail = false;

        if ("2".equals(actionId)) {
            validEmail = helperUTIL.UserEmailExist(userBean.getEmail(), userBean.getUserId());

            User xUser = userService.getUser(userBean.getId());

            if (accessLevel.equalsIgnoreCase("BAdmin")) {
                userBean.setIsBranchUser(1);
            } else {
                userBean.setIsBranchUser(0);
            }

            userBean.setCreatedBy(xUser.getCreatedBy());
            userBean.setLastModifiedBy(req.getRemoteUser());
            userBean.setLastModificationDate(new java.util.Date());
            userBean.setPassword(xUser.getPassword());

            activity.setEvent(Definitions.EVENT_USER_PROFILE_UPDATE);
            activity.setAction("User Profile Modification for user: " + userBean.getEmail());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("User Name: " + userBean.getUsername());
            activity.setActionResult("User Profile Modification for user email: " + userBean.getEmail());
            activity.setDescription("User Profile Modification for user email: " + userBean.getEmail());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(userBean.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
            activity.setBranchid(Integer.parseInt(currentUser.getBranchId()));
            /* activity.setCompanyid(Integer.parseInt(userBean.getCompanyId()));
             activity.setBranchid(Integer.parseInt(userBean.getBranchId()));
             */
        } else {
            validEmail = helperUTIL.UserEmailExist(userBean.getEmail());

            userBean.setPassword(new PassEncoder().doEndcodePass(tempPass));
            userBean.setEnabled(0);
            userBean.setMustChangePass("Y");

            if (accessLevel.equalsIgnoreCase("BAdmin")) {
                userBean.setIsBranchUser(1);
            } else {
                userBean.setIsBranchUser(0);
            }

            activity.setEvent(Definitions.EVENT_USER_PROFILE_CREATION);
            activity.setAction("User Profile Creation for user email: " + userBean.getEmail());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("User Name: " + userBean.getUsername());
            activity.setActionResult("User Profile Creation for user email: " + userBean.getEmail());
            activity.setDescription("User Profile Creation for user email: " + userBean.getEmail());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(userBean.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
            activity.setBranchid(Integer.parseInt(currentUser.getBranchId()));
        }

        userBean.setUserId(userBean.getEmail());

        if (validEmail) {

            User user = prepareModel(userBean);
            boolean created = userService.addUser(user);
            boolean mailSent = false;
            String userAdminEmail = "";

            /*		  if(created && sendMailToAdmin){	 		 
             try{
             MailBean MB=eazyCoopUTIL.getMailConfig();
             MB.setSubject(Definitions.MAIL_SUBJECT_NEW_USER_SETUP);
             MB.setToemail(userAdminEmail);
             MB.setAttachments(null);
					 
             String uri="";
			    	 
             try{
             uri=(String)new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
             }catch(NamingException e){
             e.printStackTrace();
             }					 
	 
             String mailBody= "";
             String template = "useradminactivation.ftl";
             Map model = new HashMap();
             model.put("getUsername", user.getUsername());
             model.put("getEmail", user.getEmail());
             model.put("uri", uri);
		             
             try{    
             mailBody =FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template,"UTF-8"), model);   
             }catch (TemplateNotFoundException tpex) {
             System.out.println("Error - missing email template :" + tpex.getMessage());
             tpex.printStackTrace();
             }catch (MalformedTemplateNameException tpex){
             System.out.println("Error - email template name :" + tpex.getMessage());
             tpex.printStackTrace();
             }catch (TemplateException tpex){
             System.out.println("Error - email template :" + tpex.getMessage());
             tpex.printStackTrace();
		                
             }catch (IOException tpex){
             System.out.println("Error - email template IO :" + tpex.getMessage());
             tpex.printStackTrace();
             }

             MB.setMailBody(mailBody);	
		 
             //send email
             eazyCoopUTIL.sendMail(MB);
             mailSent=true;
             }catch(RuntimeException e){
             e.printStackTrace();
             }
             }		 */
            if (created) {
                try {
                    eazyCoopUTIL.LogUserAction(activity);
                } catch (Exception ex) {
                }

                return new ModelAndView("redirect:/doFeedback.htm?message=User Record Setup/Update was successful&redirectURI=index.htm");
            } else {
                return new ModelAndView("redirect:/doError.htm?message=User Record Setup/Update failed. Please try again later&redirectURI=index.htm");
            }
        } else {//not valid Email
            return new ModelAndView("redirect:/doError.htm?message=Specified Email Address Already Exist. Please use a new email and try again&redirectURI=index.htm");
        }
    }

    @RequestMapping(value = "/coyAdminActivateBUser1", method = RequestMethod.GET)
    public ModelAndView coyAdminActivateBUser1(HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("users", userService.listInActiveBAUserBeanForCoy(currentUser.getCompanyId()));
        return new ModelAndView("coyActivateBU-1", model);
    }

    @RequestMapping(value = "/coyAdminActivateBUser2", method = RequestMethod.GET)
    public ModelAndView coyAdminActivateBUser2(@ModelAttribute("user") UserBean userBean, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

	 //System.out.println("id2="+ req.getParameter("id"));
        //System.out.println("id2="+ userBean.getUserId());
        User obj = userService.getUser(Integer.parseInt(req.getParameter("id")));

        model.put("user", prepareUserBean(obj));
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        model.put("userGroups", beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(obj.getCompanyId(), obj.getBranchId(), "2")));

        return new ModelAndView("coyActivateBU-2", model);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView listUsers() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("users", prepareListofBean(userService.listUsers()));
        return new ModelAndView("usersList", model);
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public ModelAndView addUser(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //model.put("users", prepareListofBean(userService.listUsersByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(),2)));
        model.put("users", userService.listUserBeansByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(), 2));

        String accessLevel = currentUser.getGroupId();

        model.put("userGroups", beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(), "2")));

        userBean.setCompanyId(currentUser.getCompanyId());
        userBean.setBranchId(currentUser.getBranchId());

        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));

        return new ModelAndView("addUser", model);
    }

    @RequestMapping(value = "/newUserCA", method = RequestMethod.GET)
    public ModelAndView addUserCA(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        UserGroupBean bean = new UserGroupBean();
        bean.setDescription("Cooperative Admin");
        bean.setCode("CAdmin");

        ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
        listX.add(bean);

        //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
        model.put("users", userService.listUserBeansByGroup("CAdmin"));

        model.put("userGroups", listX);
        model.put("accessL", "CAdmin");

        userBean.setCompanyId(currentUser.getCompanyId());
        userBean.setBranchId(currentUser.getBranchId());

        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));

        return new ModelAndView("addUser", model);
    }

    @RequestMapping(value = "/newUserBA", method = RequestMethod.GET)
    public ModelAndView addUserBA(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //model.put("users", prepareListofBean(userService.listBranchAdminsForCoy(currentUser.getCompanyId(), "BAdmin")));
        model.put("users", userService.listBranchAdminsForCoyBeans(currentUser.getCompanyId(), "BAdmin"));

        UserGroupBean bean = new UserGroupBean();
        bean.setDescription("Branch Admin");
        bean.setCode("BAdmin");
        model.put("accessL", "BAdmin");

        ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
        listX.add(bean);

        model.put("userGroups", listX);
	 //model.put("users", prepareListofBean(userService.listBranchAdminsForCoy(currentUser.getCompanyId(),"BAdmin")));

        userBean.setCompanyId(currentUser.getCompanyId());
        userBean.setBranchId(currentUser.getBranchId());

        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));

        return new ModelAndView("addUser", model);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ModelAndView deleteUser(@ModelAttribute("user") UserBean userBean, BindingResult result) {
        userService.deleteUser(prepareModel(userBean));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", null);
        model.put("users", prepareListofBean(userService.listUsers()));
        return new ModelAndView("addUser", model);
    }

    @RequestMapping(value = "/resetLogon", method = RequestMethod.GET)
    public ModelAndView resetLogon(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", currentUser);
        return new ModelAndView("resetLogon", model);
    }

    @RequestMapping(value = "/processFailedLogon", method = RequestMethod.GET)
    public ModelAndView processFailedLogon(HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);
        boolean disable = false;

        int FailedLogon = helperUTIL.getFailedLogonCount(currentUser.getEmail());

        if (FailedLogon + 1 == 3) {
            helperUTIL.disableUser(currentUser.getEmail());
        } else if (FailedLogon + 1 < 3) {
            helperUTIL.updateFailedLogonCount(currentUser.getEmail());
        } else {
            //helperUTIL.updateFailedLogonCount(currentUser.getEmail());
        }

        String disableMsg = " your account have been locked";
        String faileLogonMsg = " You already have " + FailedLogon + 1 + " failed logon attempt(s)";

        if (disable) {
            return new ModelAndView("redirect:/doError.htm?message=" + faileLogonMsg + " and " + disableMsg + "&redirectURI=index.htm");
        } else {
            return new ModelAndView("redirect:/doError.htm?message=" + faileLogonMsg + " please ensure you re-logon with correct credentials to avoid account lock&redirectURI=index.htm");
        }
    }

    @RequestMapping(value = "/ajaxGetInActiveUsers", method = RequestMethod.GET)
    public @ResponseBody
    List<UserBean> ajaxInActiveUserList(@RequestParam(value = "branchId", required = true) String id) {
        return userService.listInActiveUserBeansForBranch(id);
    }

    @RequestMapping(value = "/changeMyLogonPass", method = RequestMethod.GET)
    public ModelAndView changeMyLogonPass(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        System.out.println("logonUser:" + logonUser);
        System.out.println("email:" + userBean.getEmail());

        User currentUser = userService.getUserByEmail(userBean.getEmail());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", currentUser);
        return new ModelAndView("changeMyLogonPass", model);
    }

    @RequestMapping(value = "/changeMyLogonPassPost", method = RequestMethod.POST)
    public ModelAndView changeMyLogonPassPost(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        boolean passMatch = false;
        boolean success = false;
        boolean mailSent = false;
        boolean reset = false;

        String message = "";

        User user = prepareModel(userBean);

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");

        //we need to confirm if password matches
        User profile = userService.getUserByEmail(user.getEmail());
        passMatch = new PassEncoder().doMatchPass(oldPassword.trim(), profile.getPassword());

        if (!passMatch) {
            success = false;
        } else {
            String encryptedPass = new PassEncoder().doEndcodePass(newPassword);
            user.setPassword(encryptedPass);
            
            ////reset passwordlastsetdate and password credentialsnonexpired
            short one = 1;
            user.setCredentialsNonExpired(one);
            
            reset = userService.updateUserLogon(user);

            if (reset) {
                try {
                    //mail bean setup
                    MailBean MB = eazyCoopUTIL.getMailConfig();
                    MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                    MB.setToemail(userBean.getEmail());

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    /* 			     String mailBody= "	<style type=\"text/css\">" +
                     "	<!--" +
                     "	.style2 {" +
                     "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                     "		font-size: 14px;" +
                     "	}" +
                     "	-->" +
                     "	</style>" +
                     "	 <p class=\"style2\">Hello "+profile.getUsername()+", </p>" +
                     "	<p class=\"style2\">Your EazyCoop logon change request request was successful. <br>" +
                     "   <strong>Below is your new logon details: </strong></p>" +
                     "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                     "	  <tr>" +
                     "	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
                     "	    <td width=\"50%\"><span class=\"style2\">" + userBean.getEmail() + "</span></td>" +
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
                     "	  Thanks </p> " ;
 		    
                     MB.setMailBody(mailBody);	    	
                     */
                    String mailBody = "";
                    String template = "changeLogonPass.ftl";
                    Map model = new HashMap();
                    model.put("getUsername", profile.getUsername());
                    model.put("getEmail", user.getEmail());
                    model.put("uri", uri);

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    //eazyCoopUTIL.sendMail(MB);
                    eazyCoopUTIL.setTaskExecutor(taskExecutor);
                    eazyCoopUTIL.sendMailasync(MB);
                    mailSent = true;
                    success = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }

        if (success) {
            req.getSession().invalidate();
            return new ModelAndView("redirect:/doFeedback.htm?message=Application Access Update was successful&redirectURI=index.htm");
        } else if (reset) {
            return new ModelAndView("redirect:/doFeedback.htm?message=Application Access Update was successful&redirectURI=index.htm");
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Old Password does not match&redirectURI=changeMyLogonPass.htm?email=" + user.getEmail());
        }
    }

    @RequestMapping(value = "/changeMyLogonPass2", method = RequestMethod.POST)
    public ModelAndView changeMyLogonPass2(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        boolean passMatch = false;
        boolean mailSent = false;
        boolean reset = false;
        String message = "";

        User user = prepareModel(userBean);

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmpassword");

        //we need to confirm if password matches
        User profile = userService.getUserByEmail(user.getEmail());
        passMatch = new PassEncoder().doMatchPass(oldPassword.trim(), profile.getPassword());

        if (passMatch) {
            String encryptedPass = new PassEncoder().doEndcodePass(newPassword);
            user.setPassword(encryptedPass);

            ////reset passwordlastsetdate and password credentialsnonexpired
            short one = 1;
            user.setCredentialsNonExpired(one);
            
            reset = userService.updateUserLogon(user);

            if (reset) {
                try {
                    //mail bean setup
                    MailBean MB = eazyCoopUTIL.getMailConfig();
                    MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                    MB.setToemail(userBean.getEmail());

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    String mailBody = "";
                    String template = "changeLogonPass-1.ftl";
                    Map model = new HashMap();
                    model.put("getUsername", profile.getUsername());
                    model.put("getEmail", user.getEmail());
                    model.put("newPassword", newPassword);
                    model.put("uri", uri);

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    //eazyCoopUTIL.sendMail(MB);
                    eazyCoopUTIL.setTaskExecutor(taskExecutor);
                    eazyCoopUTIL.sendMailasync(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!passMatch) {
            //return new ModelAndView("redirect:/error.jsp?message=Old Password does not match&redirectURI=/changeMyLogonPass.htm");
            return new ModelAndView("redirect:/doError.htm?message=Old Password does not match.&redirectURI=changeMyLogonPass.htm");
        } else if (reset) {
            if (mailSent) {
                req.getSession().invalidate();
                return new ModelAndView("redirect:/login.jsp");
            } else {
                //return new ModelAndView("redirect:/error.jsp?message=Password change was successful but mail could not be sent&redirectURI=/changeMyLogonPass.htm");
                return new ModelAndView("redirect:/doError.htm?message=Password change was successful but mail could not be sent.&redirectURI=changeMyLogonPass.htm");
            }
        } else {
            //return new ModelAndView("redirect:/error.jsp?message=Password change could not be effected now. Please try again later.&redirectURI=/changeMyLogonPass.htm");
            return new ModelAndView("redirect:/doError.htm?message=Password change could not be effected now. Please try again later.&redirectURI=changeMyLogonPass.htm");
        }
    }

    @RequestMapping(value = "/updateAppLogon", method = RequestMethod.POST)
    public ModelAndView updateAppLogon(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = userService.getUserByEmail(userBean.getEmail());

 	  //String clearPass=user.getPassword();
        //String encryptedPass=new PassEncoder().doEndcodePass(clearPass);
        //user.setPassword(encryptedPass);
        //user.setMustChangePass("Y");
        String usergrp = user.getGroupId();
        String accessLevelCode = "";

        if ("CAdmin".equalsIgnoreCase(usergrp)) {
            accessLevelCode = "CA";
        } else if ("BAdmin".equalsIgnoreCase(usergrp)) {
            accessLevelCode = "BA";
        } else {
            accessLevelCode = "BU";
        }

        AuthPermit authPermit = new AuthPermit();

        authPermit.setBranchId(Integer.parseInt(user.getBranchId()));
        authPermit.setCompanyId(Integer.parseInt(user.getCompanyId()));
        authPermit.setEmail(userBean.getEmail());
        authPermit.setRequestStatus("E");
        authPermit.setAccessLevelCode(accessLevelCode);

        //Date curDate=eazyCoopUTIL.getTimeByZone(currentuserdisplayService.getCurrusercompany().getTimezone()); 	  
        authPermit.setRequestDate(new java.util.Date());
        boolean saved = authPermitService.addAuthPermit(authPermit);

        if (saved) {
            return new ModelAndView("redirect:/feedback.jsp?message=Request for password reset has been submitted.&redirectURI=login.jsp");
        } else {
            return new ModelAndView("redirect:/error.jsp?message=Error Processing Password Reset Request. Please try again later&redirectURI=login.jsp");
        }
    }

    @RequestMapping(value = "/updateAppLogon1", method = RequestMethod.POST)
    public ModelAndView updateAppLogon1(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = prepareModel(userBean);
        User profile = userService.getUserByEmail(user.getEmail());

        String clearPass = user.getPassword();
        String encryptedPass = new PassEncoder().doEndcodePass(clearPass);
        user.setPassword(encryptedPass);
        user.setMustChangePass("Y");
        
        ////reset passwordlastsetdate and password credentialsnonexpired
        short one = 1;
        user.setCredentialsNonExpired(one);

        boolean reset = userService.resetUserLogon(user);
        boolean mailSent = false;

        if (reset) {
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                MB.setToemail(userBean.getEmail());

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                /*		     String mailBody= "	<style type=\"text/css\">" +
                 "	<!--" +
                 "	.style2 {" +
                 "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                 "		font-size: 14px;" +
                 "	}" +
                 "	-->" +
                 "	</style>" +
                 "	 <p class=\"style2\">Hello "+profile.getUsername()+", </p>" +
                 "	<p class=\"style2\">Your EazyCoop logon password reset request was successful. <br>" +
                 "   <strong>Below is your new logon details: </strong></p>" +
                 "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                 "	  <tr>" +
                 "	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
                 "	    <td width=\"50%\"><span class=\"style2\">" + userBean.getEmail() + "</span></td>" +
                 "	  </tr>" +
					
                 "	  <tr>" +
                 "	    <td><span class=\"style2\">Temporary Password:</span></td>" +
                 "	    <td><span class=\"style2\">" + clearPass + " </span></td>" +
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
                 "	  Thanks </p> " ;
	    
                 MB.setMailBody(mailBody);	    	*/
                String mailBody = "";
                String template = "changeLogonPass-2.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());
                model.put("getEmail", user.getEmail());
                model.put("clearPass", clearPass);
                model.put("uri", uri);

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			 //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            //req.getSession().invalidate();
            return new ModelAndView("redirect:/login.jsp");
        } else if (reset) {
            return new ModelAndView("redirect:/login.jsp");
        } else {
            return new ModelAndView("redirect:/login.jsp");
        }
    }

    @RequestMapping(value = "/activateUser", method = RequestMethod.GET)
    public ModelAndView activateUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = userService.getUser(userBean.getId());

        String clearPass = passUTIL.generatePswd();
        String encryptedPass = new PassEncoder().doEndcodePass(clearPass);

        user.setPassword(encryptedPass);
        user.setMustChangePass("Y");
        
        ////reset passwordlastsetdate and password credentialsnonexpired
        short one = 1;
        user.setCredentialsNonExpired(one);

        boolean done = userService.activateUser(user);
        boolean mailSent = false;

        if (done) {
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                MB.setToemail(user.getEmail());

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                /*		     String mailBody= "	<style type=\"text/css\">" +
                 "	<!--" +
                 "	.style2 {" +
                 "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                 "		font-size: 14px;" +
                 "	}" +
                 "	-->" +
                 "	</style>" +
                 "	 <p class=\"style2\">Hello "+user.getUsername()+", </p>" +
                 "	 <p class=\"style2\">You have been setup as a user on EazyCoop Application. </p>" +
                 "    <p> <strong>Below is your new logon details: </strong></p>" +
                 "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                 "	  <tr>" +
                 "	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
                 "	    <td width=\"50%\"><span class=\"style2\">" + user.getEmail() + "</span></td>" +
                 "	  </tr>" +
					
                 "	  <tr>" +
                 "	    <td><span class=\"style2\">Temporary Password:</span></td>" +
                 "	    <td><span class=\"style2\">" + clearPass + " </span></td>" +
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
                 "	  Thanks </p> " ;
	    
                 MB.setMailBody(mailBody);	   */
                String mailBody = "";
                String template = "activateusermail.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());
                model.put("getEmail", user.getEmail());
                model.put("clearPass", clearPass);
                model.put("uri", uri);

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			 //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            return new ModelAndView("redirect:/doFeedback.htm?message=User Record Setup/Update was successful&redirectURI=index.htm");
        } else if (done) {
            return new ModelAndView("redirect:/doFeedback.htm?message=User Record Setup/Update was successful&redirectURI=index.htm");
        } else {
            return new ModelAndView("redirect:/index.htm");
        }
    }

    @RequestMapping(value = "/doActivateUser", method = RequestMethod.POST)
    public ModelAndView doActivateUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = userService.getUser(userBean.getId());
        User currentUser = userService.getUserByEmail(req.getRemoteUser());
        String clearPass = passUTIL.generatePswd();
        String encryptedPass = new PassEncoder().doEndcodePass(clearPass);

        user.setPassword(encryptedPass);
        user.setMustChangePass("Y");
        
        ////reset passwordlastsetdate and password credentialsnonexpired
        short one = 1;
        user.setCredentialsNonExpired(one);

        boolean done = userService.activateUser(user);
        boolean mailSent = false;

        Activitylog activity = new Activitylog();

        activity.setEvent(Definitions.EVENT_USER_ACTIVATION);
        activity.setAction("User Profile Activation for user email: " + userBean.getEmail());
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("User Name: " + userBean.getUsername());
        activity.setActionResult("User Profile Activation for user email: " + userBean.getEmail());
        activity.setDescription("User Profile Activation for user email: " + userBean.getEmail());
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(userBean.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
        activity.setBranchid(Integer.parseInt(currentUser.getBranchId()));
        if (done) {
            try {
                eazyCoopUTIL.LogUserAction(activity);
            } catch (Exception ex) {
            }

            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                MB.setToemail(user.getEmail());
                MB.setAttachments(null);

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                /*		     String mailBody= "	<style type=\"text/css\">" +
                 "	<!--" +
                 "	.style2 {" +
                 "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                 "		font-size: 14px;" +
                 "	}" +
                 "	-->" +
                 "	</style>" +
                 "	 <p class=\"style2\">Hello "+user.getUsername()+", </p>" +
                 "	 <p class=\"style2\">You have been setup as a user on EazyCoop Application. </p>" +
                 "    <p> <strong>Below is your new logon details: </strong></p>" +
                 "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                 "	  <tr>" +
                 "	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
                 "	    <td width=\"50%\"><span class=\"style2\">" + user.getEmail() + "</span></td>" +
                 "	  </tr>" +
					
                 "	  <tr>" +
                 "	    <td><span class=\"style2\">Temporary Password:</span></td>" +
                 "	    <td><span class=\"style2\">" + clearPass + " </span></td>" +
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
                 "	  Thanks </p> " ;
	    
                 MB.setMailBody(mailBody);	   */
                String mailBody = "";
                String template = "activateusermail.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());
                model.put("getEmail", user.getEmail());
                model.put("clearPass", clearPass);
                model.put("uri", uri);

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			  //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            return new ModelAndView("redirect:/doFeedback.htm?message=User Record Setup/Update was successful&redirectURI=index.htm");
        } else if (done) {
            return new ModelAndView("redirect:/doFeedback.htm?message=User Record Setup/Update was successful&redirectURI=index.htm");
        } else {
            return new ModelAndView("redirect:/index.htm");
        }
    }

    @RequestMapping(value = "/deactivateUser", method = RequestMethod.POST)
    public ModelAndView deactivateUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = userService.getUser(userBean.getId());
        boolean done = userService.deactivateUser(user);
        boolean mailSent = false;

        if (done) {
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_DEACTIVATION);
                MB.setToemail(user.getEmail());

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                /*		     String mailBody= "	<style type=\"text/css\">" +
                 "	<!--" +
                 "	.style2 {" +
                 "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                 "		font-size: 14px;" +
                 "	}" +
                 "	-->" +
                 "	</style>" +
                 "	 <p class=\"style2\">Hello "+user.getUsername()+", </p>" +
                 "	 <p class=\"style2\">You have been deactivated as a user on EazyCoop Application. </p>" +
                 "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +

                 "	  <tr>" +
                 "	    <td><span class=\"style2\">Please contact administrator for further details</span></td>" +
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
                 "	  Thanks </p> " ;
	    
                 MB.setMailBody(mailBody);	    	
                 */
                String mailBody = "";
                String template = "deactivateusermail.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			  //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            return new ModelAndView("redirect:/index.htm");
        } else if (done) {
            return new ModelAndView("redirect:/index.htm");
        } else {
            return new ModelAndView("redirect:/index.htm");
        }
    }

    @RequestMapping(value = "/disableUser", method = RequestMethod.POST)
    public ModelAndView disableUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        User user = userService.getUserByEmail(userBean.getEmail());
        boolean done = userService.deactivateUser(user);
        boolean mailSent = false;

        if (done) {
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_DEACTIVATION);
                MB.setToemail(user.getEmail());

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                /*		     String mailBody= "	<style type=\"text/css\">" +
                 "	<!--" +
                 "	.style2 {" +
                 "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                 "		font-size: 14px;" +
                 "	}" +
                 "	-->" +
                 "	</style>" +
                 "	 <p class=\"style2\">Hello "+user.getUsername()+", </p>" +
                 "	 <p class=\"style2\">Your account has been locked on EazyCoop Application. </p>" +
                 "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +

                 "	  <tr>" +
                 "	    <td><span class=\"style2\">Please contact administrator for further details</span></td>" +
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
                 "	  Thanks </p> " ;
	    
                 MB.setMailBody(mailBody);	    	*/
                String mailBody = "";
                String template = "disableusermail.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			  //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            return new ModelAndView("redirect:/index.htm");
        } else if (done) {
            return new ModelAndView("redirect:/index.htm");
        } else {
            return new ModelAndView("redirect:/index.htm");
        }
    }

    @RequestMapping(value = "/deactivateuser0", method = RequestMethod.GET)
    public ModelAndView deactivateuser0(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        model.put("users", helperUTIL.getActiveBranchUsers(currentUser.getBranchId()));
        return new ModelAndView("deActivateUser0", model);
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public ModelAndView editUser(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        UserBean obj = prepareUserBean(userService.getUser(userBean.getId()));
        model.put("user", obj);
        model.put("users", prepareListofBean(userService.listUsers()));

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        String accessLevel = currentUser.getGroupId().trim();

        if (Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));
            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            //model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("Audit".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));
            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            //model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("Acct".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));

            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            //model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("CAdmin".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Branch Admin");
            bean.setCode("BAdmin");
            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            model.put("userGroups", listX);
            //model.put("users", prepareListofBean(userService.listBranchAdminsForCoy(currentUser.getCompanyId(),"BAdmin")));
            model.put("users", userService.listBranchAdminsForCoyBeans(currentUser.getCompanyId(), "BAdmin"));

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("BAdmin".equalsIgnoreCase(accessLevel)) {
            model.put("userGroups", beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(obj.getCompanyId(), obj.getBranchId(), "2")));
            //model.put("users", prepareListofBean(userService.listUsersByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(),2)));
            model.put("users", userService.listUserBeansByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(), 2));
            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        }

        return new ModelAndView("editUser", model);
    }

    @RequestMapping(value = "/userActivation-1", method = RequestMethod.GET)
    public ModelAndView listInActiveUsers(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        UserBean obj = prepareUserBean(userService.getUser(userBean.getId()));
        model.put("user", obj);
		//model.put("users",       prepareListofBean(userService.listUsers()));

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);
        String accessLevel = currentUser.getGroupId();

        if (Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

	        //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            //model.put("users", userService.listUserBeansByGroup("CAdmin"));
            //model.put("userGroups",listX);   
            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("Audit".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

	        //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            //model.put("users", userService.listUserBeansByGroup("CAdmin"));
            //model.put("userGroups",listX);   
            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("Acct".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

	        //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            //model.put("users", userService.listUserBeansByGroup("CAdmin"));
            //model.put("userGroups",listX);   
            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("CAdmin".equalsIgnoreCase(accessLevel)) {
            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("BAdmin".equalsIgnoreCase(accessLevel)) {
            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        }

        return new ModelAndView("editUser", model);
    }

    @RequestMapping(value = "/listInActiveUser1", method = RequestMethod.GET)
    public ModelAndView listInActiveUser1(@ModelAttribute("user") UserBean userBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        UserBean obj = prepareUserBean(userService.getUser(userBean.getId()));

        /**
         * *******************************************************************
         * model.put("user", obj); model.put("users",
         * prepareListofBean(userService.listUsers()));
	    ********************************************************************
         */
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        String accessLevel = currentUser.getGroupId();

        if (Definitions.EASYCORP_SUPER_ADM.equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));

            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("Audit".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));

            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("Acct".equalsIgnoreCase(accessLevel)) {
            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Cooperative Admin");
            bean.setCode("CAdmin");

            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            //model.put("users", prepareListofBean(userService.listUsersByGroup("CAdmin")));
            model.put("users", userService.listUserBeansByGroup("CAdmin"));
            model.put("userGroups", listX);

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranch()));
        } else if ("CAdmin".equalsIgnoreCase(accessLevel)) {

            UserGroupBean bean = new UserGroupBean();
            bean.setDescription("Branch Admin");
            bean.setCode("BAdmin");
            ArrayList<UserGroupBean> listX = new ArrayList<UserGroupBean>();
            listX.add(bean);

            model.put("userGroups", listX);
            //model.put("users", prepareListofBean(userService.listBranchAdminsForCoy(currentUser.getCompanyId(),"BAdmin")));
            model.put("users", userService.listBranchAdminsForCoyBeans(currentUser.getCompanyId(), "BAdmin"));

            userBean.setCompanyId(currentUser.getCompanyId());
            userBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        } else if ("BAdmin".equalsIgnoreCase(accessLevel)) {
            model.put("userGroups", beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(obj.getCompanyId(), obj.getBranchId(), "2")));
            //model.put("users", prepareListofBean(userService.listUsersByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(),2)));
            model.put("users", userService.listUserBeansByGroupByCoyByBranch(currentUser.getCompanyId(), currentUser.getBranchId(), 2));
            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
        }

        return new ModelAndView("editUser", model);
    }

    @RequestMapping(value = "/userRolesAjaxList", method = RequestMethod.GET)
    public @ResponseBody
    List<UserRoleBean> getRolesForGroup(
            @RequestParam(value = "groupId", required = true) String id) {
        return beanMapper.prepareListofUserRoleBean(userRoleService.listUserRoles(id));
    }

    @RequestMapping(value = "/branchesAjaxList", method = RequestMethod.GET)
    public @ResponseBody
    List<BranchBean> getBranchesForCompany(
            @RequestParam(value = "companyId", required = true) String id) {
        return beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(id));
    }

     @RequestMapping(value="/companys22", method = RequestMethod.GET)
    public ModelAndView listCompanys22(){
	 Map<String ,Object> model = new HashMap<String, Object>();
         
	 model.put("companys",  companyService.listCompanies());
	 return new ModelAndView("companysList", model);
    }
    // get page
     @RequestMapping(value = "/userpassreset", method = RequestMethod.GET)
   public ModelAndView userpassreset(@ModelAttribute("company") CompanyBean companyBean, BindingResult result) throws Exception  {
       Map model = new HashMap();
        //List listOfUsers = new ArrayList();
        
        //List<User> listOfUsers = userService.listUsersByCompany(companyBean.getId().toString());
         model.put("companies",  companyService.listCompanies());
         //model.put("userList",  listOfUsers);
              
         
       return new ModelAndView("/userpassreset",model);
    
   }
   
     @RequestMapping(value = "/userpassresetp", method = RequestMethod.POST)
   public ModelAndView userpassreset2(@ModelAttribute("company") CompanyBean companyBean, BindingResult result) throws Exception  {
       Map model = new HashMap();
        //List listOfUsers = new ArrayList();
        
        List<User> listOfUsers = userService.listUsersByCompany(companyBean.getId().toString());
         model.put("companies",  companyService.listCompanies());
         model.put("userList",  listOfUsers);
              
         
       return new ModelAndView("/resetlist",model);
    
   }
   
    // To reset user password by admin
    @RequestMapping(value = "/doPasswordReset", method = RequestMethod.POST)
    public ModelAndView doPasswordReset(/*@ModelAttribute("user") UserBean userBean*/
            @RequestParam(value = "id", required = true) int id, 
            /*BindingResult result, */HttpServletRequest req) {
        //System.out.println("userBean :: " + userBean);
        //System.out.println("id :: " + id);
        User user = userService.getUser(id);
       // User user = prepareModel(userBean);
        System.out.println("user.getEmail():: " + user.getEmail());
        //User profile = userService.getUserByEmail(user.getEmail());
 	  //String tempPass=passUTIL.generatePswd();
        //String clearPass=user.getPassword();
        String clearPass = passUTIL.generatePswd();
        //System.out.println("ClearPass :: " + clearPass);
        short one = 1;
        String encryptedPass = new PassEncoder().doEndcodePass(clearPass);
        user.setPassword(encryptedPass);
        user.setMustChangePass("Y");
        user.setAccountNonLocked(one);
        user.setAccountNonExpired(one);
        user.setCredentialsNonExpired(one);
        user.setLoginAttempts(0);
        user.setEnabled(1);
        //user.set
        boolean userUpdated = userService.addUser(user);
        boolean reset = userService.resetUserLogon(user);
        boolean mailSent = false;

        if (reset) {
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                //MB.setToemail(userBean.getEmail());
                MB.setToemail(user.getEmail());
                
                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                String mailBody = "";
                String template = "changeLogonPass-2.ftl";
                Map model = new HashMap();
                model.put("getUsername", user.getUsername());
                model.put("getEmail", user.getEmail());
                model.put("clearPass", clearPass);
                model.put("uri", uri);

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

			 //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (mailSent) {
            //req.getSession().invalidate();
            //return new ModelAndView("redirect:/userpassreset.jsp");
            return new ModelAndView("redirect:/doFeedback.htm?message=User Reset was successful&redirectURI=userpassreset.htm");
       } else if (reset) {
             return new ModelAndView("redirect:/doFeedback.htm?message=User Reset was successful&redirectURI=userpassreset.htm");
        } else {
            return new ModelAndView("redirect:/doFeedback.htm?message=User Reset was failed&redirectURI=userpassreset.htm");

        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private User prepareModel(UserBean userBean) {
        User user = new User();

        user.setAuthMode(userBean.getAuthMode());
        user.setBranchId(userBean.getBranchId());
        user.setCompanyId(userBean.getCompanyId());
        user.setEmail(userBean.getEmail());
        user.setGroupId(userBean.getGroupId());
        user.setUsername(userBean.getUsername());
        user.setPasswordTenure(userBean.getPasswordTenure());
        user.setLastLogonDate(userBean.getLastLogonDate());
        user.setPassword(userBean.getPassword());
        user.setPhone(userBean.getPhone());
        user.setUserId(userBean.getUserId());
        user.setEnabled(userBean.getEnabled());
        user.setDeleted(userBean.getDeleted());
        user.setCreatedBy(userBean.getCreatedBy());
        user.setCreationDate(userBean.getCreationDate());
        user.setLastModifiedBy(userBean.getLastModifiedBy());
        user.setLastModificationDate(userBean.getLastModificationDate());
        user.setMustChangePass(userBean.getMustChangePass());
        user.setId(userBean.getId());
        user.setIsBranchUser(userBean.getIsBranchUser());
        user.setAccountNonLocked(userBean.getAccountNonLocked());
        user.setAccountNonExpired(userBean.getAccountNonExpired());
        user.setCredentialsNonExpired(userBean.getCredentialsNonExpired());
        return user;
    }

    private List<UserBean> prepareListofBean(List<User> users) {
        List<UserBean> beans = null;

        if (users != null && !users.isEmpty() && users.size() > 0) {
            beans = new ArrayList<UserBean>();
            UserBean bean = null;

            for (User user : users) {
                bean = new UserBean();

                bean.setAuthMode(user.getAuthMode());
                bean.setBranchId(user.getBranchId());
                bean.setCompanyId(user.getCompanyId());

                bean.setEmail(user.getEmail());
                bean.setGroupId(user.getGroupId());
                bean.setUsername(user.getUsername());
                bean.setPasswordTenure(user.getPasswordTenure());

                bean.setLastLogonDate(user.getLastLogonDate());
                bean.setPassword(user.getPassword());
                bean.setPhone(user.getPhone());
                bean.setUserId(user.getUserId());
                bean.setId(user.getId());
                bean.setEnabled(user.getEnabled());
                bean.setDeleted(user.getDeleted());
                bean.setCreatedBy(user.getCreatedBy());
                bean.setCreationDate(user.getCreationDate());
                bean.setLastModifiedBy(user.getLastModifiedBy());
                bean.setLastModificationDate(user.getLastModificationDate());
                bean.setMustChangePass(user.getMustChangePass());
                bean.setIsBranchUser(user.getIsBranchUser());
                bean.setAccountNonLocked(user.getAccountNonLocked());
                bean.setAccountNonExpired(user.getAccountNonExpired());
                bean.setCredentialsNonExpired(user.getCredentialsNonExpired());
                beans.add(bean);
            }
        }

        return beans;
    }

    private UserBean prepareUserBean(User user) {
        UserBean bean = new UserBean();

        bean.setAuthMode(user.getAuthMode());
        bean.setBranchId(user.getBranchId());
        bean.setCompanyId(user.getCompanyId());
        bean.setEmail(user.getEmail());
        bean.setGroupId(user.getGroupId());
        bean.setUsername(user.getUsername());
        bean.setPasswordTenure(user.getPasswordTenure());
        bean.setLastLogonDate(user.getLastLogonDate());
        bean.setPassword(user.getPassword());
        bean.setPhone(user.getPhone());
        bean.setUserId(user.getUserId());
        bean.setId(user.getId());
        bean.setEnabled(user.getEnabled());
        bean.setDeleted(user.getDeleted());
        bean.setCreatedBy(user.getCreatedBy());
        bean.setCreationDate(user.getCreationDate());
        bean.setLastModifiedBy(user.getLastModifiedBy());
        bean.setLastModificationDate(user.getLastModificationDate());
        bean.setMustChangePass(user.getMustChangePass());
        bean.setIsBranchUser(user.getIsBranchUser());
        bean.setAccountNonLocked(user.getAccountNonLocked());
        bean.setAccountNonExpired(user.getAccountNonExpired());
        bean.setCredentialsNonExpired(user.getCredentialsNonExpired());
        return bean;
    }
   
}
