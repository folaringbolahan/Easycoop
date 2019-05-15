package com.sift.financial;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class DAOFactory {
	
	public ObjectListingInterface getFromApplicationContext(ApplicationContext ctx, String requiredDAO) {
		
		try {
			
			return (ObjectListingInterface)(ctx.getBean(requiredDAO));
			
		 } catch (BeansException e) {
			e.printStackTrace();
			return null;
		 } 
		
	}

	
   public QueueInterface getQueueObject(ApplicationContext ctx, String requiredDAO) {
		
		try {
			
			return (QueueInterface)(ctx.getBean(requiredDAO));
			
		 } catch (BeansException e) {
			e.printStackTrace();
			return null;
		 } 
		
	}


}
