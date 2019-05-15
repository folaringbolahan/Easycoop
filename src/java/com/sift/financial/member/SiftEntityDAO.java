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
 * SiftEntity entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.SiftEntity
 * @author MyEclipse Persistence Tools
 */
public class SiftEntityDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(SiftEntityDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DESC = "desc";

	protected void initDao() {
		// do nothing
	}

	public void save(SiftEntity transientInstance) {
		log.debug("saving SiftEntity instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SiftEntity persistentInstance) {
		log.debug("deleting SiftEntity instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SiftEntity findById(java.lang.Integer id) {
		log.debug("getting SiftEntity instance with id: " + id);
		try {
			SiftEntity instance = (SiftEntity) getHibernateTemplate().get(
					"com.sift.financial.member.SiftEntity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SiftEntity instance) {
		log.debug("finding SiftEntity instance by example");
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
		log.debug("finding SiftEntity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SiftEntity as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByDesc(Object desc) {
		return findByProperty(DESC, desc);
	}

	public List findAll() {
		log.debug("finding all SiftEntity instances");
		try {
			String queryString = "from SiftEntity";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SiftEntity merge(SiftEntity detachedInstance) {
		log.debug("merging SiftEntity instance");
		try {
			SiftEntity result = (SiftEntity) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SiftEntity instance) {
		log.debug("attaching dirty SiftEntity instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SiftEntity instance) {
		log.debug("attaching clean SiftEntity instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SiftEntityDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SiftEntityDAO) ctx.getBean("SiftEntityDAO");
	}
}