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
 * BatchStock entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchStock
 * @author MyEclipse Persistence Tools
 */
public class BatchStockDAO extends HibernateDaoSupport implements BatchErrors {
	private static final Log log = LogFactory.getLog(BatchStockDAO.class);
	// property constants
	public static final String STOCK_SHORT = "stockShort";
	public static final String STOCK_VALUE = "stockValue";
	public static final String EMAIL1 = "email1";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchStock transientInstance) {
		log.debug("saving BatchStock instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        
        public void save(BatchStock transientInstance, Session sess) {
		log.debug("saving BatchStock instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
                    re.printStackTrace();
			log.error("save failed", re);
			throw re;
		}
	}
        

	public void delete(BatchStock persistentInstance) {
		log.debug("deleting BatchStock instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchStock findById(java.lang.Integer id) {
		log.debug("getting BatchStock instance with id: " + id);
		try {
			BatchStock instance = (BatchStock) getHibernateTemplate().get(
					"com.sift.financial.member.BatchStock", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchStock instance) {
		log.debug("finding BatchStock instance by example");
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
		log.debug("finding BatchStock instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BatchStock as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStockShort(Object stockShort) {
		return findByProperty(STOCK_SHORT, stockShort);
	}

	public List findByStockValue(Object stockValue) {
		return findByProperty(STOCK_VALUE, stockValue);
	}

	public List findByEmail1(Object email1) {
		return findByProperty(EMAIL1, email1);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}

	public List findAll() {
		log.debug("finding all BatchStock instances");
		try {
			String queryString = "from BatchStock";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchStock merge(BatchStock detachedInstance) {
		log.debug("merging BatchStock instance");
		try {
			BatchStock result = (BatchStock) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public BatchStock merge(BatchStock detachedInstance, Session sess) {
		log.debug("merging BatchStock instance");
		try {
			BatchStock result = (BatchStock) sess.merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchStock instance) {
		log.debug("attaching dirty BatchStock instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchStock instance) {
		log.debug("attaching clean BatchStock instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchStockDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BatchStockDAO) ctx.getBean("BatchStockDAO");
	}
        
        
        public List getBatchStockByStatus(String statShort, Integer fileUploadId, Integer companyId, Integer branchId)
        {
        
             Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchStock as model where model.status.statusShort='"+ statShort + "' and model.batchUploadFile.batchUploadFileId =:code  and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId).list();
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
	log.info("In here inside get BatchStock status:" + state + " fileUploadId " + fileUploadId);
        try
        {
		sess = getSessionFactory().openSession();
		Query query = sess.createQuery("SELECT email1, stockShort, stockValue, valError from BatchStock as model where model.status.statusShort='"+ state + "' and model.batchUploadFile.batchUploadFileId =:code and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId);
		theList = query.list();
        }
        catch(Exception ex)
        {
        
        }
        finally
        {
        sess.close();
        }
	return theList;    }
}