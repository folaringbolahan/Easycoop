package com.sift.loan.dao;

import java.util.List;

import com.sift.loan.model.Product;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ProductDao {
	 public void addProduct(Product product);
	 public List<Product> listProducts();
	 public List<Product> listProducts(String companyId);
  	 public List<Product> listProductsByBranch(String branchId);
	 public List<Product> listProductsDistinct(String productId);
	 public List<Product> listProductsDistinctByCode(String productCode);
 	 public List<Product> listProductsDistinctByCodeByBranch(String branchId,String productCode);
 	 public Product getProductsDistinctByCodeByBranch(String branchId,String productCode);
	 public Product getProduct(int id);
	 public Product getProductByTypeCode(String typeStr);
	 public Product getProductByTypeCode(String typeStr,String companyId,String branchId);
	 public Product getProductDuration(String typeStr,String companyId,String branchId);
	 public void deleteProduct(Product product);
}