package com.sift.loan.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.Branch;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;

/**
 * @author Chris Faseun
 *
 */
@Repository("bulkUploadItemDao")
public class BulkUploadItemDaoImpl implements BulkUploadItemDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addBulkUploadItem(RepaymentUploadItems item) {
   sessionFactory.getCurrentSession().saveOrUpdate(item);
 }
 
 public void addBulkUploadItems(List<RepaymentUploadItems> items){
	 if(items!=null && items.size()>0){
		 for(RepaymentUploadItems item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }
 
 public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items){
	 if(items!=null && items.size()>0){
		 for(LoanPayOffUploadItems item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }

 @SuppressWarnings("unchecked")
 public List<RepaymentUploadItems> listBulkUploadItems(){
  return (List<RepaymentUploadItems>) sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<RepaymentUploadItems> listBulkUploadItems(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<RepaymentUploadItems>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<RepaymentUploadItems> listPendingBulkUploadItems(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","E"));
	 
	 return  (List<RepaymentUploadItems>)criteria.list();
 }
 
 public List<RepaymentUploadItems> listPendingBulkUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","E"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<RepaymentUploadItems>)criteria.list();
 } 
 
 public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","F"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<RepaymentUploadItems>)criteria.list();
 }
  
 public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","N"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 criteria.add(Restrictions.eq("branchId",branchId));

	 return  (List<RepaymentUploadItems>)criteria.list();
 }
 
 public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","F"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<RepaymentUploadItems>)criteria.list();
 }
  
 public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","N"));
	 criteria.add(Restrictions.eq("batchId",batchId));

	 return  (List<RepaymentUploadItems>)criteria.list();
 }
 

 public RepaymentUploadItems getBulkUploadItem(int id){
  return (RepaymentUploadItems) sessionFactory.getCurrentSession().get(RepaymentUploadItems.class, id);
 }

 public void deleteBulkUploadItem(RepaymentUploadItems item) {
  sessionFactory.getCurrentSession().createQuery("delete from RepaymentUploadItems where id = "+item.getId()).executeUpdate();
 }
}