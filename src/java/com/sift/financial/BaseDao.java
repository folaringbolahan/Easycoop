package com.sift.financial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDao extends JdbcDaoSupport implements BaseDaoService {
	
    private int rowsAffected;
    private String message;
   // private String 'JdbcTemplate
       
    private static final Log log = LogFactory.getLog(BaseDao.class);
   
	public  boolean exists(String query){
	     
		String retStr=null;
		boolean returnVal = false;
		try{
		    log.info(query);
	        retStr=(String)getJdbcTemplate().queryForObject(query,String.class);
		}catch(Exception exp){
			log.fatal(exp);
			retStr = null;
			exp.printStackTrace();
		}
		
		if(retStr!=null && !retStr.equals(""))
		{returnVal = true;}
		
		
		return returnVal;
	}
	   
    public  String getString(String query){
	    
    	String retStr="";
		try{
		    log.info(query);
	        retStr=(String)getJdbcTemplate().queryForObject(query,String.class);
		}catch(Exception exp){
			log.fatal(exp);
			exp.printStackTrace();
		}
		
	    return retStr;	
    }

    public  boolean updateRecord(String query){
    	log.info(query);
    	try{
    	    rowsAffected=getJdbcTemplate().update(query);
        }catch(Exception exp){
           rowsAffected=0;
		   log.fatal(exp);
		   exp.printStackTrace();
	    }
        return rowsAffected>0?true:false;
    }

    public  boolean insertRecord(String query) {
    	log.info(query);
    	try{
    	     rowsAffected=getJdbcTemplate().update(query);
        }catch(Exception exp){
        	rowsAffected=0;
		   log.fatal(exp);
		   exp.printStackTrace();
	    }
        return rowsAffected>0?true:false;
    }
   
    public  boolean insertRecordWithExceptions(String query) throws Exception
    {
     	log.info(query);
     	rowsAffected=0;
     	rowsAffected=getJdbcTemplate().update(query);
         return rowsAffected>0?true:false;
     }
    
    public  boolean deleteRecord(String query){
    	
    	log.info(query);
    	
    	try{
    	    rowsAffected=getJdbcTemplate().update(query);
    	}catch(Exception exp){
    		rowsAffected=0;
    		log.fatal(exp);
    		exp.printStackTrace();
    	}
    	return rowsAffected>0?true:false;
    }
	 
    public  int getRowsAffected(){
	   return rowsAffected;
    }
    
	/**
	 * @param rowsAffected the rowsAffected to set
	 */
	public void setRowsAffected(int rowsAffected) {
		this.rowsAffected = rowsAffected;
	}

	public  List<Map<String,Object>> getResultMap(String query){
		
		log.info(query);
		List<Map<String,Object>> lst= new ArrayList<Map<String,Object>>();
		
		try{
		   lst = getJdbcTemplate().queryForList(query);
		   
	    }catch(Exception exp){
	    	//lst= null;
		    log.fatal(exp);
		    exp.printStackTrace();
	    }
	    
	    return lst;
	}
	
	public  List<Map<String,Object>> getResultMapForNull(String query){
		
		log.info(query);
		List<Map<String,Object>> lst= new ArrayList<Map<String,Object>>();
		
		try{
		   lst = getJdbcTemplate().queryForList(query);
		   
	    }catch(Exception exp){
	    	lst= null;
		    log.fatal(exp);
		    exp.printStackTrace();
	    }
	    
	    return lst;
	}
	
	
	public int queryForInt(String query)
	{
		log.info(query);
		int result = -1;
		try{
			result = getJdbcTemplate().queryForInt(query);
			   
		    }catch(Exception exp){
			    log.fatal(exp);
			    exp.printStackTrace();
		    }
		return result;
	}
	
	public Map queryForMap(String query)
	{
		log.info(query);
		Map result = new HashMap();
		try{
			result = getJdbcTemplate().queryForMap(query);
			   
		    }catch(Exception exp){
			    log.fatal(exp);
			    exp.printStackTrace();
		    }
		return result;
	}

	/**
	 * @return the message
	 */
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
