package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberHoldings entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.MemberHoldings
 * @author MyEclipse Persistence Tools
 */
public class MemberHoldingsDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MemberHoldingsDAO.class);
	// property constants
	public static final String MEMBER_ID = "memberId";
	public static final String STOCK_ID = "stockId";
	public static final String HOLDINGS = "holdings";
	public static final String CREATED_BY = "createdBy";
	public static final String COMPANY_ID = "companyId";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberHoldings transientInstance) {
		log.debug("saving MemberHoldings instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void save(MemberHoldings transientInstance, Session sess) {
		log.debug("saving MemberHoldings instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberHoldings persistentInstance) {
		log.debug("deleting MemberHoldings instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberHoldings findById(java.lang.Integer id) {
		log.debug("getting MemberHoldings instance with id: " + id);
		try {
			MemberHoldings instance = (MemberHoldings) getHibernateTemplate()
					.get("com.sift.financial.member.MemberHoldings", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberHoldings instance) {
		log.debug("finding MemberHoldings instance by example");
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
		log.debug("finding MemberHoldings instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberHoldings as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberId(Object memberId) {
		return findByProperty(MEMBER_ID, memberId);
	}

	public List findByStockId(Object stockId) {
		return findByProperty(STOCK_ID, stockId);
	}

	public List findByHoldings(Object holdings) {
		return findByProperty(HOLDINGS, holdings);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findAll() {
		log.debug("finding all MemberHoldings instances");
		try {
			String queryString = "from MemberHoldings";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberHoldings merge(MemberHoldings detachedInstance) {
		log.debug("merging MemberHoldings instance");
		try {
			MemberHoldings result = (MemberHoldings) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	
	public MemberHoldings merge(MemberHoldings detachedInstance, Session sess) {
		log.debug("merging MemberHoldings instance");
		try {
			MemberHoldings result = (MemberHoldings) sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberHoldings instance) {
		log.debug("attaching dirty MemberHoldings instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberHoldings instance) {
		log.debug("attaching clean MemberHoldings instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberHoldingsDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberHoldingsDAO) ctx.getBean("MemberHoldingsDAO");
	}
	
	
	
	public MemberHoldings getAvailableHoldings(Integer memberId, Integer companyId, Integer stockId) throws Exception
	{
		
		MemberHoldings  returnVal = null;
		
			log.info("In here inside member identifier:" + memberId);
				
			List list = getHibernateTemplate().find(" from MemberHoldings as model where model.companyId="+ companyId + " and model.memberId=" + memberId + " and model.stockId=" + stockId);
			
			if(list!=null && list.size() ==1)
			{
				
				returnVal = (MemberHoldings)list.get(0);

			}
			else if(list!=null && list.size() > 1)
			{
				
				throw new Exception ("Issues with stock account and must be corrected");
			}
			else 
			{
				
				returnVal= null;
			}
			
			
		return returnVal;
	}
        
        
        public List<MemberHoldings> getAvailableHoldings(Integer memberId, Integer companyId) throws Exception
	{
		List<MemberHoldings>  returnVal = null;
		
		log.info("In here inside member identifier:" + memberId);
				
		returnVal = getHibernateTemplate().find(" from MemberHoldings as model where model.companyId = "+ companyId + " and model.memberId=" + memberId);
			
                /**if(list!=null && list.size() ==1)
                {
                        returnVal = list;
                }
                else if(list!=null && list.size() > 1)
                {
                        throw new Exception ("Issues with stock account and must be corrected");
                }
                else 
                {
                        returnVal= null;
                }
                */
		return returnVal;
	}

  /**
    @Override
    public Object getMemberValue(Integer memberId, String memberCode, Object otherInfo) throws Exception {
        
        Session sess =  null;
        Object result = null;
       
            if(otherInfo != null)
            {
                if (otherInfo instanceof HashMap)
                {
                        try
                        {
                            sess =  getHibernateTemplate().getSessionFactory().openSession();
                            HashMap<String, Object> theObj= ( HashMap<String, Object>)otherInfo;
                            Integer stckType = Integer.parseInt((String)theObj.get(OperandInterface.PRODINFOCUSID));
                            String sql = "select SUM(CASE WHEN movement_type = \"C\" THEN movement_holdings ELSE -movement_holdings END) AS total from  member_holdings_movement where member_id=:memcode and stock_id= :stckcode and effective_date < = :effcDate ";
                            Query query = sess.createSQLQuery(sql).setParameter("memcode", memberId).setParameter("stckcode", stckType).setParameter("effcDate", (String)theObj.get(OperandInterface.EFFECTIVEDATE));
                            result = ((java.math.BigDecimal) query.uniqueResult()).doubleValue();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        finally
                        {
                            sess.close();
                        }
                }
                else
                {
                     throw new Exception ("Inappropriate Object passed");
                }
               }
        
     return result;
    }
	
    **/
	
	
}