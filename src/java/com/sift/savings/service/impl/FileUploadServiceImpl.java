/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.service.impl;

import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileUploadError;
import com.sift.savings.service.FileUploadService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Inove
 */

@Service
public class FileUploadServiceImpl implements FileUploadService{

     private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(FileUploadServiceImpl.class);
    private DAOFactory daoFactory;
    public boolean addFileUploadError(FileUploadError fileError) {
        boolean status = false;
        try {
            if (DAOFactory.getDAOFactory().getFileUploadErrorDAO().save(fileError)) {
                status = true;
            } else {
                status = false;
            }

        } catch (Exception e) {
            _logger.error("SavingServiceImpl.saveFileUpload(FileUpload fileUpload)", e);

        }
        return status;
       
    }
    
}
