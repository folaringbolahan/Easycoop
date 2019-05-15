/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccountsetupValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
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
@RequestMapping("gl/")
public class AccountsetupController{
   //@Autowired 
   private AccountsetupImpl accountsetupService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccountsetupValidator accountsetupValidator;
   private Account accountdet;
   private String accountseg;
   private String clientIpAddress;
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String lastretmess = "";
    /**
     *
     * @param binder
     */
    @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountsetupValidator = new AccountsetupValidator();
          accountdet = new Account();
          //user = new Users();
   }
   //@Autowired
    /**
     *
     * @param user
     */
    public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
    /**
     *
     * @param accountService
     */
    public void setAccountsetupService(AccountsetupImpl accountService) {
      this.accountsetupService = accountService;
      accountdet = new Account();
   }
   
  /// public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
  ///    this.currentuserdisplayService = currentuserdisplayService;
  /// }
    /**
     *
     * @param request
     * @return
     */
    @ModelAttribute("clientIpAddress")
	public String populateClientIpAddress(HttpServletRequest request) {
          return request.getRemoteAddr();
	}

   
    /**
     *
     * @param accountdet2
     * @param result
     * @return
     */
    @RequestMapping(value = {"gl/gl_accountadd","/gl_accountadd"},method=RequestMethod.POST)
   public ModelAndView addaccountgroup( @ModelAttribute("accountdet")Account accountdet2, BindingResult result)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     if (accountdet2.getDateOpenedstr()!=null) {
     opendateinstring = accountdet2.getDateOpenedstr();
     }
     
	try {
             openeddate = formatter.parse(opendateinstring);
        } catch (ParseException e) {
		//e.printStackTrace();
	}
     
     accountdet = accountdet2;
     accountsetupValidator.setCoyid(user.getCurruser().getCompanyid());
     accountsetupValidator.validate(accountdet2, result);
     
    
     
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      //  return "redirect:addaccountgroup.htm";
      // return new ModelAndView("gl_accountgrouplist","accountgroupdet",accountgroupdet2);
      return new ModelAndView("gl/gl_account");
     }
     
      ddesc = accountdet2.getName() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     dacno = accountdet2.getAccountNo();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     dacgrp = accountdet2.getAcGroup();
     dacstruct = accountdet2.getAcStruct();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     String dcurrency = user.getCurrusercompany().getBaseCurrency();
    // String ddateopened = accountdet2.getDateOpenedstr();
     String dbaltype = accountdet2.getBalanceType();
     boolean dcntrlac = accountdet2.getControlAccount();
     String dactiveorclosed = accountdet2.getActiveorclosed();
     boolean dblocked = accountdet2.getBlocked();
     String dcntrlacno = accountdet2.getControlAccountno();
     
    String retval = accountsetupService.addAccount(dacno,ddesc,dacgrp,dacstruct,dcurrency, openeddate,dbaltype,dcntrlac,dcntrlacno,dactiveorclosed,dblocked,dbranch,dcompany,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
     //redurlpath = "redirect:accountgrouplist.htm";
     if (retval.equalsIgnoreCase("ok")) {
         redurlpath = "redirect:gl_account.htm";
     }
     else {
         redurlpath = "redirect:/error.htm?er=" + retval;
     }
     accountdet = new Account();
     lastretmess = retval;
     //return redurlpath;
     return new ModelAndView(redurlpath,"accountdet",accountdet2);
   }
   
    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gl_remaccount",method=RequestMethod.GET)
   public ModelAndView removeaccount(HttpServletRequest request,HttpServletResponse response) throws Exception {
     //Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     String dcode = ServletRequestUtils.getRequiredStringParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     clientIpAddress = request.getRemoteAddr();
     String retval = accountsetupService.removeAccount(dcode,dbranch,dcompany,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
     String redurlpath;
     if (retval.equalsIgnoreCase("ok")) {
         redurlpath = "redirect:gl_editaccount.htm";
     }
     else {
         redurlpath = "redirect:/error.htm?er=" + retval;
        
     }
     return new ModelAndView(redurlpath);
   }
  
  
    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gl_pfeditaccount",method=RequestMethod.GET, params="id")
   public ModelAndView pfeditaccount(HttpServletRequest request,HttpServletResponse response) throws Exception {
     String dcode = ServletRequestUtils.getRequiredStringParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
          
     Map dmodel = new HashMap();
     dmodel.put("accountdet", accountsetupService.getAccount(dcode,dbranch,dcompany));
     dmodel.put("accountgroups", accountsetupService.getAccountgroups(dcompany));
     dmodel.put("accountstructs", accountsetupService.getAccountstructs(dcompany));
     return new ModelAndView("gl/gl_pfeditaccount",dmodel);
   }
   
    /**
     *
     * @param accountdet2
     * @param result
     * @return
     */
    @RequestMapping(value = "/gl_pfeditaccountsave",method=RequestMethod.POST)
   public ModelAndView pfeditaccountsave( @ModelAttribute("accountdet")Account accountdet2, BindingResult result)  {
     //System.out.println(accountdet2.getName()+ " name");
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     if (accountdet2.getDateOpenedstr()!=null) {
     opendateinstring = accountdet2.getDateOpenedstr();
     }
     
	try {
             openeddate = formatter.parse(opendateinstring);
        } catch (ParseException e) {
		//e.printStackTrace();
	}
     
    // accountdet = accountdet2;
     accountsetupValidator.setCoyid(user.getCurruser().getCompanyid());
     accountsetupValidator.setMode("E");
     accountsetupValidator.validate(accountdet2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("gl/gl_pfeditaccount","accountdet",accountdet2);
     }
     
    
     ddesc = accountdet2.getName() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     //dacno = accountdet2.getAccountNo();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     dacno = accountdet2.getAccountNo();
     dacgrp = accountdet2.getAcGroup();
     dacstruct = accountdet2.getAcStruct();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     String dcurrency = user.getCurrusercompany().getBaseCurrency();
    // String ddateopened = accountdet2.getDateOpenedstr();
     String dbaltype = accountdet2.getBalanceType();
     boolean dcntrlac = accountdet2.getControlAccount();
     String dactiveorclosed = accountdet2.getActiveorclosed();
     boolean dblocked = accountdet2.getBlocked();
     String dcntrlacno = accountdet2.getControlAccountno();
     //System.out.println("The company code is - " + dcompany);
     
     String retval = accountsetupService.editAccount(dacno,ddesc,dacgrp,dacstruct,dcurrency, openeddate,dbaltype,dcntrlac,dcntrlacno,dactiveorclosed,dblocked,dbranch,dcompany,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
     
     
     if (retval.equals("ok")) {
         redurlpath = "redirect:gl_editaccount.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     //accountdet = new Account();
     return new ModelAndView(redurlpath,"accountdet",accountdet2);
   }
   
    /**
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/gl_account"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String account(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       accountdet.setBalanceType("C");
       accountdet.setActiveorclosed("a");
       clientIpAddress = request.getRemoteAddr();
     //List<Account> accounts = accountsetupService.getAccounts(dbranch,dcompany) ;
     List<Account> ctrlaccs = accountsetupService.getControlAccounts(dbranch,dcompany) ;
     model.addAttribute("accountgroups", accountsetupService.getAccountgroups(dcompany));
     model.addAttribute("accountdet", accountdet);
     model.addAttribute("ctrlaccs", ctrlaccs);
     model.addAttribute("accountstructs", accountsetupService.getAccountstructs(dcompany));
     model.addAttribute("lastretmess", lastretmess);
    // model.addAttribute("accreportgroups", accountsetupService.getAccountgrpreps()); 
    
     return "gl/gl_account";
  }
   
   
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"gl/gl_editaccount","/gl_editaccount"}, method = {RequestMethod.GET,RequestMethod.POST})
     public String accountedit(ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       List<Account> accounts = accountsetupService.getAccounts(dbranch,dcompany) ;
       model.addAttribute("accounts", accounts);
     
     return "gl/gl_editaccount";
  }



    /**
     *
     * @return
     */
    @ModelAttribute("accounts")
   public List<Account> getAllAccounts() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return accountsetupService.getAccounts(dbranch,dcompany);
   }
   
    /**
     *
     * @return
     */
    @ModelAttribute("ctrlaccs")
   public List<Account> getControlAccounts() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return accountsetupService.getControlAccounts(dbranch,dcompany);
   }
   
    /**
     *
     * @param acno
     * @return
     */
    @RequestMapping(value = "/getAccdet.htm", method = RequestMethod.GET)
   public @ResponseBody Account getDAccount(@RequestParam("accno") String acno) {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     //System.out.println("was called " + acno);
     Account dacc = accountsetupService.getAccount(acno,dbranch,dcompany);
     accountdet = dacc;
     //ModelMap model;
     //model.addAttribute("accountdet", dacc);
     return dacc;
   }
  
    /**
     *
     * @param acno
     * @param acstr
     * @return
     */
    @RequestMapping(value = "/getAccseg.htm", method = RequestMethod.GET)
   public @ResponseBody List<String> getDaccountseg(@RequestParam("accno") String acno,@RequestParam("accstr") String acstr) {
     // Delegate to service
      Integer  dcompany = user.getCurruser().getCompanyid(); 
      List<String> daccsg = new ArrayList<String>();
      //accountsetupService.getAccountsegs(acno,acstr,dcompany);
      daccsg.add(accountsetupService.getAccountsegs(acno,acstr,dcompany));
     //accountseg = daccsg;
     System.out.println("acseg " + daccsg);
     return daccsg;
   }
   
    /**
     *
     * @return
     */
    @ModelAttribute("accountstructs")
   public List<Accountgrprepdetails> getAllAccountstructs() {
     Integer  dcompany = user.getCurruser().getCompanyid();  
     return accountsetupService.getAccountstructs(dcompany);
   }
    /**
     *
     * @return
     */
    @ModelAttribute("accountgroups")
   public List<Accountgroupdetail> getAllAccountgroups() {
     Integer  dcompany = user.getCurruser().getCompanyid();
     return accountsetupService.getAccountgroups(dcompany);
   }
}
