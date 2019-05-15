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
 * BatchContribution entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchContribution
 * @author MyEclipse Persistence Tools
 */
public class BatchContributionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(BatchContributionDAO.class);
	// property constants
	public static final String MEMBER_EMAIL = "memberEmail";
	public static final String BATCH_CONTRIB_VALUE = "batchContribValue";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";
	public static final String CONTRIB_PROD = "contribProd";
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String COUNTRY_ID = "countryId";
	public static final String VAL_ERROR = "valError";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchContribution transientInstance) {
		log.debug("saving BatchContribution instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BatchContribution persistentInstance) {
		log.debug("deleting BatchContribution instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchContribution findById(java.lang.Integer id) {
		log.debug("getting BatchContribution instance with id: " + id);
		try {
			BatchContribution instance = (BatchContribution) getHibernateTemplate()
					.get("com.sift.financial.member.BatchContribution", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchContribution instance) {
		log.debug("finding BatchContribution instance by example");
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
		log.debug("finding BatchContribution instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BatchContribution as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberEmail(Object memberEmail) {
		return findByProperty(MEMBER_EMAIL, memberEmail);
	}

	public List findByBatchContribValue(Object batchContribValue) {
		return findByProperty(BATCH_CONTRIB_VALUE, batchContribValue);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}

	public List findByContribProd(Object contribProd) {
		return findByProperty(CONTRIB_PROD, contribProd);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findByCountryId(Object countryId) {
		return findByProperty(COUNTRY_ID, countryId);
	}

	public List findByValError(Object valError) {
		return findByProperty(VAL_ERROR, valError);
	}

	public List findAll() {
		log.debug("finding all BatchContribution instances");
		try {
			String queryString = "from BatchContribution";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchContribution merge(BatchContribution detachedInstance) {
		log.debug("merging BatchContribution instance");
		try {
			BatchContribution result = (BatchContribution) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchContribution instance) {
		log.debug("attaching dirty BatchContribution instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchContribution instance) {
		log.debug("attaching clean BatchContribution instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchContributionDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchContributionDAO) ctx.getBean("BatchContributionDAO");
	}
}