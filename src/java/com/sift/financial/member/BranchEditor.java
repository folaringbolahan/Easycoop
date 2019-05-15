package com.sift.financial.member;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sift.financial.services.*;

public class BranchEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ManagementTempl managementTempl;
	
	public BranchEditor ()
	{}
	
	public BranchEditor (ManagementTempl templ)
	{
		managementTempl = templ ;
	}
	
	
	public String getAsText() {
		 
		if(getValue() !=null)
		{
			Branch branch = (Branch) getValue();
	      return branch.toString();
		}
		else
		{
			return "";
		}
	}
	 
	    public void setAsText(final String value) {
	    	
	        try {
	        	Branch branch = managementTempl.getBranchDAO().findById(Integer.valueOf(value));
	        
	            super.setValue(branch);
	        } catch (final ObjectRetrievalFailureException e) {
	        	logger.debug("Cannot find Branch with ID: " + value);
	            super.setValue(null);
	        }
	        
	    }

}
