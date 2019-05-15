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
@Repository("loanPayOffUploadItemDao")
public class LoanPayOffUploadItemDaoImpl implements LoanPayOffUploadItemDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanPayOffUploadItem(LoanPayOffUploadItems item) {
   sessionFactory.getCurrentSession().saveOrUpdate(item);
 }
 
 public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items){
	 if(items!=null && items.size()>0){
		 for(LoanPayOffUploadItems item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }

 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadItems> listLoanPayOffUploadItems(){
  return (List<LoanPayOffUploadItems>) sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadItems> listLoanPayOffUploadItems(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","E"));
	 
	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
 
 public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","E"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<LoanPayOffUploadItems>)criteria.list();
 } 
 
 public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","F"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
  
 public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","N"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 criteria.add(Restrictions.eq("branchId",branchId));

	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
 
 public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","F"));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
  
 public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadItems.class);
	 criteria.add(Restrictions.eq("processedStatus","N"));
	 criteria.add(Restrictions.eq("batchId",batchId));

	 return  (List<LoanPayOffUploadItems>)criteria.list();
 }
 

 public LoanPayOffUploadItems getLoanPayOffUploadItem(int id){
  return (LoanPayOffUploadItems) sessionFactory.getCurrentSession().get(LoanPayOffUploadItems.class, id);
 }

 public void deleteLoanPayOffUploadItem(LoanPayOffUploadItems item) {
  sessionFactory.getCurrentSession().createQuery("delete from LoanPayOffUploadItems where id = "+item.getId()).executeUpdate();
 }
}