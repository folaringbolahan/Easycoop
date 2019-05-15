package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Tax
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.sift.financial.member.Tax
 * @author MyEclipse Persistence Tools
 */
public class TaxDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TaxDAO.class);
	// property constants
	public static final String TAX_NAME = "taxName";
	public static final String TAX_DESCRIPTION = "taxDescription";
	public static final String RATE = "rate";
	public static final String ACTIVE = "active";
	public static final String DELETED = "deleted";
	public static final String CREATED_BY = "createdBy";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String TAX_CODE = "taxCode";
	public static final String COUNTRY_ID = "countryId";
	public static final String LOCATION_DEPENDENT = "locationDependent";
	public static final String COMPANY_ID = "companyId";
	public static final String TAX_GROUP_ID = "taxGroupId";

	protected void initDao() {
		// do nothing
	}

	public void save(Tax transientInstance) {
		log.debug("saving Tax instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tax persistentInstance) {
		log.debug("deleting Tax instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tax findById(java.lang.Long id) {
		log.debug("getting Tax instance with id: " + id);
		try {
			Tax instance = (Tax) getHibernateTemplate().get(
					"com.sift.financial.member.Tax", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tax instance) {
		log.debug("finding Tax instance by example");
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
		log.debug("finding Tax instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tax as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTaxName(Object taxName) {
		return findByProperty(TAX_NAME, taxName);
	}

	public List findByTaxDescription(Object taxDescription) {
		return findByProperty(TAX_DESCRIPTION, taxDescription);
	}

	public List findByRate(Object rate) {
		return findByProperty(RATE, rate);
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByLastModifiedBy(Object lastModifiedBy) {
		return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
	}

	public List findByTaxCode(Object taxCode) {
		return findByProperty(TAX_CODE, taxCode);
	}

	public List findByCountryId(Object countryId) {
		return findByProperty(COUNTRY_ID, countryId);
	}

	public List findByLocationDependent(Object locationDependent) {
		return findByProperty(LOCATION_DEPENDENT, locationDependent);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByTaxGroupId(Object taxGroupId) {
		return findByProperty(TAX_GROUP_ID, taxGroupId);
	}

	public List findAll() {
		log.debug("finding all Tax instances");
		try {
			String queryString = "from Tax";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tax merge(Tax detachedInstance) {
		log.debug("merging Tax instance");
		try {
			Tax result = (Tax) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tax instance) {
		log.debug("attaching dirty Tax instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tax instance) {
		log.debug("attaching clean Tax instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TaxDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TaxDAO) ctx.getBean("TaxDAO");
	}
}