package com.sift.financial.member;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CompanyLocal entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyLocal
 * @author MyEclipse Persistence Tools
 */
public class CompanyLocalDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CompanyLocalDAO.class);
	// property constants
	public static final String COMPANY_NAME = "companyName";
	public static final String COMPANY_TYPE_ID = "companyTypeId";
	public static final String COMPANY_CAT_ID = "companyCatId";
	public static final String COMPANY_ZONE_ID = "companyZoneId";
	public static final String COMPANY_BUS_ACT_ID = "companyBusActId";
	public static final String COMPANY_BUS_AREA_ID = "companyBusAreaId";
	public static final String COMPANY_OWN_STRUCT_ID = "companyOwnStructId";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyLocal transientInstance) {
		log.debug("saving CompanyLocal instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyLocal persistentInstance) {
		log.debug("deleting CompanyLocal instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyLocal findById(java.lang.Integer id) {
		log.debug("getting CompanyLocal instance with id: " + id);
		try {
			CompanyLocal instance = (CompanyLocal) getHibernateTemplate().get(
					"com.sift.financial.member.CompanyLocal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyLocal instance) {
		log.debug("finding CompanyLocal instance by example");
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
		log.debug("finding CompanyLocal instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyLocal as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyName(Object companyName) {
		return findByProperty(COMPANY_NAME, companyName);
	}

	public List findByCompanyTypeId(Object companyTypeId) {
		return findByProperty(COMPANY_TYPE_ID, companyTypeId);
	}

	public List findByCompanyCatId(Object companyCatId) {
		return findByProperty(COMPANY_CAT_ID, companyCatId);
	}

	public List findByCompanyZoneId(Object companyZoneId) {
		return findByProperty(COMPANY_ZONE_ID, companyZoneId);
	}

	public List findByCompanyBusActId(Object companyBusActId) {
		return findByProperty(COMPANY_BUS_ACT_ID, companyBusActId);
	}

	public List findByCompanyBusAreaId(Object companyBusAreaId) {
		return findByProperty(COMPANY_BUS_AREA_ID, companyBusAreaId);
	}

	public List findByCompanyOwnStructId(Object companyOwnStructId) {
		return findByProperty(COMPANY_OWN_STRUCT_ID, companyOwnStructId);
	}

	public List findAll() {
		log.debug("finding all CompanyLocal instances");
		try {
			String queryString = "from CompanyLocal";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyLocal merge(CompanyLocal detachedInstance) {
		log.debug("merging CompanyLocal instance");
		try {
			CompanyLocal result = (CompanyLocal) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyLocal instance) {
		log.debug("attaching dirty CompanyLocal instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyLocal instance) {
		log.debug("attaching clean CompanyLocal instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyLocalDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyLocalDAO) ctx.getBean("CompanyLocalDAO");
	}
}