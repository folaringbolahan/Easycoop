package com.sift.financial;

import java.util.List;

public interface BaseDaoService {
	
	  public  List getResultMap(String query);
	  
	  public void setMessage(String message);
	  
	  public String getMessage();
	  
	  public  boolean exists(String query);
	   
	  public  String getString(String query);
	   
	  public  boolean updateRecord(String query);
	   
	  public  boolean insertRecord(String query) ;
	   
	  public  boolean deleteRecord(String query);
	      
	  public  int getRowsAffected();
	   
}
