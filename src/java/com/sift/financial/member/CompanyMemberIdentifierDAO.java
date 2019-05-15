package com.sift.financial.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



/**
 * A data access object (DAO) providing persistence and search support for
 * CompanyMemberIdentifier entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompanyMemberIdentifier
 * @author MyEclipse Persistence Tools
 */
public class CompanyMemberIdentifierDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(CompanyMemberIdentifierDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String LAST_MEMBER_CODE = "lastMemberCode";

	protected void initDao() {
		// do nothing
	}

	public void save(CompanyMemberIdentifier transientInstance) {
		log.debug("saving CompanyMemberIdentifier instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CompanyMemberIdentifier persistentInstance) {
		log.debug("deleting CompanyMemberIdentifier instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanyMemberIdentifier findById(java.lang.Integer id) {
		log.debug("getting CompanyMemberIdentifier instance with id: " + id);
		try {
			CompanyMemberIdentifier instance = (CompanyMemberIdentifier) getHibernateTemplate()
					.get("com.sift.financial.member.CompanyMemberIdentifier",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CompanyMemberIdentifier instance) {
		log.debug("finding CompanyMemberIdentifier instance by example");
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
		log.debug("finding CompanyMemberIdentifier instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompanyMemberIdentifier as model where model."
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

	public List findByLastMemberCode(Object lastMemberCode) {
		return findByProperty(LAST_MEMBER_CODE, lastMemberCode);
	}

	public List findAll() {
		log.debug("finding all CompanyMemberIdentifier instances");
		try {
			String queryString = "from CompanyMemberIdentifier";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompanyMemberIdentifier merge(
			CompanyMemberIdentifier detachedInstance) {
		log.debug("merging CompanyMemberIdentifier instance");
		try {
			CompanyMemberIdentifier result = (CompanyMemberIdentifier) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanyMemberIdentifier instance) {
		log.debug("attaching dirty CompanyMemberIdentifier instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanyMemberIdentifier instance) {
		log.debug("attaching clean CompanyMemberIdentifier instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompanyMemberIdentifierDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompanyMemberIdentifierDAO) ctx
				.getBean("CompanyMemberIdentifierDAO");
	}
	
	
	
	public Map<String,String> getNextMemberIdentity (String companyId, Session sess) 
	{
		
		Map<String,String> theCode = new HashMap<String,String>();
		
		/*try {*/
		
		log.info("In here inside get Member identifier:" + companyId);
		
		Query query = sess.createSQLQuery("SELECT COMPANY_ID, LPAD(LAST_MEMBER_CODE + 1, 6 ,'00000') as NEXT_CODE, COMPANY_MEMBER_ID from Company_Member_Identifier s where s.company_id =:compCode")
		  .setParameter("compCode", companyId);
		
		List theList = query.list();
                
		
		if(theList !=null && !theList.isEmpty())
		{
			Object[] theArray = (Object[])theList.get(0);
			
			theCode.put("MEMCODE", (String)theArray[1]);
                        theCode.put("ID", ((Integer)theArray[2]).toString());
		}
		else
		{
			theCode.put("MEMCODE", StockInterface.STARTIDENTITIER);
                        theCode.put("ID", "0");
		}
		log.info("All Ok here with code" + theCode.get("MEMCODE"));
		return theCode;
		
	/*} catch (RuntimeException re) {
		log.error("get failed", re);
		return null;
	}*/
	
	}
        
        
	public boolean update(Object transientInstance, Session sess) {
		log.debug("saving Company member instance");
		try {
			
			CompanyMemberIdentifier ObjectInst=(CompanyMemberIdentifier)transientInstance;
			sess.merge(ObjectInst);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			//throw re;
			return false;
		}
	}
	
	
	public void save(CompanyMemberIdentifier transientInstance, Session sess) {
		log.debug("saving CompanyMemberIdentifier instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	
	
	
}