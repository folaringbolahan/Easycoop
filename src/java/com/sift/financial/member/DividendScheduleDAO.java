package com.sift.financial.member;

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
 * DividendSchedule entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.DividendSchedule
 * @author MyEclipse Persistence Tools
 */
public class DividendScheduleDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DividendScheduleDAO.class);
	// property constants
	public static final String DIVIDEND_ID = "dividendId";
	public static final String MEMBER_NO = "memberNo";
	public static final String PAID = "paid";
	public static final String COMPANY_CODE = "companyCode";
	public static final String BRANCH_CODE = "branchCode";
	public static final String DIVIDEND_GROSS_VALUE = "dividendGrossValue";
	public static final String DIVIDEND_TYPE = "dividendType";
	public static final String DIVIDEND_NET_VALUE = "dividendNetValue";
	public static final String DIVIDEND_TAX_VALUE = "dividendTaxValue";
	public static final String PAY_CHANNEL = "payChannel";
	public static final String PAID_BY = "paidBy";
	public static final String DIVIDEND_TAX = "dividendTax";
        public static final String STOCK_SHORT = "stockShort";

	protected void initDao() {
		// do nothing
	}

	public void save(DividendSchedule transientInstance) {
		log.debug("saving DividendSchedule instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DividendSchedule persistentInstance) {
		log.debug("deleting DividendSchedule instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DividendSchedule findById(java.lang.Integer id) {
		log.debug("getting DividendSchedule instance with id: " + id);
		try {
			DividendSchedule instance = (DividendSchedule) getHibernateTemplate()
					.get("com.sift.financial.member.DividendSchedule", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DividendSchedule instance) {
		log.debug("finding DividendSchedule instance by example");
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
		log.debug("finding DividendSchedule instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DividendSchedule as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDividendId(Object dividendId) {
		return findByProperty(DIVIDEND_ID, dividendId);
	}

	public List findByMemberNo(Object memberNo) {
		return findByProperty(MEMBER_NO, memberNo);
	}

	public List findByPaid(Object paid) {
		return findByProperty(PAID, paid);
	}

	public List findByCompanyCode(Object companyCode) {
		return findByProperty(COMPANY_CODE, companyCode);
	}

	public List findByBranchCode(Object branchCode) {
		return findByProperty(BRANCH_CODE, branchCode);
	}

	public List findByDividendGrossValue(Object dividendGrossValue) {
		return findByProperty(DIVIDEND_GROSS_VALUE, dividendGrossValue);
	}

	public List findByDividendType(Object dividendType) {
		return findByProperty(DIVIDEND_TYPE, dividendType);
	}

	public List findByDividendNetValue(Object dividendNetValue) {
		return findByProperty(DIVIDEND_NET_VALUE, dividendNetValue);
	}

	public List findByDividendTaxValue(Object dividendTaxValue) {
		return findByProperty(DIVIDEND_TAX_VALUE, dividendTaxValue);
	}

	public List findByPayChannel(Object payChannel) {
		return findByProperty(PAY_CHANNEL, payChannel);
	}

	public List findByPaidBy(Object paidBy) {
		return findByProperty(PAID_BY, paidBy);
	}

	public List findByDividendTax(Object dividendTax) {
		return findByProperty(DIVIDEND_TAX, dividendTax);
	}
        
        public List findByStockShort(Object stockShort) {
		return findByProperty(STOCK_SHORT, stockShort);
	}
        
	public List findAll() {
		log.debug("finding all DividendSchedule instances");
		try {
			String queryString = "from DividendSchedule";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DividendSchedule merge(DividendSchedule detachedInstance) {
		log.debug("merging DividendSchedule instance");
		try {
			DividendSchedule result = (DividendSchedule) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DividendSchedule instance) {
		log.debug("attaching dirty DividendSchedule instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DividendSchedule instance) {
		log.debug("attaching clean DividendSchedule instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DividendScheduleDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DividendScheduleDAO) ctx.getBean("DividendScheduleDAO");
	}
        
        
       
        public void deleteByDividendId(Integer divId, Session sess) //throws Exception
        {
		log.debug("deleting DividendSchedule instance");
                
              
		try {
			
                   Query query = sess.createQuery("delete DividendSchedule where dividendId = :divid");
                   query.setParameter("divid", divId);
                   
                   // getHibernateTemplate().delete("DividendSchedule as model where model.dividendId="+ divId);
                   
                   int result = query.executeUpdate();
 
                    if (result > 0) {
                        
                        log.debug("delete successful");
                    }
                    else
                    {
                     //throw new Exception("Deletion failed ");
                    }
                       
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
        
        public void save(DividendSchedule transientInstance, Session sess) {
		log.debug("saving DividendSchedule instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        
        public boolean updateByDividendId(Dividend div, Session sess, Date today, String paidStatus) //throws Exception
        {
		log.debug("updating DividendSchedule instance");
                
                boolean returnVal = false;
                
		try {
			
                   Query query = sess.createQuery("update DividendSchedule set effectiveDate= :effctDate, approveBy=:appBy, paid=:paid,paidDate=:payDate, paidBy=:payBy, approvedDate=:appdate  where dividendId = :divid");
                   query.setParameter("effctDate", today).setParameter("appBy", div.getApprovedBy()).setParameter("paid", "Y").setParameter("payDate", today).setParameter("payBy", div.getApprovedBy()).setParameter("appdate", today).setParameter("divid", div.getDividendId());
                 
                   query.executeUpdate();
                   
                   returnVal = true;
 
                
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
                
                return  returnVal; 
	}
}