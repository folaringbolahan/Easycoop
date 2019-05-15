package com.sift.financial.member;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sift.financial.services.ManagementTempl;

public class CompanyEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ManagementTempl managementTempl;
	
	public CompanyEditor ()
	{}
	
	public CompanyEditor (ManagementTempl templ)
	{
		managementTempl = templ ;
	}
	
	
	public String getAsText() {
		 
		if(getValue() !=null)
		{
			Company company = (Company) getValue();
	      return company.toString();
		}
		else
		{
			return "";
		}
	}
	 
	    public void setAsText(final String value) {
	    	
	        try {
	        	
	        	Company company = managementTempl.getCompanyDAO().findById(Integer.valueOf(value));
	        
	            super.setValue(company);
	        } catch (final ObjectRetrievalFailureException e) {
	        	logger.debug("Cannot find company with ID: " + value);
	            super.setValue(null);
	        }
	        
	    }

}
