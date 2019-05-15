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
 * BatchAddressEntries entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchAddressEntries
 * @author MyEclipse Persistence Tools
 */
public class BatchAddressEntriesDAO extends HibernateDaoSupport implements BatchErrors {
	private static final Log log = LogFactory
			.getLog(BatchAddressEntriesDAO.class);
	// property constants
	public static final String ACTIVE = "active";
	public static final String ADDR_FIELD_NAME = "addrFieldName";
	public static final String ADDR_FIELD_VALUE = "addrFieldValue";
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

	public void save(BatchAddressEntries transientInstance)
        {
		log.debug("saving BatchAddressEntries instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        public void save(BatchAddressEntries transientInstance, Session sess) 
        {
		log.debug("saving BatchAddressEntries instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        

	public void delete(BatchAddressEntries persistentInstance) {
		log.debug("deleting BatchAddressEntries instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchAddressEntries findById(java.lang.Integer id) {
		log.debug("getting BatchAddressEntries instance with id: " + id);
		try {
			BatchAddressEntries instance = (BatchAddressEntries) getHibernateTemplate()
					.get("com.sift.financial.member.BatchAddressEntries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchAddressEntries instance) {
		log.debug("finding BatchAddressEntries instance by example");
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
		log.debug("finding BatchAddressEntries instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BatchAddressEntries as model where model."
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

	public List findByAddrFieldName(Object addrFieldName) {
		return findByProperty(ADDR_FIELD_NAME, addrFieldName);
	}

	public List findByAddrFieldValue(Object addrFieldValue) {
		return findByProperty(ADDR_FIELD_VALUE, addrFieldValue);
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
		log.debug("finding all BatchAddressEntries instances");
		try {
			String queryString = "from BatchAddressEntries";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchAddressEntries merge(BatchAddressEntries detachedInstance) {
		log.debug("merging BatchAddressEntries instance");
		try {
			BatchAddressEntries result = (BatchAddressEntries) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public BatchAddressEntries merge(BatchAddressEntries detachedInstance, Session sess) {
		log.debug("merging BatchAddressEntries instance");
		try {
			BatchAddressEntries result = (BatchAddressEntries)sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchAddressEntries instance) {
		log.debug("attaching dirty BatchAddressEntries instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchAddressEntries instance) {
		log.debug("attaching clean BatchAddressEntries instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchAddressEntriesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchAddressEntriesDAO) ctx.getBean("BatchAddressEntriesDAO");
	}
        
        
         public List getBatchAddressByStatus(String statShort, Integer fileUploadId, Integer companyId, Integer branchId)
        {
             Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchAddressEntries as model where model.status.statusShort='"+ statShort + "' and model.batchUploadFile.batchUploadFileId =:code  and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId).list();
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
	log.info("In here inside get BatchAddressEntries:" + state + " fileUploadId " + fileUploadId);
        try
        {
		sess = getSessionFactory().openSession();
		Query query = sess.createQuery("SELECT email1, addrFieldName, addrFieldValue, valError from BatchAddressEntries as model where model.status.statusShort='"+ state + "' and model.batchUploadFile.batchUploadFileId =:code and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId);
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