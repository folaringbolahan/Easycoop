/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.controller;

import com.sift.financial.member.MemberProfileUpdate;
import com.sift.financial.member.services.MemberProfileUpdateService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.utility.HelperUtility;
import java.util.HashMap;
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
public class MemberProfileUpdateController {
  @Autowired
    private CurrentuserdisplayImpl user; 
  @Autowired
  MemberProfileUpdateService  memberProfileUpdateService;
  @Autowired
  private HelperUtility helperUTIL;
  
   @RequestMapping(value = "/showAwaitingUpdate", method = RequestMethod.GET)
 public ModelAndView awaitingProfileUpdate(@ModelAttribute("memberprofileupdate")MemberProfileUpdate membProfileUpdate, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
          
         int dbranch = user.getCurruser().getBranch(); 
         int dcompany = user.getCurruser().getCompanyid();
         model.put("memberupdate", memberProfileUpdateService.listMemberProfileUpdate(dbranch,dcompany) );
          return new ModelAndView("members/memberupdate", model);
 }     
 
      @RequestMapping(value = "/approveUpdate", method = RequestMethod.POST)
 public ModelAndView approveUpdate(@ModelAttribute("memberprofileupdate")MemberProfileUpdate membProfileUpdate, BindingResult result,HttpServletRequest req) {
	 String redurlpath="";
         
       
             String[] membupdate_id= req.getParameterValues("id");
                 
          //String[] branchid= req.getParameterValues("branchid");
          // String[] companyid= req.getParameterValues("companyid");
          //MemberProfileUpdate membUpdateObj memberProfileUpdateService.getPendingProfilebyId(id);
         String[] changetype=req.getParameterValues("changetype");
         String[] fieldvalue=req.getParameterValues("fieldvalue");
         //String fieldvalue=req.getParameter("fieldvalue");
          
         String[] memberno=req.getParameterValues("memberno");
         int branchid= membProfileUpdate.getBranchid();
         int companyid= membProfileUpdate.getCompanyid();
        // System.out.println("branch id is =="+bid +"companyid----"+compid);
         
            
         
        String loggedUser=user.getCurruser().getUserName();
        System.out.println("loggedin user at this point is ..."+loggedUser);
        System.out.println("size of the id ..."+membupdate_id.length);
         //update memberupdatepending table, change approved  to Y 
        for(int i=0; i<membupdate_id.length; i++){
           MemberProfileUpdate membUpdateObj= memberProfileUpdateService.getPendingProfilebyId(Integer.parseInt(membupdate_id[i])); 
           String changeType=membUpdateObj.getChangetype();
           if("phonenumber".equals(changeType)){
              System.out.println("value of i ..."+ i);
              
            //int phn_val=Integer.parseInt(id[i]);
               //System.out.println("++++ "+ fieldvalue[i]);
             System.out.println("phone number passed ++++ "+ membUpdateObj.getFieldvalue());
             System.out.println("Member number passed ++++ "+ membUpdateObj.getMemberno());
             String phn_num=membUpdateObj.getFieldvalue();
             String memb_no=membUpdateObj.getMemberno();
              System.out.println("member id ..."+membupdate_id[i]);
            
         helperUTIL.UpdateMemberPhoneNumber( Integer.parseInt(membupdate_id[i]),phn_num, memb_no, branchid, companyid,loggedUser);
        } 
         if("name".equals(changeType)){
         //   System.out.println("name value ... "+ fieldvalue[i]);
          MemberProfileUpdate membprofileUpdateObj= memberProfileUpdateService.getPendingProfilebyId(Integer.parseInt(membupdate_id[i]));
       System.out.println("member name passed ++++ "+ membprofileUpdateObj.getFieldvalue());
             System.out.println("Member number passed ++++ "+ membprofileUpdateObj.getMemberno());
             String name=membprofileUpdateObj.getFieldvalue();
             String memb_no=membprofileUpdateObj.getMemberno();
             
            
            helperUTIL.UpdateMemberName(Integer.parseInt(membupdate_id[i]), name, memb_no,branchid,companyid,loggedUser);
         }
          }
         
        
         redurlpath = "redirect:/doFeedback.htm?message= Member Record has been updated successfully &redirectURI=showAwaitingUpdate";   
       return new ModelAndView(redurlpath);  
 }
}
