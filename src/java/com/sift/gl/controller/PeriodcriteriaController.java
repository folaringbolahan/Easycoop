/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.model.AllItempara;
import com.sift.gl.validator.PeriodcriteriaValidator;
import com.sift.gl.dao.PeriodcriteriaImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
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
//@RequestMapping("gl/")
public class PeriodcriteriaController{
  @Autowired 
   private PeriodcriteriaImpl periodcriteriaService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
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
   
   public void setPeriodcriteriaService(PeriodcriteriaImpl periodcriteriaService) {
      this.periodcriteriaService = periodcriteriaService;
   }
   
  /// public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
  ///    this.currentuserdisplayService = currentuserdisplayService;
  /// }
   
      
   @RequestMapping(value = {"periodcriteria"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String paralist(@ModelAttribute("reportpath") String mapping1FormObject,@ModelAttribute("paratype") String mapping2FormObject,ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
        reportdet.setPeriodId(user.getCompanydetails(dbranch, dcompany).getCurrentPeriod());
        reportdet.setYear(user.getCompanydetails(dbranch, dcompany).getCurrentYear());
     model.addAttribute("prds", periodcriteriaService.getPeriods(dcompany, dbranch));
     model.addAttribute("yrs", periodcriteriaService.getYears(dcompany, dbranch));
     model.addAttribute("reportdet",reportdet);
     model.addAttribute("reportpath", mapping1FormObject);
     model.addAttribute("paratype", mapping2FormObject);
     System.out.println("debug: reportpath - paratype " + mapping1FormObject + " - " + mapping2FormObject);
     return "periodcriteria";
   }

   @ModelAttribute("yrs")
   public List<Integer> getAllYrs() {
     Integer  dcompany = user.getCurruser().getCompanyid();
     Integer  dbranch = user.getCurruser().getBranch();
     return periodcriteriaService.getYears(dcompany, dbranch);
   }
   @ModelAttribute("prds")
   public List<Integer> getAllPrds() {
      Integer  dcompany = user.getCurruser().getCompanyid();
     Integer  dbranch = user.getCurruser().getBranch();
     return periodcriteriaService.getPeriods(dcompany, dbranch);
   }
   
}
