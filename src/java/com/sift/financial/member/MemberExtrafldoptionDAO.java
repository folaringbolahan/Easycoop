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
public class MemberExtrafldoptionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(MemberExtrafldoptionDAO.class);
	// property constants
	public static final String GRP_FIELD_IDX = "groupid";
	public static final String FIELD_NAME = "fieldOption";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberExtrafldoption transientInstance) {
		log.debug("saving MemberExtrafldoption instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberExtrafldoption persistentInstance) {
		log.debug("deleting MemberExtrafldoption instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberExtrafldoption findById(java.lang.Integer id) {
		log.debug("getting MemberExtrafldoption instance with id: " + id);
		try {
			MemberExtrafldoption instance = (MemberExtrafldoption) getHibernateTemplate()
					.get("com.sift.financial.member.MemberExtrafldoption", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberExtrafldoption instance) {
		log.debug("finding MemberExtrafldoption instance by example");
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
		log.debug("finding MemberExtrafldoption instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberExtrafldoption as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGroupId(Object grpFieldIdx) {
		return findByProperty(GRP_FIELD_IDX, grpFieldIdx);
	}

	public List findByFieldName(Object FieldName) {
		return findByProperty(FIELD_NAME, FieldName);
	}

	public List findAll() {
		log.debug("finding all MemberExtrafldoption instances");
		try {
			String queryString = "from MemberExtrafldoption";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberExtrafldoption merge(MemberExtrafldoption detachedInstance) {
		log.debug("merging MemberExtrafldoption instance");
		try {
			MemberExtrafldoption result = (MemberExtrafldoption) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberExtrafldoption instance) {
		log.debug("attaching dirty MemberExtrafldoption instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberExtrafldoption instance) {
		log.debug("attaching clean MemberExtrafldoption instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberExtrafldoptionDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberExtrafldoptionDAO) ctx.getBean("MemberExtrafldoptionDAO");
	}
	
	
	public List getExtrafldByBranch(String branchId)
	{
		
		try {
			
			log.info("In here inside extrafldss identifier:" + branchId);
			
			return getHibernateTemplate().find("select id, fieldOption,model.memberextrafields.id from MemberExtrafldoption as model where model.memberextrafields.branch="+branchId +" and model.memberextrafields.grouped='Y'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
        public List getExtrafldByCompany(String companyId)
	{
		
		try {
			
			log.info("In here inside extrafldss identifier:" + companyId);
			
			return getHibernateTemplate().find("select id, fieldOption,model.memberextrafields.id from MemberExtrafldoption as model where model.memberextrafields.company="+companyId +" and model.memberextrafields.grouped='Y'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
}