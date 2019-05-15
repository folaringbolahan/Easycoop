package com.sift.loan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.Product;
import com.sift.loan.bean.ProductBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class ProductController{

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private HelperUtility helperUTIL;
	
@Autowired
private ProductService productService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();

@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
public ModelAndView saveProduct(@ModelAttribute("product")ProductBean productBean,BindingResult result,HttpServletRequest req){
	 Product product = prepareModel(productBean);
	 productService.addProduct(product);

	 return new ModelAndView("redirect:/newProduct.htm");
 }

 @RequestMapping(value="/products", method = RequestMethod.GET)
 public ModelAndView listProducts() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("products",  prepareListofBean(productService.listProducts()));
	 return new ModelAndView("productsList", model);
 }

 @RequestMapping(value = "/newProduct", method = RequestMethod.GET)
 public ModelAndView addProduct(@ModelAttribute("loan")ProductBean productBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("products",  prepareListofBean(productService.listProducts()));
  	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
     
  	 return new ModelAndView("addProduct", model);
 }

 @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
 public ModelAndView deleteProduct(@ModelAttribute("product")ProductBean productBean,BindingResult result){
	    productService.deleteProduct(prepareModel(productBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("product", null);
		model.put("products",  prepareListofBean(productService.listProducts()));
		return new ModelAndView("addProduct", model);
 }

 @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
 public ModelAndView editProduct(@ModelAttribute("product")ProductBean productBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("product", prepareProductBean(productService.getProduct(productBean.getId())));
		model.put("products",  prepareListofBean(productService.listProducts()));
		return new ModelAndView("editProduct", model);
 }

 @InitBinder
 public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
 }

 private Product prepareModel(ProductBean productBean){
	    Product product = new Product();

	    product.setCompanyId(productBean.getCompanyId());
	    product.setBranchId(productBean.getBranchId());
	    product.setId(productBean.getId());
	    product.setName(productBean.getName());
	    product.setCode(productBean.getCode());
	    product.setInterestRate(productBean.getInterestRate());
	    product.setInterestRateMin(productBean.getInterestRateMin());
	    product.setInterestRateMax(productBean.getInterestRateMax());
	    product.setInitialAmountMin(productBean.getInitialAmountMin());
	    product.setInitialAmountMax(productBean.getInitialAmountMax());
	    product.setProductTypeId(productBean.getProductTypeId());
	    product.setProductTypeCode(productBean.getProductTypeCode());
	    product.setCurrencyId(productBean.getCurrencyId());
	    product.setIsDeleted(productBean.getIsDeleted());
	    product.setIsActive(productBean.getIsActive());
	    
	    product.setHasPenalty(productBean.getHasPenalty());
	    product.setPenalty(productBean.getPenalty());
	    product.setLoanTypeCode(productBean.getLoanTypeCode());
	    product.setLoanDurationInMonth(productBean.getLoanDurationInMonth());
	    product.setPenaltyFormula(productBean.getPenaltyFormula());

	    return product;
 }

 private List<ProductBean> prepareListofBean(List<Product> products){
        List<ProductBean> beans = null;

        if(products != null && !products.isEmpty()){
        	beans = new ArrayList<ProductBean>();
        	ProductBean product = null;

        	for(Product productBean : products){
        		product = new ProductBean();

        	    product.setCompanyId(productBean.getCompanyId());
        	    product.setBranchId(productBean.getBranchId());
        	    product.setId(productBean.getId());
        	    product.setName(productBean.getName());
        	    product.setCode(productBean.getCode());
        	    product.setInterestRate(productBean.getInterestRate());
        	    product.setInterestRateMin(productBean.getInterestRateMin());
        	    product.setInterestRateMax(productBean.getInterestRateMax());
        	    product.setInitialAmountMin(productBean.getInitialAmountMin());
        	    product.setInitialAmountMax(productBean.getInitialAmountMax());
        	    product.setProductTypeId(productBean.getProductTypeId());
        	    product.setProductTypeCode(productBean.getProductTypeCode());
        	    product.setCurrencyId(productBean.getCurrencyId());
        	    product.setIsDeleted(productBean.getIsDeleted());
        	    product.setIsActive(productBean.getIsActive());
        	    
        	    product.setHasPenalty(productBean.getHasPenalty());
        	    product.setPenalty(productBean.getPenalty());
        	    product.setLoanTypeCode(productBean.getLoanTypeCode());
        	    product.setLoanDurationInMonth(productBean.getLoanDurationInMonth());
        	    
        	    product.setPenaltyFormula(productBean.getPenaltyFormula());

			    beans.add(product);
		   }
	    }

        return beans;
  }

  private ProductBean prepareProductBean(Product product){
		  ProductBean 	bean = new ProductBean();

		  bean.setCompanyId(product.getCompanyId());
		  bean.setBranchId(product.getBranchId());
		  bean.setId(product.getId());
		  bean.setName(product.getName());
		  bean.setCode(product.getCode());
		  bean.setInterestRate(product.getInterestRate());
		  bean.setInterestRateMin(product.getInterestRateMin());
		  bean.setInterestRateMax(product.getInterestRateMax());
		  bean.setInitialAmountMin(product.getInitialAmountMin());
		  bean.setInitialAmountMax(product.getInitialAmountMax());
		  bean.setProductTypeId(product.getProductTypeId());
		  bean.setProductTypeCode(product.getProductTypeCode());
		  bean.setCurrencyId(product.getCurrencyId());
		  bean.setIsDeleted(product.getIsDeleted());
		  bean.setIsActive(product.getIsActive());
		  
		  bean.setHasPenalty(product.getHasPenalty());
		  bean.setPenalty(product.getPenalty());
		  bean.setLoanTypeCode(product.getLoanTypeCode());
		  bean.setLoanDurationInMonth(product.getLoanDurationInMonth());
		  bean.setPenaltyFormula(product.getPenaltyFormula());

		  return bean;
  }
}