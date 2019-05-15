package com.sift.financial.member;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CompStockTypeDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompStockTypeDetail
 * @author MyEclipse Persistence Tools
 */
public class CompStockTypeDetailDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompStockTypeDetailDAO.class);
	// property constants
	public static final String COMP_STOCK_PPTY_VAL = "compStockPptyVal";

	protected void initDao() {
		// do nothing
	}

	public void save(CompStockTypeDetail transientInstance) {
		log.debug("saving CompStockTypeDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	
	public void save(Session sess, CompStockTypeDetail transientInstance) {
		log.debug("saving CompStockTypeDetail instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompStockTypeDetail persistentInstance) {
		log.debug("deleting CompStockTypeDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompStockTypeDetail findById(java.lang.Integer id) {
		log.debug("getting CompStockTypeDetail instance with id: " + id);
		try {
			CompStockTypeDetail instance = (CompStockTypeDetail) getHibernateTemplate()
					.get("com.sift.financial.member.CompStockTypeDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompStockTypeDetail instance) {
		log.debug("finding CompStockTypeDetail instance by example");
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
		log.debug("finding CompStockTypeDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompStockTypeDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompStockPptyVal(Object compStockPptyVal) {
		return findByProperty(COMP_STOCK_PPTY_VAL, compStockPptyVal);
	}

	public List findAll() {
		log.debug("finding all CompStockTypeDetail instances");
		try {
			String queryString = "from CompStockTypeDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompStockTypeDetail merge(CompStockTypeDetail detachedInstance) {
		log.debug("merging CompStockTypeDetail instance");
		try {
			CompStockTypeDetail result = (CompStockTypeDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompStockTypeDetail instance) {
		log.debug("attaching dirty CompStockTypeDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompStockTypeDetail instance) {
		log.debug("attaching clean CompStockTypeDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompStockTypeDetailDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompStockTypeDetailDAO) ctx.getBean("CompStockTypeDetailDAO");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDefaultStockListByCompany(String companyId, String statusShort) {
			
			try {
				
				log.info("In here inside member identifier:" + companyId);
				
				return getHibernateTemplate().find("select model.compStockTypeDetailId, model.compStockType.compStockTypeId, model.compStockType.company.name, model.compStockType.compStockName from CompStockTypeDetail as model where model.compStockType.company.companyId="+ companyId + " and model.compStockType.status.statusShort='" + statusShort + "' and model.compStockProperty.");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	
	
	public List findByPPty (String propertyId, String StockId, String companyId)
	{
		try {
			
			return getHibernateTemplate().find("select model.compStockTypeDetailId, model.compStockProperty.stockPptyName, model.compStockType.compStockTypeId, model.compStockType.compStockName, model.compStockPptyVal from CompStockTypeDetail as model where model.compStockProperty.stockPptyId="+ propertyId + " and model.compStockType.compStockTypeId=" + StockId + " and model.compStockType.company.companyId=" + companyId);

		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	
	}
        
        
        public void delete(Integer compStockTypeId, Session sess) {
		log.debug("deleting CompStockTypeDetail instance");
                
		try {
			sess.delete(" from CompStockTypeDetail where compStockType.compStockTypeId =" + compStockTypeId);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
        
       public void delete(CompStockTypeDetail persistentInstance, Session sess) {
		log.debug("deleting CompStockTypeDetail instance");
		try {
			sess.delete(persistentInstance);
			log.info("delete successful");
		} catch (RuntimeException re) {
			log.info("delete failed", re);
			throw re;
		}
	}
}