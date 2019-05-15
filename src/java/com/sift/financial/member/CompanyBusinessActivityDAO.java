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
 * CompanyBusinessActivity entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyBusinessActivity
 * @author MyEclipse Persistence Tools
 */
public class CompanyBusinessActivityDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompanyBusinessActivityDAO.class);
	// property constants
	public static final String COMPANY_BUSINESS_ACTIVITY_DESC = "companyBusinessActivityDesc";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyBusinessActivity transientInstance) {
		log.debug("saving CompanyBusinessActivity instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyBusinessActivity persistentInstance) {
		log.debug("deleting CompanyBusinessActivity instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyBusinessActivity findById(java.lang.Integer id) {
		log.debug("getting CompanyBusinessActivity instance with id: " + id);
		try {
			CompanyBusinessActivity instance = (CompanyBusinessActivity) getHibernateTemplate()
					.get("com.sift.financial.member.CompanyBusinessActivity",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyBusinessActivity instance) {
		log.debug("finding CompanyBusinessActivity instance by example");
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
		log.debug("finding CompanyBusinessActivity instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyBusinessActivity as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyBusinessActivityDesc(
			Object companyBusinessActivityDesc) {
		return findByProperty(COMPANY_BUSINESS_ACTIVITY_DESC,
				companyBusinessActivityDesc);
	}

	public List findAll() {
		log.debug("finding all CompanyBusinessActivity instances");
		try {
			String queryString = "from CompanyBusinessActivity";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyBusinessActivity merge(
			CompanyBusinessActivity detachedInstance) {
		log.debug("merging CompanyBusinessActivity instance");
		try {
			CompanyBusinessActivity result = (CompanyBusinessActivity) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyBusinessActivity instance) {
		log.debug("attaching dirty CompanyBusinessActivity instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyBusinessActivity instance) {
		log.debug("attaching clean CompanyBusinessActivity instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyBusinessActivityDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyBusinessActivityDAO) ctx
				.getBean("CompanyBusinessActivityDAO");
	}
}