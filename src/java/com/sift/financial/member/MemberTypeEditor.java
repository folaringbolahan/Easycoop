package com.sift.financial.member;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sift.financial.services.ManagementTempl;

public class MemberTypeEditor extends PropertyEditorSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ManagementTempl managementTempl;
	
	public MemberTypeEditor ()
	{}
	
	public MemberTypeEditor (ManagementTempl templ)
	{
		managementTempl = templ ;
	}

	public String getAsText() {
		 
		if(getValue() !=null)
		{
			MemberType memberType = (MemberType) getValue();
	      return memberType.toString();
		}
		else
		{
			return "";
		}
	}
	 
	    public void setAsText(final String value) {
	    	
	        try {
	        	
	        	MemberType memberType = managementTempl.getMemberTypeDAO().findById(Integer.valueOf(value));
	        
	            super.setValue(memberType);
	        } catch (final ObjectRetrievalFailureException e) {
	        	logger.debug("Cannot find memberType with ID: " + value);
	            super.setValue(null);
	        }
	        
	    }

}
