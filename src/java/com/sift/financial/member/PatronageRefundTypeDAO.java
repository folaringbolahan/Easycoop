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
 * PatronageRefundType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.PatronageRefundType
 * @author MyEclipse Persistence Tools
 */
public class PatronageRefundTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(PatronageRefundTypeDAO.class);
	// property constants
	public static final String PATRONAGE_REFUND_TYPE_NAME = "patronageRefundTypeName";
	public static final String PATRONAGE_REFUND_TYPE_SHORT = "patronageRefundTypeShort";
	public static final String DEL_FLG = "delFlg";

	protected void initDao() {
		// do nothing
	}

	public void save(PatronageRefundType transientInstance) {
		log.debug("saving PatronageRefundType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PatronageRefundType persistentInstance) {
		log.debug("deleting PatronageRefundType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PatronageRefundType findById(java.lang.Integer id) {
		log.debug("getting PatronageRefundType instance with id: " + id);
		try {
			PatronageRefundType instance = (PatronageRefundType) getHibernateTemplate()
					.get("com.sift.financial.member.PatronageRefundType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PatronageRefundType instance) {
		log.debug("finding PatronageRefundType instance by example");
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
		log.debug("finding PatronageRefundType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PatronageRefundType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPatronageRefundTypeName(Object patronageRefundTypeName) {
		return findByProperty(PATRONAGE_REFUND_TYPE_NAME,
				patronageRefundTypeName);
	}

	public List findByPatronageRefundTypeShort(Object patronageRefundTypeShort) {
		return findByProperty(PATRONAGE_REFUND_TYPE_SHORT,
				patronageRefundTypeShort);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findAll() {
		log.debug("finding all PatronageRefundType instances");
		try {
			String queryString = "from PatronageRefundType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PatronageRefundType merge(PatronageRefundType detachedInstance) {
		log.debug("merging PatronageRefundType instance");
		try {
			PatronageRefundType result = (PatronageRefundType) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PatronageRefundType instance) {
		log.debug("attaching dirty PatronageRefundType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PatronageRefundType instance) {
		log.debug("attaching clean PatronageRefundType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PatronageRefundTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PatronageRefundTypeDAO) ctx.getBean("PatronageRefundTypeDAO");
	}
}