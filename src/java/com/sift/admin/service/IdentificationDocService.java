/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.service;

import com.sift.admin.model.IdentificationDoc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface IdentificationDocService {
    public boolean addOrUpdateIdentificationDoc(IdentificationDoc IdentificationDoc);
    public boolean deleteIdentificationDoc(int docId);
    public List<IdentificationDoc> listIdentificationDocByCountry(int countryId);
    //public List<IdentificationDoc> listAllIdentificationDoc();
    public ArrayList<IdentificationDoc> listAllIdentificationDoc();
    public IdentificationDoc getIdentificationDocById(int docId);
    public String getCountryName(int countryId);
}
