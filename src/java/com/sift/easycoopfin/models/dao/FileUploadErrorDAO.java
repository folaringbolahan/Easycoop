/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.FileUploadError;
import com.sift.easycoopfin.models.FileUploadErrorCriteria;
import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import java.util.List;
import org.orm.*;

/**
 *
 * @author Inove
 */
public interface FileUploadErrorDAO {
     public FileUploadError loadFileUploadErrorByORMID(int id) throws PersistentException;

    public FileUploadError getFileUploadErrorByORMID(int id) throws PersistentException;

    public FileUploadError createFileUploadError();

    public boolean save(com.sift.easycoopfin.models.FileUploadError fileUploadError) throws PersistentException;

    public boolean delete(com.sift.easycoopfin.models.FileUploadError fileUploadError) throws PersistentException;

    public boolean refresh(com.sift.easycoopfin.models.FileUploadError fileUploadError) throws PersistentException;

    public boolean evict(com.sift.easycoopfin.models.FileUploadError fileUploadError) throws PersistentException;

    public List<FileUploadError> listAllFileUploadErrorByQuery(String condition, String orderBy) throws PersistentException;

    public List<FileUploadError> listAllFileUploadErrorByCriteria(FileUploadErrorCriteria fileUpload);

    public FileUploadError[] listFileUploadErrorByCriteria(FileUploadErrorCriteria fileUploadErrorCriteria);

    public FileUploadError loadFileUploadsByCriteria(FileUploadErrorCriteria fileUploadErrorCriteria);
    
    public void updateFileUploadStatus(int fileId);
    
}
