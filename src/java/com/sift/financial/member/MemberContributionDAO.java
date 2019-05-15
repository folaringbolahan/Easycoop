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
 * MemberContribution entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.MemberContribution
 * @author MyEclipse Persistence Tools
 */
public class MemberContributionDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(MemberContributionDAO.class);
	// property constants
	public static final String MEMBER_CONTRIB_VALUE = "memberContribValue";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberContribution transientInstance) {
		log.debug("saving MemberContribution instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        public void save(MemberContribution transientInstance, Session sess) {
		log.debug("saving MemberContribution instance");
               
               // System.out.println(transientInstance.getApprovedBy()==null?"APP=NULL":transientInstance.getApprovedBy()) ;
               // System.out.println(transientInstance.getApprovedDate()==null?"APPDATENULL":transientInstance.getApprovedDate()) ;
               // System.out.println(transientInstance.getBranchId()) ;
               // System.out.println(transientInstance.getCompanyId()) ;
              //  System.out.println(transientInstance.getContribProd()) ;
              //  System.out.println(transientInstance.getCountryId()) ;
                
              //  System.out.println(transientInstance.getCreatedBy()) ;
             ///   System.out.println(transientInstance.getCreatedDate()) ;
             //   System.out.println(transientInstance.getMemberContribValue()) ;
             //   System.out.println(transientInstance.getMember().getMemberId()) ;
              //  System.out.println(transientInstance.getModifiedBy()==null?"MOD=NULL":transientInstance.getModifiedBy()) ;
              //  System.out.println(transientInstance.getModifiedDate()==null?"MOD DATE=NULL":transientInstance.getModifiedDate()) ;
                
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberContribution persistentInstance) {
		log.debug("deleting MemberContribution instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
        
        public void delete(MemberContribution persistentInstance, Session sess) {
		log.debug("deleting MemberContribution instance");
		try {
			sess.delete(persistentInstance);
			log.info ("delete MemberContribution successful................");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberContribution findById(java.lang.Integer id) {
		log.debug("getting MemberContribution instance with id: " + id);
		try {
			MemberContribution instance = (MemberContribution) getHibernateTemplate()
					.get("com.sift.financial.member.MemberContribution", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberContribution instance) {
		log.debug("finding MemberContribution instance by example");
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
		log.debug("finding MemberContribution instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberContribution as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberContribValue(Object memberContribValue) {
		return findByProperty(MEMBER_CONTRIB_VALUE, memberContribValue);
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
		log.debug("finding all MemberContribution instances");
		try {
			String queryString = "from MemberContribution";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberContribution merge(MemberContribution detachedInstance) {
		log.debug("merging MemberContribution instance");
		try {
			MemberContribution result = (MemberContribution) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberContribution instance) {
		log.debug("attaching dirty MemberContribution instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberContribution instance) {
		log.debug("attaching clean MemberContribution instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberContributionDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberContributionDAO) ctx.getBean("MemberContributionDAO");
	}
        
        
         public List getMemberContributions (Integer memberId) 
	{	
           return getHibernateTemplate().find( " from MemberContribution as model where model.member.memberId="+ memberId ); 
           
        }
         
         
        public void delete(Member mem, Session sess) {
            
		log.debug("deleting MemberContribution instance");
                
		try {
                    
                   Query query = sess.createQuery("delete MemberContribution as model where model.member.memberId  = " + mem.getMemberId());
                    //query.setParameter("stockCode", "7277");
                    int result = query.executeUpdate();
                    
			//sess.delete("from MemberContribution as model where model.member.memberId  = " + mem.getMemberId());
			log.info ("delete MemberContribution successful................:: " + result);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
/**
    @Override
    public Object getMemberValue(Integer memberId, String memberCode, Object otherInfo) throws Exception  {

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
                            
                            String sql = "select member_id, stock_id, SUM(CASE WHEN movement_type = \"C\" THEN movement_holdings ELSE -movement_holdings END) AS total from  member_holdings_movement where member_id=:memcode and stock_id= :stckcode and effective_date < = :effcDate ";
                            
                            Query query = sess.createSQLQuery(sql).setParameter("memcode", memberId).setParameter("stckcode", stckType).setParameter("effcDate", (String)theObj.get(OperandInterface.EFFECTIVEDATE));
                            result = query.uniqueResult();
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