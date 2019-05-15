/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.controller;

import com.sift.agm.service.AgmService;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.Votes;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.AllItempara;
import com.sift.gl.validator.PeriodcriteriaValidator;
import com.sift.products.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logzy
 */
@Controller
public class AgmReportController {
    @Autowired
    private AgmService agmService;
    private AccountsetupImpl accountsetupService;
    private PeriodcriteriaValidator periodcriteriaValidator;
    private AllItempara reportdet;
    @Autowired
    private CurrentuserdisplayImpl user;

   
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //periodcriteriaValidator = new PeriodcriteriaValidator();
        //user = new Users();
    }

    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    public void setAccountsetupService(AccountsetupImpl accountService) {
        this.accountsetupService = accountService;
    }
  
    @RequestMapping(value = {"voteresults"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String paralist(@ModelAttribute("reportpath") final Object mapping1FormObject, @ModelAttribute("paratype") final Object mapping2FormObject, ModelMap model) {
        Integer dcompany = user.getCurruser().getCompanyid();
        Integer dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
        reportdet.setPeriodId(user.getCompanydetails(dbranch, dcompany).getCurrentPeriod());
        reportdet.setYear(user.getCompanydetails(dbranch, dcompany).getCurrentYear());
        //reportdet.s
        
        List<Votes> votes = agmService.listVotes(dcompany);
        model.addAttribute("votes", votes);
        model.addAttribute("reportdet", reportdet);
        model.addAttribute("reportpath", mapping1FormObject);
        model.addAttribute("paratype", mapping2FormObject);
        return "voteresults";
    }
}
