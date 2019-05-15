package com.sift.financial.member;

import com.sift.financial.services.MailInterface;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * BatchMember entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.BatchMember
 * @author MyEclipse Persistence Tools
 */
public class BatchMemberDAO extends HibernateDaoSupport implements BatchErrors, MailInterface{
	private static final Log log = LogFactory.getLog(BatchMemberDAO.class);
	// property constants
	public static final String MEMBER_COMP_ID = "memberCompId";
	public static final String FIRST_NAME = "firstName";
	public static final String MIDDLE_NAME = "middleName";
	public static final String SURNAME = "surname";
	public static final String RELIGION_ID = "religionId";
	public static final String MEMBER_TYPE_ID = "memberTypeId";
	public static final String CREATED_BY = "createdBy";
	public static final String DEL_FLG = "delFlg";
	public static final String GENDER = "gender";
	public static final String BRANCH_ID = "branchId";
	public static final String IDENTIFICATION_ID = "identificationId";
	public static final String IDENTIFICATION_CODE = "identificationCode";
	public static final String COMPANY_ID = "companyId";
	public static final String PHONE_NO1 = "phoneNo1";
	public static final String PHONE_NO2 = "phoneNo2";
	public static final String PHONE_NO3 = "phoneNo3";
	public static final String EMAIL_ADD1 = "emailAdd1";
	public static final String EMAIL_ADD2 = "emailAdd2";
	public static final String EMAIL_ADD3 = "emailAdd3";
	public static final String MEMBER_NO = "memberNo";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String APPROVED_BY = "approvedBy";
	public static final String CONTRIBUTION = "contribution";

	protected void initDao() {
		// do nothing
	}

	public void save(BatchMember transientInstance) {
		log.debug("saving BatchMember instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
        
        
        public void save(BatchMember transientInstance, Session sess) {
		log.debug("saving BatchMember instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BatchMember persistentInstance) {
		log.debug("deleting BatchMember instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BatchMember findById(java.lang.Integer id) {
		log.debug("getting BatchMember instance with id: " + id);
		try {
			BatchMember instance = (BatchMember) getHibernateTemplate().get(
					"com.sift.financial.member.BatchMember", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BatchMember instance) {
		log.debug("finding BatchMember instance by example");
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
		log.debug("finding BatchMember instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BatchMember as model where model."
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

	public List findByReligionId(Object religionId) {
		return findByProperty(RELIGION_ID, religionId);
	}

	public List findByMemberTypeId(Object memberTypeId) {
		return findByProperty(MEMBER_TYPE_ID, memberTypeId);
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

	public List findByBranchId(Object branchId) {
		return findByProperty(BRANCH_ID, branchId);
	}

	public List findByIdentificationId(Object identificationId) {
		return findByProperty(IDENTIFICATION_ID, identificationId);
	}

	public List findByIdentificationCode(Object identificationCode) {
		return findByProperty(IDENTIFICATION_CODE, identificationCode);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
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

	public List findByMemberNo(Object memberNo) {
		return findByProperty(MEMBER_NO, memberNo);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByApprovedBy(Object approvedBy) {
		return findByProperty(APPROVED_BY, approvedBy);
	}

	public List findByContribution(Object contribution) {
		return findByProperty(CONTRIBUTION, contribution);
	}

	public List findAll() {
		log.debug("finding all BatchMember instances");
		try {
			String queryString = "from BatchMember";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BatchMember merge(BatchMember detachedInstance) {
		log.debug("merging BatchMember instance");
		try {
			BatchMember result = (BatchMember) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
        
        public BatchMember merge(BatchMember detachedInstance,Session sess) {
		log.debug("merging BatchMember instance");
		try {
                    log.info("Here inside writeObjects....................inside mergiing BatcMember for = " + detachedInstance.getFirstName());
			BatchMember result = (BatchMember) sess.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BatchMember instance) {
		log.debug("attaching dirty BatchMember instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BatchMember instance) {
		log.debug("attaching clean BatchMember instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BatchMemberDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BatchMemberDAO) ctx.getBean("BatchMemberDAO");
	}
        
        
        public List getBatchMemberByStatus(String statShort, Integer fileUploadId, Integer companyId, Integer branchId)
        {
        
             Session sess = null;
                List list = null;
		try 
                {
                   sess = getSessionFactory().openSession();
	           //return getHibernateTemplate().find(" from BatchUploadFile as model where model.status.statusShort='"+ statusShort + "' and model.company_id=" + companyId + " and model.branch_id=" + branchId);
		   list = sess.createQuery(" from BatchMember as model where model.status.statusShort='"+ statShort + "' and model.batchUploadFile.batchUploadFileId =:code  and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId).list();
                  log.info("Here retrieving rrecords in file with size " + list.size() );
                   return list;
                } catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
                finally
                {
                  sess.close();
                }
        }

    @Override
    public List<Object[]> getErrorList(String state, Integer fileUploadId, Integer companyId, Integer branchId) {
      
        List<Object[]> theList = null;
        Session sess = null;
	log.info("In here inside get BatchMember status:" + state);
        try
        {
		sess = getSessionFactory().openSession();
		Query query = sess.createQuery("SELECT memberCompId, firstName, surname, emailAdd1, valError from BatchMember as model where model.status.statusShort='"+ state + "' and model.batchUploadFile.batchUploadFileId =:code and model.companyId=" + companyId + " and model.branchId=" + branchId).setInteger("code", fileUploadId);
		theList = query.list();
        }
        catch(Exception ex)
        {
        
        }
        finally
        {
        sess.close();
        }
	return theList;
    }

    @Override
    public Map<String, Object> getMailRecipient(Object obj, String action) {
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}