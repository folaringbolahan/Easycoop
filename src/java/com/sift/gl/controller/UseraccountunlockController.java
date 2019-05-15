/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.AuditlogService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.UseraccountunlockImpl;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Users;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
//@RequestMapping("gl/")
public class UseraccountunlockController{
   //@Autowired 
   private UseraccountunlockImpl useraccountunlockService;
   private Users accountdet;
   private String clientIpAddress;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountdet = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setUseraccountunlockService(UseraccountunlockImpl useraccountunlockService) {
      this.useraccountunlockService = useraccountunlockService;
      accountdet = new Users();
   }
  
   @ModelAttribute("clientIpAddress")
	public String populateClientIpAddress(HttpServletRequest request) {
          return request.getRemoteAddr();
   }
  
   @RequestMapping(value = "/pfaccountsunlock",method=RequestMethod.GET, params="id")
   public ModelAndView pfeditaccount(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     Map dmodel = new HashMap();
     dmodel.put("accountdet", useraccountunlockService.getAccount(dcode));
     return new ModelAndView("/pfaccountsunlock",dmodel);
   }
   
   @RequestMapping(value = "/pfaccountsunlocksave",method=RequestMethod.POST)
   public ModelAndView pfeditaccountsave( @ModelAttribute("accountdet")Users accountdet2, BindingResult result)  {
     Integer did = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     did = accountdet2.getId() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dcode = accountdet2.getUserId() ;
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     String redurlpath = "";
     String retval = useraccountunlockService.editAccount(did);
     if (retval.equals("ok")) {
         auditlogcall(9,"ADGLUN",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,retval.toUpperCase(),user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
         redurlpath = "redirect:accountsunlock.htm";
     }
     else {
         auditlogcall(9,"ADGLUN",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,"User Account Unlock Failed",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
         redurlpath = "redirect:includes/error.htm";
     }
     return new ModelAndView(redurlpath,"accountdet",accountdet2);
   }
   
    
    @RequestMapping(value = {"accountsunlock"}, method = {RequestMethod.GET,RequestMethod.POST})
     public String accountedit(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       List<Users> accounts = useraccountunlockService.getAccounts(dbranch,dcompany) ;
       model.addAttribute("accounts", accounts);
     
     return "accountsunlock";
  }

  @RequestMapping(value = {"accountsunlockcoy"}, method = {RequestMethod.GET,RequestMethod.POST})
     public String accounteditcoy(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       //Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       List<Users> accounts = useraccountunlockService.getAccounts(dcompany) ;
       model.addAttribute("accountscoy", accounts);
     
     return "accountsunlockcoy";
  }
  
  @RequestMapping(value = {"accountsunlocksup"}, method = {RequestMethod.GET,RequestMethod.POST})
     public String accounteditsup(ModelMap model,HttpServletRequest request)  {
       //Integer dcompany = user.getCurruser().getCompanyid();
       //Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       List<Users> accounts = useraccountunlockService.getAccounts() ;
       model.addAttribute("accountssup", accounts);
     
     return "accountsunlocksup";
  }

   @ModelAttribute("accounts")
   public List<Users> getAllAccounts() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return useraccountunlockService.getAccounts(dbranch,dcompany);
   }
   
   @ModelAttribute("accountscoy")
   public List<Users> getAllAccountscoy() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     return useraccountunlockService.getAccounts(dcompany);
   }
   
   @ModelAttribute("accountssup")
   public List<Users> getAllAccountssup() {
     // Delegate to service
     return useraccountunlockService.getAccounts();
   }
   
   public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
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
