/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.controller;

import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.UserBean;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.financial.member.MemberEmailChange;
import com.sift.financial.member.MemberEmailChangeBean;
import com.sift.financial.member.services.MemberEmailChangeService;
import com.sift.financial.member.services.MemberManageImpl;
import com.sift.gl.AuditlogService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.MailBean;
import com.sift.webservice.utility.WebServiceUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Akpos
 */
@Controller 
public class MemberEmailChangeController {
 @Autowired
private CompanyService companyService;
 @Autowired
private BranchService branchService;
 @Autowired
 private HelperUtility helperUtility;
@Autowired
private MemberManageImpl memberService;
@Autowired
private MemberEmailChangeService emailchangeService;
 @Autowired
 private CurrentuserdisplayImpl user;
  private String clientIpAddress;
 EazyCoopUtility  eazyCoopUTIL=new EazyCoopUtility();
 WebServiceUtility webServiceUtility = new WebServiceUtility();

     @RequestMapping(value="/memberEmails", method = RequestMethod.GET)
      public ModelAndView memberEmails(@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
         //load coop branch by companyid
      
         model.put("cooplist",companyService.listCompanies() );
            return new ModelAndView("membermailchange", model);
   
   }
     
        @RequestMapping(value="/viewBranchMembers", method = RequestMethod.POST)
      public ModelAndView viewBranchMembers(@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
           int cooprid= memberEmailChangeBean.getCompanyid();
           int branch=memberEmailChangeBean.getBranchid();
            System.out.println("selected cooperative id--"+cooprid);
           System.out.println("selected branch member id--"+branch);
          model.put("branchmemb", helperUtility.listBranchMembers(cooprid, branch));
            return new ModelAndView("branchmembers", model);
   
   }
         @RequestMapping(value="/editBranchMembEmail", method = RequestMethod.GET)
      public ModelAndView editBranchMembEmail(@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          System.out.println("member id is ==="+memberEmailChangeBean.getMemberid());
         model.put("branchmembdet", memberService.getMemberDao().findById(memberEmailChangeBean.getMemberid()));
            return new ModelAndView("editbranchmemb", model);
   
   }
        @RequestMapping(value = "/addNewEmail", method = RequestMethod.POST)
    public ModelAndView addNewEmail(@ModelAttribute("membMail") MemberEmailChangeBean memberEmailChangeBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String redurlpath="";
        //check for email format validity
        System.out.println("the new email address is "+memberEmailChangeBean.getNewemail());
         if(helperUtility.validEmailFormat(memberEmailChangeBean.getNewemail())==true){
         memberEmailChangeBean.setCreatedate(new java.util.Date());
           MemberEmailChange memberemail = prepareModel(memberEmailChangeBean);
           emailchangeService.addEmail(memberemail);
            clientIpAddress = req.getRemoteAddr();
           //Audit Log Call
           auditlogcall(149 ,"MEMBER-EMAIL-UPDATE",clientIpAddress, user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),memberemail.getOldemail(),"PENDING APPROVAL",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
          //send message to admin
           List<UserBean> adminMails=  helperUtility.getRegistrarAdminMails();
             String admin1= adminMails.get(0).getEmail().toString();
               String admin2= adminMails.get(1).getEmail().toString();
                 String mailBody="Member Records has just been moved to Easycoop from Easycoopfin";
                 String subject="MEMBER EMAIL UPDATE";
                if( user.getCurruser().getUserId()== admin1){    
                    MailBean MB = new MailBean();
                    MB = eazyCoopUTIL.getMailConfig();
                    MB.setSubject(subject);
                    MB.setMailBody(mailBody);
                    MB.setToemail(admin2);
                   
                    eazyCoopUTIL.sendMail(MB);
                }else{
                     MailBean MB = new MailBean();
                    MB = eazyCoopUTIL.getMailConfig();
                    MB.setSubject(subject);
                    MB.setMailBody(mailBody);
                    MB.setToemail(admin1);     
                    eazyCoopUTIL.sendMail(MB);
               
                }
               
        redurlpath = "redirect:/doFeedback.htm?message=Email has been update successfully and sent for approval &redirectURI=memberEmails";
         }else{
          redurlpath = "redirect:/doFeedback.htm?message= Wrong Email Address Format &redirectURI=memberEmails";
         }
             
        return new ModelAndView(redurlpath);
    }
 //url call to load coop branch by companyid
     @RequestMapping(value="/listcoopbranch/{coopid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=*/*")
     @ResponseBody
     public List<BranchBean> listCoopBranches(@PathVariable("coopid") String coopid,@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	  List<BranchBean> branch_list_out = new ArrayList<>();
        // Map<String ,Object> model = new HashMap<String, Object>(); 
         //load coop branch by companyid
        // model.put("coopbranch", branchService.listBranchBeans(coopid));
           List<BranchBean> branch_list_dao= branchService.listBranchBeans(coopid);
            System.out.println("the  branch list size is " + branch_list_dao.size());
            for(BranchBean uws : branch_list_dao){
            BranchBean branch_model = new BranchBean();
     
            branch_model.setId(uws.getId());
             branch_model.setBranchName(uws.getBranchName());
            branch_list_out.add(branch_model);
       }
         
            return branch_list_out;
   
   }
     @RequestMapping(value="/viewEmailChanges", method = RequestMethod.GET)
      public ModelAndView viewEmailChanges(@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
     
         model.put("membersmail", prepareListEmailChangeBean( emailchangeService.listEmailChanges()) );
            return new ModelAndView("newmemberrecord", model);
   
   }
      @RequestMapping(value="/approveMemberMailChange", method = RequestMethod.POST)
      public ModelAndView approveMailChanges(@ModelAttribute("membMail")MemberEmailChangeBean memberEmailChangeBean, BindingResult result,HttpServletRequest req) {
	  String redurlpath="";
           clientIpAddress = req.getRemoteAddr();
          Map<String ,Object> model = new HashMap<String, Object>(); 
          
         
          String approvedby=req.getParameter("approvedby");
          String[] id=req.getParameterValues("id");
          for(int i=0; i<id.length; i++){
          MemberEmailChange memberEmailChange= emailchangeService.getPendingEmailChangesbyId(Integer.parseInt(id[i])); 
          
           helperUtility.approveMemberEmailChanges(id[i], approvedby ,memberEmailChange.getNewemail(), memberEmailChange.getMemberid(), memberEmailChange.getBranchid(),memberEmailChange.getCompanyid());
            Branch brchObj=branchService.getBranch(memberEmailChange.getBranchid());
           if (brchObj.getConnectToEazyCoop().equalsIgnoreCase("Y")){
               System.out.println("connected to easycoop");
	         String resource = "useremailup";
               String membno= memberService.getMemberDao().findById(memberEmailChange.getMemberid()).getMemberNo();
                 System.out.println("member no is "+membno);
            webServiceUtility.webserviceClient(resource, webServiceUtility.useremailupdate(memberEmailChange.getCompanyid(),memberEmailChange.getBranchid(),membno,memberEmailChange.getOldemail(),memberEmailChange.getNewemail()));
			            } 
           //Audit Log
             auditlogcall(150,"MEMBER-UPDATE-APPRV",clientIpAddress, user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),memberEmailChange.getNewemail(),"APPROVED",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
          }
             redurlpath = "redirect:/doFeedback.htm?message=Member Email Changes Approved  &redirectURI=viewEmailChanges";
        return new ModelAndView(redurlpath);
   
   }
  
