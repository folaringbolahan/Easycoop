/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.service;

import com.sift.easycoopfin.models.FileUploadError;

/**
 *
 * @author Inove
 */
public interface FileUploadService {
  public boolean addFileUploadError(FileUploadError fileError);  
}
