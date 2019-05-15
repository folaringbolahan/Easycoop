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
 * AddressEntries entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.AddressEntries
 * @author MyEclipse Persistence Tools
 */
public class AddressEntriesDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AddressEntriesDAO.class);
	// property constants
	public static final String KEY_ID = "keyId";
	public static final String TYPE_ID = "typeId";
	public static final String ADDR_FIELD_NAME = "addrFieldName";
	public static final String ADDR_FIELD_VALUE = "addrFieldValue";
	public static final String SERIAL_POS = "serialPos";
	public static final String ACTIVE = "active";
	public static final String CREATED_BY = "createdBy";
	public static final String DELETED = "deleted";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(AddressEntries transientInstance) {
		log.debug("saving AddressEntries instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void save(AddressEntries transientInstance, Session sess) {
		log.debug("saving AddressEntries instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AddressEntries persistentInstance) {
		log.debug("deleting AddressEntries instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AddressEntries findById(java.lang.Long id) {
		log.debug("getting AddressEntries instance with id: " + id);
		try {
			AddressEntries instance = (AddressEntries) getHibernateTemplate()
					.get("com.sift.financial.member.AddressEntries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AddressEntries instance) {
		log.debug("finding AddressEntries instance by example");
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
		log.debug("finding AddressEntries instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AddressEntries as model where model."
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

	public List findByAddrFieldName(Object addrFieldName) {
		return findByProperty(ADDR_FIELD_NAME, addrFieldName);
	}

	public List findByAddrFieldValue(Object addrFieldValue) {
		return findByProperty(ADDR_FIELD_VALUE, addrFieldValue);
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
		log.debug("finding all AddressEntries instances");
		try {
			String queryString = "from AddressEntries";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AddressEntries merge(AddressEntries detachedInstance) {
		log.debug("merging AddressEntries instance");
		try {
			AddressEntries result = (AddressEntries) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AddressEntries instance) {
		log.debug("attaching dirty AddressEntries instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AddressEntries instance) {
		log.debug("attaching clean AddressEntries instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AddressEntriesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AddressEntriesDAO) ctx.getBean("AddressEntriesDAO");
	}
	
	
	public List<AddressEntries> getMemberAddressObjectByType(String memberId, String typeId)
	{
		
		try {
			
			log.info("In here inside member identifier:" + memberId);
			
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return getHibernateTemplate().find(" from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	

	public void delete(AddressEntries persistentInstance,Session sess) {
		log.debug("deleting AddressEntries instance");
		try {
			sess.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	
	
	public boolean delete(String keyId, String typeId, Session sess) {
		
		boolean returnVal = false;
		
		try {
			log.info("In here inside deleting keyId and TypeId: " + keyId  + "::::::" + typeId);
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			sess.delete(" from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ keyId);
			returnVal = true;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		
		return returnVal;
	}
        
        
        public AddressEntries getMemberAddressObjectByTypeField(String memberId, String typeId, String addField)
	{
		
		try {
			
			log.info("In here inside AddressEntries identifier:" + memberId);
			//return getHibernateTemplate().find("select model.id, model.keyId, model.typeId, model.addrFieldName, model.addrFieldValue, model.serialPos from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId);
			
			return (AddressEntries)getHibernateTemplate().find(" from AddressEntries as model where model.typeId=" + typeId + "  and model.keyId="+ memberId + " and model.addrFieldName='" +addField+ "'").get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
	
}