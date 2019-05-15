package com.sift.financial.member;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sift.financial.services.ManagementTempl;

public class ReligionEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ManagementTempl managementTempl;
	
	
	
	public ReligionEditor ()
	{}
	
	public ReligionEditor (ManagementTempl templ)
	{
		managementTempl = templ ;
	}
	
	
	public String getAsText() {
		 
		if(getValue() !=null)
		{
			Religion religion = (Religion) getValue();
	      return religion.toString();
		}
		else
		{
			return "";
		}
	}
	 
	    public void setAsText(final String value) {
	    	
	        try {
	        	
	        	Religion religion = managementTempl.getReligionDAO().findById(Integer.valueOf(value));
	        
	            super.setValue(religion);
	        } catch (final ObjectRetrievalFailureException e) {
	        	logger.debug("Cannot find religion with ID: " + value);
	            super.setValue(null);
	        }
	        
	    }

}
