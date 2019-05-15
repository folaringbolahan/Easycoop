package com.sift.financial.member;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CountryAddressFilter entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CountryAddressFilter
 * @author MyEclipse Persistence Tools
 */
public class CountryAddressFilterDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CountryAddressFilterDAO.class);
	// property constants
	public static final String ADDR_FIELD_IDX = "addrFieldIdx";
	public static final String ADDR_FIELD_NAME = "addrFieldName";

	protected void initDao() {
		// do nothing
	}

	public void save(CountryAddressFilter transientInstance) {
		log.debug("saving CountryAddressFilter instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CountryAddressFilter persistentInstance) {
		log.debug("deleting CountryAddressFilter instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CountryAddressFilter findById(java.lang.Integer id) {
		log.debug("getting CountryAddressFilter instance with id: " + id);
		try {
			CountryAddressFilter instance = (CountryAddressFilter) getHibernateTemplate()
					.get("com.sift.financial.member.CountryAddressFilter", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CountryAddressFilter instance) {
		log.debug("finding CountryAddressFilter instance by example");
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
		log.debug("finding CountryAddressFilter instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CountryAddressFilter as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAddrFieldIdx(Object addrFieldIdx) {
		return findByProperty(ADDR_FIELD_IDX, addrFieldIdx);
	}

	public List findByAddrFieldName(Object addrFieldName) {
		return findByProperty(ADDR_FIELD_NAME, addrFieldName);
	}

	public List findAll() {
		log.debug("finding all CountryAddressFilter instances");
		try {
			String queryString = "from CountryAddressFilter";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CountryAddressFilter merge(CountryAddressFilter detachedInstance) {
		log.debug("merging CountryAddressFilter instance");
		try {
			CountryAddressFilter result = (CountryAddressFilter) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CountryAddressFilter instance) {
		log.debug("attaching dirty CountryAddressFilter instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CountryAddressFilter instance) {
		log.debug("attaching clean CountryAddressFilter instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CountryAddressFilterDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CountryAddressFilterDAO) ctx.getBean("CountryAddressFilterDAO");
	}
	
	
	
	 public List getCountryAddressByCountryId(String countryId) {
			
			try {
				
				log.info("In here inside Adress Filter identifier:" + countryId);
				
				return getHibernateTemplate().find("select id, addrFieldIdx, addrFieldName from CountryAddressFilter as model where model.countries.id="+countryId +" order by addrFieldIdx asc ");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	
	
	
}