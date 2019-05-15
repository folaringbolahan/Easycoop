package com.sift.financial.member;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sift.financial.services.ManagementTempl;

public class IdentificationDocEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ManagementTempl managementTempl;
	
	public IdentificationDocEditor ()
	{}
	
	public IdentificationDocEditor (ManagementTempl templ)
	{
		managementTempl = templ ;
	}
	
	
	public String getAsText() {
		 
		if(getValue() !=null)
		{
			IdentificationDoc identificationDoc = (IdentificationDoc) getValue();
	      return identificationDoc.toString();
		}
		else
		{
			return "";
		}
	}
	 
	    public void setAsText(final String value) {
	    	
	        try {
	        	
	        	IdentificationDoc identificationDoc = managementTempl.getIdentificationDocDAO().findById(Integer.valueOf(value));
	        
	            super.setValue(identificationDoc);
	        } catch (final ObjectRetrievalFailureException e) {
	        	logger.debug("Cannot find Doc with ID: " + value);
	            super.setValue(null);
	        }
	        
	    }

}
