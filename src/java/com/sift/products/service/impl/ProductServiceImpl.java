/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.products.service.impl;

import com.sift.admin.model.InterestType;
import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.LoanOperands;
import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductAccountType;
import com.sift.easycoopfin.models.ProductAccountTypeCriteria;
import com.sift.easycoopfin.models.ProductAccountVal;
import com.sift.easycoopfin.models.ProductAccountValCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.SavingsAccount;
import com.sift.easycoopfin.models.impl.SavingsDAOImpl;
import com.sift.loan.model.Product;
import com.sift.products.service.ProductService;
import com.sift.webservice.utility.WebServiceUtility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.PersistentTransaction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author logzy
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductServiceImpl.class);
    private DAOFactory daoFactory;
    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String output;
    PersistentTransaction t = null;
    boolean validationStatus = false;
    WebServiceUtility webServiceUtility = new WebServiceUtility();

    @Override
    public String getAvailableCode(String code, int companyId, int branchId) {
        Products product = null;
        ProductsCriteria criteria;
        String status = "";
        String productCode = code;
        try {
            criteria = new ProductsCriteria();
            Criterion codeCriterion = Restrictions.eq("code", code);
            Criterion companyCriterion = Restrictions.eq("companyId", companyId);
            Criterion branchCriterion = Restrictions.eq("branchId", branchId);
            LogicalExpression andExp = Restrictions.and(codeCriterion, branchCriterion);
            criteria.add(andExp);

            product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(criteria);
            if (product == null) {

                status = "ok";
            } else {

                status = "notok";
            }
        } catch (PersistentException ex) {
            _logger.error("getAvailableCode(String code, int companyId, int branchId)", ex);
        }
        return status;
    }

    public Products createProduct(Products product) {
        boolean success = false;
        Products savedProduct = null;
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            DBASE_URI = uri;

            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            webResource = client.resource(DBASE_URI).path("productws/save");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, product);

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
            } else {
                success = true;
            }

            savedProduct = response.getEntity(Products.class);

        } catch (NamingException nx) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedProduct;
    }

    @Override
    public List<Products> listProductsByBranchAndCompanyId(int branchId, int companyId) {

        List<Products> products = null;
        byte isActive = 1;
        try {
            ProductsCriteria criteria = new ProductsCriteria();
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("isActive", isActive));
            /*
             LogicalExpression orExp = Restrictions.or(null, null);
             criteria.add(orExp);
             */
            products = com.sift.easycoopfin.models.DAOFactoryImpl.getDAOFactory().getProductsDAO().listAllProductsByCriteria(criteria);
        } catch (PersistentException ex) {
            _logger.error("getAvailableCode(String code, int companyId, int branchId)", ex);
        }

        return products;

    }

    public List<com.sift.gl.model.Account> getAccountsByCompany(int branchId, int companyId, byte acc) {

        List<com.sift.gl.model.Account> accounts = null;
        String accountType = "G";
        try {
            String glQuery = "select * from `accounts`  account where branch='" + branchId + "' and Companyid='" + companyId + "' and ControlAccount='" + acc + "' and Accounttype='" + accountType + "'";
            accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAllAccountsByQuery(glQuery);

        } catch (Exception ex) {
            _logger.error("getAccountsByCompany(int branchId, int companyId, byte acc)", ex);
        }
        return accounts;
    }

    @Override
    public String getSegmentCode(String accountNumber, int companyId) {
        PersistentSession session;
        String segmentCode = null;
        String acStruct = null;
        String strCode = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("select * from `accounts`  account where AccountNo = '" + accountNumber + "'");

            List<Object[]> rows = query.list();
            if (rows.isEmpty()) {
                segmentCode = "";
            } else {
                Object[] row = rows.get(0);
                acStruct = row[6].toString();

                SQLQuery accountStrSql = session.createSQLQuery("select * from `accountstructures` where StructureCode = '" + acStruct + "' and companyid='" + companyId + "'");

                List<Object[]> structure = accountStrSql.list();

                Object[] strRow = structure.get(0);

                if (Integer.valueOf(strRow[3].toString()) == 3) {
                    segmentCode = row[7].toString();
                }
                if (Integer.valueOf(strRow[4].toString()) == 3) {
                    segmentCode = row[8].toString();
                }
                if (Integer.valueOf(strRow[5].toString()) == 3) {
                    segmentCode = row[9].toString();
                }
                if (Integer.valueOf(strRow[6].toString()) == 3) {
                    segmentCode = row[10].toString();
                }
                if (Integer.valueOf(strRow[7].toString()) == 3) {
                    segmentCode = row[11].toString();
                }
                if (Integer.valueOf(strRow[8].toString()) == 3) {
                    segmentCode = row[12].toString();
                }
                if (Integer.valueOf(strRow[9].toString()) == 3) {
                    segmentCode = row[13].toString();
                }
                if (Integer.valueOf(strRow[10].toString()) == 3) {
                    segmentCode = row[14].toString();
                }
                if (Integer.valueOf(strRow[11].toString()) == 3) {
                    segmentCode = row[15].toString();
                }
                if (Integer.valueOf(strRow[12].toString()) == 3) {
                    segmentCode = row[16].toString();
                }

            }
        } catch (PersistentException ex) {
            // Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            segmentCode = "";
        }
        if (segmentCode != null && segmentCode.length() == 1) {
            segmentCode = "00" + segmentCode;
        } else if (segmentCode != null && segmentCode.length() == 2) {
            segmentCode = "0" + segmentCode;
        }
        return segmentCode;
    }

    @Override
    public synchronized String getProductCode(int branchId, int companyId) {
        PersistentSession session;
        String productCode = null;

        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            Query query = session.createSQLQuery("select count(*) from `products` where branch_id = '" + branchId + "'");

            int value = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
            productCode = String.valueOf(value + 1);

            if (productCode != null && productCode.length() == 1) {
                productCode = "00" + productCode;
            } else if (productCode != null && productCode.length() == 2) {
                productCode = "0" + productCode;
            }

        } catch (PersistentException ex) {
            Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productCode;
    }

    public String deleteProduct(int id) {
        String status = "";
        try {
            Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);
            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().delete(product)) {
                status = "ok";
            } else {
                status = "notok";
            }
        } catch (PersistentException ex) {
            _logger.error("deleteProductAccount(int accountId)", ex);
            status = "notok";
        }
        return status;
    }

    @Async
    public String deleteProductONGen1And2(int id) {
        String status = "";
        try {
            Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);
            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().delete(product)) {
                status = "ok";
                // Delete from EasyCoop If connected.

                String resource = "webserviceutil";
                String method = "deleteprod";
                System.out.println("resource::  " + resource + ", method :: " + method);
                //System.out.println("product = " + product);
                //.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
                webServiceUtility.webserviceClient(resource, webServiceUtility.deleteProduct(method, product.getId(), product.getCompanyId(), product.getBranchId(),  product.getProductTypeCode(), product.getCode()));

            } else {
                status = "notok";
            }
        } catch (PersistentException ex) {
            _logger.error("deleteProductAccount(int accountId)", ex);
            status = "notok";
        }
        return status;
    }

    public String deleteProductAccount(int accountId) {
        String status = "";
        try {
            ProductAccount account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().getProductAccountByORMID(accountId);
            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().delete(account)) {
                status = "ok";
            } else {
                status = "notok";
            }
        } catch (PersistentException ex) {
            _logger.error("deleteProductAccount(int accountId)", ex);
            status = "notok";
        }
        return status;
    }

    public boolean validateProduct(int productId, String productTypeCode) {
        PersistentSession session;
        List<ProductAccount> productAccounts;
        String accountCodes = "";
        String condition = " ";
        boolean isNotFound = false;
        byte isActive = 1;
        int accountCount = 0;
        try {

            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", productId));

            ProductAccountValCriteria pValCriteria = new ProductAccountValCriteria();
            pValCriteria.add(Restrictions.eq("productType", productTypeCode));

            //Lists all the account types that can be added to a product type
            List<ProductAccountVal> productAccountVals = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountValDAO().listAllProductAccountByCriteria(pValCriteria);
            accountCount = productAccountVals.size();
            if (productAccountVals.isEmpty()) {

                validationStatus = false;

            } else {

                productAccounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
                if (productAccounts.isEmpty()) {
                    validationStatus = false;

                } else {
                    for (ProductAccount p : productAccounts) {
                        accountCodes += "|" + p.getProductAccountTypeCode();

                    }
                    for (ProductAccountVal pv : productAccountVals) {

                        if (accountCodes.indexOf(pv.getProductAccountTypeCode()) == -1) {
                            validationStatus = false;
                            isNotFound = true;

                            break;
                        }
                    }
                    if (isNotFound) {
                        validationStatus = false;
                    } else {
                        Products p = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(productId);
                        p.setIsActive(isActive);

                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(p);
                        validationStatus = true;
                    }

                }
            }
        } catch (PersistentException ex) {
            // t.rollback();
            _logger.error("validateProduct(int accountId, String productTypeCode)", ex);
        }
        return validationStatus;
    }

    @Override
    public String checkIfContributionExists(int branchId, int companyId) {
        PersistentSession session;
        String productCode = null;
        String returnString = "";
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            Query query = session.createSQLQuery("select count(*) from `products` where branch_id = '" + branchId + "' and product_type_code = 'C'");

            int value = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
            productCode = String.valueOf(value + 1);

            if (value == 0) {
                returnString = "ok";
            } else {
                returnString = "notok";
            }

        } catch (PersistentException ex) {
            Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //return productCode;
        return returnString;
    }

    @Override
    public String validateProductAccounts(int productId, String productTypeCode) {
        PersistentSession session;
        List<ProductAccount> productAccounts;
        String accountCodes = "";
        String condition = " ";
        String requiredAccount = "";
        boolean isNotFound = false;
        byte isActive = 1;
        int accountCount = 0;
        int countOfAccount = 0;
        try {

            ProductAccountCriteria criteria = new ProductAccountCriteria();
            criteria.add(Restrictions.eq("productId", productId));

            ProductAccountValCriteria pValCriteria = new ProductAccountValCriteria();
            pValCriteria.add(Restrictions.eq("productType", productTypeCode));
            System.out.println("product type code: " + productTypeCode);
            //Lists all the account types that can be added to a product type
            List<ProductAccountVal> productAccountVals = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountValDAO().listAllProductAccountByCriteria(pValCriteria);
            accountCount = productAccountVals.size();
            for (ProductAccountVal pv : productAccountVals) {
                ProductAccountTypeCriteria accCriteria = new ProductAccountTypeCriteria();
                accCriteria.add(Restrictions.eq("code", pv.getProductAccountTypeCode()));

                ProductAccountType accountType = DAOFactory.getDAOFactory().getProductAccountTypeDAO().loadProductAccountTypeByCriteria(accCriteria);
                countOfAccount += 1;
                requiredAccount += " " + countOfAccount + ") " + accountType.getDescription();

            }
            if (productAccountVals.isEmpty()) {

                validationStatus = false;

            } else {

                productAccounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().listAllProductAccountByCriteria(criteria);
                if (productAccounts.isEmpty()) {
                    validationStatus = false;

                } else {
                    for (ProductAccount p : productAccounts) {
                        accountCodes += "|" + p.getProductAccountTypeCode();

                    }
                    System.out.println("Required Account: " + requiredAccount);
                    for (ProductAccountVal pv : productAccountVals) {

                        if (accountCodes.indexOf(pv.getProductAccountTypeCode()) == -1) {
                            validationStatus = false;
                            isNotFound = true;

                            break;
                        }
                    }
                    if (isNotFound) {
                        validationStatus = false;
                    } else {
                        Products p = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(productId);
                        p.setIsActive(isActive);

                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(p);
                        validationStatus = true;
                    }

                }
            }
        } catch (PersistentException ex) {
            // t.rollback();
            _logger.error("validateProduct(int accountId, String productTypeCode)", ex);
        }
        if (validationStatus) {
            return "ok";
        } else {
            return requiredAccount;
        }

    }

    @Override
    public List<LoanOperands> getLoanOperands() {
        PersistentSession session;
        List<LoanOperands> operands = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("SELECT * from loan_operands");

            List<Object[]> rows = query.list();

            if (!rows.isEmpty()) {
                operands = new ArrayList<LoanOperands>();
                for (Object[] row : rows) {
                    LoanOperands operand = new LoanOperands();

                    operand.setId(Integer.valueOf(row[0].toString()));
                    operand.setName(row[1].toString());
                    operand.setCode(row[2].toString());

                    operands.add(operand);
                }
            }
        } catch (PersistentException ex) {
            _logger.error("getLoanOperands", ex);
        } catch (Exception ex) {
            _logger.error("getLoanOperands", ex);
        }
        return operands;

    }

    @Override
    public int getProductIdByAccountNumber(String accountNumber) {
        Custaccountdetails account = null;
        int productId = 0;
        try {
            CustaccountdetailsCriteria criteria = new CustaccountdetailsCriteria();
            criteria.add(Restrictions.eq("id", accountNumber));
            account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCustaccountdetailsDAO().loadAccountByCriteria(criteria);
            String productCode = account.getProduct();
            ProductsCriteria pCriteria = new ProductsCriteria();
            pCriteria.add(Restrictions.eq("code", productCode));
            Products product = DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(pCriteria);
            productId = product.getId();
        } catch (PersistentException ex) {
            _logger.error("getProductIdByAccountNumber(String accountNumber, int branchId, int companyId)", ex);
        }
        return productId;
    }

    public List<InterestType> getInterestType(String type) {
        List<InterestType> interestTypes = null;

        if (type.equals("L")) {
            interestTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listInterestTypes();
        } else {
            interestTypes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listPurchasesInterest();
        }
        return interestTypes;
    }

    @Override
    public Products getProductById(int id) {
        Products products = null;
        //byte isActive = 1;
        System.out.println("ID in getProductById() :: " + id);
        try {
            ProductsCriteria criteria = new ProductsCriteria();
            criteria.add(Restrictions.eq("id", id));

            //products = com.sift.easycoopfin.models.DAOFactoryImpl.getDAOFactory().getProductsDAO().loadProductsByCriteria(criteria);
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(id);

            //products = com.sift.easycoopfin.models.DAOFactoryImpl.getDAOFactory().getProductsDAO().getProductsByORMID(id);
        } catch (PersistentException ex) {
            _logger.error(" getProductById(String id) ", ex);
        }

        return products;
    }

    @Override
    @Async
    public void createProdWS(Products product, int compId, int branchId) {
        System.out.println("Company connected to easycoop...");
        //call to web service
        if (product.getProductTypeCode().equalsIgnoreCase("L")) {
            String resource = "prodloan";
            System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
            webServiceUtility.webserviceClient(resource, webServiceUtility.prodLoan(product.getId(), compId, branchId, product.getName(), product.getCode()));
        } else if (product.getProductTypeCode().equalsIgnoreCase("P")) {
            System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
            webServiceUtility.webserviceClient("prodoffer", webServiceUtility.prodOffer(product.getId(), compId, branchId, product.getName(), product.getCode()));
        }

    }

    //@Override
    @Async
    public void deleteProdWS(Products product, int compId, int branchId) {
        System.out.println("Company connected to easycoop...");
        //call to web service
        String resource = "webserviceutil";
        String method = "deleteprod";
        System.out.println("resource::  " + resource + " method :: " + method);
        System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
        //   webServiceUtility.webserviceClient(resource, webServiceUtility.prodLoan(product.getId(), compId, branchId, product.getName(), product.getCode()));
        webServiceUtility.webserviceClient(resource, webServiceUtility.deleteProduct(method, product.getId(), compId, branchId, product.getCode(), product.getProductTypeCode()));

        /*
         if (product.getProductTypeCode().equalsIgnoreCase("L")) {
         //String resource = "prodloan";
         System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
         webServiceUtility.webserviceClient(resource, webServiceUtility.prodLoan(product.getId(), compId, branchId, product.getName(), product.getCode()));
         } else if (product.getProductTypeCode().equalsIgnoreCase("P")) {
         System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
         webServiceUtility.webserviceClient("prodoffer", webServiceUtility.prodOffer(product.getId(), compId, branchId, product.getName(), product.getCode()));
         }
         */
    }

}
