/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.dao.FileUploadDAO;
import java.util.List;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class FileUploadDAOImpl implements FileUploadDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(FileUploadDAOImpl.class);

    @Override
    public FileUpload loadFileUploadByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadFileUploadByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public FileUpload loadFileUploadByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (FileUpload) session.load(com.sift.easycoopfin.models.FileUpload.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public FileUpload getFileUploadByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getFileUploadByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getFileUploadByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public FileUpload getFileUploadByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (FileUpload) session.get(com.sift.easycoopfin.models.FileUpload.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getFileUploadByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public FileUpload createFileUpload() {
        return new com.sift.easycoopfin.models.FileUpload();
    }

    @Override
    public boolean save(FileUpload fileUpload) throws PersistentException {
        try {
            System.out.println("fileUpload.getFileSum() :: "  + fileUpload.getFileSum() );
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(fileUpload);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.FileUpload fileUpload)", e);
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    @Override
    public boolean delete(FileUpload fileUpload) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(fileUpload);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.models.FileUpload fileUpload)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public boolean refresh(FileUpload fileUpload) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(fileUpload);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.FileUpload fileUpload)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public boolean evict(FileUpload fileUpload) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(fileUpload);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.FileUpload fileUpload)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<FileUpload> listAllFileUploadByQuery(String condition, String orderBy) throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.FileUpload as FileUpload");
        if (condition != null) {
            sb.append(" Where ").append(condition);

        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<FileUpload> list = query.list();
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllFileUploadByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }finally{
            session.close();
        }
    }

    @Override
    public List<FileUpload> listAllFileUploadByCriteria(FileUploadCriteria fileUpload) {
        return fileUpload.listAllFiles();
    }

    public FileUpload loadFileUploadsByCriteria(FileUploadCriteria fileUploadCriteria) {
          FileUpload[] fileUploads = listFileUploadByCriteria(fileUploadCriteria);
        if (fileUploads == null || fileUploads.length == 0) {
            return null;
        }
        return fileUploads[0];
    }

    public FileUpload[] listFileUploadByCriteria(FileUploadCriteria fileUploadCriteria) {
        return fileUploadCriteria.listFileUploads();
    }
}
