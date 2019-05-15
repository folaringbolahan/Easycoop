package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component("companyFormatter")
public class CompanyFormatter implements Formatter<Company> {
	
	
	 @Autowired
	 private CompanyDAO companyDAO;

	@Override
	public String print(Company arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getId().toString();
	}

	@Override
	public Company parse(String arg0, Locale arg1) throws ParseException {
		if(arg0==null)
   	 {
 
   		 return new Company();
   	 }
   	 else
   	 {
   	
   		Company comp = companyDAO.findById(Integer.valueOf(arg0));
   		 
   		 if(comp==null)
   		 {
   			comp= new Company();
   		 }
   		 
   		 return comp;
   	 }
		
	}

}
