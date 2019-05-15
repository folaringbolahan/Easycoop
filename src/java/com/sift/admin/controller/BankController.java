/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.controller;

import com.sift.admin.model.Bank;
import com.sift.admin.service.BankService;
import com.sift.admin.service.CountryService;
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
public class BankController {
    
    @Autowired
    public BankService bankService;
    @Autowired
    private CountryService countryService;
    
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    
    @RequestMapping(value = "/addBank.htm", method = RequestMethod.GET)
    public ModelAndView getBank(ModelMap map){
        
        logger.info("Inside Bank Controller");
        
        map.addAttribute("bank", new Bank());
        map.addAttribute("countries", countryService.listCountry());
        map.addAttribute("listAllBanks", bankService.listAllBanks());
        return new ModelAndView("bank", map);
    }
    
    @RequestMapping(value = "/saveBank", method = RequestMethod.POST)
    public ModelAndView saveBank(@ModelAttribute(value = "bank") Bank bank, BindingResult result){
        
        if(result.hasErrors() == true){
            List<FieldError> lerr = result.getFieldErrors();
                for(FieldError err : lerr){
                    System.out.println(err);
                }
            //System.out.println("There is and error  in saveBank");
            logger.info("Bank Form has error");
        return new ModelAndView("redirect:/addBank");     
        }
        //bank.setCountryName(bankService.getCountryName(bank.getCountryId())); //country id to get countryname
        bankService.addOrUpdateBank(bank);
        logger.info("Bank Record Saved");
    return new ModelAndView("redirect:/addBank.htm");        
    }
    
    @RequestMapping(value = "bank/edit/saveBank", method = RequestMethod.POST)
    public ModelAndView UpdateBank(@ModelAttribute(value = "bank") Bank bank, BindingResult result){
        
        if(result.hasErrors() == true){
            List<FieldError> lerr = result.getFieldErrors();
                for(FieldError err : lerr){
                    System.out.println(err);
                    logger.info("Unable to update Bank Form has error");
                }
            //System.out.println("There is and error ");
        return new ModelAndView("redirect:/addBank");   
        }
       // bank.setCountryName(bankService.getCountryName(bank.getCountryId())); //country id to get countryname
        bankService.addOrUpdateBank(bank);
        logger.info("Bank Record Updated");
    return new ModelAndView("redirect:/addBank.htm");        
    }
    
    @RequestMapping(value = "/bank/delete/{id}")
    public ModelAndView deleteBank(@PathVariable("id") int id) {
        bankService.deleteBank(id);
        logger.info("Bank Record " + id +" deleted");
        return new ModelAndView("redirect:/addBank.htm");
    }
    
    @RequestMapping(value = "/bank/edit/{id}",  method = RequestMethod.GET)
    public ModelAndView editBank(@PathVariable("id") int id, ModelMap map) {
        logger.info("Edit bank record");
        map.addAttribute("bank", bankService.getBankById(id));
        map.addAttribute("countries", countryService.listCountry());
       // map.addAttribute("listAllBanks", bankService.listAllBanks());
        return new ModelAndView("bank", map);
    }
}
