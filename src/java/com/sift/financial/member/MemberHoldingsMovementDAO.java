package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * MemberHoldingsMovement entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sift.financial.member.MemberHoldingsMovement
 * @author MyEclipse Persistence Tools
 */
public class MemberHoldingsMovementDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(MemberHoldingsMovementDAO.class);
	// property constants
	public static final String MOVEMENT_TYPE = "movementType";
	public static final String DEL_FLG = "delFlg";
	public static final String CREATED_BY = "createdBy";
	public static final String MOVEMENT_HOLDINGS = "movementHoldings";
	public static final String COMPANY_ID = "companyId";

	protected void initDao() {
		// do nothing
	}

	public void save(MemberHoldingsMovement transientInstance) {
		log.debug("saving MemberHoldingsMovement instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	
	public void save(MemberHoldingsMovement transientInstance, Session sess) {
		log.debug("saving MemberHoldingsMovement instance");
		try {
			sess.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MemberHoldingsMovement persistentInstance) {
		log.debug("deleting MemberHoldingsMovement instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MemberHoldingsMovement findById(java.lang.Integer id) {
		log.debug("getting MemberHoldingsMovement instance with id: " + id);
		try {
			MemberHoldingsMovement instance = (MemberHoldingsMovement) getHibernateTemplate()
					.get("com.sift.financial.member.MemberHoldingsMovement", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MemberHoldingsMovement instance) {
		log.debug("finding MemberHoldingsMovement instance by example");
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
		log.debug("finding MemberHoldingsMovement instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MemberHoldingsMovement as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMovementType(Object movementType) {
		return findByProperty(MOVEMENT_TYPE, movementType);
	}

	public List findByDelFlg(Object delFlg) {
		return findByProperty(DEL_FLG, delFlg);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByMovementHoldings(Object movementHoldings) {
		return findByProperty(MOVEMENT_HOLDINGS, movementHoldings);
	}

	public List findByCompanyId(Object companyId) {
		return findByProperty(COMPANY_ID, companyId);
	}

	public List findAll() {
		log.debug("finding all MemberHoldingsMovement instances");
		try {
			String queryString = "from MemberHoldingsMovement";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MemberHoldingsMovement merge(MemberHoldingsMovement detachedInstance) {
		log.debug("merging MemberHoldingsMovement instance");
		try {
			MemberHoldingsMovement result = (MemberHoldingsMovement) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MemberHoldingsMovement instance) {
		log.debug("attaching dirty MemberHoldingsMovement instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MemberHoldingsMovement instance) {
		log.debug("attaching clean MemberHoldingsMovement instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberHoldingsMovementDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MemberHoldingsMovementDAO) ctx
				.getBean("MemberHoldingsMovementDAO");
	}
	
	
	public List findByEventAndMember (Event event, Member mem)
	{
		try {

			
			return getHibernateTemplate().find(" from MemberHoldingsMovement as model where model.company.companyId="+ mem.getCompany().getId() + " and model.event.eventId='" + event.getEventId() + "' and model.member.memberId=" + mem.getMemberId());
			
			//return getHibernateTemplate().find("select model.movementId, model.compStockType.compStockTypeId, model.movementHoldings from MemberHoldingsMovement as model where model.company.companyId="+ mem.getCompany().getCompanyId() + " and model.event.eventId='" + event.getEventId() + "' and model.member.memberId=" + mem.getMemberId());

		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	
	}
	
	public boolean deleteByEventAndMember (Event event, Member mem, Session sess)
	{
		boolean returnVal = false;
		
		try {

			 sess.delete(" from MemberHoldingsMovement as model where model.company.companyId="+ mem.getCompany().getId() + " and model.event.eventId='" + event.getEventId() + "' and model.member.memberId=" + mem.getMemberId());
			
			 returnVal =true;
			 
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		
		return returnVal;
	
	}
}