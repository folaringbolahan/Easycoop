package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Event
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.sift.financial.member.Event
 * @author MyEclipse Persistence Tools
 */
public class EventDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(EventDAO.class);
	// property constants
	public static final String EVENT_NAME = "eventName";
	public static final String EVENT_SHORT = "eventShort";
	public static final String CREATED_BY = "createdBy";
	public static final String DEL_FLG = "delFlg";

	protected void initDao() {
		// do nothing
	}

	public void save(Event transientInstance) {
		log.debug("saving Event instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Event persistentInstance) {
		log.debug("deleting Event instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Event findById(java.lang.Integer id) {
		log.debug("getting Event instance with id: " + id);
		try {
			Event instance = (Event) getHibernateTemplate().get(
					"com.sift.financial.member.Event", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Event instance) {
		log.debug("finding Event instance by example");
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
		log.debug("finding Event instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Event as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEventName(Object eventName) {
		return findByProperty(EVENT_NAME, eventName);
	}

	public List findByEventShort(Object eventShort) {
		return findByProperty(EVENT_SHORT, eventShort);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findAll() {
		log.debug("finding all Event instances");
		try {
			String queryString = "from Event";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Event merge(Event detachedInstance) {
		log.debug("merging Event instance");
		try {
			Event result = (Event) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Event instance) {
		log.debug("attaching dirty Event instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Event instance) {
		log.debug("attaching clean Event instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EventDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EventDAO) ctx.getBean("EventDAO");
	}
}