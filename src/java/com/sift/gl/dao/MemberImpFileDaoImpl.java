/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.model.FileUploadImp;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("memberImpFileDao")
@Transactional
public class MemberImpFileDaoImpl implements MemberImpFileDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public boolean addBulkUpload(FileUploadImp fileUpload) {

        boolean success = false;
        try {            
            this.sessionFactory.getCurrentSession().saveOrUpdate(fileUpload);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public void enterBatchUpload(FileUploadImp fileUpload) {
        sessionFactory.getCurrentSession().createQuery("update FileUploadImp set verifierId='" + fileUpload.getVerifierId() + "',processingStatus='E' where referenceNumber = '" + fileUpload.getReferenceNumber() + "'").executeUpdate();

    }

    @Override
    public boolean authorizeBatchUpload(FileUploadImp fileUpload) {
        boolean success = false;
        try {
            sessionFactory.getCurrentSession().createQuery("update FileUploadImp set approvedBy='" + fileUpload.getApprovedBy() + "',processingStatus='A' where referenceNumber = '" + fileUpload.getReferenceNumber() + "'").executeUpdate();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public void cancelBatchUpload(FileUploadImp fileUpload) {
        sessionFactory.getCurrentSession().createQuery("update FileUploadImp set ,verifierId='" + fileUpload.getVerifierId() + "', processingStatus='C' where referenceNumber = '" + fileUpload.getReferenceNumber() + "'").executeUpdate();

    }

    @Override
    public boolean rejectBatchUpload(FileUploadImp fileUpload) {
        boolean success = false;
        try {
            sessionFactory.getCurrentSession().createQuery("update FileUploadImp set approvedBy='" + fileUpload.getApprovedBy() + "',processingStatus='R' where referenceNumber = '" + fileUpload.getReferenceNumber() + "'").executeUpdate();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    

    @Override
    public List<FileUploadImp> listBulkUploadsForAuth(int companyId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadImp.class);
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("processingStatus", "E"));
        criteria.add(Restrictions.eq("isVerified", 1));

        return (List<FileUploadImp>) criteria.list();
    }

    @Override
    public FileUploadImp getBulkUpload(int id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadImp.class);
        criteria.add(Restrictions.eq("id", id));

        return criteria.list().size() == 0 ? null : (FileUploadImp) criteria.list().get(0);
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public List<FileUploadImp> listPendingMemberAcctList(int companyId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadImp.class);
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        //criteria.add(Restrictions.eq("isVerified", 0));
        criteria.add(Restrictions.eq("toCreateAcct", 1));
        criteria.add(Restrictions.eq("processingStatus", "E"));

        return (List<FileUploadImp>) criteria.list();
    }

    
    @Override
    public FileUploadImp getFileUploadByBatchIdAndBranchId(int branchId, String batchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadImp.class);        
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("referenceNumber", batchId));       

        return criteria.list().size() == 0 ? null : (FileUploadImp) criteria.list().get(0);
    }

    @Override
    public boolean processBatchUpload(int companyId, int branchId, String batchId, String userId, String processedStatus) {
        boolean success = false;
        try {
            sessionFactory.getCurrentSession().createQuery("update FileUploadImp set approvedBy='" + userId + "',processingStatus='" + processedStatus + "' where referenceNumber = '" + batchId + "'"
                    + "AND companyId = '" + companyId + "' AND branchId = '" + branchId + "'").executeUpdate();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}
