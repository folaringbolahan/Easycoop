package com.sift.financial.member;

import com.sift.financial.ReminderInterface;
import com.sift.financial.services.MailInterface;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Dividend entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sift.financial.member.Dividend
 * @author MyEclipse Persistence Tools
 */
public class DividendDAO extends HibernateDaoSupport implements MailInterface{
	private static final Log log = LogFactory.getLog(DividendDAO.class);
	// property constants
	public static final String COMPANY_ID = "companyId";
	public static final String BRANCH_ID = "branchId";
	public static final String DIV_YEAR = "divYear";
	public static final String DIV_PERIOD = "divPeriod";
	public static final String FORMULA = "formula";
	public static final String DIV_VALUE = "divValue";
	public static final String CREATED_BY = "createdBy";
	public static final String APPROVED_BY = "approvedBy";
        public static final String DIV_RETAINED_EARNINGS_ACCT = "divRetainedEarningsAcct";
        public static final String DIV_PAYABLE_ACCT = "divPayableAcct";
        public static final String DIV_CASH_ACCT = "divCashAcct";
        public static final String POST_ENTRIES = "postEntries";

	protected void initDao() {
		// do nothing
	}

	public void save(Dividend transientInstance) {
		log.debug("saving Dividend instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Integer saveWithId(Dividend transientInstance) {
		log.debug("saving Dividend instance");
		try {
			getHibernateTemplate().save(transientInstance);
			
			log.debug("save successful");
			
			return transientInstance.getDividendId();
			
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void save(Dividend transientInstance, Session sess) {
		log.debug("saving Dividend instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Integer saveWithId(Dividend transientInstance, Session sess) {
		log.debug("saving Dividend instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
			
			return transientInstance.getDividendId();
			
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Dividend persistentInstance) {
		log.debug("deleting Dividend instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Dividend findById(java.lang.Integer id) {
		log.debug("getting Dividend instance with id: " + id);
		try {
			Dividend instance = (Dividend) getHibernateTemplate().get(
					"com.sift.financial.member.Dividend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Dividend instance) {
		log.debug("finding Dividend instance by example");
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
		log.debug("finding Dividend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dividend as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findByDivYear(Object divYear) {
		return findByProperty(DIV_YEAR, divYear);
	}

	public List findByDivPeriod(Object divPeriod) {
		return findByProperty(DIV_PERIOD, divPeriod);
	}

	public List findByFormula(Object formula) {
		return findByProperty(FORMULA, formula);
	}

	public List findByDivValue(Object divValue) {
		return findByProperty(DIV_VALUE, divValue);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}
        
        
        
        public List findByDivRetainedEarningsAcct(Object divRetainedEarningsAcct) {
		return findByProperty(DIV_RETAINED_EARNINGS_ACCT, divRetainedEarningsAcct);
	}

	public List findByDivPayableAcct(Object divPayableAcct) {
		return findByProperty(DIV_PAYABLE_ACCT, divPayableAcct);
	}

	public List findByDivCashAcct(Object divCashAcct) {
		return findByProperty(DIV_CASH_ACCT, divCashAcct);
	}
        
        public List findByPostEntries(Object postEntries) {
		return findByProperty(POST_ENTRIES, postEntries);
	}
        
        

	public List findAll() {
		log.debug("finding all Dividend instances");
		try {
			String queryString = "from Dividend";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dividend merge(Dividend detachedInstance) {
		log.debug("merging Dividend instance");
		try {
			Dividend result = (Dividend) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	public Dividend merge(Dividend detachedInstance, Session sess) {
		log.debug("merging Dividend instance");
		try {
			Dividend result = (Dividend) sess.merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dividend instance) {
		log.debug("attaching dirty Dividend instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dividend instance) {
		log.debug("attaching clean Dividend instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DividendDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DividendDAO) ctx.getBean("DividendDAO");
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDivListByStatusInCol(String companyId, String branchId, String statusShort) {
			
			try {
				
				log.info("In here inside dividend identifier:" + companyId);
				
				return getHibernateTemplate().find("select model.dividendId, model.divYear, model.divPeriod, model.divDeclarationDate, model.exDividendDate, model.createdBy. model.divNumber, model.divPayDate, model.dividendType.dividendTypeName,model.dividendType.dividendTypeCode, model.status.statusShort, model.status.statusDesc  from Dividend as model where model.companyId="+ companyId + " and model.branchId=" + branchId  + " and model.status.statusShort='" + statusShort + "'");
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	
	
	@SuppressWarnings("unchecked")
	public List getDividendListByStatus(String companyId, String branchId, String statusShort) {
			
			try {
				log.info("In here inside dividend identifier:" + companyId);
				//return getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId + " and model.branchId=" + branchId  + " and model.status.statusShort='" + statusShort + "'");
				return getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId +  " and model.status.statusShort='" + statusShort + "'");
                                
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
	
		
	@SuppressWarnings("unchecked")
	public Dividend findByIdAndCompany(String companyId, String branchId, String dividendId) {
		
			try {
				
				log.info("In here inside dividend companyId:" + companyId);
				log.info("In here inside dividend dividendId:" + dividendId);
				
				return (Dividend)getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId + " and model.branchId=" + branchId  + " and model.dividendId=" + dividendId ).get(0);
				
			} catch (RuntimeException re) {
				log.error("get failed", re);
				return null;
			}
		}
        
        
        @SuppressWarnings("unchecked")
	public Dividend findByIdAndCompany(int dividendId, int companyId) 
        {
            try 
            {

                log.info("In here inside dividend companyId:" + companyId);
                log.info("In here inside dividend dividendId:" + dividendId);

                return (Dividend)getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId +  " and model.dividendId=" + dividendId ).get(0);

            } 
            catch (RuntimeException re) {
                    log.error("get failed", re);
                    return null;
            }
        }
        
        @SuppressWarnings("unchecked")
	public Dividend findByYearPeriodCompStatState(String year, int companyId, String period, String failState) 
        {
            Dividend div = null;
            try 
            {
                log.info("In here inside dividend companyId:" + companyId);
                log.info("In here inside dividend dividendId:" + period);
                log.info("In here inside dividend year:" + year);
                
              List theList = getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId +  " and model.divYear=" + year +  " and model.divPeriod=" + period +  " and model.status.failState='" + failState + "'");
               
              if(theList!=null && theList.size()>0)
              {
                 div = (Dividend)theList.get(0);
              }
              
                return div;
            } 
            catch (RuntimeException re) {
                    log.error("get failed", re);
                    return null;
            }
        }
        
        
        @SuppressWarnings("unchecked")
	public Dividend findByDivNumberCompStatState(String divNumber, int companyId, String failState) 
        {
            Dividend div = null;
            try 
            {
                log.info("In here inside dividend companyId:" + companyId);
                log.info("In here inside dividend divNumber:" + divNumber);
                             
              List theList = getHibernateTemplate().find(" from Dividend as model where model.companyId="+ companyId +  " and model.divNumber=" + divNumber + " and model.status.failState='" + failState + "'");
               
              if(theList!=null && theList.size()>0)
              {
                 div = (Dividend)theList.get(0);
              }
              
                return div;
            } 
            catch (RuntimeException re) {
                    log.error("get failed", re);
                    return null;
            }
        }

    @Override
    public Map<String, Object> getMailRecipient(Object obj, String action) {
        
        Dividend div = (Dividend)obj;
        
        Map<String, Object> theMap = new HashMap<String, Object>();
        
        theMap.put(ReminderInterface.INITIATOR, div.getCreatedBy());
        //theMap.put(ReminderInterface.MODIFIER
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 
    public List  getDividendByPayDate(Date today, String statusShort) throws Exception {
        
              
        List result = null;
      
                    Session sess = null;
                        try
                        {
                            sess = getHibernateTemplate().getSessionFactory().openSession();
                            
                            log.info("Inside Dividend selection " );
                            
                            Query query = sess.createQuery(" from Dividend as model where model.createdDate <= :eRecDate and model.status.statusShort=:status").setParameter("eRecDate", today).setParameter("status", statusShort);
                                                                          
                            result = query.list();  
                            
                            log.info("After Dividend selection with Size:" + result.size());
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
     return result;
    }
         
}