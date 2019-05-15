/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccounttxnsImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Accounttxnenq;
import com.sift.gl.model.Accounttxnsdetail;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccounttxnsValidator;
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

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class AccounttxnsController{
   //@Autowired 
   private AccounttxnsImpl accounttxnsService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccounttxnsValidator accounttxnsValidator;
   private Accounttxnsdetail accounttxnsdet;
   List<Account> accs;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String clientIpAddress;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accounttxnsValidator = new AccounttxnsValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setAccounttxnsService(AccounttxnsImpl accounttxnsService) {
      this.accounttxnsService = accounttxnsService;
      accounttxnsdet = new Accounttxnsdetail();
   }
 
   
   
   
   @RequestMapping(value = {"gl/gl_accounttxnview","gl_accounttxnview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView accounttxnslist(@ModelAttribute("txnparadet")Accounttxnenq accounttxnenq, BindingResult result) throws Exception  {
     String accountno = accounttxnenq.getAccountno();
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String startdateinstring = "";
     String enddateinstring = "";
     Date startdate = new Date();
     Date enddate = new Date();
     if (accounttxnenq.getStartdate()!=null) {
     startdateinstring = accounttxnenq.getStartdate();
     }
     if (accounttxnenq.getEnddate()!=null) {
     enddateinstring = accounttxnenq.getEnddate();
     }
	try {
             startdate = formatter.parse(startdateinstring);
             enddate = formatter.parse(enddateinstring);
	} catch (ParseException e) {
		//e.printStackTrace();
	}
     
     int dbranch = user.getCurruser().getBranch();
     int dcompany = user.getCurruser().getCompanyid();
     Map model = new HashMap();
     model.put("accounttxns",accounttxnsService.retrieveAccounttxns(accountno,dbranch,dcompany,startdate,enddate));
     model.put("accs", accounttxnsService.getAccounts(dbranch,dcompany));
     //model.put("totdeb", accounttxnsService.getDebits());
     accounttxnenq.setTotdr(accounttxnsService.getDebits());
     accounttxnenq.setTotcr(accounttxnsService.getCredits());
     accounttxnenq.setNetmvmt(accounttxnsService.getNetmvmt());
     accounttxnenq.setBal(accounttxnsService.getBal());
     
     //model.put("totcre", accounttxnsService.getCredits());
     return new ModelAndView("gl/gl_accounttxnview",model);
     //
    /* String dbranch = user.getBranch();
     String dcompany = user.getCompanyid();  
     String daccess = user.getAccessLevel();
     
     model.addAttribute("usermodules", currentuserdisplayService.getModules(daccess,dbranch,dcompany));
     model.addAttribute("usermenus", currentuserdisplayService.getModulemenus(daccess,dbranch,dcompany));
     */
     //return "gl/gl_accounttxnview";
     //return new ModelAndView("accountgroupList", "command", accountgroups);
   }
   @ModelAttribute("accs")
   public List<Account> getAllAccs()  throws Exception  {
     return accounttxnsService.getAccounts(user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
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