      private MemberEmailChange prepareModel(MemberEmailChangeBean memberEmailChangeBean) {
        MemberEmailChange memberEmailChange = new MemberEmailChange();
        memberEmailChange.setCompanyid(memberEmailChangeBean.getCompanyid());
        memberEmailChange.setBranchid(memberEmailChangeBean.getBranchid());
        memberEmailChange.setCreatedby(memberEmailChangeBean.getCreatedby());
        memberEmailChange.setFirstname(memberEmailChangeBean.getFirstname());
        memberEmailChange.setMemberid(memberEmailChangeBean.getMemberid());
        memberEmailChange.setMiddlename(memberEmailChangeBean.getMiddlename());
        memberEmailChange.setNewemail(memberEmailChangeBean.getNewemail());
        memberEmailChange.setOldemail(memberEmailChangeBean.getEmailAdd1());
        memberEmailChange.setSurname(memberEmailChangeBean.getSurname());
        memberEmailChange.setApproved("N");
          memberEmailChange.setCreatedate(new java.util.Date());
        return memberEmailChange;

    }
    
      
       private List<MemberEmailChangeBean> prepareListEmailChangeBean(List<MemberEmailChange> allMembNewMail) {
        List<MemberEmailChangeBean> beans = null;
       
        if ((allMembNewMail != null )&& (! allMembNewMail.isEmpty())) {
            beans = new ArrayList<MemberEmailChangeBean>();
          MemberEmailChangeBean bean = null;

            for (MemberEmailChange memb: allMembNewMail ) {
                bean = new MemberEmailChangeBean();
           bean.setId(memb.getId());
           bean.setFirstname(memb.getFirstname());
           bean.setMiddlename(memb.getMiddlename());
           bean.setSurname(memb.getSurname());
           bean.setEmailAdd1(memb.getOldemail());
           bean.setNewemail(memb.getNewemail());
           bean.setMemberid(memb.getMemberid());
           bean.setCreatedby(memb.getCreatedby());
           bean.setCompanyName(companyService.getCompany(memb.getCompanyid()).getName());
           bean.setBranchName(branchService.getBranch(memb.getBranchid()).getBranchName());      
                beans.add(bean);
            }
        }

        return beans;
    }
    public void auditlogcall(int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       //ent.setEvent(1);
       //ent.setAction("GLGAC");
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
       AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
    
}
