package com.sift.financial.member;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for RecipientType entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sift.financial.member.RecipientType
  * @author MyEclipse Persistence Tools 
 */
public class RecipientTypeDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(RecipientTypeDAO.class);
		//property constants
	public static final String RECIPIENT_TYPE = "recipientType";



	protected void initDao() {
		//do nothing
	}
    
    public void save(RecipientType transientInstance) {
        log.debug("saving RecipientType instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(RecipientType persistentInstance) {
        log.debug("deleting RecipientType instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public RecipientType findById( java.lang.Integer id) {
        log.debug("getting RecipientType instance with id: " + id);
        try {
            RecipientType instance = (RecipientType) getHibernateTemplate()
                    .get("com.sift.financial.member.RecipientType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(RecipientType instance) {
        log.debug("finding RecipientType instance by example");
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
      log.debug("finding RecipientType instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from RecipientType as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByRecipientType(Object recipientType
	) {
		return findByProperty(RECIPIENT_TYPE, recipientType
		);
	}
	

	public List findAll() {
		log.debug("finding all RecipientType instances");
		try {
			String queryString = "from RecipientType";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public RecipientType merge(RecipientType detachedInstance) {
        log.debug("merging RecipientType instance");
        try {
            RecipientType result = (RecipientType) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(RecipientType instance) {
        log.debug("attaching dirty RecipientType instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(RecipientType instance) {
        log.debug("attaching clean RecipientType instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static RecipientTypeDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (RecipientTypeDAO) ctx.getBean("RecipientTypeDAO");
	}
}