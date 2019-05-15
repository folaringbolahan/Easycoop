package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

@Component
public class ReligionFormatter implements Formatter<Religion> {
	
	 @Autowired
	 private ReligionDAO religionDAO;

	@Override
	public String print(Religion arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getReligionId().toString();
	}

	@Override
	public Religion parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new Religion();
	   	 }
	   	 else
	   	 {
	   	
	   		Religion comp = religionDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new Religion();
	   		 }
	   		 
	   		 return comp;
	   	 }
	}

}
