/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Company;
import com.sift.gl.model.Moduledetails;
import com.sift.gl.model.Modulemenudetails;
import com.sift.gl.model.Users;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("/")
public class IndexController{
   @Autowired 
   private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   ////@Autowired
   //private Users userdet;
   private Users duser=new Users();
   private Company dcoy = new Company();
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          //accountgroupValidator = new AccountgroupValidator();
          //user = new Users();
   }
   ///@Autowired
   public void setUserdet(Users userdet) {
      this.duser = userdet;
   }
   
   public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
      this.currentuserdisplayService = currentuserdisplayService;
   }
   
    
   @RequestMapping(value = {"index"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String navmenu(ModelMap model)  {
    /* String dbranch = user.getBranch();
     String dcompany = user.getCompanyid();  
     String daccess = user.getAccessLevel();
     */
       
      //
       String currentUserName = "";
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     if (!(authentication instanceof AnonymousAuthenticationToken)) {
        currentUserName = authentication.getName();
     }
     // 
     
      duser = currentuserdisplayService.getdCurruser(currentUserName);
     Integer dbranch = duser.getBranch();
     Integer dcompany = duser.getCompanyid();  
     String daccess = duser.getAccessLevel();
     String mustChangePass = duser.getMustChangePassword();
     dcoy = currentuserdisplayService.getCompanydetails(dbranch,dcompany);
     model.addAttribute("usermodules", currentuserdisplayService.getModules(daccess,dbranch,dcompany));
     model.addAttribute("usermenus", currentuserdisplayService.getModulemenus(daccess,dbranch,dcompany)); 
     model.addAttribute("mustChangePass", mustChangePass); 
     //currentuserdisplayService.getdCurruser(currentUserName);
     
     return "index";
   }

   
   @ModelAttribute("usermodules")
   public List<Moduledetails> getAllModules(String daccess,Integer dbranch,Integer dcompany) {
     return currentuserdisplayService.getModules(duser.getAccessLevel(),duser.getBranch(),duser.getCompanyid());
   }
   @ModelAttribute("usermenus")
   public List<Modulemenudetails> getAllModulemenus(String daccess,Integer dbranch,Integer dcompany) {
     return currentuserdisplayService.getModulemenus(duser.getAccessLevel(),duser.getBranch(),duser.getCompanyid());
   }
}
