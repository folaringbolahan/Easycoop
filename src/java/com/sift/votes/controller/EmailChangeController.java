/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;

import com.sift.admin.bean.UserBean;
import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.bean.VotMemberMailBean;
import com.sift.votes.bean.VotMembersBean;
import com.sift.votes.model.VotMemberMail;
import com.sift.votes.model.VotMembers;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotMemberMailService;
import com.sift.votes.service.VotMemberService;
import com.sift.votes.utility.AgmHelperUtility;
import com.sift.votes.utility.VotBeanMapperUtility;
import com.sift.votes.utility.VotMailBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Akpos
 */
@Controller
public class EmailChangeController {
    @Autowired
    private VotMemberService votMemberService;
    @Autowired
    private VotAgmService votAgmService;
    @Autowired
    private AgmHelperUtility agmhelperUTIL;
    @Autowired
    VotMemberMailService votMemberMailService;
    VotBeanMapperUtility votutility = new VotBeanMapperUtility();
    
      @RequestMapping(value = "/votMembEmails", method = RequestMethod.GET)
       public ModelAndView listAgms(@ModelAttribute("votAgm") VotAgmBean votagmBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("agmList", votAgmService.listActiveAgms());
       return new ModelAndView("votmembagm", model); 

    }
     @RequestMapping(value = "/viewVotMembEmails", method = RequestMethod.POST)
    public ModelAndView listVotMembersEmail(@ModelAttribute("votAgm") VotAgmBean votagmBean, BindingResult result, HttpServletRequest req) {
        String agmid= req.getParameter("description");
        System.out.println("the agmid is "+agmid);
        int agmid_int= Integer.parseInt(agmid);
         Map<String, Object> model = new HashMap<String, Object>();
        model.put("member",prepareListofVotMembersBean(votMemberService.listAgmMemberDetailsByAgmid(agmid_int)));
        return new ModelAndView("viewMembersEmail", model); 

    }
     @RequestMapping(value = "/editVotMembEmail", method = RequestMethod.GET)
    public ModelAndView editVotMemberEmail(@ModelAttribute("votmembers") VotMembersBean votmembersBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        System.out.println("selected id"+votmembersBean.getMemberid());
        model.put("membersdet",prepareVotMembersBean(votMemberService.getVotMemberDetails(votmembersBean.getMemberid())));
        return new ModelAndView("editvotmail", model); 

    }

      @RequestMapping(value = "/votMailApprv", method = RequestMethod.GET)
    public ModelAndView listVotMailsForApprv(@ModelAttribute("votmembermail") VotMemberMailBean votmembermailBean, BindingResult result, HttpServletRequest req) {
         Map<String, Object> model = new HashMap<String, Object>();
        model.put("mails",prepareListofVotMemberMailBean(votMemberMailService.listVotMailChangesForApprv()));
        return new ModelAndView("viewmailapprv", model); 

    }
     
