package com.sift.financial.member;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * StatusFlow entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.StatusFlow
 * @author MyEclipse Persistence Tools
 */
public class StatusFlowDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StatusFlowDAO.class);
	// property constants
	public static final String STATUS_SUCCESS_ID = "statusSuccessId";
	public static final String STATUS_FAIL_ID = "statusFailId";
	public static final String STATUS_VALID_ID = "statusValidId";

	protected void initDao() {
		// do nothing
	}

	public void save(StatusFlow transientInstance) {
		log.debug("saving StatusFlow instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StatusFlow persistentInstance) {
		log.debug("deleting StatusFlow instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StatusFlow findById(java.lang.Integer id) {
		log.debug("getting StatusFlow instance with id: " + id);
		try {
			StatusFlow instance = (StatusFlow) getHibernateTemplate().get(
					"com.sift.financial.member.StatusFlow", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StatusFlow instance) {
		log.debug("finding StatusFlow instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StatusFlow instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StatusFlow as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findByStatusSuccessId(Object statusSuccessId) {
		return findByProperty(STATUS_SUCCESS_ID, statusSuccessId);
	}

	public List findByStatusFailId(Object statusFailId) {
		return findByProperty(STATUS_FAIL_ID, statusFailId);
	}

	public List findByStatusValidId(Object statusValidId) {
		return findByProperty(STATUS_VALID_ID, statusValidId);
	}

	public List findAll() {
		log.debug("finding all StatusFlow instances");
		try {
			String queryString = "from StatusFlow";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StatusFlow merge(StatusFlow detachedInstance) {
		log.debug("merging StatusFlow instance");
		try {
			StatusFlow result = (StatusFlow) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StatusFlow instance) {
		log.debug("attaching dirty StatusFlow instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StatusFlow instance) {
		log.debug("attaching clean StatusFlow instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StatusFlowDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StatusFlowDAO) ctx.getBean("StatusFlowDAO");
	}
	
	
	public StatusFlow getFlowFromEventStatus (Event event, Status curStat)
	{
		StatusFlow statFlow= null;
		
		Query q = getHibernateTemplate().getSessionFactory().openSession().createQuery(" from StatusFlow e where e.event.eventId= "   +  event.getEventId() + " and e.status.statusId=" + curStat.getStatusId());	
		
		List theList = q.list();
	 
		 if(theList.size() >0)
		 {
			 statFlow = (StatusFlow)(theList.get(0));
		 }
	
		return statFlow;
	}
}