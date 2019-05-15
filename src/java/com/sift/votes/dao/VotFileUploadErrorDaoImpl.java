/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotFileUploadError;
import org.orm.PersistentException;

/**
 *
 * @author Nelson Akpos
 */
public class VotFileUploadErrorDaoImpl implements VotFileUploadErrorDao {
     private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotFileUploadErrorDaoImpl.class);
    public boolean save(VotFileUploadError fileUploadError) throws PersistentException {
        try {
            System.out.println("Error with file upload");
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(fileUploadError);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.FileUploadError fileUploadError)", e);
            throw new PersistentException(e);
        }
    }
    
}
