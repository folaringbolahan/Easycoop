/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.ReportlistImpl;
import com.sift.gl.model.Report;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
//@RequestMapping("reports/")
public class ReportlistController{
   //@Autowired 
   private ReportlistImpl reporlistService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setReportlistService(ReportlistImpl reporlistService) {
      this.reporlistService = reporlistService;
   }
    
   @RequestMapping(value = {"allreports"}, method = {RequestMethod.GET})
   public String reportlist(ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       String daccess = user.getCurruser().getAccessLevel();
       
     List<Report> reportsdet = reporlistService.retrieveReportlist(dcompany, dbranch, daccess);
     model.addAttribute("reportsdet", reportsdet);
     return "/allreports";
     //return new ModelAndView("accountgroupList", "command", accountgroups);
   }
@RequestMapping(value = {"allregreports"}, method = {RequestMethod.GET})
   public String reportreglist(ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       String daccess = user.getCurruser().getAccessLevel();
       
     List<Report> reportsdet = reporlistService.retrieveregReportlist();
     model.addAttribute("reportsdet", reportsdet);
     return "/allregreports";
   }
}
