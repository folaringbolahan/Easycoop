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
 * CompanyCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyCategory
 * @author MyEclipse Persistence Tools
 */
public class CompanyCategoryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CompanyCategoryDAO.class);
	// property constants
	public static final String COMPANY_CATEGORY_NAME = "companyCategoryName";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyCategory transientInstance) {
		log.debug("saving CompanyCategory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyCategory persistentInstance) {
		log.debug("deleting CompanyCategory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyCategory findById(java.lang.Integer id) {
		log.debug("getting CompanyCategory instance with id: " + id);
		try {
			CompanyCategory instance = (CompanyCategory) getHibernateTemplate()
					.get("com.sift.financial.member.CompanyCategory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyCategory instance) {
		log.debug("finding CompanyCategory instance by example");
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
		log.debug("finding CompanyCategory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyCategory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyCategoryName(Object companyCategoryName) {
		return findByProperty(COMPANY_CATEGORY_NAME, companyCategoryName);
	}

	public List findAll() {
		log.debug("finding all CompanyCategory instances");
		try {
			String queryString = "from CompanyCategory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyCategory merge(CompanyCategory detachedInstance) {
		log.debug("merging CompanyCategory instance");
		try {
			CompanyCategory result = (CompanyCategory) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyCategory instance) {
		log.debug("attaching dirty CompanyCategory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyCategory instance) {
		log.debug("attaching clean CompanyCategory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyCategoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyCategoryDAO) ctx.getBean("CompanyCategoryDAO");
	}
}