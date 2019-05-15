/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.products.controller;

/**
 *
 * @author logzy
 */
import com.sift.admin.service.BranchService;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Currency;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductAccountRel;
import com.sift.easycoopfin.models.ProductAccountType;
import com.sift.easycoopfin.models.ProductType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import com.sift.easycoopfin.models.Products;
import java.util.List;
import org.springframework.ui.ModelMap;
import com.sift.easycoopfin.models.impl.ProductsDAOImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.model.Product;
import com.sift.products.service.ProductService;
import com.sift.webservice.utility.WebServiceUtility;
import java.util.ArrayList;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/productaccount")
public class ProductAccountController {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductAccountController.class);
    @Autowired
    private CurrentuserdisplayImpl user;
    private ProductService productService;
    @Autowired
    private BranchService branchService;
    WebServiceUtility webServiceUtility = new WebServiceUtility();
    //@Autowired

    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    public void setProductService(ProductService productServiceX) {
        productService = productServiceX;
    }

    @RequestMapping(value = "/list", method
            = RequestMethod.GET)
    public ModelAndView listProductsAccounts(ModelMap model) {
        List<Products> products = null;
        List<Currency> currencies = null;
        List<ProductType> productTypes = null;

        return new ModelAndView("products", "products", products);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    String getAvailableCode(@RequestParam("id") int id) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();

        return productService.deleteProductAccount(id);
        
        
    }

    @RequestMapping(value = "/addaccount", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductAccountRel> addProductAccount(
            @RequestBody ProductAccount productAccount,
            ModelMap model) throws PersistentException {
        List<ProductAccount> pacc = null;
        List<ProductAccountRel> acc = null;
        ProductAccountCriteria criteria;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            productAccount.setBranchId(dbranch);
            productAccount.setCompanyId(dcompany);

            ProductAccountCriteria criteria1 = new ProductAccountCriteria();

            criteria1.add(Restrictions.eq("productAccountTypeCode", productAccount.getProductAccountTypeCode()));
            criteria1.add(Restrictions.eq("productId", productAccount.getProductId()));
            pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria1);
            acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(productAccount.getProductId());
            if (pacc.isEmpty()) {
                System.out.println("No record  exists");
                if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().save(productAccount)) {
                    criteria = new ProductAccountCriteria();
                    criteria.add(Restrictions.eq("productId", productAccount.getProductId()));
                    pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
                    acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(productAccount.getProductId());
                }
            } else {

                criteria = new ProductAccountCriteria();
                criteria.add(Restrictions.eq("productId", productAccount.getProductId()));
                pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
                acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(productAccount.getProductId());
            }

        } catch (PersistentException ex) {
            criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", productAccount.getProductId()));
            pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
            acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(productAccount.getProductId());
            // _logger.error("addProductAccount calling method listAllProductAccountByCriteria(criteria)", ex);
        }

        // return pacc;
        return acc;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public @ResponseBody
    String validateProduct(@RequestParam int productId, @RequestParam String productTypeCode) {

        //return productService.validateProductAccounts(productId, productTypeCode);
        String result = productService.validateProductAccounts(productId, productTypeCode);
        // to call web service for product creation on Generation 1
        if ("ok".equalsIgnoreCase(result)) {
            // call the web service 
            int dbranch = user.getCurruser().getBranch();
            int compId = user.getCurruser().getCompanyid();
            Products product = productService.getProductById(productId);
            System.out.println("product :: " + product);
            
            //call easycoop web service
            if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                    && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {
            
                productService.createProdWS(product, compId, dbranch);
                
                
            } else {
                System.out.println("Company not connected to easycoop...");
                System.out.println("Is it Connected to EasyCoop: " + branchService.getBranch(dbranch).getConnectToEazyCoop());

            }

        }{
        // Don't call web service
        }

        return result;
    }
    
    
    
    
    @RequestMapping(value = "/deleteprod", method = RequestMethod.GET)
    public @ResponseBody
    String deleteProduct(@RequestParam("id") int productId) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();

        //return productService.deleteProduct(id);
        // call web service to delete product on GEN 1
        String result = productService.deleteProduct(productId);
        System.out.println("result :: " + result + " id ::  " + productId);
       // Products product = productService.getProductById(id);
        int did = 0;
        //did = productId;
         did = productId = 20;
        //System.out.println("ProdcutTypeCode in ProductsController = " + product.getProductTypeCode());
        // to call web service for product creation on Generation 1
        if ("ok".equalsIgnoreCase(result)) {
            System.out.println("result is ok  ");
            // call the web service 
            int dbranch = user.getCurruser().getBranch();
            int compId = user.getCurruser().getCompanyid();
            //Products product = productService.getProductById(id); 
            Products product = productService.getProductById(did);
            // System.out.println("ProdcutTypeCode in ProductsController = " + product.getProductTypeCode());
            //call easycoop web service
            if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                    && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {
                System.out.println("product object   = " + product);
                System.out.println("ProdcutTypeCode in ProductsController = " + product.getProductTypeCode());

                
                 productService.deleteProdWS(product, compId, branchId);
                 
                /*
                String resource = "webserviceutil";
                String method = "deleteprod";
                System.out.println("resource::  " + resource + " method :: " + method);
                System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
                //   webServiceUtility.webserviceClient(resource, webServiceUtility.prodLoan(product.getId(), compId, branchId, product.getName(), product.getCode()));
                webServiceUtility.webserviceClient(resource, webServiceUtility.deleteProduct(method, product.getId(), compId, branchId, product.getCode(), product.getProductTypeCode()));
                 */
            } else {
                System.out.println("Company not connected to easycoop...");
                System.out.println("Is it Connected to EasyCoop: " + branchService.getBranch(dbranch).getConnectToEazyCoop());

            }

        }
        {
        // Don't call web service
            //System.out.println("result is not ok");
        }
        System.out.println("About to return");
        return result;
    }
    
    
    

    @RequestMapping(value = "/getaccounts", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductAccountRel> getAccounts(@RequestParam int id) {

        List<ProductAccount> accounts = null;
        List<ProductAccountRel> acc = null;
        try {
            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", id));
            criteria.addOrder(Order.asc("id"));
            // accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
            acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(id);

        } catch (PersistentException ex) {

            _logger.error("loadProdcut error ", ex);
        }

        return acc;
    }

    @RequestMapping(value = "/index", method
            = RequestMethod.GET)
    public String index() {
        return "index";

    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public ModelAndView showProductForm(@ModelAttribute("productAccountForm") ProductAccount productAccount,
            @PathVariable int id, ModelMap model) {

        ProductAccount productAccountForm = new ProductAccount();
        productAccountForm.setProductId(id);
        List<com.sift.gl.model.Account> accounts = null;
        List<ProductAccountType> accountTypes = null;
        List<ProductAccount> pacc = null;
        List<ProductAccountRel> acc = null;
        model.put("productForm", productAccountForm);
        model.put("productId", id);
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByORMID(id);
            String glQuery = "select * from `accounts`  account where branch='" + dbranch + "' and Companyid='" + dcompany + "' and Accounttype='G'";
            accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAllAccountsByQuery(glQuery);

            accountTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().listAllProductAccountTypeByQuery(null, null);

            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", id));
            criteria.add(Restrictions.eq("branchId", dbranch));
            pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
            acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(id);

            model.put("productAccounts", acc);
            if (product.getBranchId()==dbranch) 
            {
            model.put("product", product);
            }
            else
            {
                model.put("product", null);
            }
            model.put("accounts", accounts);
            model.put("accountTypes", accountTypes);
          //  model.put("productAccounts", pacc);

        } catch (PersistentException ex) {
            _logger.error("listCurrency calling method listCurrencyByQuery(null, null)", ex);
        }

        model.put("productTypeList", "");
        //return "product";
        return new ModelAndView("productaccount", "productaccount", productAccountForm);
    }
}
