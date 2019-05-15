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
@Repository("loanPayOffUploadErrorDao")
public class LoanPayOffUploadErrorDaoImpl implements LoanPayOffUploadErrorDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanPayOffUploadError(LoanPayOffUploadErrors item) {
   sessionFactory.getCurrentSession().saveOrUpdate(item);
 }
 
 public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items){
	 if(items!=null && items.size()>0){
		 for(LoanPayOffUploadErrors item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
 }

 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(){
  return (List<LoanPayOffUploadErrors>) sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadErrors.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadErrors.class);
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<LoanPayOffUploadErrors>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String branchId,String batchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadErrors.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<LoanPayOffUploadErrors>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanPayOffUploadErrors> listPendingLoanPayOffUploadErrors(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanPayOffUploadErrors.class);
	 criteria.add(Restrictions.eq("uploadStatus","E"));
	 
	 return  (List<LoanPayOffUploadErrors>)criteria.list();
 }
 

 public LoanPayOffUploadErrors getLoanPayOffUploadError(int id){
  return (LoanPayOffUploadErrors) sessionFactory.getCurrentSession().get(LoanPayOffUploadErrors.class, id);
 }


 public void deleteLoanPayOffUploadError(LoanPayOffUploadErrors item) {
  sessionFactory.getCurrentSession().createQuery("delete from LoanPayOffUploadErrors where id = "+item.getId()).executeUpdate();
 }
}