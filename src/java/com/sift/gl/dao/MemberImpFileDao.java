/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.dao;

import com.sift.gl.model.FileUploadImp;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface MemberImpFileDao {
    public boolean addBulkUpload(FileUploadImp fileUpload);    
    public void enterBatchUpload(FileUploadImp fileUpload);
    public boolean authorizeBatchUpload(FileUploadImp fileUpload);
    public void cancelBatchUpload(FileUploadImp fileUpload);
    public boolean rejectBatchUpload(FileUploadImp fileUpload);
    public List<FileUploadImp> listPendingMemberAcctList(int companyId, int branchId);   
    public List<FileUploadImp> listBulkUploadsForAuth(int companyId, int branchId);   
    public FileUploadImp getBulkUpload(int id);    
    public FileUploadImp getFileUploadByBatchIdAndBranchId(int branchId, String batchId);
    public boolean processBatchUpload(int companyId, int branchId, String batchId, String userId,String processedStatus);
}
