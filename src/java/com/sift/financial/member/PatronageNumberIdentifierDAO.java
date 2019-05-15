package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PatronageNumberIdentifier entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.sift.financial.member.PatronageNumberIdentifier
 * @author MyEclipse Persistence Tools
 */
public class PatronageNumberIdentifierDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(PatronageNumberIdentifierDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String LAST_REFUND_NUMBER = "lastRefundNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(PatronageNumberIdentifier transientInstance) {
		log.debug("saving PatronageNumberIdentifier instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PatronageNumberIdentifier persistentInstance) {
		log.debug("deleting PatronageNumberIdentifier instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PatronageNumberIdentifier findById(java.lang.Integer id) {
		log.debug("getting PatronageNumberIdentifier instance with id: " + id);
		try {
			PatronageNumberIdentifier instance = (PatronageNumberIdentifier) getHibernateTemplate()
					.get("com.sift.financial.member.PatronageNumberIdentifier",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PatronageNumberIdentifier instance) {
		log.debug("finding PatronageNumberIdentifier instance by example");
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
		log.debug("finding PatronageNumberIdentifier instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PatronageNumberIdentifier as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findByLastRefundNumber(Object lastRefundNumber) {
		return findByProperty(LAST_REFUND_NUMBER, lastRefundNumber);
	}

	public List findAll() {
		log.debug("finding all PatronageNumberIdentifier instances");
		try {
			String queryString = "from PatronageNumberIdentifier";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PatronageNumberIdentifier merge(
			PatronageNumberIdentifier detachedInstance) {
		log.debug("merging PatronageNumberIdentifier instance");
		try {
			PatronageNumberIdentifier result = (PatronageNumberIdentifier) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PatronageNumberIdentifier instance) {
		log.debug("attaching dirty PatronageNumberIdentifier instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PatronageNumberIdentifier instance) {
		log.debug("attaching clean PatronageNumberIdentifier instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PatronageNumberIdentifierDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PatronageNumberIdentifierDAO) ctx
				.getBean("PatronageNumberIdentifierDAO");
	}
}