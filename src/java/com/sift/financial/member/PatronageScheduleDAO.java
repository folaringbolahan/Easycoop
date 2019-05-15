package com.sift.financial.member;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PatronageSchedule entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.PatronageSchedule
 * @author MyEclipse Persistence Tools
 */
public class PatronageScheduleDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(PatronageScheduleDAO.class);
	// property constants
	public static final String REFUND_ID = "refundId";
	public static final String MEMBER_NO = "memberNo";
	public static final String PAID = "paid";
	public static final String COMPANY_CODE = "companyCode";
	public static final String BRANCH_CODE = "branchCode";
	public static final String PATRONAGE_GROSS_VALUE = "patronageGrossValue";
	public static final String PATRONAGE_TYPE = "patronageType";
	public static final String REFUND_NET_VALUE = "refundNetValue";
	public static final String REFUND_TAX_VALUE = "refundTaxValue";
	public static final String PAY_CHANNEL = "payChannel";
	public static final String PAID_BY = "paidBy";
	public static final String REFUND_TAX = "refundTax";

	protected void initDao() {
		// do nothing
	}

	public void save(PatronageSchedule transientInstance) {
		log.debug("saving PatronageSchedule instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PatronageSchedule persistentInstance) {
		log.debug("deleting PatronageSchedule instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PatronageSchedule findById(java.lang.Integer id) {
		log.debug("getting PatronageSchedule instance with id: " + id);
		try {
			PatronageSchedule instance = (PatronageSchedule) getHibernateTemplate()
					.get("com.sift.financial.member.PatronageSchedule", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PatronageSchedule instance) {
		log.debug("finding PatronageSchedule instance by example");
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
		log.debug("finding PatronageSchedule instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PatronageSchedule as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRefundId(Object refundId) {
		return findByProperty(REFUND_ID, refundId);
	}

	public List findByMemberNo(Object memberNo) {
		return findByProperty(MEMBER_NO, memberNo);
	}

	public List findByPaid(Object paid) {
		return findByProperty(PAID, paid);
	}

	public List findByCompanyCode(Object companyCode) {
		return findByProperty(COMPANY_CODE, companyCode);
	}

	public List findByBranchCode(Object branchCode) {
		return findByProperty(BRANCH_CODE, branchCode);
	}

	public List findByPatronageGrossValue(Object patronageGrossValue) {
		return findByProperty(PATRONAGE_GROSS_VALUE, patronageGrossValue);
	}

	public List findByPatronageType(Object patronageType) {
		return findByProperty(PATRONAGE_TYPE, patronageType);
	}

	public List findByRefundNetValue(Object refundNetValue) {
		return findByProperty(REFUND_NET_VALUE, refundNetValue);
	}

	public List findByRefundTaxValue(Object refundTaxValue) {
		return findByProperty(REFUND_TAX_VALUE, refundTaxValue);
	}

	public List findByPayChannel(Object payChannel) {
		return findByProperty(PAY_CHANNEL, payChannel);
	}

	public List findByPaidBy(Object paidBy) {
		return findByProperty(PAID_BY, paidBy);
	}

	public List findByRefundTax(Object refundTax) {
		return findByProperty(REFUND_TAX, refundTax);
	}

	public List findAll() {
		log.debug("finding all PatronageSchedule instances");
		try {
			String queryString = "from PatronageSchedule";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PatronageSchedule merge(PatronageSchedule detachedInstance) {
		log.debug("merging PatronageSchedule instance");
		try {
			PatronageSchedule result = (PatronageSchedule) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PatronageSchedule instance) {
		log.debug("attaching dirty PatronageSchedule instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PatronageSchedule instance) {
		log.debug("attaching clean PatronageSchedule instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PatronageScheduleDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PatronageScheduleDAO) ctx.getBean("PatronageScheduleDAO");
	}
}