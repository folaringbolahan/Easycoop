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
 * TaxGroups entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.TaxGroups
 * @author MyEclipse Persistence Tools
 */
public class TaxGroupsDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TaxGroupsDAO.class);
	// property constants
	public static final String ACTIVE = "active";
	public static final String CODE = "code";
	public static final String COMPANY_ID = "companyId";
	public static final String DELETED = "deleted";
	public static final String DESCRIPTION = "description";

	protected void initDao() {
		// do nothing
	}

	public void save(TaxGroups transientInstance) {
		log.debug("saving TaxGroups instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TaxGroups persistentInstance) {
		log.debug("deleting TaxGroups instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TaxGroups findById(java.lang.Integer id) {
		log.debug("getting TaxGroups instance with id: " + id);
		try {
			TaxGroups instance = (TaxGroups) getHibernateTemplate().get(
					"com.sift.financial.member.TaxGroups", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TaxGroups instance) {
		log.debug("finding TaxGroups instance by example");
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
		log.debug("finding TaxGroups instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TaxGroups as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all TaxGroups instances");
		try {
			String queryString = "from TaxGroups";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TaxGroups merge(TaxGroups detachedInstance) {
		log.debug("merging TaxGroups instance");
		try {
			TaxGroups result = (TaxGroups) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TaxGroups instance) {
		log.debug("attaching dirty TaxGroups instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TaxGroups instance) {
		log.debug("attaching clean TaxGroups instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TaxGroupsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TaxGroupsDAO) ctx.getBean("TaxGroupsDAO");
	}
	
	 public List getTaxGroupsByCompanyId(String compId) {
			
			try {
				
				log.info("In here inside tax groups identifier:" + compId);
				
				return getHibernateTemplate().find("select id, code, description from TaxGroups as model where model.companyId="+compId +" and model.active='Y' and model.deleted='N'");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
}