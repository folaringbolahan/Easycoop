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
 * Religion entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Religion
 * @author MyEclipse Persistence Tools
 */
public class ReligionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ReligionDAO.class);
	// property constants
	public static final String RELIGION_NAME = "religionName";
	public static final String RELIGION_DESC = "religionDesc";
	public static final String DEL_FLG = "delFlg";

	protected void initDao() {
		// do nothing
	}

	public void save(Religion transientInstance) {
		log.debug("saving Religion instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Religion persistentInstance) {
		log.debug("deleting Religion instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Religion findById(java.lang.Integer id) {
		log.debug("getting Religion instance with id: " + id);
		try {
			Religion instance = (Religion) getHibernateTemplate().get(
					"com.sift.financial.member.Religion", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Religion instance) {
		log.debug("finding Religion instance by example");
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
		log.debug("finding Religion instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Religion as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReligionName(Object religionName) {
		return findByProperty(RELIGION_NAME, religionName);
	}

	public List findByReligionDesc(Object religionDesc) {
		return findByProperty(RELIGION_DESC, religionDesc);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findAll() {
		log.debug("finding all Religion instances");
		try {
			String queryString = "from Religion";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Religion merge(Religion detachedInstance) {
		log.debug("merging Religion instance");
		try {
			Religion result = (Religion) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Religion instance) {
		log.debug("attaching dirty Religion instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Religion instance) {
		log.debug("attaching clean Religion instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReligionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReligionDAO) ctx.getBean("ReligionDAO");
	}
	
	
	 public List getReligionByCountryId(String countryId) {
			
			try {
				
				log.info("In here inside doc identifier:" + countryId);
				
				return getHibernateTemplate().find(" from Religion as model where model.countries.id="+countryId+"");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
         
         
         
          public Religion getReligionByCountryIdNDesc(String countryId, String desc) {
			
			try {
			        log.info("desc.toUpperCase()" +  desc.toUpperCase());
				return (Religion)getHibernateTemplate().find(" from Religion as model where model.countries.id=" + countryId + " and model.religionDesc='" + desc.toUpperCase() + "'").get(0);
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
}