package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Company entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Company
 * @author MyEclipse Persistence Tools
 */
public class CompanyDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CompanyDAO.class);
	// property constants
	public static final String NAME = "name";
	//public static final String COMPANY_ZONE_ID = "companyZoneId";
	public static final String SHORT_NAME = "shortName";
	public static final String FAX = "fax";
	public static final String WEBSITE = "website";
	public static final String ACTIVE = "active";
	public static final String DELETED = "deleted";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";
	public static final String REGNO = "regno";
	public static final String CODE = "code";

	protected void initDao() {
		// do nothing
	}

	public void save(Company transientInstance) {
		log.debug("saving Company instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Company persistentInstance) {
		log.debug("deleting Company instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Company findById(java.lang.Integer id) {
		log.debug("getting Company instance with id: " + id);
		try {
			Company instance = (Company) getHibernateTemplate().get(
					"com.sift.financial.member.Company", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Company instance) {
		log.debug("finding Company instance by example");
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
		log.info("finding Company instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Company as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.info("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	/**
         * public List findByCompanyZoneId(Object companyZoneId) {
		return findByProperty(COMPANY_ZONE_ID, companyZoneId);
	}*/

	public List findByShortName(Object shortName) {
		return findByProperty(SHORT_NAME, shortName);
	}

	public List findByFax(Object fax) {
		return findByProperty(FAX, fax);
	}

	public List findByWebsite(Object website) {
		return findByProperty(WEBSITE, website);
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByLastModifiedBy(Object lastModifiedBy) {
		return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
	}

	public List findByRegno(Object regno) {
		return findByProperty(REGNO, regno);
	}

	public List findAll() {
		log.debug("finding all Company instances");
		try {
			String queryString = "from Company";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Company merge(Company detachedInstance) {
		log.debug("merging Company instance");
		try {
			Company result = (Company) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Company instance) {
		log.debug("attaching dirty Company instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Company instance) {
		log.debug("attaching clean Company instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CompanyDAO) ctx.getBean("CompanyDAO");
	}
}