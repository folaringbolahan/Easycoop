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
 * Branch entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Branch
 * @author MyEclipse Persistence Tools
 */
public class BranchDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BranchDAO.class);
	// property constants
	public static final String BRANCH_NAME = "branchName";
	public static final String DEL_FLG = "delFlg";

	protected void initDao() {
		// do nothing
	}

	public void save(Branch transientInstance) {
		log.debug("saving Branch instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Branch persistentInstance) {
		log.debug("deleting Branch instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Branch findById(java.lang.Integer id) {
		log.debug("getting Branch instance with id: " + id);
		try {
			Branch instance = (Branch) getHibernateTemplate().get(
					"com.sift.financial.member.Branch", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Branch instance) {
		log.debug("finding Branch instance by example");
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
		log.debug("finding Branch instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Branch as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBranchName(Object branchName) {
		return findByProperty(BRANCH_NAME, branchName);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findAll() {
		log.debug("finding all Branch instances");
		try {
			String queryString = "from Branch";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Branch merge(Branch detachedInstance) {
		log.debug("merging Branch instance");
		try {
			Branch result = (Branch) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Branch instance) {
		log.debug("attaching dirty Branch instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Branch instance) {
		log.debug("attaching clean Branch instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BranchDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BranchDAO) ctx.getBean("BranchDAO");
	}
	
	
	
	 public List getBrachesByCompanyId(String companyId) {
			
			try {
				
				log.info("In here inside branch identifier:" + companyId);
				
				return getHibernateTemplate().find(" from Branch as model where model.company.Id="+companyId+"");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	 
	 
	 public Object[] get(String branchId) {
		 
		 List theObject = null;
		 
		 Object[] returnVal= null;
			
			try {
				
				log.info("In here inside branch identifier:" + branchId);
				
				theObject = getHibernateTemplate().find("select branchCode,branchName from Branch as model where model.Id="+branchId+"");
				
				if (theObject !=null)
				{
					returnVal = ( Object[])theObject.get(0);
				}
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
			
		
			return returnVal;
		}
}