/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

import java.util.List;

/**
 *
 * @author baydel200
 */
public interface BatchErrors {
    
    public List<Object[]> getErrorList(String state,Integer fileUploadId, Integer companyId, Integer branchId) ;
    
}
