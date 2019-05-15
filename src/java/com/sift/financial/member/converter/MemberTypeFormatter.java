package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component
public class MemberTypeFormatter implements Formatter<MemberType> {
	
	 @Autowired
	 private MemberTypeDAO memberTypeDAO;

	@Override
	public String print(MemberType arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getMemberTypeId().toString();
	}

	@Override
	public MemberType parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new MemberType();
	   	 }
	   	 else
	   	 {
	   	
	   		MemberType comp = memberTypeDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new MemberType();
	   		 }
	   		 
	   		 return comp;
	   	 }
	}

}
