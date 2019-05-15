package com.sift.financial.member;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberExtrafld entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.MemberExtrafld
 * @author MyEclipse Persistence Tools
 */
public class MemberExtrafldDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(MemberExtrafldDAO.class);
	// property constants
	public static final String GROUPED = "grouped";
	public static final String FIELD_NAME = "description";
        public static final String BRANCHID = "branch";
	public static final String COMPANYID = "company";
        
	protected void initDao() {
		// do nothing
	}

	public void save(MemberExtrafld transientInstance) {
		log.debug("saving MemberExtrafld instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberExtrafld persistentInstance) {
		log.debug("deleting MemberExtrafld instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberExtrafld findById(java.lang.Integer id) {
		log.debug("getting MemberExtrafld instance with id: " + id);
		try {
			MemberExtrafld instance = (MemberExtrafld) getHibernateTemplate()
					.get("com.sift.financial.member.MemberExtrafld", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberExtrafld instance) {
		log.debug("finding MemberExtrafld instance by example");
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
		log.debug("finding MemberExtrafld instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberExtrafld as model where active = 'Y' and model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGrouped(Object Field) {
		return findByProperty(GROUPED, Field);
	}

	public List findByDescription(Object FieldName) {
		return findByProperty(FIELD_NAME, FieldName);
	}
        public List findByCompany(Object FieldIdx) {
		return findByProperty(COMPANYID, FieldIdx);
	}

	public List findByBranch(Object FieldName) {
		return findByProperty(BRANCHID, FieldName);
	}
	public List findAll() {
		log.debug("finding all MemberExtrafld instances");
		try {
			String queryString = "from MemberExtrafld";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberExtrafld merge(MemberExtrafld detachedInstance) {
		log.debug("merging MemberExtrafld instance");
		try {
			MemberExtrafld result = (MemberExtrafld) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberExtrafld instance) {
		log.debug("attaching dirty MemberExtrafld instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberExtrafld instance) {
		log.debug("attaching clean MemberExtrafld instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberExtrafldDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberExtrafldDAO) ctx.getBean("MemberExtrafldDAO");
	}
	
	
	/*
	 public List getCountryAddressByCountryId(String countryId) {
			
			try {
				
				log.info("In here inside Adress Filter identifier:" + countryId);
				
				return getHibernateTemplate().find("select id, addrFieldIdx, addrFieldName from MemberExtrafld as model where model.countries.id="+countryId +" order by addrFieldIdx asc ");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	
	*/
        /*
         public List getCountryAddressByCountryId(String countryId) {
			
			try {
				
				log.info("In here inside Adress Filter identifier:" + countryId);
				
				return getHibernateTemplate().find("select id, addrFieldIdx, addrFieldName from CountryAddressFilter as model where model.countries.id="+countryId +" order by addrFieldIdx asc ");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	*/
}