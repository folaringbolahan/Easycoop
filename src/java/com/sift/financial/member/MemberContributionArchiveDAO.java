package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberContributionArchive entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.sift.financial.member.MemberContributionArchive
 * @author MyEclipse Persistence Tools
 */
public class MemberContributionArchiveDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(MemberContributionArchiveDAO.class);
	// property constants
	public static final String MEMBER_ID = "memberId";
	public static final String MEMBER_CONTRIB_VALUE = "memberContribValue";
	public static final String CREATED_BY = "createdBy";
	public static final String CONTRIB_PROD = "contribProd";
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String COUNTRY_ID = "countryId";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberContributionArchive transientInstance) {
		log.debug("saving MemberContributionArchive instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        public void save(MemberContributionArchive transientInstance, Session sess) {
		log.debug("saving MemberContributionArchive instance");
		try {
			sess.save(transientInstance);
			log.info("save MemberContributionArchive successful................");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberContributionArchive persistentInstance) {
		log.debug("deleting MemberContributionArchive instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberContributionArchive findById(java.lang.Integer id) {
		log.debug("getting MemberContributionArchive instance with id: " + id);
		try {
			MemberContributionArchive instance = (MemberContributionArchive) getHibernateTemplate()
					.get("com.sift.financial.member.MemberContributionArchive",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberContributionArchive instance) {
		log.debug("finding MemberContributionArchive instance by example");
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
		log.debug("finding MemberContributionArchive instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberContributionArchive as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberId(Object memberId) {
		return findByProperty(MEMBER_ID, memberId);
	}

	public List findByMemberContribValue(Object memberContribValue) {
		return findByProperty(MEMBER_CONTRIB_VALUE, memberContribValue);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
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

	public List findAll() {
		log.debug("finding all MemberContributionArchive instances");
		try {
			String queryString = "from MemberContributionArchive";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberContributionArchive merge(
			MemberContributionArchive detachedInstance) {
		log.debug("merging MemberContributionArchive instance");
		try {
			MemberContributionArchive result = (MemberContributionArchive) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberContributionArchive instance) {
		log.debug("attaching dirty MemberContributionArchive instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberContributionArchive instance) {
		log.debug("attaching clean MemberContributionArchive instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberContributionArchiveDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberContributionArchiveDAO) ctx
				.getBean("MemberContributionArchiveDAO");
	}
}