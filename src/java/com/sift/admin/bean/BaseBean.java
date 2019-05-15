package com.sift.admin.bean;

import java.util.*;
public class BaseBean extends TreeMap<String,String> {

	public BaseBean(){
		super();
	}
	
	final public String getString(String key) {		
		if(key==null || key.trim().equals("")){
			throw new RuntimeException("Invald key");
		}else{
		   return super.get(key.toLowerCase().trim());
		}
		
	}
	
	final public String setString(String key, String value) {		
		if(key==null || key.trim().equals(""))
			throw new RuntimeException("Invald key");
			
		System.out.println("------------------");
		System.out.println(key.toLowerCase().trim());
		System.out.println(value==null?"":value);
		
	    return super.put(key.toLowerCase().trim(),value==null?"":value);
	    
	}
	
	final public String get(String key){
       return getString(key);
	}
	
	
	final public String put(String key, String value){
		return setString(key, value);
	}
	
}
