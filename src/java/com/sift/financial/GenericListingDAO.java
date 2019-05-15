package com.sift.financial;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericListingDAO extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(GenericListingDAO.class);
	
	private Map<String,String> listQueryMap= new HashMap<String, String>();

}
