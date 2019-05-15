/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.converter;


import com.sift.financial.member.BatchUploadType;
import com.sift.financial.member.BatchUploadTypeDAO;
import com.sift.financial.member.Company;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author baydel200
 */


@Component("batchUploadTypeFormatter")
public class BatchUploadTypeFormatter implements Formatter<BatchUploadType> {
    
    @Autowired
    private BatchUploadTypeDAO batchUploadTypeDAO;

    @Override
    public String print(BatchUploadType t, Locale locale) {
        return t.getBatchUploadTypeId().toString();   
    }

    @Override
    public BatchUploadType parse(String string, Locale locale) throws ParseException {
        
        if(string==null)
   	 {
 
   		 return new BatchUploadType();
   	 }
   	 else
   	 {
   	
   		BatchUploadType comp = batchUploadTypeDAO.findById(Integer.valueOf(string));
   		 
   		 if(comp==null)
   		 {
   			comp= new BatchUploadType();
   		 }
   		 
   		 return comp;
   	 }
    }
    
}
