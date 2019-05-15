/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.model.AllItempara;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.model.Product;
import com.sift.loan.model.Product;
import com.sift.loan.service.ProductService;
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
 * @author ABAYOMI AWOSUSI
 */
@Controller
//@RequestMapping("gl/")
public class LoanStatusDateController{
 
   private AllItempara reportdet;
   @Autowired
   private CurrentuserdisplayImpl user;
   @Autowired
    private ProductService productService;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
         /// periodcriteriaValidator = new PeriodcriteriaValidator();
          //user = new Users();
   }
   
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
  
      
   @RequestMapping(value = {"loanstatusdate"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String paralist(@ModelAttribute("reportpath") final Object mapping1FormObject,@ModelAttribute("paratype") final Object mapping2FormObject,ModelMap model)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer  dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
       List<Product> products = productService.listProductsByBranch(dbranch.toString());
       model.addAttribute("products", products);
       model.addAttribute("reportdet",reportdet);
       model.addAttribute("reportpath", mapping1FormObject);
       model.addAttribute("paratype", mapping2FormObject);
     return "loanstatusdate";
   }

}
