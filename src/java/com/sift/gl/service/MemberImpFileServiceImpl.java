/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.service;

import com.sift.gl.model.FileUploadImp;
import com.sift.gl.dao.MemberImpFileDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("memberImpFileService")
@Transactional
public class MemberImpFileServiceImpl implements MemberImpFileService{

    @Autowired
    private MemberImpFileDao memberImpFileDao;
    
    @Override
    public boolean addBulkUpload(FileUploadImp fileUpload) {
        return memberImpFileDao.addBulkUpload(fileUpload);
    }

    @Override
    public void enterBatchUpload(FileUploadImp fileUpload) {
        memberImpFileDao.enterBatchUpload(fileUpload);
    }

    @Override
    public boolean authorizeBatchUpload(FileUploadImp fileUpload) {
        return memberImpFileDao.authorizeBatchUpload(fileUpload);
    }

    @Override
    public void cancelBatchUpload(FileUploadImp fileUpload) {
        memberImpFileDao.cancelBatchUpload(fileUpload);
    }

    @Override
    public boolean rejectBatchUpload(FileUploadImp fileUpload) {
        return memberImpFileDao.rejectBatchUpload(fileUpload);
    }

    @Override
    public List<FileUploadImp> listBulkUploads() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listBulkUploads(String companyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listBulkUploads(String companyId, String branchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listBulkUploadsForAuth(int companyId, int branchId) {
        return memberImpFileDao.listBulkUploadsForAuth(companyId, branchId);
    }

    @Override
    public List<FileUploadImp> listPendingBulkUploads() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listPendingBulkUploadsByType(String uploadType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listPendingBulkUploads(String statusField, String statusValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileUploadImp getBulkUpload(int id) {
        return memberImpFileDao.getBulkUpload(id);
    }

    @Override
    public void deleteBulkUpload(FileUploadImp fileUpload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadImp> listPendingMemberAcctList(int companyId, int branchId) {
        return memberImpFileDao.listPendingMemberAcctList(companyId, branchId);
    }

    @Override
    public FileUploadImp getFileUploadByBatchIdAndBranchId(int branchId, String batchId) {
         return memberImpFileDao.getFileUploadByBatchIdAndBranchId(branchId, batchId);
    }

    @Override
    public boolean processBatchUpload(int companyId, int branchId, String batchId, String userId, String processedStatus) {
        return memberImpFileDao.processBatchUpload(companyId, branchId, batchId, userId, processedStatus);
    }
    
}
