package com.sift.loan.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.Branch;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.Product;

/**
 * @author Chris Faseun
 *
 */
@Repository("bulkUploadDao")
public class BulkUploadDaoImpl implements BulkUploadDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addBulkUpload(FileUpload fileUpload) {
   sessionFactory.getCurrentSession().saveOrUpdate(fileUpload);
 }

 @SuppressWarnings("unchecked")
 public List<FileUpload> listBulkUploads(){
  return (List<FileUpload>) sessionFactory.getCurrentSession().createCriteria(FileUpload.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listBulkUploads(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<FileUpload>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listBulkUploads(String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));

	 return  (List<FileUpload>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listBulkUploadsForAuth(String companyId,String branchId,String processingStatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("processingStatus",processingStatus));

	 return  (List<FileUpload>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploads(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("uploadStatus","U"));
	 
	 return  (List<FileUpload>)criteria.list();
 } 
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploads(String statusField,String statusValue){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq(statusField,statusValue));
	 
	 return  (List<FileUpload>)criteria.list();
 } 
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploads(String paymentStatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("uploadStatus","U"));
	 criteria.add(Restrictions.eq("processingStatus","A"));
	 criteria.add(Restrictions.eq("paymentStatus",paymentStatus));
	 
	 return  (List<FileUpload>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploads(String paymentStatus,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("uploadStatus","U"));
	 criteria.add(Restrictions.eq("processingStatus","A"));
	 criteria.add(Restrictions.eq("paymentStatus",paymentStatus));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
         
	 return  (List<FileUpload>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploadsByType(String uploadType){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("uploadStatus","U"));
	 criteria.add(Restrictions.eq("processingStatus","N"));
	 criteria.add(Restrictions.eq("uploadType",uploadType));
	 
	 return  (List<FileUpload>)criteria.list();	 
 }
 
 @SuppressWarnings("unchecked")
 public List<FileUpload> listPendingBulkUploadsByType(String uploadType,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUpload.class);
	 criteria.add(Restrictions.eq("uploadStatus","U"));
	 criteria.add(Restrictions.eq("processingStatus","N"));
	 criteria.add(Restrictions.eq("uploadType",uploadType));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
         
	 return  (List<FileUpload>)criteria.list();	 
 }        
 public FileUpload getBulkUpload(int id){
      return (FileUpload) sessionFactory.getCurrentSession().get(FileUpload.class, id);
 }

 public void deleteBulkUpload(FileUpload fileUpload) {
      sessionFactory.getCurrentSession().createQuery("delete from FileUpload where id = "+fileUpload.getId()).executeUpdate();
 }
 
 public void markBatchForPayment(FileUpload fileUpload){
	  sessionFactory.getCurrentSession().createQuery("update FileUpload set paymentStatus='E' where id = "+fileUpload.getId()).executeUpdate();
 }
 
 public void enterBatchUpload(FileUpload fileUpload){
	  sessionFactory.getCurrentSession().createQuery("update FileUpload set verifiedBy='"+fileUpload.getVerifiedBy() + "',processingStatus='E' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
 }
 
 public void cancelBatchUpload(FileUpload fileUpload){
	  sessionFactory.getCurrentSession().createQuery("update FileUpload set paymentStatus='C',processingStatus='C' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
 }
 
 public void authorizeBatchUpload(FileUpload fileUpload){
	  sessionFactory.getCurrentSession().createQuery("update FileUpload set approvedBy='"+fileUpload.getApprovedBy() + "',paymentStatus='E',processingStatus='A' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
	  
	  if("LP".equalsIgnoreCase(fileUpload.getUploadType())){
		  sessionFactory.getCurrentSession().createQuery("update LoanPayOffUploadItems set processedStatus='E' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
	  }else if("LR".equalsIgnoreCase(fileUpload.getUploadType())){
		  sessionFactory.getCurrentSession().createQuery("update RepaymentUploadItems set processedStatus='E' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
	  }
 }
 
 public void rejectBatchUpload(FileUpload fileUpload){
	  sessionFactory.getCurrentSession().createQuery("update FileUpload set approvedBy='"+fileUpload.getApprovedBy() + "',paymentStatus='R',processingStatus='R' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();	  
 
	  if("LP".equalsIgnoreCase(fileUpload.getUploadType())){
		  sessionFactory.getCurrentSession().createQuery("update LoanPayOffUploadItems set processedStatus='R' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
	  }else if("LR".equalsIgnoreCase(fileUpload.getUploadType())){
		  sessionFactory.getCurrentSession().createQuery("update RepaymentUploadItems set processedStatus='R' where batchId = '"+fileUpload.getBatchId() + "'").executeUpdate();
	  }
 }
}