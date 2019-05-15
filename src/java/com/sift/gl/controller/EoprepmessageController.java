/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.EOPException;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.Eopmessagedao;
import com.sift.gl.model.Periodend;
import java.util.Date;
import java.util.Enumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class EoprepmessageController{
 int eoprepretval = 0;
 Periodend pedm;
  private String clientIpAddress;
  @Autowired
   private CurrentuserdisplayImpl user;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
       pedm = new Periodend();  
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
      
   @RequestMapping(value = {"gl_eoprep"}, method = {RequestMethod.GET})
   public ModelAndView startofday(@ModelAttribute("pedm")Periodend pedm2,HttpServletRequest request) {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr(); 
       Eopmessagedao eopm = new Eopmessagedao();
       String redurlpath = "";
       try {
         eoprepretval = eopm.messageval(dcompany,dbranch,user.getCurrusercompany().getPostDate());
       }
       catch(EOPException ex) {
         redurlpath = "redirect:/error.htm?er=" + ex.getMessage();
       }
       pedm.setCompletionstatus(eoprepretval);
       //model.addAttribute("eoprepretval", eoprepretval);
       redurlpath = "gl/gl_eoprep";
       return new ModelAndView(redurlpath,"pedm",pedm);
      //return ;
   }

   @ModelAttribute("pedm")
   public Periodend getPedm() {
      // System.out.println("check " + pedm.getStartdatestr());
    return pedm;
   }
   
}
