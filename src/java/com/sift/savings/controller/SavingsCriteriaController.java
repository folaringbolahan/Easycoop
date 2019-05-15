package com.sift.savings.controller;

import com.sift.easycoopfin.models.Products;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.model.AllItempara;
import com.sift.gl.validator.PeriodcriteriaValidator;
import com.sift.gl.dao.PeriodcriteriaImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Users;
import com.sift.products.service.ProductService;
import com.sift.products.service.impl.ProductServiceImpl;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
public class SavingsCriteriaController {

    //@Autowired
    private ProductService productService;
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

   // @Autowired
    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    public void setAccountsetupService(AccountsetupImpl accountService) {
        this.accountsetupService = accountService;
    }
   public void setProductService(ProductService productServiceX){
       productService = productServiceX;
   }
    @RequestMapping(value = {"productdaterange"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String paralist(@ModelAttribute("reportpath") final Object mapping1FormObject, @ModelAttribute("paratype") final Object mapping2FormObject, ModelMap model) {
        Integer dcompany = user.getCurruser().getCompanyid();
        Integer dbranch = user.getCurruser().getBranch();
        reportdet = new AllItempara();
        reportdet.setPeriodId(user.getCompanydetails(dbranch, dcompany).getCurrentPeriod());
        reportdet.setYear(user.getCompanydetails(dbranch, dcompany).getCurrentYear());
        //reportdet.s
        
        List<Products> products = productService.listProductsByBranchAndCompanyId(dbranch, dcompany);
        model.addAttribute("products", products);
        model.addAttribute("reportdet", reportdet);
        model.addAttribute("reportpath", mapping1FormObject);
        model.addAttribute("paratype", mapping2FormObject);
        return "productdaterange";
    }
}
