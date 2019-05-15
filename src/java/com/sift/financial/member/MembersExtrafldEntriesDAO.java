package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.*;

/**
 * A data access object (DAO) providing persistence and search support for
 * MembersExtrafldEntries entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.MembersExtrafldEntries
 * @author MyEclipse Persistence Tools
 */
public class MembersExtrafldEntriesDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MembersExtrafldEntriesDAO.class);
	// property constants
	public static final String KEY_ID = "keyId";
	public static final String TYPE_ID = "typeId";
	public static final String MEM_FIELD_NAME = "extraFieldName";
	public static final String MEM_FIELD_VALUE = "extraFieldValue";
        public static final String MEM_FIELD_ID = "extraFieldId";
        public static final String MEM_FIELDOPT_VALUE = "extraFieldoptionValue";
        public static final String MEM_FIELDOPT_ID = "extraFieldoptionId";
	public static final String SERIAL_POS = "serialPos";
	public static final String ACTIVE = "active";
	public static final String CREATED_BY = "createdBy";
	public static final String DELETED = "deleted";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(MembersExtrafldEntries transientInstance) {
		log.debug("saving MembersExtrafldEntries instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void save(MembersExtrafldEntries transientInstance, Session sess) {
		log.debug("saving MembersExtrafldEntries instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MembersExtrafldEntries persistentInstance) {
		log.debug("deleting MembersExtrafldEntries instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MembersExtrafldEntries findById(java.lang.Long id) {
		log.debug("getting MembersExtrafldEntries instance with id: " + id);
		try {
			MembersExtrafldEntries instance = (MembersExtrafldEntries) getHibernateTemplate()
					.get("com.sift.financial.member.MembersExtrafldEntries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MembersExtrafldEntries instance) {
		log.debug("finding MembersExtrafldEntries instance by example");
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
		log.debug("finding MembersExtrafldEntries instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MembersExtrafldEntries as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByKeyId(Object keyId) {
		return findByProperty(KEY_ID, keyId);
	}

	public List findByTypeId(Object typeId) {
		return findByProperty(TYPE_ID, typeId);
	}

	public List findByExtraFieldName(Object addrFieldName) {
		return findByProperty(MEM_FIELD_NAME, addrFieldName);
	}

	public List findByExtraFieldValue(Object addrFieldValue) {
		return findByProperty(MEM_FIELD_VALUE, addrFieldValue);
	}
        public List findByExtraFieldId(Object addrFieldName) {
		return findByProperty(MEM_FIELD_ID, addrFieldName);
	}

	public List findByExtraFieldoptionId(Object addrFieldValue) {
		return findByProperty(MEM_FIELDOPT_VALUE, addrFieldValue);
	}
        public List findByExtraFieldoptionValue(Object addrFieldValue) {
		return findByProperty(MEM_FIELDOPT_ID, addrFieldValue);
	}
	public List findBySerialPos(Object serialPos) {
		return findByProperty(SERIAL_POS, serialPos);
	}

	public List findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByDeleted(Object deleted) {
		return findByProperty(DELETED, deleted);
	}

	public List findByLastModifiedBy(Object lastModifiedBy) {
		return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
	}

	public List findAll() {
		log.debug("finding all MembersExtrafldEntries instances");
		try {
			String queryString = "from MembersExtrafldEntries";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MembersExtrafldEntries merge(MembersExtrafldEntries detachedInstance) {
		log.debug("merging MembersExtrafldEntries instance");
		try {
			MembersExtrafldEntries result = (MembersExtrafldEntries) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MembersExtrafldEntries instance) {
		log.debug("attaching dirty MembersExtrafldEntries instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MembersExtrafldEntries instance) {
		log.debug("attaching clean MembersExtrafldEntries instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MembersExtrafldEntriesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MembersExtrafldEntriesDAO) ctx.getBean("MembersExtrafldEntriesDAO");
	}
	
	
	public List<MembersExtrafldEntries> getMemberExtrafldObjectByType(String memberId, String typeId)
	{
		
		try {
			
			log.info("In here inside member identifier:" + memberId);
			
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from MembersExtrafldEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return getHibernateTemplate().find(" from MembersExtrafldEntries as model where model.typeId='" + typeId + "'  and model.keyId="+ memberId);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
        public List<MembersExtrafldEntries> getMemberExtrafldObjectByType(String memberId)
	{
		
		try {
			
			log.info("In here inside member identifier:" + memberId);
			
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from MembersExtrafldEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return getHibernateTemplate().find(" from MembersExtrafldEntries as model where model.keyId="+ memberId);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
	public void delete(MembersExtrafldEntries persistentInstance,Session sess) {
		log.debug("deleting MembersExtrafldEntries instance");
		try {
			sess.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	
	
	public boolean delete(String keyId, Session sess) {
		
		boolean returnVal = false;
		
		try {
			log.info("In here inside deleting keyId : " + keyId  + "::::::" );
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from MembersExtrafldEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			sess.delete(" from MembersExtrafldEntries as model where model.keyId="+ keyId);
			returnVal = true;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		
		return returnVal;
	}
        
        
        public MembersExtrafldEntries getMemberExtrafldObjectByTypeField(String memberId, String typeId, Integer addField)
	{
		
		try {
			
			log.info("In here inside MembersExtrafldEntries identifier:" + memberId);
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from MembersExtrafldEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return (MembersExtrafldEntries)getHibernateTemplate().find(" from MembersExtrafldEntries as model where model.typeId='" + typeId + "'  and model.keyId="+ memberId + " and model.extraFieldId=" +addField+ "").get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
	 public MembersExtrafldEntries getMemberExtrafldObjectByTypeField(String memberId, Integer addField)
	{
		
		try {
			
			log.info("In here inside MembersExtrafldEntries identifier:" + memberId);
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from MembersExtrafldEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return (MembersExtrafldEntries)getHibernateTemplate().find(" from MembersExtrafldEntries as model where model.keyId="+ memberId + " and model.extraFieldId=" +addField+ "").get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
}