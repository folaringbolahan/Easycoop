/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import java.util.List;
import org.orm.*;

/**
 *
 * @author logzy
 */
public interface FileUploadDAO {

    public FileUpload loadFileUploadByORMID(int id) throws PersistentException;

    public FileUpload getFileUploadByORMID(int id) throws PersistentException;

    public FileUpload createFileUpload();

    public boolean save(com.sift.easycoopfin.models.FileUpload fileUpload) throws PersistentException;

    public boolean delete(com.sift.easycoopfin.models.FileUpload fileUpload) throws PersistentException;

    public boolean refresh(com.sift.easycoopfin.models.FileUpload fileUpload) throws PersistentException;

    public boolean evict(com.sift.easycoopfin.models.FileUpload fileUpload) throws PersistentException;

    public List<FileUpload> listAllFileUploadByQuery(String condition, String orderBy) throws PersistentException;

    public List<FileUpload> listAllFileUploadByCriteria(FileUploadCriteria fileUpload);

    public FileUpload[] listFileUploadByCriteria(FileUploadCriteria productsCriteria);

    public FileUpload loadFileUploadsByCriteria(FileUploadCriteria fileUploadCriteria);
}
