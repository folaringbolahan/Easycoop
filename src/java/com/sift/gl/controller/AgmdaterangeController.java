/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.model.AllItempara;
import com.sift.gl.validator.PeriodcriteriaValidator;
import com.sift.gl.dao.PeriodcriteriaImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Users;
import com.sift.votes.model.VotAgm;
import com.sift.votes.service.VotAgmService;
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
//@RequestMapping("gl/")
public class AgmdaterangeController{
    @Autowired
   private VotAgmService votAgmService;
   private PeriodcriteriaValidator periodcriteriaValidator;
   private AllItempara reportdet;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          periodcriteriaValidator = new PeriodcriteriaValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
  public void setVotAgmService(VotAgmService votAgmService) {
      this.votAgmService = votAgmService;
  }
      
   @RequestMapping(value = {"agmdterangcriteria"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String agmdtparalist(@ModelAttribute("reportpath") final Object mapping1FormObject,@ModelAttribute("paratype") final Object mapping2FormObject,ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
        reportdet.setPeriodId(user.getCompanydetails(dbranch, dcompany).getCurrentPeriod());
        reportdet.setYear(user.getCompanydetails(dbranch, dcompany).getCurrentYear());
     List<VotAgm> agms = votAgmService.listAllAgm() ;
     model.addAttribute("agms", agms);
     model.addAttribute("reportdet",reportdet);
     model.addAttribute("reportpath", mapping1FormObject);
     model.addAttribute("paratype", mapping2FormObject);
     return "agmdterangcriteria";
   }
  
   @RequestMapping(value = {"agmcriteria"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String agmparalist(@ModelAttribute("reportpath") final Object mapping1FormObject,@ModelAttribute("paratype") final Object mapping2FormObject,ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
        //reportdet.setPeriodId(user.getCompanydetails(dbranch, dcompany).getCurrentPeriod());
        //reportdet.setYear(user.getCompanydetails(dbranch, dcompany).getCurrentYear());
     List<VotAgm> agms = votAgmService.listAllAgm() ;
     model.addAttribute("agms", agms);
     model.addAttribute("reportdet",reportdet);
     model.addAttribute("reportpath", mapping1FormObject);
     model.addAttribute("paratype", mapping2FormObject);
     return "agmcriteria";
   }
  
}
