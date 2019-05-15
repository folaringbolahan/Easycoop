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
 * CompStockProperty entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompStockProperty
 * @author MyEclipse Persistence Tools
 */
public class CompStockPropertyDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompStockPropertyDAO.class);
	// property constants
	public static final String STOCK_PPTY_NAME = "stockPptyName";
	public static final String DEL_FLG = "delFlg";
	public static final String STOCK_PPTY_DESC = "stockPptyDesc";
	public static final String STOCK_PPTY_DISPLAY = "stockPptyDisplay";
        public static final String STOCK_PPTY_TIP = "stockPptyTip";

	protected void initDao() {
		// do nothing
	}

	public void save(CompStockProperty transientInstance) {
		log.debug("saving CompStockProperty instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompStockProperty persistentInstance) {
		log.debug("deleting CompStockProperty instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompStockProperty findById(java.lang.Integer id) {
		log.debug("getting CompStockProperty instance with id: " + id);
		try {
			CompStockProperty instance = (CompStockProperty) getHibernateTemplate()
					.get("com.sift.financial.member.CompStockProperty", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompStockProperty instance) {
		log.debug("finding CompStockProperty instance by example");
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
            
		log.info("finding CompStockProperty instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompStockProperty as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStockPptyName(Object stockPptyName) {
		return findByProperty(STOCK_PPTY_NAME, stockPptyName);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByStockPptyDesc(Object stockPptyDesc) {
		return findByProperty(STOCK_PPTY_DESC, stockPptyDesc);
	}

	public List findByStockPptyDisplay(Object stockPptyDisplay) {
		return findByProperty(STOCK_PPTY_DISPLAY, stockPptyDisplay);
	}
        
        public List findByStockPptyTip(Object stockPptyTip) {
		return findByProperty(STOCK_PPTY_TIP, stockPptyTip);
	}

	public List findAll() {
		log.debug("finding all CompStockProperty instances");
		try {
			String queryString = "from CompStockProperty";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompStockProperty merge(CompStockProperty detachedInstance) {
		log.debug("merging CompStockProperty instance");
		try {
			CompStockProperty result = (CompStockProperty) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompStockProperty instance) {
		log.debug("attaching dirty CompStockProperty instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompStockProperty instance) {
		log.debug("attaching clean CompStockProperty instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompStockPropertyDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompStockPropertyDAO) ctx.getBean("CompStockPropertyDAO");
	}
}