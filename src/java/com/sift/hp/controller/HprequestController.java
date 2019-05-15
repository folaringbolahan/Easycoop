/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.controller;
import com.sift.gl.Formulafactory;
import com.sift.gl.NotificationService;
import com.sift.gl.controller.*;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Generictableviewbean;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccountsetupValidator;
import com.sift.hp.Hprequestutility;
import com.sift.hp.dao.HprequestImpl;
import com.sift.hp.model.Hpintcalcmtd;
import com.sift.hp.model.Hpproduct;
import com.sift.hp.model.Hprepaymtfreq;
import com.sift.hp.model.Hprepymtschddetails;
import com.sift.hp.model.Hprequestdetails;
import com.sift.hp.model.Hpvalidationrules;
import com.sift.hp.model.Member;
import com.sift.hp.validator.HppaymntsingValidator;
import com.sift.hp.validator.HprequestValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("hp/")
public class HprequestController{
   //@Autowired 
   private HprequestImpl hprequestService;
   @Autowired
    private NotificationService notificationService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccountsetupValidator accountsetupValidator;
   private Account accountdet;
   private HprequestValidator hprequestValidator;
   private HppaymntsingValidator hppaymntsingValidator;
   private Hprequestdetails hprequestdet;
   private String accountseg;
   private String clientIpAddress;
   private String dmess = "";
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @Value("${emailsubject.hpreqapprvemail}")
   private String emailsubjecthpreqapprvemail;
   @Value("${emailsubject.hppursalemail}")
   private String emailsubjecthppursalemail;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          hprequestValidator = new HprequestValidator();
          hprequestdet = new Hprequestdetails();
          hprequestdet.setAnalysiscode1(dmess);
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setHprequestService(HprequestImpl hprequestService) {
      this.hprequestService = hprequestService;
      hprequestdet = new Hprequestdetails();
      hprequestdet.setAnalysiscode1(dmess);
   }
   
   
   
  /// public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
  ///    this.currentuserdisplayService = currentuserdisplayService;
  /// }
   @ModelAttribute("clientIpAddress")
	public String populateClientIpAddress(HttpServletRequest request) {
          return request.getRemoteAddr();
	}

   @RequestMapping(value = {"/hp_request"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String account(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
     //  accountdet.setBalanceType("C");
     //  accountdet.setActiveorclosed("a");
       clientIpAddress = request.getRemoteAddr();
     //List<Account> accounts = accountsetupService.getAccounts(dbranch,dcompany) ;
     List<Member> members = hprequestService.getMembers(dbranch,dcompany) ;
     model.addAttribute("hpproducts", hprequestService.getHpproducts(dbranch,dcompany));
     model.addAttribute("hprequestdet", hprequestdet);
     model.addAttribute("members", members);
     model.addAttribute("repayfrequencys", hprequestService.getHprepymtfreqs());
     model.addAttribute("intcalcmtds", hprequestService.getHpintcalcmtds());
    // model.addAttribute("accreportgroups", accountsetupService.getAccountgrpreps()); 
    
     return "hp/hp_request";
  }
   
  
   @ModelAttribute("members")
   public List<Member> getAllmembers() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return hprequestService.getMembers(dbranch,dcompany);
   }
   
   @ModelAttribute("hpproducts")
   public List<Hpproduct> getAllhpproducts() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return hprequestService.getHpproducts(dbranch,dcompany);
   }
   
   @ModelAttribute("repayfrequencys")
   public List<Hprepaymtfreq> getAllrepayfrequencys() {
     return hprequestService.getHprepymtfreqs();
   }
   
   @ModelAttribute("intcalcmtds")
   public List<Hpintcalcmtd> getAllintcalcmtds() {
     return hprequestService.getHpintcalcmtds();
   }
    
   
   @RequestMapping(value = {"hp/hp_requestadd","/hp_requestadd"},method=RequestMethod.POST)
   public ModelAndView addrequest( @ModelAttribute("hprequestdet")Hprequestdetails hprequestdet2, BindingResult result)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     String dstr1 = "" ;
     String dstr2 = "" ;
     double dstr3 = 0.0 ;
     double dstr4 = 0.0 ;
     double dstr5 = 0.0 ;
     String dstr6 = "" ;
     String dstr7 = "" ;
     double dstr8 = 0.0 ;
     double dstr9 = 0.0 ;
     String dstr10 = "" ;
     int dstr11 = 0 ;
     double dstr12 = 0 ;
     int dstr13 = 0 ;
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     if (hprequestdet2.getTxndatestr()!=null) {
     opendateinstring = hprequestdet2.getTxndatestr();
     }
        try {
             openeddate = formatter.parse(opendateinstring);
        } catch (ParseException e) {
		//e.printStackTrace();
	}
     
