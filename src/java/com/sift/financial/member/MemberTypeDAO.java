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
 * MemberType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.MemberType
 * @author MyEclipse Persistence Tools
 */
public class MemberTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MemberTypeDAO.class);
	// property constants
	public static final String MEMBER_TYPE_VAL = "memberTypeVal";
	public static final String MEMBER_TYPE_DESC = "memberTypeDesc";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberType transientInstance) {
		log.debug("saving MemberType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberType persistentInstance) {
		log.debug("deleting MemberType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberType findById(java.lang.Integer id) {
		log.debug("getting MemberType instance with id: " + id);
		try {
			MemberType instance = (MemberType) getHibernateTemplate().get(
					"com.sift.financial.member.MemberType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberType instance) {
		log.debug("finding MemberType instance by example");
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
		log.debug("finding MemberType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MemberType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberTypeVal(Object memberTypeVal) {
		return findByProperty(MEMBER_TYPE_VAL, memberTypeVal);
	}

	public List findByMemberTypeDesc(Object memberTypeDesc) {
		return findByProperty(MEMBER_TYPE_DESC, memberTypeDesc);
	}

	public List findAll() {
		log.debug("finding all MemberType instances");
		try {
			String queryString = "from MemberType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberType merge(MemberType detachedInstance) {
		log.debug("merging MemberType instance");
		try {
			MemberType result = (MemberType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberType instance) {
		log.debug("attaching dirty MemberType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberType instance) {
		log.debug("attaching clean MemberType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MemberTypeDAO) ctx.getBean("MemberTypeDAO");
	}
}