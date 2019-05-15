package com.sift.financial.member;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for MailNotification entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sift.financial.member.MailNotification
  * @author MyEclipse Persistence Tools 
 */
public class MailNotificationDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(MailNotificationDAO.class);
		//property constants
	public static final String ACTION = "action";
	public static final String SUBJECT = "subject";
	public static final String TEMPLATE = "template";
	public static final String DEL_FLG = "delFlg";



	protected void initDao() {
		//do nothing
	}
    
    public void save(MailNotification transientInstance) {
        log.debug("saving MailNotification instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MailNotification persistentInstance) {
        log.debug("deleting MailNotification instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MailNotification findById( java.lang.Integer id) {
        log.debug("getting MailNotification instance with id: " + id);
        try {
            MailNotification instance = (MailNotification) getHibernateTemplate()
                    .get("com.sift.financial.member.MailNotification", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MailNotification instance) {
        log.debug("finding MailNotification instance by example");
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
      log.debug("finding MailNotification instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MailNotification as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByAction(Object action
	) {
		return findByProperty(ACTION, action
		);
	}
	
	public List findBySubject(Object subject
	) {
		return findByProperty(SUBJECT, subject
		);
	}
	
	public List findByTemplate(Object template
	) {
		return findByProperty(TEMPLATE, template
		);
	}
	
	public List findByDelFlg(Object delFlg
	) {
		return findByProperty(DEL_FLG, delFlg
		);
	}
	

	public List findAll() {
		log.debug("finding all MailNotification instances");
		try {
			String queryString = "from MailNotification";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public MailNotification merge(MailNotification detachedInstance) {
        log.debug("merging MailNotification instance");
        try {
            MailNotification result = (MailNotification) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MailNotification instance) {
        log.debug("attaching dirty MailNotification instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MailNotification instance) {
        log.debug("attaching clean MailNotification instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static MailNotificationDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (MailNotificationDAO) ctx.getBean("MailNotificationDAO");
	}
}