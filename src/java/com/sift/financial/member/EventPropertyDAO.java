package com.sift.financial.member;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for EventProperty entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sift.financial.member.EventProperty
  * @author MyEclipse Persistence Tools 
 */
public class EventPropertyDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(EventPropertyDAO.class);
		//property constants
	public static final String EVENT_PPTY_NAME_DESC = "eventPptyNameDesc";
	public static final String EVENT_PPTY_SHORT = "eventPptyShort";
	public static final String EVENT_PPTY_VALUE = "eventPptyValue";
	public static final String DEL_VAL = "delVal";



	protected void initDao() {
		//do nothing
	}
    
    public void save(EventProperty transientInstance) {
        log.debug("saving EventProperty instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(EventProperty persistentInstance) {
        log.debug("deleting EventProperty instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public EventProperty findById( java.lang.Integer id) {
        log.debug("getting EventProperty instance with id: " + id);
        try {
            EventProperty instance = (EventProperty) getHibernateTemplate()
                    .get("com.sift.financial.member.EventProperty", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(EventProperty instance) {
        log.debug("finding EventProperty instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding EventProperty instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from EventProperty as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByEventPptyNameDesc(Object eventPptyNameDesc
	) {
		return findByProperty(EVENT_PPTY_NAME_DESC, eventPptyNameDesc
		);
	}
	
	public List findByEventPptyShort(Object eventPptyShort
	) {
		return findByProperty(EVENT_PPTY_SHORT, eventPptyShort
		);
	}
	
	public List findByEventPptyValue(Object eventPptyValue
	) {
		return findByProperty(EVENT_PPTY_VALUE, eventPptyValue
		);
	}
	
	public List findByDelVal(Object delVal
	) {
		return findByProperty(DEL_VAL, delVal
		);
	}
	

	public List findAll() {
		log.debug("finding all EventProperty instances");
		try {
			String queryString = "from EventProperty";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public EventProperty merge(EventProperty detachedInstance) {
        log.debug("merging EventProperty instance");
        try {
            EventProperty result = (EventProperty) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(EventProperty instance) {
        log.debug("attaching dirty EventProperty instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(EventProperty instance) {
        log.debug("attaching clean EventProperty instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static EventPropertyDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (EventPropertyDAO) ctx.getBean("EventPropertyDAO");
	}
}