     //hprequestdet = hprequestdet2;
     hprequestValidator.setCoyid(user.getCurruser().getCompanyid());
     hprequestValidator.validate(hprequestdet2, result);
     
     String redurlpath="";
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      //  return "redirect:addaccountgroup.htm";
      // return new ModelAndView("gl_accountgrouplist","accountgroupdet",accountgroupdet2);
      return new ModelAndView("hp/hp_request");
     }
     
     dstr2 = hprequestdet2.getProduct() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     dstr1 = hprequestdet2.getMemberno();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     dstr3 = hprequestdet2.getHpprice();
     dstr4 = hprequestdet2.getCashprice();
     dstr5 = hprequestdet2.getInterestamt();
     dstr7 = hprequestdet2.getInterestcalcmtd();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     String dcurrency = user.getCurrusercompany().getBaseCurrency();
    // String ddateopened = hprequestdet2.getDateOpenedstr();
     dstr6 = hprequestdet2.getInvref();
     dstr8 = hprequestdet2.getInterestrate();
     dstr9 = hprequestdet2.getDownpaymentamount();
     
     dstr10 = hprequestdet2.getRepaymentfrequency();
     dstr11 = hprequestdet2.getRepaymentperiodinmonths();
     //dstr12 = hprequestdet2.getEqualpaymentamount();
     dstr13 = hprequestdet2.getNoofpayments();
     //dstr14 = hprequestdet2.getDownpaymentamount();
     
     //convert txndate from string to date
     //calculate and display d following
     //1 equalrepayment amt - use hputility class
     //2. interest amt
     //3. hpprice
     //inputs - Interestcalcmtd,double InterestRate,java.util.Date TxnDate openeddate,double DownpaymentAmount,String RepaymentFrequency,int RepaymentPeriodinMonths,double EqualPaymentAmount
     dmess = "";
     Hprequestutility hprequtil = new Hprequestutility(hprequestdet2.getInterestcalcmtd(),hprequestdet2.getRepaymentfrequency(), hprequestdet2.getRepaymentperiodinmonths(),hprequestdet2.getCashprice(), hprequestdet2.getInterestrate(), hprequestdet2.getDownpaymentamount(),hprequestdet2.getInterestamt(),hprequestdet2.getHpprice(),openeddate);
     hprequtil.docalculations();
     
     //if (hprequestdet2.getInterestcalcmtd().equalsIgnoreCase("SUD")==false){
        dstr5 = hprequtil.getInterestamt(); 
     //}
     String mailsubject = emailsubjecthpreqapprvemail;
    String retval = hprequestService.addRequest(dstr1,dstr2,hprequtil.getHpprice(),dstr4,dstr5,dstr6,dstr7,dstr8,openeddate,dstr9,dstr10,dstr11,hprequtil.getNoofpayments(),dbranch,dcompany,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),mailsubject,notificationService);
     if (retval.contains("ok:")) {
         dmess = retval;
         redurlpath = "redirect:hp_request.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     hprequestdet = new Hprequestdetails();
     hprequestdet.setAnalysiscode1(dmess);
     //System.out.println(hprequestdet.getAnalysiscode1());
     return new ModelAndView(redurlpath,"hprequestdet",hprequestdet);
   }
   
   @RequestMapping(value = "/getMess.htm", method = RequestMethod.GET)
   public @ResponseBody String getDmess(@RequestParam("accstr") String acstr) {
     // Delegate to service
      String daccsg = "";
      daccsg=dmess;
    //  System.out.println("called here7 " + daccsg);
     return daccsg;
   }
   
   @RequestMapping(value = {"/hp_reqpapprv","hp/hp_reqpapprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hpreq4apprv(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreqs4apprv", hprequestService.getHpreqs4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_reqpapprv";
   }
   
   @RequestMapping(value = {"/hp_apprvrequest","hp/hp_apprvrequest"}, method = {RequestMethod.GET,RequestMethod.POST}, params="id")
   public String apprvrequest(ModelMap model,HttpServletRequest request)  throws Exception {
       String dref = ServletRequestUtils.getRequiredStringParameter(request, "id");
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       
       
      List<String> colhdrs = new ArrayList<String>();
      colhdrs.add(0, "Installment No.");colhdrs.add(1, "Installment");colhdrs.add(2, "Principal");colhdrs.add(3, "Interest");colhdrs.add(4, "Interest To Date");colhdrs.add(5, "Principal To Date");colhdrs.add(6, "Schedule Balance");colhdrs.add(7, "Repayment Date");
      
      Generictableviewbean gtbb = new Generictableviewbean();//generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
      gtbb.setHeader(colhdrs);
      Hprequestdetails hpdet  = hprequestService.getHpreq4apprv(dref, dcompany, dbranch,user.getCurruser().getUserId()); 
      Hprequestutility hprequtil = new Hprequestutility(hpdet.getInterestcalcmtd(),hpdet.getRepaymentfrequency(), hpdet.getRepaymentperiodinmonths(),hpdet.getCashprice(), hpdet.getInterestrate(), hpdet.getDownpaymentamount(),hpdet.getInterestamt(),hpdet.getHpprice(),hpdet.getTxndate());
      //hprequtil.docalculations();
      gtbb.setBody(hprequtil.getSchcalculationsstr());
      //System.out.println("check it " + gtbb.getHeader().size());
      model.addAttribute("headere", gtbb.getHeader());
      model.addAttribute("body", gtbb.getBody()); 
      
      HashMap<String,List<String>> maplist = new HashMap();
      List<String> oprandslist1 = new ArrayList();
      List<String> oprandslist2 = new ArrayList();
      oprandslist1.add(hpdet.getMemberno());
      oprandslist1.add(dbranch.toString());
      oprandslist1.add(dcompany.toString());
      maplist.put("hpcontrbbal", oprandslist1);
      oprandslist2.add(hpdet.getMemberno());
      oprandslist2.add(dbranch.toString());
      oprandslist2.add(dcompany.toString());
      maplist.put("hpsavbal", oprandslist2);
      
      List<Hpvalidationrules> hprul = hprequestService.getHpvalrules(hpdet.getProduct(), dbranch, dcompany);
      String validmess = "";
      boolean allcriteriaok = true;
       if (hprul.size() > 0) {
           for (Hpvalidationrules b : hprul) {

               Formulafactory ff = new Formulafactory(hpdet.getProduct(), b.getCode(), dbranch, dcompany, maplist);
               ff.determineformulavalue();
               String valmess = ff.getMessage();
               double resval = ff.getResult();
               Hpvalidationrules hpvl = ff.getValidationrules();

               if (hpvl != null) {
                   if (hpvl.getResultcond().equals("<=")) {
                       if (resval < hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
                   if (hpvl.getResultcond().equals(">=")) {
                       if (resval > hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
                   if (hpvl.getResultcond().equals("<")) {
                       if (resval <= hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
                   if (hpvl.getResultcond().equals(">")) {
                       if (resval >= hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
                   if (hpvl.getResultcond().equals("=")) {
                       if (resval != hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
                   if (hpvl.getResultcond().equals("!=")) {
                       if (resval == hpdet.getLoanBalance()) {
                           validmess = validmess + "<strong><font style=\"color: red\">Criteria not met. Rule - " + hpvl.getFormula() + ".</font></strong>";
                           allcriteriaok = false;
                       }
                   }
               }
           }
       }
      hpdet.setAnalysiscode1(validmess);
      // 
      // System.out.println("body size" + gtbb.getBody().size());
     //List<Account> accounts = accountsetupService.getAccounts(dbranch,dcompany) ;
    // List<Member> members = hprequestService.getMembers(dbranch,dcompany) ;
    // model.addAttribute("hpproducts", hprequestService.getHpproducts(dbranch,dcompany));
     // System.out.println("hpdet.getRefid() " + hpdet.getRefid());
     model.addAttribute("hprequestdetres",hpdet );
   
     return "hp/hp_apprvrequest";
  }
   
   @RequestMapping(value = "/hp_pfapprvrequest",method=RequestMethod.POST)
   public ModelAndView pfapprvrequestsave( @ModelAttribute("hprequestdetres")Hprequestdetails hprequestdetres2, BindingResult result)  {
     String ddesc = "" ;
     String dacno = "";//
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     String redurlpath;
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
      String mailsubject = emailsubjecthppursalemail;
     Hprequestdetails hpdet  = hprequestService.getHpreq4apprv(hprequestdetres2.getRefid(), dcompany, dbranch,user.getCurruser().getUserId()); 
     Hprequestutility hprequtil = new Hprequestutility(hpdet.getInterestcalcmtd(),hpdet.getRepaymentfrequency(), hpdet.getRepaymentperiodinmonths(),hpdet.getCashprice(), hpdet.getInterestrate(), hpdet.getDownpaymentamount(),hpdet.getInterestamt(),hpdet.getHpprice(),hpdet.getTxndate());
     String retval = hprequestService.saveHpdetails(hprequestdetres2, hprequtil.getSchcalculations(),dcompany,dbranch,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),mailsubject,notificationService);
     
     if (retval.contains("ok")) {
         redurlpath = "redirect:hp_reqpapprv.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     return new ModelAndView(redurlpath);
   }
    
   @RequestMapping(value = {"/hp_sales","hp/hp_sales"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hpreqs4sale(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreqs4apprv", hprequestService.getHpreqs4sale(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_sales";
   }
   
   @RequestMapping(value = {"/hp_postsales","hp/hp_postsales"}, method = {RequestMethod.GET,RequestMethod.POST}, params="id")
   public String salesrequest(ModelMap model,HttpServletRequest request)  throws Exception {
       String dref = ServletRequestUtils.getRequiredStringParameter(request, "id");
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
      List<String> colhdrs = new ArrayList<String>();
      colhdrs.add(0, "Installment No.");colhdrs.add(1, "Installment");colhdrs.add(2, "Principal");colhdrs.add(3, "Interest");colhdrs.add(4, "Interest To Date");colhdrs.add(5, "Principal To Date");colhdrs.add(6, "Schedule Balance");colhdrs.add(7, "Repayment Date");
      Generictableviewbean gtbb = new Generictableviewbean();//generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
      gtbb.setHeader(colhdrs);
      Hprequestdetails hpdet  = hprequestService.getHpreq4sale(dref, dcompany, dbranch,user.getCurruser().getUserId()); 
     model.addAttribute("hprequestdetres",hpdet );
   
     return "hp/hp_postsales";
  }
   
   @RequestMapping(value = "/hp_pfpostsales",method=RequestMethod.POST)
   public ModelAndView pfpostsales( @ModelAttribute("hprequestdetres")Hprequestdetails hprequestdetres2, BindingResult result)  {
     String ddesc = "" ;
     String dacno = "";//
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     String redurlpath;
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     String retval = hprequestService.postHpsales(hprequestdetres2, dcompany,dbranch,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),user.getCurrusercompany().getCurrentYear(),user.getCurrusercompany().getCurrentPeriod(),user.getCurrusercompany().getPostDate());
     
     if (retval.contains("ok")) {
         redurlpath = "redirect:hp_sales.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     return new ModelAndView(redurlpath);
   } 
   
   @RequestMapping(value =  {"/hp_cancelhp"},method=RequestMethod.GET)
   public ModelAndView cancelhp(HttpServletRequest request,HttpServletResponse response) throws Exception {
     //Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     String dcode = ServletRequestUtils.getRequiredStringParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     clientIpAddress = request.getRemoteAddr();
     String retval = hprequestService.cancelHpdetails(dcode,dcompany,dbranch,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
     String redurlpath;
     if (retval.contains("ok")) {
         redurlpath = "redirect:hp_hpactive.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
        
     }
     return new ModelAndView(redurlpath);
   }
   
   @RequestMapping(value = {"/hp_hpactive","hp/hp_hpactive"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hpreqsactive(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreqs4apprv", hprequestService.getHpreqsactive(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_hpactive";
   }
   
   @RequestMapping(value = "/getProddet.htm", method = RequestMethod.GET)
   public @ResponseBody Hpproduct getDproduct(@RequestParam("prodcode") String prodcode) {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     Hpproduct dprod = hprequestService.getDproduct(prodcode,dbranch,dcompany);
     return dprod;
   }
  /// single payment
   
   @RequestMapping(value = {"/hp_paysing","hp/hp_paysing"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hpreq4paysin(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreqs4apprv", hprequestService.getHpreqs4singpymt(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_paysing";
   }
   
   @RequestMapping(value = {"/hp_paysingdet","hp/hp_paysingdet"}, method = {RequestMethod.GET,RequestMethod.POST}, params="id")
   public String paysingdet(ModelMap model,HttpServletRequest request)  throws Exception {
       String dref = ServletRequestUtils.getRequiredStringParameter(request, "id");
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       List<String> colhdrs = new ArrayList<String>();
      colhdrs.add(0, "Instalment No.");colhdrs.add(1, "Instalment");colhdrs.add(2, "Principal");colhdrs.add(3, "Interest");colhdrs.add(4, "Interest To Date");colhdrs.add(5, "Principal To Date");colhdrs.add(6, "Schedule Balance");colhdrs.add(7, "Repayment Date");colhdrs.add(8, "Status");
      
      Generictableviewbean gtbb = new Generictableviewbean();//generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
      gtbb.setHeader(colhdrs);
      Hprequestdetails hpdet2  = hprequestService.getHpreq4disp(dref, dcompany, dbranch,user.getCurruser().getUserId()); 
      List<Hprepymtschddetails> hpreqscd = hprequestService.getHpscdhdet(dref, dcompany, dbranch,user.getCurruser().getUserId()); 
      gtbb.setBody(hprequestService.generaterepymtschdlasstr(hpreqscd));
      model.addAttribute("headere", gtbb.getHeader());
      model.addAttribute("body", gtbb.getBody()); 
     //System.out.println("hpdet2.getProduct() " + hpdet2.getProduct());
    
     model.addAttribute("hprequestdetres",hpdet2 );
   
     return "hp/hp_paysingdet";
  }
   
   
   @RequestMapping(value = {"/hp_pfpaysing","hp/hp_pfpaysing"}, method = {RequestMethod.GET,RequestMethod.POST}, params="id")
   public String pfpaysing(ModelMap model,HttpServletRequest request)  throws Exception {
       String dref = ServletRequestUtils.getRequiredStringParameter(request, "rf");
       int did = ServletRequestUtils.getRequiredIntParameter(request, "id");
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
      Hprequestdetails hpdet2  = hprequestService.getHpreq4disp(dref, dcompany, dbranch,user.getCurruser().getUserId());
      Hprepymtschddetails hpdet  = hprequestService.getHpreq4paysing(dref,did,dcompany, dbranch,user.getCurruser().getUserId());
      
     model.addAttribute("hpscdldetres",hpdet );  
     model.addAttribute("hprequestdetres",hpdet2 );
   
     return "hp/hp_pfpaysing";
  }
   
   @RequestMapping(value = "/hp_pfpaysingpost",method=RequestMethod.POST)
   public ModelAndView pfpaysingpost( @ModelAttribute("hpscdldetres")Hprepymtschddetails hprequestdetres2, BindingResult result)  {
     String ddesc = "" ;
     String dacno = "";//
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     String redurlpath;
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     Date openeddate = new Date();
     if (hprequestdetres2.getPaymentdatestr()!=null) {
     opendateinstring = hprequestdetres2.getPaymentdatestr();
     }
        try {
             openeddate = formatter.parse(opendateinstring);
        } catch (ParseException e) {
		//e.printStackTrace();
	}
     hprequestdetres2.setPaymentdate(openeddate);
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     hppaymntsingValidator = new HppaymntsingValidator();
     hppaymntsingValidator.validate(hprequestdetres2, result);
     
       if (result.hasErrors() == true) {
           List<FieldError> lerr = result.getFieldErrors();
           for (FieldError err : lerr) {
               System.out.println(err);
           }
           return new ModelAndView("hp/hp_pfpaysingpost");
       }
     
     String retval = hprequestService.postsingHpsales(hprequestdetres2, dcompany,dbranch,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),user.getCurrusercompany().getCurrentYear(),user.getCurrusercompany().getCurrentPeriod(),user.getCurrusercompany().getPostDate());
     
     if (retval.contains("ok")) {
         redurlpath = "redirect:hp_paysing.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     return new ModelAndView(redurlpath);
   } 
   
   @RequestMapping(value = "/getPenalty.htm", method = RequestMethod.GET)
   public @ResponseBody double getDAccount(@RequestParam("paydate") String paydatestr,@RequestParam("principal") double principal,@RequestParam("interest") double interest,@RequestParam("rpymtdatestr") String rpymtdatestr,@RequestParam("product") String product) {
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     Date rpymtdate=user.getCurrusercompany().getPostDate();
     Date paydate=user.getCurrusercompany().getPostDate();
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
     
     try {
        rpymtdate = formatter.parse(rpymtdatestr);
        paydate = formatter.parse(paydatestr);
     } catch (ParseException e) {
		//e.printStackTrace();
     }
     
     double penamt = 0.0;
     penamt = hprequestService.determinepenalty(principal,interest,paydate,rpymtdate, product,dbranch,dcompany);
     System.out.println("penamt " + penamt);
     return penamt;
     //Account dacc = accountsetupService.getAccount(acno,dbranch,dcompany);
     //accountdet = dacc;
     //return dacc;
   }
}
