package com.sift.financial.member;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Status entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Status
 * @author MyEclipse Persistence Tools
 */
public class StatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(StatusDAO.class);
	// property constants
	public static final String STATUS_DESC = "statusDesc";
	public static final String STATUS_SHORT = "statusShort";
	public static final String DEL_FLG = "delFlg";
	public static final String FAIL_STATE = "failState";

	protected void initDao() {
		// do nothing
	}

	public void save(Status transientInstance) {
		log.debug("saving Status instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Status persistentInstance) {
		log.debug("deleting Status instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Status findById(java.lang.Integer id) {
		log.debug("getting Status instance with id: " + id);
		try {
			Status instance = (Status) getHibernateTemplate().get(
					"com.sift.financial.member.Status", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Status instance) {
		log.debug("finding Status instance by example");
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
		log.debug("finding Status instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Status as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatusDesc(Object statusDesc) {
		return findByProperty(STATUS_DESC, statusDesc);
	}

	public List findByStatusShort(Object statusShort) {
		return findByProperty(STATUS_SHORT, statusShort);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByFailState(Object failState) {
		return findByProperty(FAIL_STATE, failState);
	}

	public List findAll() {
		log.debug("finding all Status instances");
		try {
			String queryString = "from Status";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Status merge(Status detachedInstance) {
		log.debug("merging Status instance");
		try {
			Status result = (Status) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Status instance) {
		log.debug("attaching dirty Status instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Status instance) {
		log.debug("attaching clean Status instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StatusDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StatusDAO) ctx.getBean("StatusDAO");
	}
	
	public Status getStatusFromShort (String curStat)
	{
		Status stat= null;
		
		Query q = getHibernateTemplate().getSessionFactory().openSession().createQuery(" from Status e where e.statusShort='" + curStat +"'");	
		
		List theList = q.list();
	 
		 if(theList.size() >0)
		 {
			 stat = (Status)(theList.get(0));
		 }
	
		return stat;
	}
}