/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.dao;

import com.sift.gl.model.FileUploadErrors;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface FileUploadErrorsDao {
    public void addFileUploadError(FileUploadErrors item);
    public void addFileUploadErrors(List<FileUploadErrors> items);
    public List<FileUploadErrors> listFileUploadErrors(int companyId,int branchId, String batchId);
    public FileUploadErrors getFileUploadError(int id);
    public void writeErrorToExcel(List<FileUploadErrors> memAcctList ,  HttpServletResponse response);
}
