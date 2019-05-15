/**
 * 
 */
package com.sift.financial.member.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;


import org.springframework.stereotype.Component;

import com.sift.financial.member.*;

/**
 * @author baydel200
 *
 */
@Component("banksFormatter")
public class BanksFormatter implements Formatter<Banks> {
	
	 @Autowired
	 private BanksDAO banksDAO;

	@Override
	public String print(Banks arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return arg0.getBankId().toString();
	}

	@Override
	public Banks parse(String arg0, Locale arg1) throws ParseException {
		
		if(arg0==null)
	   	 {
	 	   		 return new Banks();
	   	 }
	   	 else
	   	 {
	   		Banks comp = banksDAO.findById(Integer.valueOf(arg0));
	   		 
	   		 if(comp==null)
	   		 {
	   			comp= new Banks();
	   		 }
	   		 
	   		 return comp;
	   	 }
	}

}
