package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component
public class BranchFormatter implements Formatter<Branch> {
	
	 @Autowired
	 private BranchDAO branchDAO;

	@Override
	public String print(Branch arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getId().toString();
	}

	@Override
	public Branch parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new Branch();
	   	 }
	   	 else
	   	 {
	   	
	   		Branch comp = branchDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new Branch();
	   		 }
	   		 
	   		 return comp;
	   	 }
		
	}

}
