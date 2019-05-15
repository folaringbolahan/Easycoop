package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;
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
 * BatchExtrafldEntries entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchExtrafldEntries
 * @author MyEclipse Persistence Tools
 */
public class BatchExtrafldEntriesDAO extends HibernateDaoSupport implements BatchErrors {
	private static final Log log = LogFactory
			.getLog(BatchExtrafldEntriesDAO.class);
	// property constants
	public static final String ACTIVE = "active";
	public static final String EXT_FIELD_NAME = "extrFieldName";
	public static final String EXT_FIELD_VALUE = "extrFieldValue";
        public static final String EXT_FIELD_ID = "extrFieldId";
	public static final String EXT_FIELDOPT_VALUE = "extrFieldoptValue";
        public static final String EXT_FIELD_GROUPED = "extrFieldGrouped";
	public static final String CREATED_BY = "createdBy";
	public static final String DELETED = "deleted";
	public static final String KEY_ID = "keyId";
	public static final String SERIAL_POS = "serialPos";
	public static final String TYPE_ID = "typeId";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchExtrafldEntries transientInstance)
        {
		log.debug("saving BatchExtrafldEntries instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        public void save(BatchExtrafldEntries transientInstance, Session sess) 
        {
		log.debug("saving BatchExtrafldEntries instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        

	public void delete(BatchExtrafldEntries persistentInstance) {
		log.debug("deleting BatchExtrafldEntries instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchExtrafldEntries findById(java.lang.Integer id) {
		log.debug("getting BatchExtrafldEntries instance with id: " + id);
		try {
			BatchExtrafldEntries instance = (BatchExtrafldEntries) getHibernateTemplate()
					.get("com.sift.financial.member.BatchExtrafldEntries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchExtrafldEntries instance) {
		log.debug("finding BatchExtrafldEntries instance by example");
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
		log.debug("finding BatchExtrafldEntries instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BatchExtrafldEntries as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByExtrFieldName(Object addrFieldName) {
		return findByProperty(EXT_FIELD_NAME, addrFieldName);
	}

	public List findByExtrFieldValue(Object addrFieldValue) {
		return findByProperty(EXT_FIELD_VALUE, addrFieldValue);
	}
        public List findByExtrFieldId(Object addrFieldid) {
		return findByProperty(EXT_FIELD_ID, addrFieldid);
	}
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByKeyId(Object keyId) {
		return findByProperty(KEY_ID, keyId);
	}

	public List findBySerialPos(Object serialPos) {
		return findByProperty(SERIAL_POS, serialPos);
	}

	public List findByTypeId(Object typeId) {
		return findByProperty(TYPE_ID, typeId);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findAll() {
		log.debug("finding all BatchExtrafldEntries instances");
		try {
			String queryString = "from BatchExtrafldEntries";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchExtrafldEntries merge(BatchExtrafldEntries detachedInstance) {
		log.debug("merging BatchExtrafldEntries instance");
		try {
			BatchExtrafldEntries result = (BatchExtrafldEntries) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public BatchExtrafldEntries merge(BatchExtrafldEntries detachedInstance, Session sess) {
		log.debug("merging BatchExtrafldEntries instance");
		try {
			BatchExtrafldEntries result = (BatchExtrafldEntries)sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchExtrafldEntries instance) {
		log.debug("attaching dirty BatchExtrafldEntries instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchExtrafldEntries instance) {
		log.debug("attaching clean BatchExtrafldEntries instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchExtrafldEntriesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchExtrafldEntriesDAO) ctx.getBean("BatchExtrafldEntriesDAO");
	}
        
        
         public List getBatchExtrafldByStatus(String statShort, Integer fileUploadId, Integer companyId, Integer branchId)
        {
             Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchExtrafldEntries as model where model.status.statusShort='"+ statShort + "' and model.batchUploadFile.batchUploadFileId =:code  and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId).list();
                  log.info("Here retrieving rrecords in file with size " + list.size() );
                   return list;
                } catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
                finally
                {
                  sess.close();
                }
        }

    @Override
    public List<Object[]> getErrorList(String state, Integer fileUploadId, Integer companyId, Integer branchId) {
       List<Object[]> theList = null;
        Session sess = null;
	log.info("In here inside get BatchExtrafldEntries:" + state + " fileUploadId " + fileUploadId);
        try
        {
		sess = getSessionFactory().openSession();
		Query query = sess.createQuery("SELECT email1, extrFieldName, extrFieldValue, valError from BatchExtrafldEntries as model where model.status.statusShort='"+ state + "' and model.batchUploadFile.batchUploadFileId =:code and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId);
		theList = query.list();
        }
        catch(Exception ex)
        {
        
        }
        finally
        {
        sess.close();
        }
	return theList;    
    }
}