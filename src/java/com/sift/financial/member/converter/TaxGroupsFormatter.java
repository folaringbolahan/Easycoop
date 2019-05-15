package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component("taxGroupsFormatter")
public class TaxGroupsFormatter implements Formatter<TaxGroups> {
	
	 @Autowired
	 private TaxGroupsDAO taxGroupsDAO;

	@Override
	public String print(TaxGroups arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getId().toString();
	}

	@Override
	public TaxGroups parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new TaxGroups();
	   	 }
	   	 else
	   	 {
	   		TaxGroups comp = taxGroupsDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new TaxGroups();
	   		 }
	   		 
	   		 return comp;
	   	 }
	}

}
