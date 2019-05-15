package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * DividendNumberIdentifier entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.sift.financial.member.DividendNumberIdentifier
 * @author MyEclipse Persistence Tools
 */
public class DividendNumberIdentifierDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(DividendNumberIdentifierDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String LAST_DIVIDEND_NUMBER = "lastDividendNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(DividendNumberIdentifier transientInstance) {
		log.debug("saving DividendNumberIdentifier instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DividendNumberIdentifier persistentInstance) {
		log.debug("deleting DividendNumberIdentifier instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DividendNumberIdentifier findById(java.lang.Integer id) {
		log.debug("getting DividendNumberIdentifier instance with id: " + id);
		try {
			DividendNumberIdentifier instance = (DividendNumberIdentifier) getHibernateTemplate()
					.get("com.sift.financial.member.DividendNumberIdentifier",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DividendNumberIdentifier instance) {
		log.debug("finding DividendNumberIdentifier instance by example");
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
		log.debug("finding DividendNumberIdentifier instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DividendNumberIdentifier as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findByLastDividendNumber(Object lastDividendNumber) {
		return findByProperty(LAST_DIVIDEND_NUMBER, lastDividendNumber);
	}

	public List findAll() {
		log.debug("finding all DividendNumberIdentifier instances");
		try {
			String queryString = "from DividendNumberIdentifier";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DividendNumberIdentifier merge(
			DividendNumberIdentifier detachedInstance) {
		log.debug("merging DividendNumberIdentifier instance");
		try {
			DividendNumberIdentifier result = (DividendNumberIdentifier) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DividendNumberIdentifier instance) {
		log.debug("attaching dirty DividendNumberIdentifier instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DividendNumberIdentifier instance) {
		log.debug("attaching clean DividendNumberIdentifier instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DividendNumberIdentifierDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DividendNumberIdentifierDAO) ctx
				.getBean("DividendNumberIdentifierDAO");
	}
	
	
	public String getNextMemberIdentity (String companyId, String branchId, Session sess) 
	{
		
		String theCode = null;
		
		/*try {*/
		
		log.info("In here inside get Member identifier:" + companyId);
		
		Query query = sess.createSQLQuery("SELECT COMPANY_ID, BRANCH_ID, LPAD(last_dividend_number + 1, 4,'0000') as NEXT_CODE from Dividend_Number_Identifier s where s.company_id =:compCode and s.branch_id=:branchCode")
		  .setParameter("compCode", companyId).setParameter("branchCode", companyId);
		
		List theList = query.list();
		
		if(theList !=null && !theList.isEmpty())
		{
			Object[] theArray = (Object[])theList.get(0);
			
			theCode = (String)theArray[2];
		}
		else
		{
			theCode=StockInterface.STARTDIVIDENTITIER;
		}
		
		return theCode;
		
	/*} catch (RuntimeException re) {
		log.error("get failed", re);
		return null;
	}*/
	
	}
	
	public boolean update(Object transientInstance, Session sess) {
		log.debug("saving Divinumber  instance");
		try {
			
			DividendNumberIdentifier ObjectInst=(DividendNumberIdentifier)transientInstance;
			sess.merge(ObjectInst);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			//throw re;
			return false;
		}
	}
	
	
	public void save(DividendNumberIdentifier transientInstance, Session sess) {
		log.debug("saving DividendNumberIdentifier instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
}