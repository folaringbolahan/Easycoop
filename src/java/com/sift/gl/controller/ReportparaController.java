/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.ReportlistImpl;
import com.sift.gl.model.Report;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("reports/")
public class ReportparaController{
  
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
         // accountgroupValidator = new AccountgroupValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setReportlistService(ReportlistImpl reporlistService) {
      //this.reporlistService = reporlistService;
   }
  
   @RequestMapping(value = {"{repid}/{fileformat}/para"}, method = {RequestMethod.GET})
   public ModelAndView reportdet(HttpServletRequest request,@PathVariable String repid,@PathVariable String fileformat, final RedirectAttributes redirectAttributes) throws ServletRequestBindingException {
       String drep = repid;
       String dfileformat = fileformat;
       String dparatype = ServletRequestUtils.getRequiredStringParameter(request, "pr");
     Map dmodel = new HashMap();
     //dmodel.put("reportpath","reports/" + drep + "/" + dfileformat); 
     //dmodel.put("paratype", dparatype);
     String redurlpath ="";
     //redirectAttributes.addFlashAttribute("reportpath", "reports/" + drep + "/" + dfileformat);
     //redirectAttributes.addFlashAttribute("paratype", dparatype);
     redirectAttributes.addAttribute("reportpath", "reports/" + drep + "/" + dfileformat);
     redirectAttributes.addAttribute("paratype", dparatype);
     if (dparatype.equalsIgnoreCase("P")) {
         
         redurlpath = "redirect:/periodcriteria.htm";
     }
     if (dparatype.equalsIgnoreCase("A")) {
         redurlpath = "redirect:/acctcriteria.htm";
     }
     if (dparatype.equalsIgnoreCase("B")) {
         redurlpath = "redirect:/acctrangcriteria.htm";
     }
     if (dparatype.equalsIgnoreCase("C")) {
         redurlpath = "redirect:/acctdterangcriteria.htm";
     }
     if (dparatype.equalsIgnoreCase("D")) {
         redurlpath = "redirect:/datecriteria.htm";
     }
     if (dparatype.equalsIgnoreCase("W")) {
         redurlpath = "redirect:/productdaterange.htm";
     }
     if(dparatype.equalsIgnoreCase("Z")){
         redurlpath = "redirect:/loancaseid.htm";
     }
     if(dparatype.equalsIgnoreCase("Y")){
         redurlpath = "redirect:/loanstatusdate.htm";
     }
     if(dparatype.equalsIgnoreCase("G")){
         redurlpath = "redirect:/loanmatcriteria.htm";
     }
     if(dparatype.equalsIgnoreCase("E")){
         redurlpath = "redirect:/memberrepid.htm";
     }
     if(dparatype.equalsIgnoreCase("F")){
         redurlpath = "redirect:/loandaterange.htm";
     }
     if(dparatype.equalsIgnoreCase("X")){
         redurlpath = "redirect:/loanschedule.htm";
     }
     
     if(dparatype.equalsIgnoreCase("K")){
         redurlpath = "redirect:/purstatuscriteria.htm";
     }
     if(dparatype.equalsIgnoreCase("H")){
         redurlpath = "redirect:/divschdlecriteria.htm";
     }
     if(dparatype.equalsIgnoreCase("V")){
         redurlpath = "redirect:/datestrcriteria.htm";
     }
     //if(dparatype.equalsIgnoreCase("AD")){
    //     redurlpath = "redirect:/agmdterangcriteria.htm";
    // }
    // if(dparatype.equalsIgnoreCase("AG")){
     //    redurlpath = "redirect:/agmcriteria.htm";
     //}
     return new ModelAndView(redurlpath,dmodel);
  
   }
   
   @RequestMapping(value = {"reg/{repid}/{fileformat}/para"}, method = {RequestMethod.GET})
   public ModelAndView reportregdet(HttpServletRequest request,@PathVariable String repid,@PathVariable String fileformat, final RedirectAttributes redirectAttributes) throws ServletRequestBindingException {
       String drep = repid;
       String dfileformat = fileformat;
       String dparatype = ServletRequestUtils.getRequiredStringParameter(request, "pr");
     Map dmodel = new HashMap();
     String redurlpath ="";
     redirectAttributes.addAttribute("reportpath", "reports/reg/" + drep + "/" + dfileformat);
     redirectAttributes.addAttribute("paratype", dparatype);
    
     if(dparatype.equalsIgnoreCase("AD")){
         redurlpath = "redirect:/agmdterangcriteria.htm";
     }
     if(dparatype.equalsIgnoreCase("AG")){
         redurlpath = "redirect:/agmcriteria.htm";
     }
     return new ModelAndView(redurlpath,dmodel);
  
   }

}
