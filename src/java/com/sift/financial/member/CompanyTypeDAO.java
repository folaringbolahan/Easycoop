package com.sift.financial.member;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CompanyType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyType
 * @author MyEclipse Persistence Tools
 */
public class CompanyTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CompanyTypeDAO.class);
	// property constants
	public static final String COMPANY_TYPE_NAME = "companyTypeName";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyType transientInstance) {
		log.debug("saving CompanyType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyType persistentInstance) {
		log.debug("deleting CompanyType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyType findById(java.lang.Integer id) {
		log.debug("getting CompanyType instance with id: " + id);
		try {
			CompanyType instance = (CompanyType) getHibernateTemplate().get(
					"com.sift.financial.member.CompanyType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyType instance) {
		log.debug("finding CompanyType instance by example");
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
		log.debug("finding CompanyType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CompanyType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyTypeName(Object companyTypeName) {
		return findByProperty(COMPANY_TYPE_NAME, companyTypeName);
	}

	public List findAll() {
		log.debug("finding all CompanyType instances");
		try {
			String queryString = "from CompanyType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyType merge(CompanyType detachedInstance) {
		log.debug("merging CompanyType instance");
		try {
			CompanyType result = (CompanyType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyType instance) {
		log.debug("attaching dirty CompanyType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyType instance) {
		log.debug("attaching clean CompanyType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyTypeDAO) ctx.getBean("CompanyTypeDAO");
	}
}