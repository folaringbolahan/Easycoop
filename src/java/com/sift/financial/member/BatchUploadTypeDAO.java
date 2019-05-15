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
 * BatchUploadType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchUploadType
 * @author MyEclipse Persistence Tools
 */
public class BatchUploadTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BatchUploadTypeDAO.class);
	// property constants
	public static final String UPLOAD_TYPE_NAME = "uploadTypeName";
	public static final String DEL_FLG = "delFlg";
	public static final String UPLOAD_TYPE_SHORT = "uploadTypeShort";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchUploadType transientInstance) {
		log.debug("saving BatchUploadType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BatchUploadType persistentInstance) {
		log.debug("deleting BatchUploadType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchUploadType findById(java.lang.Integer id) {
		log.debug("getting BatchUploadType instance with id: " + id);
		try {
			BatchUploadType instance = (BatchUploadType) getHibernateTemplate()
					.get("com.sift.financial.member.BatchUploadType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchUploadType instance) {
		log.debug("finding BatchUploadType instance by example");
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
		log.debug("finding BatchUploadType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BatchUploadType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUploadTypeName(Object uploadTypeName) {
		return findByProperty(UPLOAD_TYPE_NAME, uploadTypeName);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByUploadTypeShort(Object uploadTypeShort) {
		return findByProperty(UPLOAD_TYPE_SHORT, uploadTypeShort);
	}

	public List findAll() {
		log.debug("finding all BatchUploadType instances");
		try {
			String queryString = "from BatchUploadType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchUploadType merge(BatchUploadType detachedInstance) {
		log.debug("merging BatchUploadType instance");
		try {
			BatchUploadType result = (BatchUploadType) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchUploadType instance) {
		log.debug("attaching dirty BatchUploadType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchUploadType instance) {
		log.debug("attaching clean BatchUploadType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchUploadTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchUploadTypeDAO) ctx.getBean("BatchUploadTypeDAO");
	}
}