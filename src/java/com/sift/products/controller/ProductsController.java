/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.products.controller;

/**
 *
 * @author logzy
 */
import com.sift.admin.model.InterestType;
import com.sift.admin.service.BranchService;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Currency;
import com.sift.easycoopfin.models.CurrencyCriteria;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.LoanOperands;
import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductAccountRel;
import com.sift.easycoopfin.models.ProductAccountType;
import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.impl.ProductsDAOImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.loan.model.Product;
import com.sift.products.service.ProductService;
import com.sift.savings.utility.HelperUtil;
import com.sift.webservice.utility.WebServiceUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/product")
public class ProductsController {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductsController.class);
    //@Autowired
    private ProductService productService;

    @Autowired
    private CurrentuserdisplayImpl user;
    String clientIPAddress;
    @Autowired
    private BranchService branchService;
    WebServiceUtility webServiceUtility = new WebServiceUtility();

    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    public void setProductService(ProductService productServiceX) {
        productService = productServiceX;
    }

    @RequestMapping(value = "/index", method
            = RequestMethod.GET)
    public String index() {
        PersistentSession session;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("SELECT a.id,a.gl_account_number,a.product_id, a.product_account_type_code, b.description FROM productaccount a, productaccounttype b where b.code=a.product_account_type_code ");

            List<Object[]> rows = query.list();
            List<ProductAccountRel> rels = null;
            if (!rows.isEmpty()) {
                rels = new ArrayList<ProductAccountRel>();
                for (Object[] row : rows) {
                    ProductAccountRel rel = new ProductAccountRel();
                    ProductAccount acc = new ProductAccount();
                    acc.setId(Integer.valueOf(row[0].toString()));
                    acc.setGlAccountNumber(row[1].toString());
                    acc.setProductId(Integer.valueOf(row[2].toString()));
                    acc.setProductAccountTypeCode(row[3].toString());

                    ProductAccountType accType = new ProductAccountType();
                    accType.setDescription(row[4].toString());

                    rel.setId(acc.getId());
                    rel.setGlAccountNumber(acc.getGlAccountNumber());
                    rel.setProductAccountTypeCode(acc.getProductAccountTypeCode());
                    rel.setDescription(accType.getDescription());

                    rels.add(rel);
                }

            }
        } catch (PersistentException ex) {
            _logger.error("Join Error", ex);
        }
        return "index";

    }

    @RequestMapping(value = "/blank", method
            = RequestMethod.GET)
    public String blank() {
        return "blank";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    String getAvailableCode(@RequestParam("id") int id) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();

        //return productService.deleteProduct(id);
        // call web service to delete product on GEN 1
        //String result = productService.deleteProduct(id);
        String result;
        //System.out.println("result :: " + result + " id ::  " + id);
        // Products product = productService.getProductById(id);
     
        //System.out.println("ProdcutTypeCode in ProductsController = " + product.getProductTypeCode());
        // to call web service for product creation on Generation 1
        
           //call easycoop web service
            if (branchService.getBranch(branchId).getConnectToEazyCoop().equalsIgnoreCase("Y")
                    && branchService.getBranch(branchId).getActive().equalsIgnoreCase("Y")) {
                //System.out.println("product object   = " + product);
                //System.out.println("ProdcutTypeCode in ProductsController = " + product.getProductTypeCode());

                //productService.deleteProdWS(product, compId, branchId);
                result = productService.deleteProductONGen1And2(id);

            } else {
                System.out.println("Company not connected to easycoop...");
                System.out.println("Is it Connected to EasyCoop: " + branchService.getBranch(branchId).getConnectToEazyCoop());
                result = productService.deleteProduct(id);
            }
            
            
            
            
      
        
        System.out.println("About to return");
        return result;
    }

    @RequestMapping(value = "/list", method
            = RequestMethod.GET)
    public ModelAndView listProducts(ModelMap model) throws PersistentException {
        List<Products> products = null;
        List<Currency> currencies = null;
        List<ProductType> productTypes = null;
        List<ProductAccountType> accountTypes = null;
        List<com.sift.gl.model.Account> accounts = null;
        List<ProductAccount> pacc = null;
        byte status = 3;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            ProductsCriteria pcriteria = new ProductsCriteria();
            pcriteria.add(Restrictions.eq("companyId", dcompany));
            pcriteria.add(Restrictions.eq("branchId", dbranch));
            pcriteria.add(Restrictions.ne("isActive", status));
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listAllProductsByCriteria(pcriteria);

            currencies = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCurrencyDAO().listCurrencyByQuery(null, null);
            ProductTypeCriteria ptcriteria = new ProductTypeCriteria();
            ptcriteria.add(Restrictions.ne("code", "T"));
            productTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductAccountByCriteria(ptcriteria);
            // productTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductTypeByQuery(null, null);
            accountTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().listAllProductAccountTypeByQuery(null, null);

            String glQuery = "select * from `accounts`  account where branch='" + dbranch + "' and Companyid='" + dcompany + "' and Accounttype='G'";
            accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAllAccountsByQuery(glQuery);

            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", new Integer(1)));
            pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);

            List<InterestType> interestTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listInterestTypes();

            model.put("interestTypes", interestTypes);
            model.put("companyId", dcompany);
            model.put("branchId", dbranch);
            model.put("accountTypes", accountTypes);
            model.put("accounts", accounts);
            model.put("productTypes", productTypes);
            model.put("currencies", currencies);
            model.put("productAccounts", pacc);
            model.addAttribute("products",
                    products);
        } catch (PersistentException ex) {

            _logger.error("listProducts calling method listAllProductsByQuery(null, null)", ex);
        } finally {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
        }

        //return "products";
        return new ModelAndView("products", "products", products);
    }

    @RequestMapping(value = "/createp", method
            = RequestMethod.GET)
    public String createProduct(ModelMap model) {
        byte interest = 0;
        Products product = new Products();
        product.setBranchId(new Integer(3));
        product.setCode("DAD7");
        product.setCompanyId(new Integer(4));
        product.setCurrencyId(new Integer(2));
        product.setHasInterest(interest);
        product.setInitialAmountMax(new Float(0));
        product.setInterestRate(new Float(0));
        product.setIsTaxable(interest);
        product.setName("DAD7 Saving");
        product.setProductTypeCode(new String("S"));
        product.setSegmentCode(new String("001"));
        if (productService.createProduct(product) != null) {
            System.out.println("Successful");
        } else {
            System.out.println("failed");
        }
        return "index";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public String addProduct(
            @ModelAttribute("productForm") Products product,
            ModelMap model) {
        Long lastInsertId = null;
        try {

            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(product)) {
            } else {

                System.out.print("Couldn't save");
            }

        } catch (PersistentException ex) {

            _logger.error("listProducts calling method listAllProductsByQuery(null, null)", ex);
        }

        return "redirect:/views/productaccount/add/14";

    }

    @RequestMapping(value = "/editproduct", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Products edit(
            @RequestBody Products product,
            ModelMap model, HttpServletRequest request) {

        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            clientIPAddress = request.getRemoteAddr();
            String currencyCode = user.getCurrusercompany().getBaseCurrency();
            System.out.println("Currency: " + currencyCode);
            CurrencyCriteria currencyCriteria = new CurrencyCriteria();
            currencyCriteria.add(Restrictions.eq("code", currencyCode));
            Currency currency = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getCurrencyByCode(currencyCriteria);
            product.setCurrencyId(currency.getId());

            product.setBranchId(dbranch);
            product.setCompanyId(dcompany);
            // product.setCode(productService.getProductCode(dbranch, dcompany));
            //System.out.println("Account Number: "+product.getControlAccount());
            //   product.setSegmentCode(productService.getSegmentCode(product.getControlAccount(), dcompany));

            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(product)) {
                HelperUtil.logEvent(5, "UPDATEPR", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), product.getName(), "OK", user.getCurruser().getBranch(), dcompany);

            } else {
                HelperUtil.logEvent(5, "UPDATEPR", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), product.getName(), "FAILED", user.getCurruser().getBranch(), dcompany);

            }

        } catch (PersistentException ex) {

            _logger.error("editProduct error ", ex);
        }

        return product;

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Products editProduct(
            @RequestBody Products product,
            ModelMap model, HttpServletRequest request) {

        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            String productCode = product.getProductTypeCode();
            clientIPAddress = request.getRemoteAddr();

            String currencyCode = user.getCurrusercompany().getBaseCurrency();
            System.out.println("Currency: " + currencyCode);
            CurrencyCriteria currencyCriteria = new CurrencyCriteria();
            currencyCriteria.add(Restrictions.eq("code", currencyCode));
            Currency currency = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getCurrencyByCode(currencyCriteria);
            product.setCurrencyId(currency.getId());

            product.setBranchId(dbranch);
            product.setCompanyId(dcompany);
            product.setCode(productService.getProductCode(dbranch, dcompany));
            System.out.println("Account Number: " + product.getControlAccount());
            product.setSegmentCode(productService.getSegmentCode(product.getControlAccount(), dcompany));

            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().add(product)) {
                ProductAccount productAccount = new ProductAccount();
                productAccount.setGlAccountNumber(product.getControlAccount());
                productAccount.setBranchId(dbranch);
                productAccount.setCompanyId(dcompany);
                productAccount.setProductId(product.getId());
                productAccount.setProductAccountTypeCode("CTR");

                if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().save(productAccount)) {

                    /*
                     //call easycoop web service

                     if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                     && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {

                     System.out.println("Company connected to easycoop...");
                     //call to web service
                     if (product.getProductTypeCode().equalsIgnoreCase("L")) {
                     String resource = "prodloan";
                     System.out.println("ProdcutTypeCode = "+ product.getProductTypeCode());
                     webServiceUtility.webserviceClient(resource,webServiceUtility.prodLoan(product.getId(),user.getCurruser().getCompanyid(),user.getCurruser().getBranch(), product.getName(),product.getCode()));
                     } else if (product.getProductTypeCode().equalsIgnoreCase("P")) {
                     System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
                     webServiceUtility.webserviceClient("prodoffer", webServiceUtility.prodOffer(product.getId(), user.getCurruser().getCompanyid(),user.getCurruser().getBranch(), product.getName(),product.getCode()));
                     }
                     } else {
                     System.out.println( "Company not connected to easycoop..."); 
                     System.out.println("Is it Connected to EasyCoop: " + branchService.getBranch(dbranch).getConnectToEazyCoop());
                     
                     }
                    
                     */
                    HelperUtil.logEvent(5, "CREATEPR", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), product.getName(), "OK", user.getCurruser().getBranch(), dcompany);
                }
            } else {
                HelperUtil.logEvent(5, "CREATEPR", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), product.getName(), "FAILED", user.getCurruser().getBranch(), dcompany);
                System.out.print("Couldn't save");
            }

        } catch (PersistentException ex) {

            _logger.error("editProduct error ", ex);
        }

        return product;

    }

    @RequestMapping(value = "/getproduct", method = RequestMethod.GET)
    public @ResponseBody
    Products getAvailability(@RequestParam int id) {

        Products product = null;
        try {
            product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);
        } catch (PersistentException ex) {

            _logger.error("loadProdcut error ", ex);
        }

        return product;
    }

    @RequestMapping(value = "/getcode", method = RequestMethod.GET)
    public @ResponseBody
    String getAvailableCode(@RequestParam("code") String code) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        return productService.getAvailableCode(code, companyId, branchId);
    }

    @RequestMapping(value = "/checkproduct", method = RequestMethod.GET)
    public @ResponseBody
    String checkIfProductExists(@RequestParam("code") String productTypeCode) {

        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();

        return productService.checkIfContributionExists(branchId, companyId);
    }

    @RequestMapping(value = "/getsegmentcode", method = RequestMethod.GET)
    public @ResponseBody
    String getSegmentCode(@RequestParam("accountNumber") String accountNumber) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        return productService.getSegmentCode(accountNumber, companyId);
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public ModelAndView editProductForm(ModelMap model, @PathVariable int id) {

        Products productForm = null;
        List<ProductAccount> pacc = null;
        List<ProductAccountRel> acc = null;
        List<ProductAccountType> accountTypes = null;
        String accountType = "G";
        List<InterestType> interestTypes = null;
        byte control = 1;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();

            productForm = DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);
            List<Currency> currencyList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCurrencyDAO().listCurrencyByQuery(null, null);
            ProductTypeCriteria ptcriteria = new ProductTypeCriteria();
            ptcriteria.add(Restrictions.ne("code", "T"));
            List<ProductType> productList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductAccountByCriteria(ptcriteria);
            //List<ProductType> productList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductTypeByQuery(null, null);
            String glQuery = "select * from `accounts`  account where branch='" + dbranch + "' and Companyid='" + dcompany + "'";
            List<com.sift.gl.model.Account> glAccounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAllAccountsByQuery(glQuery);

            interestTypes = productService.getInterestType(productForm.getProductTypeCode());
            List<LoanOperands> operands = productService.getLoanOperands();

            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", id));
            criteria.add(Restrictions.eq("branchId", dbranch));
            pacc = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
            acc = DAOFactory.getDAOFactory().getProductAccountDAO().getAccountRelByProductId(id);
            accountTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountTypeDAO().listAllProductAccountTypeByQuery(null, null);
            model.put("productAccounts", acc);
            model.put("accountTypes", accountTypes);
            model.put("operands", operands);
            model.put("interestTypes", interestTypes);
            model.put("accounts", glAccounts);
            model.put("productList", productList);
            model.put("currencyList", currencyList);
            model.put("companyId", dcompany);
            model.put("branchId", dbranch);
            if (productForm.getBranchId()==dbranch) 
            {   
             model.put("productForm", productForm);
            }
            else
            {
               productForm =  new Products();
               model.put("productForm", productForm); 
               model.put("productAccounts", null);
            }    
        } catch (PersistentException ex) {
            _logger.error("showProductForm(null, null)", ex);
        } catch (Exception ex) {
            _logger.error(" showProductForm(null, null)", ex);
        }

        model.put("productTypeList", "");
        //return "product";
        return new ModelAndView("editproduct", "product", productForm);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showProductForm(ModelMap model) {

        Products productForm = new Products();
        model.put("productForm", productForm);
        // List<ProductAccountType> accountTypes = null;

        String accountType = "G";
        byte control = 1;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            //HelperUtil.sendMailByUserGroup("SA7", dbranch, dcompany, user.getCurruser().getUserId(),"","");
            List<Currency> currencyList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCurrencyDAO().listCurrencyByQuery(null, null);
            ProductTypeCriteria ptcriteria = new ProductTypeCriteria();
            ptcriteria.add(Restrictions.ne("code", "T"));
            List<ProductType> productList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductAccountByCriteria(ptcriteria);
            //List<ProductType> productList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductTypeDAO().listAllProductTypeByQuery(null, null);
            String glQuery = "select * from `accounts`  account where branch='" + dbranch + "' and Companyid='" + dcompany + "' and ControlAccount='" + control + "'";
            List<com.sift.gl.model.Account> glAccounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAllAccountsByQuery(glQuery);
            List<InterestType> interestTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listInterestTypes();
            List<LoanOperands> operands = productService.getLoanOperands();

            // model.put("accountTypes", accountTypes);
            model.put("operands", operands);
            model.put("interestTypes", interestTypes);
            model.put("accounts", glAccounts);
            model.put("productList", productList);
            model.put("currencyList", currencyList);
            model.put("companyId", dcompany);
            model.put("branchId", dbranch);

        } catch (PersistentException ex) {
            _logger.error("showProductForm(null, null)", ex);
        } catch (Exception ex) {
            _logger.error(" showProductForm(null, null)", ex);
        }

        model.put("productTypeList", "");
        //return "product";
        return new ModelAndView("product", "product", productForm);
    }

    @RequestMapping(value = "/loantypes", method = RequestMethod.GET)
    public @ResponseBody
    List<InterestType> getLoanTypes(@RequestParam("type") String type) {

        return productService.getInterestType(type);
    }

    @RequestMapping(value = "/getaccounts", method = RequestMethod.GET)
    public @ResponseBody
    List<com.sift.gl.model.Account> getAccounts(@RequestParam byte control) {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        return productService.getAccountsByCompany(dbranch, dcompany, control);

    }
}
