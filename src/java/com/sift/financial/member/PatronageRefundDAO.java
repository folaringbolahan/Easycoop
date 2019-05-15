package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PatronageRefund entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.PatronageRefund
 * @author MyEclipse Persistence Tools
 */
public class PatronageRefundDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PatronageRefundDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String REFUND_YEAR = "refundYear";
	public static final String REFUND_PERIOD = "refundPeriod";
	public static final String FORMULA = "formula";
	public static final String REFUND_VALUE = "refundValue";
	public static final String CREATED_BY = "createdBy";
	public static final String APPROVED_BY = "approvedBy";
	public static final String REFUND_PAY_ACCOUNT = "refundPayAccount";
	public static final String REFUND_NUMBER = "refundNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(PatronageRefund transientInstance) {
		log.debug("saving PatronageRefund instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PatronageRefund persistentInstance) {
		log.debug("deleting PatronageRefund instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PatronageRefund findById(java.lang.Integer id) {
		log.debug("getting PatronageRefund instance with id: " + id);
		try {
			PatronageRefund instance = (PatronageRefund) getHibernateTemplate()
					.get("com.sift.financial.member.PatronageRefund", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PatronageRefund instance) {
		log.debug("finding PatronageRefund instance by example");
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
		log.debug("finding PatronageRefund instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PatronageRefund as model where model."
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

	public List findByRefundYear(Object refundYear) {
		return findByProperty(REFUND_YEAR, refundYear);
	}

	public List findByRefundPeriod(Object refundPeriod) {
		return findByProperty(REFUND_PERIOD, refundPeriod);
	}

	public List findByFormula(Object formula) {
		return findByProperty(FORMULA, formula);
	}

	public List findByRefundValue(Object refundValue) {
		return findByProperty(REFUND_VALUE, refundValue);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}

	public List findByRefundPayAccount(Object refundPayAccount) {
		return findByProperty(REFUND_PAY_ACCOUNT, refundPayAccount);
	}

	public List findByRefundNumber(Object refundNumber) {
		return findByProperty(REFUND_NUMBER, refundNumber);
	}

	public List findAll() {
		log.debug("finding all PatronageRefund instances");
		try {
			String queryString = "from PatronageRefund";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PatronageRefund merge(PatronageRefund detachedInstance) {
		log.debug("merging PatronageRefund instance");
		try {
			PatronageRefund result = (PatronageRefund) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PatronageRefund instance) {
		log.debug("attaching dirty PatronageRefund instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PatronageRefund instance) {
		log.debug("attaching clean PatronageRefund instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PatronageRefundDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PatronageRefundDAO) ctx.getBean("PatronageRefundDAO");
	}
}