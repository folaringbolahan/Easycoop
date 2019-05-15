/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.controller;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.GenerictableviewImpl;
import com.sift.gl.model.Generictableviewbean;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("hp/")
public class HpgenerictableviewController{
   //@Autowired 
   private GenerictableviewImpl generictableviewService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private String clientIpAddress;
   @Autowired
   private CurrentuserdisplayImpl user;
   private Generictableviewbean globalgtbb;
   
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
         // accounttxnsetupValidator = new AccounttxnsetupValidator();
         // accountdet = new Account();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setGenerictableviewService(GenerictableviewImpl generictableviewService) {
      this.generictableviewService = generictableviewService;
   }
  
   @ModelAttribute("clientIpAddress")
   public String populateClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
   }
   
   
   @RequestMapping(value = {"/hp_gentblview","hp/hp_gentblview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String loadvalhp(ModelMap model,HttpServletRequest request,@RequestParam("id") String batchid,@RequestParam("nm") String reportcode)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch(); 
      String sql = "";
      List<String> colhdrs = new ArrayList<String>();
      List<String> colflds = new ArrayList<String>();
      String pagetitle = "";
      globalgtbb = null;
      if (reportcode.equals("1")) {
          sql = "select * from hpreptxnimportpending where reference = '" + batchid + "' and companyid = " + dcompany + " and branchid = " + dbranch + " and haserrors = 0";
          pagetitle = "Details of Transaction records ok for file reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Reference");colhdrs.add(2, "Account");colhdrs.add(3, "Repayment date");colhdrs.add(4, "Amount");colhdrs.add(5, "Penalty");
          colflds.add(0, "Rownum");colflds.add(1, "Hprefid");colflds.add(2, "Accountno");colflds.add(3, "Repaymentdate");colflds.add(4, "Amount");colflds.add(5, "Penalty");
      }
      
      if (reportcode.equals("2")) {
          sql = "select * from hpreptxnimportpending where reference = '" + batchid + "' and companyid = " + dcompany + " and branchid = " + dbranch + " and haserrors = 1";
          pagetitle = "Details of failed Transaction records for file reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Reference");colhdrs.add(2, "Account");colhdrs.add(3, "Repayment date");colhdrs.add(4, "Amount");colhdrs.add(5, "Penalty");colhdrs.add(6, "Error");
          colflds.add(0, "Rownum");colflds.add(1, "Hprefid");colflds.add(2, "Accountno");colflds.add(3, "Repaymentdate");colflds.add(4, "Amount");colflds.add(5, "Penalty");colflds.add(6, "Errormessage");
      }
     // try {
      Generictableviewbean gtbb = generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
      
      globalgtbb = gtbb;
      //System.out.println("check it " + gtbb.getHeader().size());
      model.addAttribute("pagetitle", gtbb.getPagetitle()); 
      model.addAttribute("headere", gtbb.getHeader());
      model.addAttribute("body", gtbb.getBody()); 
      model.addAttribute("dBatchId", batchid);
      model.addAttribute("nm", reportcode);
    //  }
    //  catch (NullPointerException nex) {System.out.println("null records");}
     return "hp/hp_gentblview";
   }
   
   
   @RequestMapping(value = {"hp/hp_downloadrecs/{batchId}/{nm}", "/hp_downloadrecs/{batchId}/{nm}"}, method = RequestMethod.POST)
    public ModelAndView downloadFailedRecordsToExcelhp(@PathVariable("batchId") String batchId, @PathVariable("nm") String nm,HttpServletRequest req, HttpServletResponse response) {
        String filename = "hdw" + user.getCurruser().getCompanyid() + String.valueOf(System.currentTimeMillis());
        generictableviewService.writeListToExcel(globalgtbb, response,filename);
        return new ModelAndView("redirect:/hp/hp_gentblview");
    }
}
