package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * A data access object (DAO) providing persistence and search support for
 * ActivityLog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.ActivityLog
 * @author MyEclipse Persistence Tools
 */
public class ActivityLogDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ActivityLogDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String DESCRIPTION = "description";
	public static final String ACTION = "action";
	public static final String ACTION_ITEM = "actionItem";
	public static final String ACTION_RESULT = "actionResult";
	public static final String BRANCH_ID = "branchId";
	public static final String COMPANY_ID = "companyId";
	public static final String IPADDRESS = "ipaddress";
	

	protected void initDao() {
		// do nothing
	}

	public void save(ActivityLog transientInstance) {
		log.debug("saving ActivityLog instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
                        System.out.println("Activity save successful");
		} catch (Exception re) {
			log.error("save failed", re);
                        re.printStackTrace();
			//throw re;
		}
	}
	public void save(ActivityLog transientInstance,Session sess) {
		log.debug("saving ActivityLog instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ActivityLog persistentInstance) {
		log.debug("deleting ActivityLog instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ActivityLog findById(java.lang.Integer id) {
		log.debug("getting ActivityLog instance with id: " + id);
		try {
			ActivityLog instance = (ActivityLog) getHibernateTemplate().get(
					"com.sift.financial.member.ActivityLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ActivityLog instance) {
		log.debug("finding ActivityLog instance by example");
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
		log.debug("finding ActivityLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ActivityLog as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findByAction(Object action) {
		return findByProperty(ACTION, action);
	}

	public List findByActionItem(Object actionItem) {
		return findByProperty(ACTION_ITEM, actionItem);
	}

	public List findByActionResult(Object actionResult) {
		return findByProperty(ACTION_RESULT, actionResult);
	}

	public List findAll() {
		log.debug("finding all ActivityLog instances");
		try {
			String queryString = "from ActivityLog";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ActivityLog merge(ActivityLog detachedInstance) {
		log.debug("merging ActivityLog instance");
		try {
			ActivityLog result = (ActivityLog) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ActivityLog instance) {
		log.debug("attaching dirty ActivityLog instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ActivityLog instance) {
		log.debug("attaching clean ActivityLog instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ActivityLogDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ActivityLogDAO) ctx.getBean("ActivityLogDAO");
	}
	
	public List findByWhereColumnClause(String whereClause, Session ss) {

		log.debug(" finding all ActivityLog instances by sent where clause");
        Query q = ss.createQuery("select username , actionDate, description, actionresult,action, actionItem, event.eventName, branchCode, compCode  from ActivityLog e where "   +  whereClause);
		return q.list();
	}
	
	public List findByWhereColumn(String whereClause, Session ss) {

		log.debug(" finding all ActivityLog instances by sent where clause");
        Query q = ss.createQuery("  from ActivityLog e where "   +  whereClause);
		return q.list();
	}
}