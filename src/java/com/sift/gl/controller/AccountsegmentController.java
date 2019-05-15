/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.AccountsegValidator;
import com.sift.gl.model.Accountsegmentdetlist;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.dao.AccountsegImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Users;
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
public class AccountsegmentController{
   //@Autowired 
   private AccountsegImpl accountsegService;
   //@Autowired
   private AccountsegValidator accountsegValidator;
   private List<Accountsegmentdetails> accountsegs;
   private Accountsegmentdetlist accountsegmentdetlist = new Accountsegmentdetlist();
   private String clientIpAddress;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountsegValidator = new AccountsegValidator();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setAccountsegService(AccountsegImpl accountsegService) {
      this.accountsegService = accountsegService;
     // accountsegdet = new Accountsegmentdetails();
   }
   
   
   @RequestMapping(value = "/gl_upaccountseg",method=RequestMethod.POST)
    public ModelAndView addaccountseg( @ModelAttribute("accountsegmentdetlist")Accountsegmentdetlist accountsegs2, BindingResult result)  {
     int dcompany = 0;
     
     //this.accountsegmentdetlist.setAccsegdets(accountsegs2);
     this.accountsegmentdetlist=accountsegs2;
     int ii = 0;
     
     Accountsegmentdetlist acseglist = new Accountsegmentdetlist();
     acseglist=accountsegs2;
     accountsegValidator.validate(acseglist.getAccsegdets(), result);
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
       return new ModelAndView("gl/gl_accountseg");
      }
     
     String redurlpath;
     
     String accsegno = "";
     Accountsegmentdetlist acseglist2 = new Accountsegmentdetlist();
     List<Accountsegmentdetails> accountsegs3 = new LinkedList<Accountsegmentdetails>();
     int k = 0;
     for (Accountsegmentdetails acsegdet : acseglist.getAccsegdets()) {
         //System.out.println("kountere" +k);
         //System.out.println("kountere4" + acsegdet.getName());
      if(acsegdet.getInuse()==true) {
          accountsegs3.add(acsegdet);
          k = k +1;
          accsegno = accsegno  + acsegdet.getSegmentid() + ", ";
         //  acseglist.getAccsegdets().add(new Accountsegmentdetails(acsegdet.getName(),acsegdet.getType(),acsegdet.getLength(),acsegdet.getMandatory(),acsegdet.getPredefined()));
      }
     }
      acseglist2.setAccsegdets(accountsegs3);
    
     dcompany = user.getCurruser().getCompanyid();
     //System.out.println("The company code is - " + dcompany);
     
     String retval = accountsegService.addData(acseglist2,dcompany);
     //redurlpath = "redirect:accountseglist.htm";
     if (retval.equals("ok")) {
         auditlogcall(7,"GLASS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),accsegno,retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_accountseg";
     }
     else {
         auditlogcall(7,"GLASS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),accsegno,retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error";
     }
     //accountsegdet = new Accountsegmentdetails();
     //return redurlpath;
     return new ModelAndView(redurlpath,"accountsegmentdetlist",accountsegmentdetlist);
   }
 
    
   @RequestMapping(value = {"gl/gl_accountseg","/gl_accountseg"}, method = {RequestMethod.GET})
   public String accountseglist(ModelMap model,HttpServletRequest request)  {
     Accountsegmentdetlist acseglist2 = new Accountsegmentdetlist();
     acseglist2.setAccsegdets(accountsegService.getAccountsegs(user.getCurruser().getCompanyid()));
     model.addAttribute("accountsegmentdetlist",acseglist2 );
     clientIpAddress = request.getRemoteAddr();
    // model.addAttribute("accountsegdet", accountsegdet);
     return "gl/gl_accountseg";
    }

   @ModelAttribute("accountsegmentdetlist")
   public Accountsegmentdetlist getAllAccountsegs() {
        accountsegmentdetlist.setAccsegdets(accountsegService.getAccountsegs(user.getCurruser().getCompanyid()));
       return accountsegmentdetlist;
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
