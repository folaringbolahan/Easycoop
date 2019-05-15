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
 * DividendType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.DividendType
 * @author MyEclipse Persistence Tools
 */
public class DividendTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DividendTypeDAO.class);
	// property constants
	public static final String DIVIDEND_TYPE_NAME = "dividendTypeName";
	public static final String DEL_FLG = "delFlg";
	public static final String DIVIDEND_TYPE_CODE = "dividendTypeCode";

	protected void initDao() {
		// do nothing
	}

	public void save(DividendType transientInstance) {
		log.debug("saving DividendType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DividendType persistentInstance) {
		log.debug("deleting DividendType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DividendType findById(java.lang.Integer id) {
		log.debug("getting DividendType instance with id: " + id);
		try {
			DividendType instance = (DividendType) getHibernateTemplate().get(
					"com.sift.financial.member.DividendType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DividendType instance) {
		log.debug("finding DividendType instance by example");
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
		log.debug("finding DividendType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DividendType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDividendTypeName(Object dividendTypeName) {
		return findByProperty(DIVIDEND_TYPE_NAME, dividendTypeName);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByDividendTypeCode(Object dividendTypeCode) {
		return findByProperty(DIVIDEND_TYPE_CODE, dividendTypeCode);
	}

	public List findAll() {
		log.debug("finding all DividendType instances");
		try {
			String queryString = "from DividendType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DividendType merge(DividendType detachedInstance) {
		log.debug("merging DividendType instance");
		try {
			DividendType result = (DividendType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DividendType instance) {
		log.debug("attaching dirty DividendType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DividendType instance) {
		log.debug("attaching clean DividendType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DividendTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DividendTypeDAO) ctx.getBean("DividendTypeDAO");
	}
}