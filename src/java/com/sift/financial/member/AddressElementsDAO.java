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
 * AddressElements entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.AddressElements
 * @author MyEclipse Persistence Tools
 */
public class AddressElementsDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AddressElementsDAO.class);
	// property constants
	public static final String ADDR_FIELD_NAME = "addrFieldName";
	public static final String ACTIVE = "active";
	public static final String DELETED = "deleted";
	public static final String CREATED_BY = "createdBy";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(AddressElements transientInstance) {
		log.debug("saving AddressElements instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AddressElements persistentInstance) {
		log.debug("deleting AddressElements instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AddressElements findById(java.lang.Long id) {
		log.debug("getting AddressElements instance with id: " + id);
		try {
			AddressElements instance = (AddressElements) getHibernateTemplate()
					.get("com.sift.financial.member.AddressElements", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AddressElements instance) {
		log.debug("finding AddressElements instance by example");
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
		log.debug("finding AddressElements instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AddressElements as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAddrFieldName(Object addrFieldName) {
		return findByProperty(ADDR_FIELD_NAME, addrFieldName);
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

	public List findAll() {
		log.debug("finding all AddressElements instances");
		try {
			String queryString = "from AddressElements";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AddressElements merge(AddressElements detachedInstance) {
		log.debug("merging AddressElements instance");
		try {
			AddressElements result = (AddressElements) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AddressElements instance) {
		log.debug("attaching dirty AddressElements instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AddressElements instance) {
		log.debug("attaching clean AddressElements instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AddressElementsDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AddressElementsDAO) ctx.getBean("AddressElementsDAO");
	}
}