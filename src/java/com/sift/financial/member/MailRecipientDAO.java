package com.sift.financial.member;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for MailRecipient entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sift.financial.member.MailRecipient
  * @author MyEclipse Persistence Tools 
 */
public class MailRecipientDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(MailRecipientDAO.class);
		//property constants
	public static final String RECIPIENT = "recipient";



	protected void initDao() {
		//do nothing
	}
    
    public void save(MailRecipient transientInstance) {
        log.debug("saving MailRecipient instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MailRecipient persistentInstance) {
        log.debug("deleting MailRecipient instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MailRecipient findById( java.lang.Integer id) {
        log.debug("getting MailRecipient instance with id: " + id);
        try {
            MailRecipient instance = (MailRecipient) getHibernateTemplate()
                    .get("com.sift.financial.member.MailRecipient", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MailRecipient instance) {
        log.debug("finding MailRecipient instance by example");
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
      log.debug("finding MailRecipient instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MailRecipient as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByRecipient(Object recipient
	) {
		return findByProperty(RECIPIENT, recipient
		);
	}
	

	public List findAll() {
		log.debug("finding all MailRecipient instances");
		try {
			String queryString = "from MailRecipient";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public MailRecipient merge(MailRecipient detachedInstance) {
        log.debug("merging MailRecipient instance");
        try {
            MailRecipient result = (MailRecipient) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MailRecipient instance) {
        log.debug("attaching dirty MailRecipient instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MailRecipient instance) {
        log.debug("attaching clean MailRecipient instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static MailRecipientDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (MailRecipientDAO) ctx.getBean("MailRecipientDAO");
	}
	
public List<MailRecipient> findByMailID(String mailId) {
		
		log.debug("getting MailRecipient instance with mailId: " + mailId);
		
		try {
			
			String query=" from MailRecipient model where model.mailNotification.emailId= "+mailId+" ";
			return getHibernateTemplate().find(query);
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<MailRecipient> findByMailTemplate(String template) {
		
		log.debug("getting MailRecipient instance with mailId: " + template );
		
		try {
			
			String query=" from MailRecipient model where model.mailNotification.template= '"+template+"' ";
			return getHibernateTemplate().find(query);
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
        
        
            public List<MailRecipient> findByMailEventAndAction(String eventShort, String action) {
		
		log.debug("getting MailRecipient instance with eventShort & Action: " + eventShort + " & " + action);
		
		try {
			
			String query=" from MailRecipient model where model.mailNotification.event.eventShort= '"+eventShort+"' and model.mailNotification.action='" + action + "'";
			return getHibernateTemplate().find(query);
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
}