/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.CalcDate;
import com.sift.gl.EOPException;
import com.sift.gl.InitializeAPPmanual;
import com.sift.gl.model.AllItempara;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Activitylog;
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
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class StartofdayController{
 
   private StartofdaydateValidator startofdaydateValidator;
   
   private Startofdaydate startofdaydate;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String clientIpAddress;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          startofdaydateValidator = new StartofdaydateValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
  
      
   @RequestMapping(value = {"gl_startofday"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String startofday(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
       CalcDate cddate = new CalcDate();
       java.util.Date vdate ;
       SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
       Integer countryid = Integer.valueOf(user.getCurrusercompany().getCountryId());
       //System.out.println("check 1 " + countryid + " - " + user.getCurrusercompany().getPostDate());
       String datestr = "";
       /*if (user.getCurrusercompany().getStartofDay().isEmpty()==false) {
       try {
            vdate = cddate.CalcValueDate( user.getCurrusercompany().getPostDate(), 1,dcompany,dbranch);
            datestr = formatter1.format(vdate);
       } catch(EOPException eop) {} 
       startofdaydate = new Startofdaydate();
       startofdaydate.setStartdatestr(datestr);
       }
       else {
          startofdaydate = new Startofdaydate();
          datestr = formatter1.format(user.getCurrusercompany().getPostDate());
          startofdaydate.setStartdatestr(datestr); 
       }*/
       try {
            vdate = cddate.CalcValueDate( user.getCurrusercompany().getPostDate(), 1,dcompany,dbranch);
            datestr = formatter1.format(vdate);
       } catch(EOPException eop) {} 
       startofdaydate = new Startofdaydate();
       startofdaydate.setStartdatestr(datestr);
       Calendar cal = Calendar.getInstance();
       cal.setTime(user.getCurrusercompany().getPostDate());
       startofdaydate.setPostdateyr(cal.get(Calendar.YEAR));
       startofdaydate.setPostdatemth(cal.get(Calendar.MONTH));
       startofdaydate.setPostdateday(cal.get(Calendar.DATE));
       model.addAttribute("startofdaydate", startofdaydate);
      return "gl/gl_startofday";
   }

 
   @RequestMapping(value = {"gl/gl_startofdayproc","/gl_startofdayproc"},method=RequestMethod.POST)
   public ModelAndView startofdayproc( @ModelAttribute("startofdaydate")Startofdaydate startofdaydate2, BindingResult result)  {
     String ddate = "" ;
     Integer dperiod = 0;
     Integer dyear = 0;
     Integer dgrpid = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     
     //startofdaydate = startofdaydate2;
     startofdaydateValidator.validate(startofdaydate2, result);
     
     String redurlpath;
     
     if (startofdaydate.getStartdatestr().equalsIgnoreCase(startofdaydate2.getStartdatestr())==false) {
         return new ModelAndView("redirect:/error.htm?er=System Initialization Error - A post date has been skipped;Please contact Admininstrator to set date as holiday before proceeding!");
     }
     
     
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
       return new ModelAndView("gl/gl_startofday");
     }
     
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     ddate = startofdaydate2.getStartdatestr();
     dperiod = user.getCurrusercompany().getCurrentPeriod();
     dyear = user.getCurrusercompany().getCurrentYear();
     
     InitializeAPPmanual initapp = new InitializeAPPmanual(user.getCurrusercompany().getTimezone());
     boolean isdateclosed = initapp.iscurrentdateclosed(dbranch);
     if (isdateclosed==false) {
         return new ModelAndView("redirect:/error.htm?er=System Initialization Error - The Current Post date must be closed before starting a new day!");
     }
     
     String retval = initapp.startdaynow(ddate,dperiod,dyear,dcompany,dbranch);
     startofdaydate2.setOpmessage(retval);
    /// if (retval.equals("ok")) {
         redurlpath = "redirect:/appFeedback.htm?message=New Post date initialized successfully&redirectURI=index.htm";
         if (retval.equals("ok")) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy"); 
            java.util.Date newpostdate = new java.util.Date();
           try {
            newpostdate = formatter1.parse(ddate);
           } catch (ParseException e) {
           System.out.println("Parse Error" + e.getMessage());
           }  
           user.getCurrusercompany().setPostDate(newpostdate );
           user.getCurrusercompany().setStartofDay("");
            redurlpath = "redirect:/glappfeedback.htm?message=New Post date (" + ddate + ") initialized successfully";
           auditlogcall(13,"GLIND",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcompany.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         }
         else {
             System.out.println(" Error" + retval);
             auditlogcall(13,"GLIND",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcompany.toString(),"Process Failed!",user.getCurruser().getBranch(),dcompany);
             redurlpath = "redirect:/error.htm?er=" + retval;
         }
     
     return new ModelAndView(redurlpath,"startofdaydate",startofdaydate2);
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
