package com.sift.financial.member;

import com.sift.financial.ApprovaInterface;
import com.sift.financial.ReminderInterface;
import com.sift.financial.services.MailInterface;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * BatchUploadFile entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchUploadFile
 * @author MyEclipse Persistence Tools
 */
public class BatchUploadFileDAO extends HibernateDaoSupport implements ApprovaInterface, MailInterface {
	private static final Log log = LogFactory.getLog(BatchUploadFileDAO.class);
	// property constants
	public static final String BATCH_UPLOAD_FILE_NAME = "batchUploadFileName";
	public static final String BATCH_RECORD_COUNT = "batchRecordCount";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchUploadFile transientInstance) {
		log.debug("saving BatchUploadFile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        public void save(BatchUploadFile transientInstance, Session sess) {
		log.debug("saving BatchUploadFile instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BatchUploadFile persistentInstance) {
		log.debug("deleting BatchUploadFile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchUploadFile findById(java.lang.Integer id) {
		log.debug("getting BatchUploadFile instance with id: " + id);
		try {
			BatchUploadFile instance = (BatchUploadFile) getHibernateTemplate()
					.get("com.sift.financial.member.BatchUploadFile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchUploadFile instance) {
		log.debug("finding BatchUploadFile instance by example");
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
		log.debug("finding BatchUploadFile instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BatchUploadFile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBatchUploadFileName(Object batchUploadFileName) {
		return findByProperty(BATCH_UPLOAD_FILE_NAME, batchUploadFileName);
	}

	public List findByBatchRecordCount(Object batchRecordCount) {
		return findByProperty(BATCH_RECORD_COUNT, batchRecordCount);
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
		log.debug("finding all BatchUploadFile instances");
		try {
			String queryString = "from BatchUploadFile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchUploadFile merge(BatchUploadFile detachedInstance) {
		log.debug("merging BatchUploadFile instance");
		try {
			BatchUploadFile result = (BatchUploadFile) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public BatchUploadFile merge(BatchUploadFile detachedInstance, Session sess) {
		log.debug("merging BatchUploadFile instance");
                log.info("Here inside writeObjects....................merging BatchFileUpload = " + detachedInstance.getBatchUploadFileId());
		try {
			//BatchUploadFile result = (BatchUploadFile) getHibernateTemplate()
			BatchUploadFile result = (BatchUploadFile)sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchUploadFile instance) {
		log.debug("attaching dirty BatchUploadFile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchUploadFile instance) {
		log.debug("attaching clean BatchUploadFile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchUploadFileDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchUploadFileDAO) ctx.getBean("BatchUploadFileDAO");
	}
        
        public List getByStatus(String statusShort) {
            
		try {
			return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
        public List getByReferenceStatus(String statusShort) {
            
		try {
			return getHibernateTemplate().find(" from BatchUploadFile as model where model.batchUploadReference.status.statusShort='"+ statusShort + "'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
        public List getByReference(String reference) {
            
		try {
	           return getHibernateTemplate().find(" from BatchUploadFile as model where model.batchUploadReference.batchUploadReferenceId='"+ reference + "'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
         public List getByReference(String reference, String statusShort) {
            
		try {
	           return getHibernateTemplate().find(" from BatchUploadFile as model where model.batchUploadReference.batchUploadReferenceId='"+ reference + "' and model.status.statusShort='" + statusShort +"'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
         
         
         public boolean closeReferenceProcessing(BatchUploadFile crBatch, String event)
                 
                 {
                 
                 
                 
                 return false;
                 }
         
          public List getFilesByStatus(String statusShort,Integer companyId, Integer branchId) {
              
                Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.companyId=" + companyId + " and model.branchId=" + branchId).list();
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
          
           public Object getFileByIdComp(Integer id,Integer companyId, Integer branchId) {
              
                Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchUploadFile as model where model.batchUploadFileId=:batchId and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("batchId", id).list();
                   return list.get(0);
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
    public boolean approveFirstLevel(String initiator, String modifier, String approval) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean approveSecondLevel(String initiator, String modifier, String approval, String nextApproval) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> getMailRecipient(Object obj, String action) {
        
        Map<String, Object>  returVal = new HashMap<String, Object>();
        
       
        BatchUploadFile objCasted = (BatchUploadFile)obj;
        
        if(action.equals(ReminderInterface.CREATOR))
        {
            returVal.put(ReminderInterface.INITIATOR, objCasted.getCreatedBy());
        }
        else  if(action.equals(ReminderInterface.MODIFIER))
        {
            returVal.put(ReminderInterface.INITIATOR, objCasted.getModifiedBy());
        }
        else  if(action.equals(ReminderInterface.MODIFIER))
        {
           returVal.put(ReminderInterface.INITIATOR, objCasted.getApprovedBy());
        }
        
        return returVal;
    }
       
    
}