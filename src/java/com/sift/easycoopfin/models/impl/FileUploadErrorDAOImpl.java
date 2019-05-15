/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.FileUploadError;
import com.sift.easycoopfin.models.FileUploadErrorCriteria;
import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.dao.FileUploadDAO;
import com.sift.easycoopfin.models.dao.FileUploadErrorDAO;
import java.util.List;
import org.apache.log4j.Priority;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;
/**
 *
 * @author Inove
 */
public class FileUploadErrorDAOImpl implements FileUploadErrorDAO {
     private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(FileUploadErrorDAOImpl.class);

    
    public FileUploadError loadFileUploadErrorByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadFileUploadErrorByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadFileUploadErrorByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public FileUploadError loadFileUploadErrorByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (FileUploadError) session.load(com.sift.easycoopfin.models.FileUploadError.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadFileUploadErrorByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

   
    public FileUploadError getFileUploadErrorByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getFileUploadErrorByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getFileUploadErrorByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public FileUploadError getFileUploadErrorByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (FileUploadError) session.get(com.sift.easycoopfin.models.FileUploadError.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getFileUploadByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

  
    public FileUploadError createFileUploadError() {
        return new com.sift.easycoopfin.models.FileUploadError();
    }

  
    public boolean save(FileUploadError fileUploadError) throws PersistentException {
        try {
            System.out.println("Error with file upload");
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(fileUploadError);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.FileUploadError fileUploadError)", e);
            throw new PersistentException(e);
        }
    }

  
    public boolean delete(FileUploadError fileUploadError) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(fileUploadError);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.models.FileUploadError fileUploadError)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(FileUploadError fileUploadError) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(fileUploadError);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.FileUploadError fileUploadError)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(FileUploadError fileUploadError) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(fileUploadError);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.FileUploadError fileUploadError)", e);
            throw new PersistentException(e);
        }
    }

    
    public List<FileUploadError> listAllFileUploadErrorByQuery(String condition, String orderBy) throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.FileUploadError as FileUploadError");
        if (condition != null) {
            sb.append(" Where ").append(condition);

        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<FileUploadError> list = query.list();
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllFileUploadErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }finally{
            session.close();
        }
    }

   
    public List<FileUploadError> listAllFileUploadErrorByCriteria(FileUploadErrorCriteria fileUploadError) {
        return fileUploadError.listAllFiles();
    }

    public FileUploadError loadFileUploadsByCriteria(FileUploadErrorCriteria fileUploadErrorCriteria) {
          FileUploadError[] fileUploads = listFileUploadErrorByCriteria(fileUploadErrorCriteria);
        if (fileUploads == null || fileUploads.length == 0) {
            return null;
        }
        return fileUploads[0];
    }

    public FileUploadError[] listFileUploadErrorByCriteria(FileUploadErrorCriteria fileUploadErrorCriteria) {
        return fileUploadErrorCriteria.listFileUploads();
    }

   
    public void updateFileUploadStatus(int fileId) {
         PersistentSession session;
         
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            session.createQuery("update FileUpload set state='B' where id = " + fileId).executeUpdate();

        } catch (PersistentException ex) {
            _logger.log(Priority.ERROR, ex);
           ex.printStackTrace();
        }
    }
}
