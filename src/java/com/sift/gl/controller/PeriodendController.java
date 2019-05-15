/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.CalcDate;
import com.sift.gl.EOPException;
import com.sift.gl.EOPeriodprocessing;
import com.sift.gl.InitializeAPPmanual;
import com.sift.gl.model.AllItempara;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Periodend;
import com.sift.gl.model.Startofdaydate;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sift.gl.validator.StartofdaydateValidator;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.ServletRequestUtils;
/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class PeriodendController{
 Periodend ped = new Periodend();
  // private StartofdaydateValidator startofdaydateValidator;
   private String clientIpAddress;
  // private Startofdaydate startofdaydate;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
        //  startofdaydateValidator = new StartofdaydateValidator();
          //user = new Users();
      
           
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
  
      
   @RequestMapping(value = {"gl_periodend"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String startofday(ModelMap model,HttpServletRequest request) {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
       //String dcode = ServletRequestUtils.getRequiredStringParameter(request, "msg");
      clientIpAddress = request.getRemoteAddr();
      EOPeriodprocessing ueop = new EOPeriodprocessing();
      try {
       ped.setCompletionstatus(ueop.isdayclosed(dcompany,dbranch));
      }
       catch(EOPException ex) {
       auditlogcall(14,"GLEDP",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcompany.toString(),"Completion status retieval Failed!",user.getCurruser().getBranch(),dcompany);
      }
       model.addAttribute("periodenddet", ped);
      return "gl/gl_periodend";
   }

 
   @RequestMapping(value = {"gl/gl_periodendproc","/gl_periodendproc"},method=RequestMethod.POST)
   public ModelAndView periodendproc(@ModelAttribute("periodenddet")Periodend periodenddet2, BindingResult result)  {
     Date ddate ;
     Integer dperiod = 0;
     Integer dyear = 0;
     Integer dgrpid = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     ddate = user.getCurrusercompany().getPostDate();
     dperiod = user.getCurrusercompany().getCurrentPeriod();
     dyear = user.getCurrusercompany().getCurrentYear();
     
     Integer countryid = Integer.valueOf(user.getCurrusercompany().getCountryId());
     EOPeriodprocessing eop = new EOPeriodprocessing();
     String redurlpath = "";
     try {
      eop.EOPeriodprocessinginit(dcompany,dbranch,dyear,dperiod,ddate,user.getCurrusercompany().getCurrentsystemDate(),user.getCurruser().getUserId(),countryid,user.getCurrusercompany().getBaseCurrency(),user.getCurrusercompany().getProcmethod(),user.getCurrusercompany().getTimezone());
      auditlogcall(14,"GLEDP",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcompany.toString(),"Process Completed!",user.getCurruser().getBranch(),dcompany);
      ped.setCompletionstatus(eop.isdayclosed(dcompany,dbranch));
      redurlpath = "redirect:gl_periodend.htm";
     }
     catch(EOPException ex) {
      auditlogcall(14,"GLEDP",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcompany.toString(),"Process Failed!",user.getCurruser().getBranch(),dcompany);
      redurlpath = "redirect:/error.htm?er=" + ex.getMessage();
     }
     
     periodenddet2.setOpmessage("Process Completed!");
     ped.setOpmessage("Process Completed!");
     
    /// if (retval.equals("ok")) {
    
     
     return new ModelAndView(redurlpath,"periodendet",periodenddet2);
   }
  
   @ModelAttribute("periodendet")
   public Periodend getPeriodend() {
     return ped;
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
