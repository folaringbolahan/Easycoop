package com.sift.loan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.Branch;
import com.sift.loan.model.Product;

/**
 * @author Chris Faseun
 *
 */
@Repository("productDao")
public class ProductDaoImpl implements ProductDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addProduct(Product product) {
   sessionFactory.getCurrentSession().saveOrUpdate(product);
 }

 @SuppressWarnings("unchecked")
 public List<Product> listProducts(){
     //return (List<Product>) sessionFactory.getCurrentSession().createCriteria(Product.class).list();
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("productTypeCode","L"));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return  (List<Product>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<Product> listProducts(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("productTypeCode","L"));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return  (List<Product>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<Product> listProductsByBranch(String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("productTypeCode","L"));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return  (List<Product>)criteria.list();
 }
 
 public List<Product> listProductsDistinct(String productId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("id",Integer.parseInt(productId)));
	 
	 return  (List<Product>)criteria.list();
 }
 
 public List<Product> listProductsDistinctByCode(String productCode){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("code",productCode));
	 
	 return  (List<Product>)criteria.list();
 }
 
 public List<Product> listProductsDistinctByCodeByBranch(String branchId,String productCode){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("code",productCode));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return  (List<Product>)criteria.list();
 }
 
 public Product getProductsDistinctByCodeByBranch(String branchId,String productCode){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("code",productCode));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return  (Product)criteria.list().get(0);
 }

 public Product getProduct(int id){
     return (Product) sessionFactory.getCurrentSession().get(Product.class, id);
 }
 
 public Product getProductByTypeCode(String typeStr){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("code",typeStr));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return (Product) criteria.list().get(0);
 }
 
 public Product getProductByTypeCode(String typeStr,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("code",typeStr));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return (Product) criteria.list().get(0);
 }
 
 public Product getProductDuration(String typeStr,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	 criteria.add(Restrictions.eq("code",typeStr));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("isActive",1));
	 
	 return (Product) criteria.list().get(0);
 }

 public void deleteProduct(Product product) {
     sessionFactory.getCurrentSession().createQuery("delete from products where id = "+product.getId()).executeUpdate();
 }
}