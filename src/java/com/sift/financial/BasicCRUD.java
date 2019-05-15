package com.sift.financial;

import org.hibernate.Session;

public interface BasicCRUD {
	
	
	public boolean create(Object transientInstance, Session ss) ;
	public boolean update(Object transientInstance, Session ss) ;
	public Object findById(Long objectId) ;

	
}
