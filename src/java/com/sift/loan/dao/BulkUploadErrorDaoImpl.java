package com.sift.loan.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.Branch;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;

/**
 * @author Chris Faseun
 *
 */
@Repository("bulkUploadErrorDao")
public class BulkUploadErrorDaoImpl implements BulkUploadErrorDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addBulkUploadError(RepaymentUploadErrors item) {
   sessionFactory.getCurrentSession().saveOrUpdate(item);
 }
 
 public void addBulkUploadErrors(List<RepaymentUploadErrors> items){
	 if(items!=null && items.size()>0){
		 for(RepaymentUploadErrors item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }
 
 public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items){
	 if(items!=null && items.size()>0){
		 for(LoanPayOffUploadErrors item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }

 @SuppressWarnings("unchecked")
 public List<RepaymentUploadErrors> listBulkUploadErrors(){
  return (List<RepaymentUploadErrors>) sessionFactory.getCurrentSession().createCriteria(RepaymentUploadErrors.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadErrors.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<RepaymentUploadErrors>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId,String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadErrors.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<RepaymentUploadErrors>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String companyId,String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadErrors.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<LoanPayOffUploadErrors>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<RepaymentUploadErrors> listPendingBulkUploadErrors(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepaymentUploadErrors.class);
	 criteria.add(Restrictions.eq("uploadStatus","E"));
	 
	 return  (List<RepaymentUploadErrors>)criteria.list();
 }
 

 public RepaymentUploadErrors getBulkUploadError(int id){
  return (RepaymentUploadErrors) sessionFactory.getCurrentSession().get(RepaymentUploadErrors.class, id);
 }


 public void deleteBulkUploadError(RepaymentUploadErrors item) {
  sessionFactory.getCurrentSession().createQuery("delete from RepaymentUploadErrors where id = "+item.getId()).executeUpdate();
 }
}