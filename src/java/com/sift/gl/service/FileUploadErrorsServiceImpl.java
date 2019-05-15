/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.service;

import com.sift.gl.dao.FileUploadErrorsDao;
import com.sift.gl.model.FileUploadErrors;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("fileUploadErrorsService")
@Transactional
public class FileUploadErrorsServiceImpl implements FileUploadErrorsService{

    @Autowired
    private FileUploadErrorsDao fileUploadErrorsDao;
    @Override
    public void addFileUploadError(FileUploadErrors item) {
        fileUploadErrorsDao.addFileUploadError(item);
    }

    @Override
    public void addFileUploadErrors(List<FileUploadErrors> items) {
        fileUploadErrorsDao.addFileUploadErrors(items);
    }

    @Override
    public List<FileUploadErrors> listFileUploadErrors(int companyId, int branchId, String batchId) {
        return fileUploadErrorsDao.listFileUploadErrors(companyId, branchId, batchId);
    }

    @Override
    public FileUploadErrors getFileUploadError(int id) {
        return fileUploadErrorsDao.getFileUploadError(id);
    }

    @Override
    public void writeErrorToExcel(List<FileUploadErrors> memAcctList, HttpServletResponse response) {
        fileUploadErrorsDao.writeErrorToExcel(memAcctList, response);
    }
    
}
