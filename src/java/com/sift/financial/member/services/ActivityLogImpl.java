package com.sift.financial.member.services;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sift.financial.member.*;

public class ActivityLogImpl {

	private static final Log logger = LogFactory.getLog(ActivityLogImpl.class);
	private ActivityLogDAO activityLogDAO;

	public ActivityLogImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the activityLogDAO
	 */
	public ActivityLogDAO getActivityLogDAO() {
		return activityLogDAO;
	}

	/**
	 * @param activityLogDAO the activityLogDAO to set
	 */
	public void setActivityLogDAO(ActivityLogDAO activityLogDAO) {
		this.activityLogDAO = activityLogDAO;
	}
	
	
	public void doLog(ActivityLog activityLog)
	{
		Session session = getActivityLogDAO().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try
		{
			getActivityLogDAO().save(activityLog, session);
			tx.commit();
		}
		catch(Exception ex)
		{
			tx.rollback();
			logger.info(ActivityLogImpl.class.getName() + " :: ActivityLogImpl logging :: " + "  User: " + activityLog.getUsername() + " Action:: " + activityLog.getAction() + "  :ActionDate:: " +activityLog.getActionDate().toString() + " ::Actiondecsription :" + activityLog.getDescription() + " :Result:: " + activityLog.getActionResult());
		}
		finally
		{
			session.close();
		}
	}
	
	public void doLog(List activityLogList)
	{
		Session session = getActivityLogDAO().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Iterator iter = activityLogList.iterator();
		
		try
		{
			while(iter.hasNext())
			{
				ActivityLog	activityLog = (ActivityLog)iter.next();
				logger.info(ActivityLogImpl.class.getName() + " :: ActivityLogImpl logging :: " + "  User: " + activityLog.getUsername() + " Action:: " + activityLog.getAction() + "  :ActionDate:: " +activityLog.getActionDate().toString() + " ::Actiondecsription :" + activityLog.getDescription() + " :Result:: " + activityLog.getActionResult());
				getActivityLogDAO().save(activityLog, session);
			}
			tx.commit();
		}
		catch(Exception ex)
		{
			tx.rollback();
			//logger.info(PersistenceActivity.class.getName() + " :: PersistenceActivityFail logging :: " + "  User: " + activityLog.getUsername() + " Action:: " + activityLog.getAction() + "  :ActionDate:: " +activityLog.getActionDate().toString() + " ::Actiondecsription :" + activityLog.getDescription() + " :Result:: " + activityLog.getActionresult());
		}
		finally
		{
			session.close();
		}
	}
	

	public List findByWhereColumnClause(ActivityLog activityLog)
	{
		Session session = getActivityLogDAO().getSessionFactory().openSession();
		//Transaction tx = session.beginTransaction();
		List theList = new ArrayList();
		
		String theClause = "1=1 ";
		
		if(activityLog != null)
		{
			if (activityLog.getAction()!=null && !activityLog.getAction().equals(""))
			{
				theClause = theClause + " and  e.action='"+ activityLog.getAction()+"'";
			}
			
			if(activityLog.getActionItem()!=null && !activityLog.getActionItem().equals(""))
			{
				theClause = theClause + " and e.actionItem='" + activityLog.getActionItem() + "'";
			}
			
			if(activityLog.getActionResult()!=null && !activityLog.getActionResult().equals(""))
			{
				theClause = theClause + " and e.actionresult='" + activityLog.getActionResult() + "'";
			}
			
			if(activityLog.getUsername()!=null && !activityLog.getUsername().equals(""))
			{
				theClause = theClause + " and e.username like '%" + activityLog.getUsername() + "%'";
			}
			
			
			if(activityLog.getFromDate()!=null && !activityLog.getFromDate().equals("") && activityLog.getToDate()!=null && !activityLog.getToDate().equals(""))
			{
				theClause = theClause + " and trunc(e.actionDate) between to_date('" + activityLog.getFromDate() + "','dd-mm-yyyy') and to_date('" + activityLog.getToDate() + "','dd-mm-yyyy')";
			}
			
			if((activityLog.getFromDate()==null || activityLog.getFromDate().equals("")) && (activityLog.getToDate()!=null && !activityLog.getToDate().equals("")))
			{
				theClause = theClause + " and trunc(e.actionDate) <= to_date('" + activityLog.getToDate() + "','dd-mm-yyyy')";
			}
			
			
			if((activityLog.getFromDate()!=null && !activityLog.getFromDate().equals("")) && (activityLog.getToDate()==null && activityLog.getToDate().equals("")))
			{
				theClause = theClause + " and trunc(e.actionDate) >= to_date('" + activityLog.getFromDate() + "','dd-mm-yyyy')";
			}
			
		}
	
		try
		{
			
			theList = getActivityLogDAO().findByWhereColumnClause(theClause, session);
		}
		catch(Exception ex)
		{
		}
		finally
		{
			session.close();
		}
	
		return theList;
	}
	
	public List findByWhere(String theClause)
	{
		Session session = getActivityLogDAO().getSessionFactory().openSession();
		//Transaction tx = session.beginTransaction();
		List theList = new ArrayList();
		try
		{
			
			theList = getActivityLogDAO().findByWhereColumn(theClause, session);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
	
		return theList;
	}
	
	
}
