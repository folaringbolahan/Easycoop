
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Agms;
import com.sift.easycoopfin.models.AgmsCriteria;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class AgmsDAOImpl implements com.sift.easycoopfin.models.dao.AgmsDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmsDAOImpl.class);
	public Agms loadAgmsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadAgmsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms getAgmsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getAgmsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getAgmsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms getAgmsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getAgmsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getAgmsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Agms) session.load(com.sift.easycoopfin.models.Agms.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadAgmsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms getAgmsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Agms) session.get(com.sift.easycoopfin.models.Agms.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getAgmsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Agms) session.load(com.sift.easycoopfin.models.Agms.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms getAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Agms) session.get(com.sift.easycoopfin.models.Agms.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms[] listAgmsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listAgmsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms[] listAgmsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listAgmsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms[] listAgmsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agms as Agms");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Agms[]) list.toArray(new Agms[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listAgmsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms[] listAgmsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agms as Agms");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Agms[]) list.toArray(new Agms[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listAgmsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Agms[] agmses = listAgmsByQuery(session, condition, orderBy);
		if (agmses != null && agmses.length > 0)
			return agmses[0];
		else
			return null;
	}
	
	public Agms loadAgmsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Agms[] agmses = listAgmsByQuery(session, condition, orderBy, lockMode);
		if (agmses != null && agmses.length > 0)
			return agmses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateAgmsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateAgmsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateAgmsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateAgmsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agms as Agms");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateAgmsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agms as Agms");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateAgmsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms createAgms() {
		return new com.sift.easycoopfin.models.Agms();
	}
	
	public boolean save(com.sift.easycoopfin.models.Agms agms) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(agms);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Agms agms)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Agms agms) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(agms);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Agms agms)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Agms agms) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(agms);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Agms agms)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Agms agms) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(agms);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Agms agms)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agms loadAgmsByCriteria(AgmsCriteria agmsCriteria) {
		Agms[] agmses = listAgmsByCriteria(agmsCriteria);
		if(agmses == null || agmses.length == 0) {
			return null;
		}
		return agmses[0];
	}
	
	public Agms[] listAgmsByCriteria(AgmsCriteria agmsCriteria) {
		return agmsCriteria.listAgms();
	}

    @Override
    public List<Agms> listAllAgmsByCriteria(AgmsCriteria agmsCriteria) {
        return agmsCriteria.listAllAgms();
    }
}
