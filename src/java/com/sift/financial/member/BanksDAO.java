package com.sift.financial.member;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Banks entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.sift.financial.member.Banks
  * @author MyEclipse Persistence Tools 
 */
public class BanksDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(BanksDAO.class);
		//property constants
	public static final String BANK_NAME = "bankName";
	public static final String DEL_FLG = "delFlg";



	protected void initDao() {
		//do nothing
	}
    
    public void save(Banks transientInstance) {
        log.debug("saving Banks instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Banks persistentInstance) {
        log.debug("deleting Banks instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Banks findById( java.lang.Integer id) {
        log.debug("getting Banks instance with id: " + id);
        try {
            Banks instance = (Banks) getHibernateTemplate()
                    .get("com.sift.financial.member.Banks", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Banks instance) {
        log.debug("finding Banks instance by example");
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
      log.debug("finding Banks instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Banks as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByBankName(Object bankName
	) {
		return findByProperty(BANK_NAME, bankName
		);
	}
	
	public List findByDelFlg(Object delFlg
	) {
		return findByProperty(DEL_FLG, delFlg
		);
	}
	

	public List findAll() {
		log.debug("finding all Banks instances");
		try {
			String queryString = "from Banks";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Banks merge(Banks detachedInstance) {
        log.debug("merging Banks instance");
        try {
            Banks result = (Banks) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Banks instance) {
        log.debug("attaching dirty Banks instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Banks instance) {
        log.debug("attaching clean Banks instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static BanksDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (BanksDAO) ctx.getBean("BanksDAO");
	}
	
	
	
	public List getBanksByCountry(String countryId)
	{
		
		try {
			
			log.info("In here inside banks identifier:" + countryId);
			
			return getHibernateTemplate().find("select bankId, bankName from Banks as model where model.countries.id="+countryId +" and model.delFlg='N'");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
        
        public Banks getBanksByCountryNDesc(String countryId, String desc)
	{
		try {	
			return (Banks)getHibernateTemplate().find(" from Banks as model where model.countries.id="+countryId +" and model.delFlg='N' and model.bankCode='" + desc + "'").get(0);
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
}