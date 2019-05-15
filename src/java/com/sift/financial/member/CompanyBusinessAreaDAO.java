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
 * CompanyBusinessArea entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyBusinessArea
 * @author MyEclipse Persistence Tools
 */
public class CompanyBusinessAreaDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompanyBusinessAreaDAO.class);
	// property constants
	public static final String COMPANY_BUS_AREA_DESC = "companyBusAreaDesc";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyBusinessArea transientInstance) {
		log.debug("saving CompanyBusinessArea instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyBusinessArea persistentInstance) {
		log.debug("deleting CompanyBusinessArea instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyBusinessArea findById(java.lang.Integer id) {
		log.debug("getting CompanyBusinessArea instance with id: " + id);
		try {
			CompanyBusinessArea instance = (CompanyBusinessArea) getHibernateTemplate()
					.get("com.sift.financial.member.CompanyBusinessArea", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyBusinessArea instance) {
		log.debug("finding CompanyBusinessArea instance by example");
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
		log.debug("finding CompanyBusinessArea instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyBusinessArea as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyBusAreaDesc(Object companyBusAreaDesc) {
		return findByProperty(COMPANY_BUS_AREA_DESC, companyBusAreaDesc);
	}

	public List findAll() {
		log.debug("finding all CompanyBusinessArea instances");
		try {
			String queryString = "from CompanyBusinessArea";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyBusinessArea merge(CompanyBusinessArea detachedInstance) {
		log.debug("merging CompanyBusinessArea instance");
		try {
			CompanyBusinessArea result = (CompanyBusinessArea) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyBusinessArea instance) {
		log.debug("attaching dirty CompanyBusinessArea instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyBusinessArea instance) {
		log.debug("attaching clean CompanyBusinessArea instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyBusinessAreaDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyBusinessAreaDAO) ctx.getBean("CompanyBusinessAreaDAO");
	}
}