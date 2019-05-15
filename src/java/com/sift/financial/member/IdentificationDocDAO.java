package com.sift.financial.member;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * A data access object (DAO) providing persistence and search support for
 * IdentificationDoc entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.IdentificationDoc
 * @author MyEclipse Persistence Tools
 */
public class IdentificationDocDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(IdentificationDocDAO.class);
	// property constants
	public static final String IDENTIFICATION_DOC_NAME = "identificationDocName";
	public static final String IDENTIFICATION_DOC_DESC = "identificationDocDesc";
	public static final String DEL_FLG = "delFlg";

	protected void initDao() {
		// do nothing
	}

	public void save(IdentificationDoc transientInstance) {
		log.debug("saving IdentificationDoc instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IdentificationDoc persistentInstance) {
		log.debug("deleting IdentificationDoc instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IdentificationDoc findById(java.lang.Integer id) {
		log.debug("getting IdentificationDoc instance with id: " + id);
		try {
			IdentificationDoc instance = (IdentificationDoc) getHibernateTemplate()
					.get("com.sift.financial.member.IdentificationDoc", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IdentificationDoc instance) {
		log.debug("finding IdentificationDoc instance by example");
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
		log.debug("finding IdentificationDoc instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IdentificationDoc as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIdentificationDocName(Object identificationDocName) {
		return findByProperty(IDENTIFICATION_DOC_NAME, identificationDocName);
	}

	public List findByIdentificationDocDesc(Object identificationDocDesc) {
		return findByProperty(IDENTIFICATION_DOC_DESC, identificationDocDesc);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findAll() {
		log.debug("finding all IdentificationDoc instances");
		try {
			String queryString = "from IdentificationDoc";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IdentificationDoc merge(IdentificationDoc detachedInstance) {
		log.debug("merging IdentificationDoc instance");
		try {
			IdentificationDoc result = (IdentificationDoc) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IdentificationDoc instance) {
		log.debug("attaching dirty IdentificationDoc instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IdentificationDoc instance) {
		log.debug("attaching clean IdentificationDoc instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IdentificationDocDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IdentificationDocDAO) ctx.getBean("IdentificationDocDAO");
	}
	
    public List getIdentificationDocByCountryId(String countryId) {
		
		try {
			
			log.info("In here inside doc identifier:" + countryId);
			
			return getHibernateTemplate().find(" from IdentificationDoc as model where model.countryId="+countryId+"");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
    
    
    public IdentificationDoc getIdentificationDocByCountryIdNDesc(String countryId, String desc) {
		
		try {
			
			log.info("In here inside doc identifier:" + desc);
			
			return (IdentificationDoc)getHibernateTemplate().find(" from IdentificationDoc as model where model.countryId="+countryId + " and model.identificationDocDesc='" + desc.toUpperCase() + "'").get(0);
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
}