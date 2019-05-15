package com.sift.financial.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * A data access object (DAO) providing persistence and search support for
 * Member entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Member
 * @author MyEclipse Persistence Tools
 */

public class MemberDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MemberDAO.class);
	// property constants
	public static final String MEMBER_COMP_ID = "memberCompId";
	public static final String FIRST_NAME = "firstName";
	public static final String MIDDLE_NAME = "middleName";
	public static final String SURNAME = "surname";
	public static final String CREATED_BY = "createdBy";
	public static final String DEL_FLG = "delFlg";
	public static final String GENDER = "gender";
	public static final String IDENTIFICATION_CODE = "identificationCode";
	public static final String PHONE_NO1 = "phoneNo1";
	public static final String PHONE_NO2 = "phoneNo2";
	public static final String PHONE_NO3 = "phoneNo3";
	public static final String EMAIL_ADD1 = "emailAdd1";
	public static final String EMAIL_ADD2 = "emailAdd2";
	public static final String EMAIL_ADD3 = "emailAdd3";
	public static final String MEMBER_NO = "memberNo";

	protected void initDao() {
		// do nothing
	}

	public void save(Member transientInstance) {
		log.debug("saving Member instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
		
	public int save(Member transientInstance, Session session) {
		log.debug("saving Member instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		
		return transientInstance.getMemberId();
	}
        
        public Member saveReturn(Member transientInstance, Session session) {
		log.debug("saving Member instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		
		return transientInstance;
	}
	

	public void delete(Member persistentInstance) {
		log.info("deleting Member instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.info("delete successful");
		} catch (RuntimeException re) {
			log.info("delete failed", re);
			throw re;
		}
	}
        
        public void delete(Member persistentInstance, Session sess) {
		log.info("deleting Member instance");
		try {
			sess.delete(persistentInstance);
			log.info("delete successful");
		} catch (RuntimeException re) {
			log.info("delete failed", re);
			throw re;
		}
	}

	public Member findById(java.lang.Integer id) {
		log.debug("getting Member instance with id: " + id);
		try {
			Member instance = (Member) getHibernateTemplate().get(
					"com.sift.financial.member.Member", id);
			log.info("Member retrieveed successfilly ........");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
        
        

        public Member findByIds(java.lang.Integer id, java.lang.Integer compid) 
        {
		 Session sess = null;
                 Object obj = null;
                
                 try
                 {
                     sess = getHibernateTemplate().getSessionFactory().openSession();
		
                    log.info("In here inside get Member companyId:" + compid);
		
                Query query = sess.createQuery(" from Member as model where model.memberId=:memid and model.company.Id=:compCode").setParameter("memid", id).setParameter("compCode", compid);
		
		   obj = query.list().get(0);
                 }
                 catch(Exception ex)
                 {
                     ex.printStackTrace();
                 }
                 finally
                 {
                 sess.close();
                 }
		
		return (Member)obj;
	}

	public List findByExample(Member instance) {
		log.debug("finding Member instance by example");
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
		log.debug("finding Member instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Member as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMemberCompId(Object memberCompId) {
		return findByProperty(MEMBER_COMP_ID, memberCompId);
	}

	public List findByFirstName(Object firstName) {
		return findByProperty(FIRST_NAME, firstName);
	}

	public List findByMiddleName(Object middleName) {
		return findByProperty(MIDDLE_NAME, middleName);
	}

	public List findBySurname(Object surname) {
		return findByProperty(SURNAME, surname);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByGender(Object gender) {
		return findByProperty(GENDER, gender);
	}

	public List findByIdentificationCode(Object identificationCode) {
		return findByProperty(IDENTIFICATION_CODE, identificationCode);
	}

	public List findByPhoneNo1(Object phoneNo1) {
		return findByProperty(PHONE_NO1, phoneNo1);
	}

	public List findByPhoneNo2(Object phoneNo2) {
		return findByProperty(PHONE_NO2, phoneNo2);
	}

	public List findByPhoneNo3(Object phoneNo3) {
		return findByProperty(PHONE_NO3, phoneNo3);
	}

	public List findByEmailAdd1(Object emailAdd1) {
		return findByProperty(EMAIL_ADD1, emailAdd1);
	}

	public List findByEmailAdd2(Object emailAdd2) {
		return findByProperty(EMAIL_ADD2, emailAdd2);
	}

	public List findByEmailAdd3(Object emailAdd3) {
		return findByProperty(EMAIL_ADD3, emailAdd3);
	}

	public List findByMemberCode(Object memberNo) {
		return findByProperty(MEMBER_NO, memberNo);
	}

	public List findAll() {
		log.debug("finding all Member instances");
		try {
			String queryString = "from Member";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Member merge(Member detachedInstance) {
		log.debug("merging Member instance");
		try {
			Member result = (Member) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	
	public Member merge(Member detachedInstance, Session sess) {
		log.debug("merging Member instance");
                 log.info("Here inside writeObjects....................merging member = " + detachedInstance.getMemberNo());
		try {
			Member result = (Member) sess.merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Member instance) {
		log.debug("attaching dirty Member instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Member instance) {
		log.debug("attaching clean Member instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MemberDAO) ctx.getBean("MemberDAO");
	}
	
	 /**
	 * @param companyId
	 * @param statusShort
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMemberListByStatus(String companyId, String statusShort, String branchId) {
            
            Session sess = null;
            Transaction tx = null;
            List<Object[]> theList = null;
         
            sess = getHibernateTemplate().getSessionFactory().openSession();
           
			try {
				tx = sess.beginTransaction();
				log.info("In here inside member identifier:" + companyId);
                                
                                String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId ";
				
                                if(branchId==null)
                                {
				 theList=  sess.createQuery("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "'").list();
                                }
                                else
                                {
                                theList=  sess.createQuery("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.branch.Id =" + branchId + " and model.status.statusShort='" + statusShort + "'").list();
                                }
                                
                              tx.commit();
                              
			} catch (RuntimeException re) {
                            tx.rollback();
                            re.printStackTrace();
				log.error("get failed", re);
				//return null;
			}
                        finally
                        {
                        sess.close();
                        }
                        
                   return theList;    
		}
        
        
        @SuppressWarnings("unchecked")
	public List<Object[]> getMemberListByStatuswthemail(String companyId, String statusShort, String branchId) {
            
            Session sess = null;
            Transaction tx = null;
            List<Object[]> theList = null;
         
            sess = getHibernateTemplate().getSessionFactory().openSession();
           
			try {
				tx = sess.beginTransaction();
				log.info("In here inside member identifier:" + companyId);
                                
                                String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch,model.company, model.createdDate, model.emailAdd1, model.phoneNo1, model.middleName, model.memberCompId, model.status.statusShort ";
				
                                if(branchId==null)
                                {
				 theList=  sess.createQuery("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "'").list();
                                }
                                else
                                {
                                theList=  sess.createQuery("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.branch.Id =" + branchId + " and model.status.statusShort='" + statusShort + "'").list();
                                }
                                
                              tx.commit();
                              
			} catch (RuntimeException re) {
                            tx.rollback();
                            re.printStackTrace();
				log.error("get failed", re);
				//return null;
			}
                        finally
                        {
                        sess.close();
                        }
                        
                   return theList;    
		}
        
         @SuppressWarnings("unchecked")
	public List<Object> getMemberListByStatus(Integer companyId, String statusShort) {
			
            try {

                       
                    log.info("In here inside member identifier:" + companyId);
                                
                    String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId ";

                    if(statusShort!=null)
                    {
                        return getHibernateTemplate().find("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "'");
                    }
                    else
                    {
                        return getHibernateTemplate().find("select " + columnList + " from Member as model where model.company.Id="+ companyId );
                    }

            } catch (RuntimeException re) {
                re.printStackTrace();
                    log.error("get failed", re);
                    return null;
            }
	}
	
	/**
	 * Method returns system generated ID for member at company 
	 * @param compId
	 * @return
	 */
	public String getMemberSystemCompanyId(String compId)
	{
		
		return null;
	}
	
	
	/**
	 * @param companyId
	 * @param branchId
	 * @param memCode
	 * @param prodCode
	 * @param sess
	 * @return
	 */
	public List getMemberActiveAccounts (String companyId, String branchId, String memCode, String prodCode, Session sess) 
	{
		
		List theList = null;
		
		log.info("In here inside get Member aCCOUNTS:" + companyId);
		
		Query query = sess.createSQLQuery("SELECT a.accountNo, a.name, b.product, a.controlAccountno,    from accounts a, custaccountdetails b where a.accountno=b.accountno and a.active='1' and a.closed='0' and blocked='0' and a.companyId =:compCode and a.branchId=:brnchCode and a.asegc= :mmecode and b.product=:prodCode")
		  .setParameter("compCode", companyId).setParameter("prodCode", prodCode).setParameter("mmecode", memCode).setParameter("brnchCode", branchId);
		
		theList = query.list();
		
		return theList;
		
	}
        
        
        public List getMemberByIdCompBranch (Integer companyId, Integer branchId, Integer memId) 
	{
				
		String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId ";

                return getHibernateTemplate().find("select " + columnList + " from Member as model where model.company.Id="+ companyId + " and model.branch.id=" + branchId + " and model.memberId="+ memId);
                
	}
        
        
        public List getValidatedMemberByIdCompBranch (Integer companyId, Integer branchId, String stat, Integer fileId) 
	{	
		//String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId ";
            log.info("Here inside processObjects....................getting Validated for Upload Id = " + fileId);
            return getHibernateTemplate().find( " from Member as model where model.company.Id="+ companyId + " and model.branch.id=" + branchId + " and model.status.statusShort='"+ stat + "' and model.uploadId=" + fileId); 
	}
        
        
         public Member getValidatedMemberByIdCompBranch (Integer companyId, Integer branchId, Integer batchMemberId) 
	{		
            log.info("Here inside WriteObjects....................getting member record for batchMember Id = " + batchMemberId);
                return (Member)getHibernateTemplate().find( " from Member as model where model.company.id="+ companyId + " and model.branch.id=" + branchId + " and model.batchMemberId=" + batchMemberId).get(0);
                
	}
         
       
        public Member getMemberByEmailCompBranch (Integer companyId, Integer branchId, String emailAdd1) 
	{		
            log.info("Here inside MemberDAO....................getting member record for email  = " + emailAdd1.toLowerCase());
                return (Member)getHibernateTemplate().find( " from Member as model where model.company.id="+ companyId + " and model.branch.id=" + branchId + " and model.emailAdd1='" + emailAdd1.toLowerCase()+"'").get(0);
                
	}
        
        
    public List  getMemberByRecordDate(Object otherInfo, String statusShort) throws Exception {
        
        //Session sess =  null;
       
        String columnList = " model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId,model.branch.Id, model.company.Id ";
        
        List result = null;
       
            if(otherInfo != null)
            {
                if (otherInfo instanceof Dividend)
                {
                    Dividend div = (Dividend)otherInfo;
                    Session sess = null;
                        try
                        {
                            //String sql = "select " + columnList + " from Member as model where model.company.Id="+ div.getCompanyId() + " and model.createdDate <= '" + div.getDivDateRecord()+ "' and model.status.statusShort='" + statusShort + "'";
                           // String sql = "select " + columnList + " from Member as model where model.company.Id="+ div.getCompanyId() + " and model.createdDate <= '" + div.getDivDateRecord()+ "' and model.status.statusShort='" + statusShort + "'";
                            //log.info("sql In Member For Div= " + sql);
                            sess = getHibernateTemplate().getSessionFactory().openSession();
                             log.info("Inside member selection " );
                            Query query = sess.createQuery("select model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId,model.branch.Id, model.company.Id  from Member as model where model.company.Id=:eCompany and model.createdDate <= :eRecDate and model.status.statusShort=:status").setParameter("eCompany", div.getCompanyId()).setParameter("eRecDate", div.getDivDateRecord()).setParameter("status", statusShort);
                            //Query query = sess.createQuery("select model.memberId, model.firstName, model.surname, model.memberNo, model.branch.branchName, model.memberType.memberTypeVal, model.status.statusShort,model.status.statusDesc,model.middleName, model.memberCompId,model.branch.Id, model.company.Id  from Member as model where model.company.Id=:eCompany and model.status.statusShort=:status").setParameter("eCompany", div.getCompanyId()).setParameter("status", statusShort);
                                  
                            //result = getHibernateTemplate().find(sql);                         
                            result = query.list();  
                            
                            log.info("After member selection " );
                        }
                        catch(Exception ex)
                        {
                            log.info("Failure inside member selection " + ex.getMessage());
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
	
	
	
	
}