       @RequestMapping(value = "/updateVotMemberEmails", method = RequestMethod.POST)
    public ModelAndView updateEmailChanges(@ModelAttribute("votmembermail") VotMemberMailBean votmembermailBean, BindingResult result, HttpServletRequest req) {
        String oldemail= req.getParameter("oldemail");
        String newemail= req.getParameter("newemail");
        String id= req.getParameter("agmid");
         String createdby= req.getParameter("createdby");
        int id_int=Integer.parseInt(id);
        String redurlpath="";
        boolean emailexist;
        //function to check if mail exist
         emailexist= votMemberMailService.checkVotMailExistence(oldemail);
        //if mail already exist in vot_mailchange table run update
         if(emailexist==true){
        agmhelperUTIL.updateVotMembMailRecord(id_int,newemail,oldemail);
        
         
         }else{
         //insert freshrecord
             System.out.println("old email "+votmembermailBean.getOldemail());
          VotMemberMail votmembmail = prepareVotMemberMailModel(votmembermailBean);
          votMemberMailService.addVotMemberMail(votmembmail);
         
         
      }
        //else insert into the table fresh record
       //send mail to admin for approval
          //send mail
            List<UserBean> mails= agmhelperUTIL.getAdminMails();
               String admin1= mails.get(0).getEmail().toString();
               String admin2= mails.get(1).getEmail().toString();
                System.out.println("the admin mail1 is "+ mails.get(0).getEmail().toString());
                System.out.println("the logged in User is  "+ createdby);
                     String mailBody="An Update has been made for Agm member emails";
                    if( createdby.equals(admin1)){
                     System.out.println("i am in this block");
                    
                       VotMailBean MB = votutility.getMailConfig();
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SMTP PASSWORD "+MB.getPassword());
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SSLORTLS "+MB.getSslortls());
                      MB.setSubject("EMAIL UPDATE");
                      MB.setToemail(admin2);
                      MB.setMailBody(mailBody);
                     votutility.sendMail(MB);
                       }else if(createdby.equals(admin2)){
                     
                      
                       VotMailBean MB = VotBeanMapperUtility.getMailConfig();
                      MB.setSubject("EMAIL UPDATE");
                      MB.setToemail(admin1);
                      MB.setMailBody(mailBody);
                       votutility.sendMail(MB);
         }
         
       redurlpath = "redirect:/doFeedback.htm?message=Email Changed Successfully and sent for approval&redirectURI=votMembEmails";
       return new ModelAndView(redurlpath);

    }
      
      
      @RequestMapping(value = "/approveEmailChanges", method = RequestMethod.POST)
    public ModelAndView approvemails(@ModelAttribute("votmembermail") VotMemberMailBean votmembermailBean, BindingResult result, HttpServletRequest req) {
          String redurlpath="";
          String[] agmid= req.getParameterValues("agmid");
          String[] id= req.getParameterValues("id");
          String approvedby= req.getParameter("approvedby");
          String[] newemail= req.getParameterValues("newemail");
          String[] oldemail= req.getParameterValues("oldemail");
          for(int i=0; i<id.length; i++){
          agmhelperUTIL.approveVotMemberEmail(id[i],agmid[i],approvedby,newemail[i],oldemail[i]);
          }
         redurlpath = "redirect:/doFeedback.htm?message=Approval For Mail change Successful&redirectURI=votMailApprv";
       return new ModelAndView(redurlpath); 

    }
   
     
    private List<VotMembersBean> prepareListofVotMembersBean(List<VotMembers> allvotmembers) {
        List<VotMembersBean> beans = null;
       
        if ((allvotmembers != null) && (!allvotmembers.isEmpty())) {
            beans = new ArrayList<VotMembersBean>();
           VotMembersBean bean = null;

            for (VotMembers votmemb : allvotmembers) {
                bean = new VotMembersBean();
                bean.setMemberid(votmemb.getMemberid());
                bean.setAgmid(votmemb.getAgmid());
                bean.setEmail(votmemb.getEmail());
                bean.setFirstname(votmemb.getFirstname());
                bean.setMiddlename(votmemb.getMiddlename());
                bean.setSurname(votmemb.getSurname());
                beans.add(bean);
            }
        }

        return beans;
    }
private VotMembersBean prepareVotMembersBean(VotMembers votmembers){
		 VotMembersBean bean = new VotMembersBean();
                  bean.setMemberid(votmembers.getMemberid());
                  bean.setEmail(votmembers.getEmail());
                  bean.setFirstname(votmembers.getFirstname());
                  bean.setMiddlename(votmembers.getMiddlename());
                  bean.setSurname(votmembers.getSurname()); 
                  bean.setAgmid(votmembers.getAgmid());
		  return bean;
 }
private VotMemberMail prepareVotMemberMailModel(VotMemberMailBean votmembermailBean) {
        VotMemberMail votmembermail = new VotMemberMail();
        votmembermail.setId(votmembermailBean.getId());
        votmembermail.setAgmid(votmembermailBean.getAgmid());
        votmembermail.setMemberid(votmembermailBean.getMemberid());
        votmembermail.setFirstname(votmembermailBean.getFirstname());
        votmembermail.setMiddlename(votmembermailBean.getMiddlename());
        votmembermail.setSurname(votmembermailBean.getSurname());
        votmembermail.setNewemail(votmembermailBean.getNewemail());
        votmembermail.setOldemail(votmembermailBean.getOldemail());
        votmembermail.setCreatedby(votmembermailBean.getCreatedby());
        votmembermail.setCreatedate(new java.util.Date());
        return votmembermail;
        
    }
    private List<VotMemberMailBean> prepareListofVotMemberMailBean(List<VotMemberMail> allvotmembermail) {
        List<VotMemberMailBean> beans = null;
       
        if ((allvotmembermail != null) && (!allvotmembermail.isEmpty())) {
            beans = new ArrayList<VotMemberMailBean>();
           VotMemberMailBean bean = null;

            for (VotMemberMail votmembmail : allvotmembermail) {
                bean = new VotMemberMailBean();
                bean.setId(votmembmail.getId());
                bean.setAgmid(votmembmail.getAgmid());
                bean.setMemberid(votmembmail.getMemberid());
                bean.setFirstname(votmembmail.getFirstname());
                bean.setMiddlename(votmembmail.getMiddlename());
                bean.setSurname(votmembmail.getSurname());
                bean.setOldemail(votmembmail.getOldemail());
                bean.setNewemail(votmembmail.getNewemail());
                bean.setCreatedby(votmembmail.getCreatedby());
                beans.add(bean);
            }
        }

        return beans;
    }

}
