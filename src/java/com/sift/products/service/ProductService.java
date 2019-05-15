/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.products.service;

import com.sift.admin.model.InterestType;
import com.sift.easycoopfin.models.LoanOperands;
import com.sift.easycoopfin.models.Products;
import com.sift.loan.model.Product;
import java.util.List;

/**
 *
 * @author logzy
 */
public interface ProductService {
    public String getAvailableCode(String code, int companyId, int branchId);
    public Products createProduct(Products product);
    public List<Products> listProductsByBranchAndCompanyId(int branchId,int companyId);
    public List<com.sift.gl.model.Account> getAccountsByCompany(int branchId, int companyId, byte acc);
    public String getSegmentCode(String accountNumber,int companyId);
    public String getProductCode(int branchId, int companyId);
    public String deleteProduct(int id);
    public String deleteProductAccount(int accountId);
    public boolean validateProduct(int productId, String productTypeCode);
    public String validateProductAccounts(int productId, String productTypeCode);
    public String checkIfContributionExists(int branchId, int companyId);
    public List<LoanOperands> getLoanOperands();
    public int getProductIdByAccountNumber(String accountNumber);
    public List<InterestType> getInterestType(String type);
    public Products getProductById(int id);
    public void createProdWS(Products product, int compId, int branchId);
    public void deleteProdWS(Products product, int compId, int branchId);
    public String deleteProductONGen1And2(int productId);
}
