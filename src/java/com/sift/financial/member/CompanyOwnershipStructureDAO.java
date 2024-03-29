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
 * CompanyOwnershipStructure entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.sift.financial.member.CompanyOwnershipStructure
 * @author MyEclipse Persistence Tools
 */
public class CompanyOwnershipStructureDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompanyOwnershipStructureDAO.class);
	// property constants
	public static final String COMPANY_OWNER_STRUCT_DESC = "companyOwnerStructDesc";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyOwnershipStructure transientInstance) {
		log.debug("saving CompanyOwnershipStructure instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyOwnershipStructure persistentInstance) {
		log.debug("deleting CompanyOwnershipStructure instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyOwnershipStructure findById(java.lang.Integer id) {
		log.debug("getting CompanyOwnershipStructure instance with id: " + id);
		try {
			CompanyOwnershipStructure instance = (CompanyOwnershipStructure) getHibernateTemplate()
					.get("com.sift.financial.member.CompanyOwnershipStructure",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyOwnershipStructure instance) {
		log.debug("finding CompanyOwnershipStructure instance by example");
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
		log.debug("finding CompanyOwnershipStructure instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyOwnershipStructure as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyOwnerStructDesc(Object companyOwnerStructDesc) {
		return findByProperty(COMPANY_OWNER_STRUCT_DESC, companyOwnerStructDesc);
	}

	public List findAll() {
		log.debug("finding all CompanyOwnershipStructure instances");
		try {
			String queryString = "from CompanyOwnershipStructure";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyOwnershipStructure merge(
			CompanyOwnershipStructure detachedInstance) {
		log.debug("merging CompanyOwnershipStructure instance");
		try {
			CompanyOwnershipStructure result = (CompanyOwnershipStructure) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyOwnershipStructure instance) {
		log.debug("attaching dirty CompanyOwnershipStructure instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyOwnershipStructure instance) {
		log.debug("attaching clean CompanyOwnershipStructure instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyOwnershipStructureDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyOwnershipStructureDAO) ctx
				.getBean("CompanyOwnershipStructureDAO");
	}
}