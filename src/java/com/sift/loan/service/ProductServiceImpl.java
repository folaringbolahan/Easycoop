package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.loan.model.Product;
import com.sift.loan.dao.ProductDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("productService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {
	 @Autowired
	 private ProductDao productDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addProduct(Product product) {
		 productDao.addProduct(product);
	 }

	 public List<Product> listProducts() {
	  return productDao.listProducts();
	 }

	 public List<Product> listProducts(String companyId){
		  return productDao.listProducts(companyId);
	 }
	 
	 public List<Product> listProductsByBranch(String branchId){
		  return productDao.listProductsByBranch(branchId);
	 }

	 public List<Product> listProductsDistinct(String productId){
	      return productDao.listProductsDistinct(productId);
	 }
	 
	 public List<Product> listProductsDistinctByCode(String productCode){
		  return productDao.listProductsDistinctByCode(productCode);
	 }
	 
	 public List<Product> listProductsDistinctByCodeByBranch(String branchId,String productCode){
		  return productDao.listProductsDistinctByCodeByBranch(branchId,productCode);
	 }
	 
	 public Product getProductsDistinctByCodeByBranch(String branchId,String productCode){
		 return productDao.getProductsDistinctByCodeByBranch(branchId,productCode);
	 }
	 
	 public Product getProduct(int id){
	  return productDao.getProduct(id);
	 }
	 
	 public Product getProductByTypeCode(String typeStr){
		 return productDao.getProductByTypeCode(typeStr);
	 }
	 
	 public Product getProductByTypeCode(String typeStr,String companyId,String branchId){
		 return productDao.getProductByTypeCode(typeStr,companyId,branchId);
	 }
	 
	 public Product getProductDuration(String typeStr,String companyId,String branchId){
		 return productDao.getProductDuration(typeStr,companyId,branchId);
	 }

	 public void deleteProduct(Product product) {
		 productDao.deleteProduct(product);
	 }
}