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
 * A data access object (DAO) providing persistence and search support for
 * Countries entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Countries
 * @author MyEclipse Persistence Tools
 */
public class CountriesDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CountriesDAO.class);
	// property constants
	public static final String COUNTRY_CODE = "countryCode";
	public static final String COUNTRY_NAME = "countryName";
	public static final String ACTIVE = "active";
	public static final String CREATED_BY = "createdBy";
	public static final String DELETED = "deleted";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(Countries transientInstance) {
		log.debug("saving Countries instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Countries persistentInstance) {
		log.debug("deleting Countries instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Countries findById(java.lang.Long id) {
		log.debug("getting Countries instance with id: " + id);
		try {
			Countries instance = (Countries) getHibernateTemplate().get(
					"com.sift.financial.member.Countries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Countries instance) {
		log.debug("finding Countries instance by example");
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
		log.debug("finding Countries instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Countries as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCountryCode(Object countryCode) {
		return findByProperty(COUNTRY_CODE, countryCode);
	}

	public List findByCountryName(Object countryName) {
		return findByProperty(COUNTRY_NAME, countryName);
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByLastModifiedBy(Object lastModifiedBy) {
		return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
	}

	public List findAll() {
		log.debug("finding all Countries instances");
		try {
			String queryString = "from Countries";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Countries merge(Countries detachedInstance) {
		log.debug("merging Countries instance");
		try {
			Countries result = (Countries) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Countries instance) {
		log.debug("attaching dirty Countries instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Countries instance) {
		log.debug("attaching clean Countries instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CountriesDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CountriesDAO) ctx.getBean("CountriesDAO");
	}
}