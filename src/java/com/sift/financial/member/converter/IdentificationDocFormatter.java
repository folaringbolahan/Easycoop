package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.format.Formatter;

import com.sift.financial.member.*;


@Component
public class IdentificationDocFormatter implements Formatter<IdentificationDoc> {
	
	 @Autowired
	 private IdentificationDocDAO identificationDocDAO;
     //Some service class which can give the Actor after
     //fetching from Database
 
     @Override
     public String print(IdentificationDoc identificationDoc, Locale arg1) {
           return identificationDoc.getIdentificationDocId().toString();
     }
 
     @Override
      public IdentificationDoc parse(String value, Locale arg1) throws ParseException {
         
    	 if(value==null)
    	 {
  
    		 return new IdentificationDoc();
    	 }
    	 else
    	 {
    	 
    		 IdentificationDoc idoc = identificationDocDAO.findById(Integer.valueOf(value));
    		 
    		 if(idoc==null)
    		 {
    			 idoc= new IdentificationDoc();
    		 }
    		 
    		 return idoc;
    	 }
           
      }

	/*@Override
	public IdentificationDoc parse(String arg0, Locale arg1)
			throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public String print(IdentificationDoc arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentificationDoc parse(String arg0, Locale arg1)
			throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}*/

}
