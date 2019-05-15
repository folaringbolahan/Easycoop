package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;


import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component("dividendTypeFormatter")
public class DividendTypeFormatter implements Formatter<DividendType> {
	
	
	 @Autowired
	 private DividendTypeDAO dividendTypeDAO;

	@Override
	public String print(DividendType arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getDividendTypeId().toString();
	}

	@Override
	public DividendType parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new DividendType();
	   	 }
	   	 else
	   	 {
	   		DividendType comp = dividendTypeDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new DividendType();
	   		 }
	   		 
	   		 return comp;
	   	 }
	}
	

}
