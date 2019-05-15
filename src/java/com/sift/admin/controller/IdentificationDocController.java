/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.controller;

import com.sift.admin.model.IdentificationDoc;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.IdentificationDocService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Olakunle Awotunbo
 */
@Controller
public class IdentificationDocController {
    
    @Autowired
    public IdentificationDocService identificationDocService;
    @Autowired 
    public CountryService countryService;
    
    public static final Logger logger =  LoggerFactory.getLogger(IdentificationDocController.class);
    
    @RequestMapping(value = "/addIdenDoc.htm", method = RequestMethod.GET)
    public ModelAndView getIdentificationDoc(ModelMap map){
        
        logger.info("Inside IdentificationDoc Controller");
        
        map.addAttribute("identificationDoc", new IdentificationDoc());
        map.addAttribute("countries", countryService.listCountry());
        map.addAttribute("listAllIdentificationDoc", identificationDocService.listAllIdentificationDoc());
        return new ModelAndView("identificationDoc", map);
    }
    
    @RequestMapping(value = "/saveIdenDoc", method = RequestMethod.POST)
    public ModelAndView saveIdenDoc(@ModelAttribute(value = "identificationDoc") IdentificationDoc identificationDoc,
            BindingResult result){
        
        if(result.hasErrors() == true){
            List<FieldError> lerr = result.getFieldErrors();
                for(FieldError err : lerr){
                    System.out.println(err);
                }
            //System.out.println("There is and error in saveIdenDoc");
            logger.info("IdentificationDoc Form has error");
        return new ModelAndView("redirect:/addIdenDoc");     
        }
        //identificationDoc.setCountryName(identificationDocService.getCountryName(identificationDoc.getCountryId())); //country id to get countryname
        identificationDocService.addOrUpdateIdentificationDoc(identificationDoc);
        logger.info("IdentificationDoc Record Saved");
    return new ModelAndView("redirect:/addIdenDoc.htm");        
    }
    
    @RequestMapping(value = "identificationDoc/edit/saveIdenDoc", method = RequestMethod.POST)
    public ModelAndView UpdateIdentificationDoc(@ModelAttribute(value = "identificationDoc") IdentificationDoc identificationDoc,
            BindingResult result){
        
        if(result.hasErrors() == true){
            List<FieldError> lerr = result.getFieldErrors();
                for(FieldError err : lerr){
                    System.out.println(err);
                    logger.info("Unable to update identificationDoc Form has error");
                }
            //System.out.println("There is an error in UpdateIdentificationDoc");
        return new ModelAndView("redirect:/addIdenDoc");   
        }
        
        identificationDocService.addOrUpdateIdentificationDoc(identificationDoc);
        logger.info("IdentificationDoc Record Updated");
    return new ModelAndView("redirect:/addIdenDoc.htm");        
    }
    
    @RequestMapping(value = "/identificationDoc/delete/{id}")
    public ModelAndView deleteIdentificationDoc(@PathVariable("id") int id) {        
        identificationDocService.deleteIdentificationDoc(id);
        logger.info("IdentificationDoc Record " + id +" deleted");
        return new ModelAndView("redirect:/addIdenDoc.htm");
    }
    
    @RequestMapping(value = "/identificationDoc/edit/{id}",  method = RequestMethod.GET)
    public ModelAndView editIdentificationDoc(@PathVariable("id") int id, ModelMap map) {
        logger.info("Edit IdentificationDoc record");        
        map.addAttribute("identificationDoc", identificationDocService.getIdentificationDocById(id));
        map.addAttribute("countries", countryService.listCountry());
       // map.addAttribute("listAllIdentificationDoc", identificationDocService.listAllIdentificationDoc());
        return new ModelAndView("identificationDoc", map);
    }
    
}
