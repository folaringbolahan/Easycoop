/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;


import com.sift.admin.model.Settingcoop;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.SettingcoopService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.bean.SettingcoopBean;


import javax.servlet.http.HttpServletRequest;




/**
 *
 * @author Nelson Akpos
 */
@Controller
public class SettingcoopController {
    @Autowired
    private SettingcoopService settingService;
    
     

   
    
     @Autowired
    private CompanyService companyService;
     
       private static final Logger logger = LoggerFactory.getLogger(SettingcoopController.class);
     
   @RequestMapping(value = "/smssettings", method = RequestMethod.GET)
 public ModelAndView enableSetting() {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("setting_coop", prepareListofBean(settingService.listSetting()));
       
     return new ModelAndView("enablesms", model);
 }
       @RequestMapping(value = "/editsettings", method = RequestMethod.GET)
public ModelAndView editSetting(@ModelAttribute("setting") SettingcoopBean settingBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
               model.put("setting", prepareSettingBean(settingService.getSetting(settingBean.getId())));
              model.put("settins", companyService.getCompany(settingBean.getId()).getName());
		return new ModelAndView("editsetting", model);
 }
    
@RequestMapping(value = "/saveSetting", method = RequestMethod.POST)
public ModelAndView saveSetting(@ModelAttribute("setting")SettingcoopBean  settingBean,BindingResult result,HttpServletRequest req){
       
         String redurlpath = null;
       //settingBean.setDisplay("Y");
        
       Settingcoop setting = prepareModel(settingBean);
	String value=settingBean.getValue();
        int dcompany=settingBean.getCompanyid();
        // System.out.println("display value is - " + Display);
         if("NO".equalsIgnoreCase(value)){
          // System.out.println("display value is - " + Display);
            //String sql = "update display set  countries where id=(select country_id from company where id='" + companyId + "')";
           // Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
             
            
          
             redurlpath = "redirect:/doFeedback.htm?message= SMS Settings Deactivated&redirectURI=smssettings.htm";     
         }else{
              // System.out.println("display value is Y " );
            
             
            //settingService.enableUser(value);
            
              redurlpath = "redirect:/doFeedback.htm?message= SMS Settings Activated&redirectURI=smssettings.htm";     
         }
        
          settingService.addSetting(setting);
	 return new ModelAndView(redurlpath);
 
}
 private Settingcoop prepareModel(SettingcoopBean settingBean){
     Settingcoop setting = new Settingcoop();
     setting.setCompanyid(settingBean.getCompanyid());
     setting.setValue(settingBean.getValue());
     setting.setSetting(settingBean.getSetting());
     setting.setDisplay(settingBean.getDisplay());
     setting.setId(settingBean.getId());
     
     return setting;
 }
    
   private SettingcoopBean prepareSettingBean(Settingcoop setting){
		  SettingcoopBean 	bean = new SettingcoopBean();
                  System.out.println("id - "+setting.getId());
		  bean.setId(setting.getId());
		  bean.setSetting(setting.getSetting());
		  bean.setValue(setting.getValue());
                  bean.setDisplay(setting.getDisplay());
                  bean.setCompanyid(setting.getCompanyid());
                  bean.setCompanyName(companyService.getCompany(setting.getCompanyid()).getName());
                  
                  //System.out.println("comp id - "+setting.getCompanyid());
                  //System.out.println("company name - "+bean.getCompanyName());
		  

		  return bean;
 }
   
   private List<SettingcoopBean> prepareListofBean(List<Settingcoop> settins){
        List<SettingcoopBean> beans = null;

        if(settins != null && !settins.isEmpty()){
        	beans = new ArrayList<SettingcoopBean>();
        	SettingcoopBean setting = null;

        	for(Settingcoop settingBean : settins){
        		setting = new SettingcoopBean();

        		     setting.setId(settingBean.getId());
			    setting.setSetting(settingBean.getSetting());
			    setting.setValue(settingBean.getValue());
			    setting.setCompanyid(settingBean.getCompanyid());
                            setting.setDisplay(settingBean.getDisplay());
                           // bean.setCompanyName(companyService.getCompany(setting.getCompanyid()).getName());
                            setting.setCompanyName(companyService.getCompany(setting.getCompanyid()).getName());
                           // System.out.println("this is the company name "+ setting.getCompanyName());

			    beans.add(setting);
		   }
	    }

        return beans;
 }
}
