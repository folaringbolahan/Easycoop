/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.service;

import com.sift.admin.dao.IdentificationDocDao;
import com.sift.admin.model.IdentificationDoc;
import com.sift.admin.service.IdentificationDocService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("identificationDocService")
@Transactional
public class IdentificationDocServiceImpl implements IdentificationDocService{

    @Autowired
    public IdentificationDocDao identificationDocDao;
    @Override
    public boolean addOrUpdateIdentificationDoc(IdentificationDoc IdentificationDoc) {
        return identificationDocDao.addOrUpdateIdentificationDoc(IdentificationDoc);
    }

    @Override
    public boolean deleteIdentificationDoc(int docId) {
        return identificationDocDao.deleteIdentificationDoc(docId);
    }

    @Override
    public List<IdentificationDoc> listIdentificationDocByCountry(int countryId) {
        return identificationDocDao.listIdentificationDocByCountry(countryId);
    }

    @Override
    public ArrayList<IdentificationDoc> listAllIdentificationDoc() {
       return identificationDocDao.listAllIdentificationDoc();
    }

    @Override
    public IdentificationDoc getIdentificationDocById(int docId) {
        return identificationDocDao.getIdentificationDocById(docId);
    }

    @Override
    public String getCountryName(int countryId) {
        return identificationDocDao.getCountryName(countryId);
    }
    
}
