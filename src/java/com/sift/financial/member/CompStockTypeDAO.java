package com.sift.financial.member;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sift.financial.*;

/**
 * A data access object (DAO) providing persistence and search support for
 * CompStockType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.CompStockType
 * @author MyEclipse Persistence Tools
 */
public class CompStockTypeDAO extends HibernateDaoSupport implements ApprovaInterface{
	private static final Log log = LogFactory.getLog(CompStockTypeDAO.class);
	// property constants
	public static final String COMP_STOCK_NAME = "compStockName";
	public static final String DEL_FLG = "delFlg";
	public static final String CREATED_BY = "createdBy";

	protected void initDao() {
		// do nothing
	}

	public void save(CompStockType transientInstance) {
		log.debug("saving CompStockType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	
	public int save(Session session, CompStockType transientInstance) {
		log.debug("saving CompStockType instance");
		try {
			session.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return transientInstance.getCompStockTypeId();
	}

	public void delete(CompStockType persistentInstance) {
		log.debug("deleting CompStockType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

       public boolean delete2(Integer compStockTypeId, Session sess) {
        log.debug("deleting CompStockType instance with theStock :: " + compStockTypeId);
        boolean status = false;
        try {
            sess.delete(" from CompStockType where compStockTypeId =" + compStockTypeId);
            log.debug("delete successful");
            status = true;
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
        return status;
    }

    public void delete3(CompStockType persistentInstance, Session sess) {
        log.debug("deleting CompStockType instance");
        try {
            sess.delete(persistentInstance);
            //getHibernateTemplate().getSessionFactory().getCurrentSession().delete(persistentInstance);
            //getHibernateTemplate().delete(persistentInstance);

            // sess.delete(" from CompStockType as model where model.compStockTypeId=" + persistentInstance.getCompStockTypeId());
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
     public void deleteByStockId(Integer stockId, Session sess) //throws Exception
    {
        log.debug("deleting Stock instance");

        try {

            // Delete from the foriegn table
            Query query0 = sess.createQuery("delete CompStockTypeDetail where compStockTypeId = :compStockTypeId");
            query0.setParameter("compStockTypeId", stockId);
            System.out.println("query0 :: " + query0);

            // getHibernateTemplate().delete("DividendSchedule as model where model.dividendId="+ divId);
            int deleteCompStockTypeDetail = query0.executeUpdate();
            System.out.println("deleteCompStockTypeDetail :: " + deleteCompStockTypeDetail);

            // Now delete from the CompStockType
            Query query = sess.createQuery("delete CompStockType where compStockTypeId = :compStockTypeId");
            query.setParameter("compStockTypeId", stockId);

            // getHibernateTemplate().delete("DividendSchedule as model where model.dividendId="+ divId);
            int result = query.executeUpdate();

            if (result > 0) {

                log.debug("delete successful");
            } else {
                //throw new Exception("Deletion failed ");
            }

        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

	public CompStockType findById(java.lang.Integer id) {
		log.debug("getting CompStockType instance with id: " + id);
		try {
			CompStockType instance = (CompStockType) getHibernateTemplate()
					.get("com.sift.financial.member.CompStockType", id);
			
		log.info("Got com.sift.financial.member.CompStockType ::: " + instance.getShortName());
		
			return instance;
		} catch (RuntimeException re) {
			
			log.info("Got com.sift.financial.member.CompStockType ::: " + re.getMessage());
			throw re;
		}
	}

	public List findByExample(CompStockType instance) {
		log.debug("finding CompStockType instance by example");
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
		log.debug("finding CompStockType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CompStockType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompStockName(Object compStockName) {
		return findByProperty(COMP_STOCK_NAME, compStockName);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findAll() {
		log.debug("finding all CompStockType instances");
		try {
			String queryString = "from CompStockType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CompStockType merge(CompStockType detachedInstance) {
		log.debug("merging CompStockType instance");
		try {
			CompStockType result = (CompStockType) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public CompStockType merge(CompStockType detachedInstance, Session sess) {
		log.debug("merging CompStockType instance");
		try {
			CompStockType result = (CompStockType) sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CompStockType instance) {
		log.debug("attaching dirty CompStockType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompStockType instance) {
		log.debug("attaching clean CompStockType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CompStockTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CompStockTypeDAO) ctx.getBean("CompStockTypeDAO");
	}
	
	
	 @SuppressWarnings("unchecked")
		public List<Object[]> getStockListByCompany(String companyId) {
				
				try {
					
					log.info("In here inside member identifier:" + companyId);
					
					return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName, model.createdDate, model.createdBy from CompStockType as model where model.company.Id="+ companyId);
					
				} catch (RuntimeException re) {
					log.error("get failed", re);
					return null;
				}
			}
		
		
		 @SuppressWarnings("unchecked")
			public List<Object[]> getStockListByCompany(String companyId, String statusShort) {
					
					try {
						
						log.info("In here inside member identifier:" + companyId);
						
						return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName, model.createdDate, model.createdBy from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "'");
						
					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
			
			
			@SuppressWarnings("unchecked")
			public List getDefaultStockListByCompany(String companyId, String statusShort) {
					
					try {
						
						log.info("In here inside member identifier:" + companyId);
						
						return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='Y'");
						
						//	return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName from CompStockType as model where model.company.companyId="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='Y'");

					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
                        
                        @SuppressWarnings("unchecked")
			public List getDefaultRegisterStockListByCompany(String companyId, String statusShort) {
					
					try {
						
						log.info("In here inside member identifier:" + companyId);
						
						return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.registerStock='Y'");
						
						//	return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName from CompStockType as model where model.company.companyId="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='Y'");

					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
			
			@SuppressWarnings("unchecked")
			public  List<Object[]> getDefaultStockListByCompanyCol(String companyId, String statusShort) {
					
					try {
						log.info("In here inside member identifier:" + companyId);
						//return getHibernateTemplate().find(" from CompStockType as model where model.company.companyId="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='Y'");
						return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='Y'");

					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
			
			@SuppressWarnings("unchecked")
			public List<Object[]> getNonDefaultStockListByCompany(String companyId, String statusShort) {
					
					try {
						log.info("In here inside member identifier:" + companyId);
						return getHibernateTemplate().find("select model.compStockTypeId, model.company.name, model.compStockName from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.defaultStock='N'");
					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
			
			
			@SuppressWarnings("unchecked")
			public List getAllValidStockListByCompany(String companyId, String statusShort) {
					
					try {
						log.info("In here inside CompStock Company identifier:" + companyId);
						
						return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "'");
						
					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
                        
                        @SuppressWarnings("unchecked")
			public List getStocksByNameCompStatus(String companyId, String statusShort, String name) {
					
					try {
						log.info("In here inside CompStock Company identifier:" + companyId);
						
						return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.compStockName='" + name.toUpperCase() + "'");
						
					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
                        @SuppressWarnings("unchecked")
			public List getStocksByNameCompStatus(String companyId, String statusShort, String name, String compId) {
					
					try {
						log.info("In here inside CompStock Company identifier:" + companyId);
						
						return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.compStockName='" + name.toUpperCase() + "' and  model.compStockTypeId !=" + compId);
						
					} catch (RuntimeException re) {
						log.error("get failed", re);
						return null;
					}
				}
                       
               public CompStockType findByIdAndCompany(java.lang.Integer stockId, java.lang.Integer companyId) {
		log.debug("getting CompStockType instance with id: " + stockId);
		try {
			
                    System.out.println("java.lang.Integer stockId :"  + stockId);
                    
                     System.out.println("java.lang.Integer companyId :" + companyId );
                    
                    
                    Query q = getHibernateTemplate().getSessionFactory().openSession().createQuery(" from CompStockType as model where model.company.Id="+ companyId + " and model.compStockTypeId=:stockId")
                            .setInteger("stockId", stockId);
                    
                    List queryList = q.list();
                    
                    CompStockType instance =(CompStockType)queryList.get(0);
			
                    log.info("Got com.sift.financial.member.CompStockType ::: " + instance.getShortName());
		
			return instance;
		} catch (RuntimeException re) {
			
			log.info("Got com.sift.financial.member.CompStockType ::: " + re.getMessage());
			throw re;
		}
               }
                
                @SuppressWarnings("unchecked")
                public List getStocksByShortNameCompStatus(String companyId, String statusShort, String shortName, String compId) 
                {
                                try {
                                        log.info("In here inside CompStock Company identifier:" + companyId);

                                        return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + " and model.status.statusShort='" + statusShort + "' and model.status.shortName='" + shortName.toUpperCase() + "'");

                                } catch (RuntimeException re) {
                                        log.error("get failed", re);
                                        return null;
                                }
                }
                
                
                
                 @SuppressWarnings("unchecked")
                public String getNewShortName(String companyId, String stockName) 
                {
                    Session sess = null;
                    java.math.BigInteger theCount;
                    String shortStart = stockName.toUpperCase().substring(0, 3) +"-";
                    try {
                        
                        sess = getHibernateTemplate().getSessionFactory().openSession();
                        
                        Query q = sess.createSQLQuery("SELECT count(*) FROM comp_stock_type where company_id=" + companyId);

                        theCount =  (java.math.BigInteger)q.uniqueResult();
                            
                        return  shortStart + theCount  + "-STOCK";

                    } catch (RuntimeException re) {
                        re.printStackTrace();
                        log.error("get failed", re);
                        return null;
                    }
                    finally
                    {
                        sess.close();
                    }
                }

    @Override
    public boolean approveFirstLevel(String initiator, String modifier, String approval) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean approveSecondLevel(String initiator, String modifier, String approval, String nextApproval) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @SuppressWarnings("unchecked")
    public CompStockType getStocksByCompIdShortName(Integer companyId, String shortName) 
    {
                try {
                        log.info("In here inside CompStock Company identifier:" + companyId  + " and Shortname :: " + shortName.toUpperCase());

                        return (CompStockType)getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + "  and model.shortName='" + shortName.toUpperCase() + "'").get(0);

                } catch (RuntimeException re) {
                        log.error("get failed", re);
                        return null;
                }
    }
    
    @SuppressWarnings("unchecked")
    public List getStockListByCompIdStatShort(Integer companyId, String statusShort) 
    {
                try {
                        log.info("In here inside CompStock Company identifier:" + companyId  + " and satusShort :: " + statusShort.toUpperCase());

                        return getHibernateTemplate().find(" from CompStockType as model where model.company.Id="+ companyId + "  and model.status.statusShort='" + statusShort.toUpperCase() + "'");

                } catch (RuntimeException re) {
                        log.error("get failed", re);
                        return null;
                }
    }
    
    
    // Check if stock is used my members
    public boolean canEditStock(int companyId, int stockId) {
        boolean status = false;
         Session sess = null;
        int countMemberHoldings = 0;
        int countMemberHoldingsMovement = 0;

        try {

            sess = getHibernateTemplate().getSessionFactory().openSession();

            Query q1 = sess.createSQLQuery("SELECT count(*) FROM member_holdings as model where model.company_id=" + companyId + "  and model.stock_id=" + stockId);
            Query q2 = sess.createSQLQuery("SELECT count(*) FROM member_holdings_movement as model where model.company_id=" + companyId + "  and model.stock_id=" + stockId);

            
            String q1StrCount = (q1 == null || q1.list().isEmpty()) ? "0" : q1.list().get(0).toString();
            String q2StrCount = (q2 == null || q2.list().isEmpty()) ? "0" : q2.list().get(0).toString();
            
            countMemberHoldings = Integer.parseInt(q1StrCount);
            countMemberHoldingsMovement = Integer.parseInt(q2StrCount);
            
            //System.out.println("countMemberHoldings :: " + countMemberHoldings);
           // System.out.println("countMemberHoldingsMovement :: " + countMemberHoldingsMovement);
            
            if (countMemberHoldings > 0 || countMemberHoldingsMovement > 0) {
               // System.out.println("In here");
                status = false;
            } else {
                System.out.println("Out hereeee");
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unabled to determine status", e);
        } finally {
            sess.close();
        }

        return status;
    }
	
}