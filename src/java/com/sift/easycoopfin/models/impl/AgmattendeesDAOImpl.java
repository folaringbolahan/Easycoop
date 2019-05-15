
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.AgmattendeesCriteria;
import com.sift.easycoopfin.models.Agmattendees;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class AgmattendeesDAOImpl implements com.sift.easycoopfin.models.dao.AgmattendeesDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmattendeesDAOImpl.class);
	public Agmattendees loadAgmattendeesByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmattendeesByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees getAgmattendeesByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getAgmattendeesByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getAgmattendeesByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmattendeesByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees getAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getAgmattendeesByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Agmattendees) session.load(com.sift.easycoopfin.models.Agmattendees.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees getAgmattendeesByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Agmattendees) session.get(com.sift.easycoopfin.models.Agmattendees.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getAgmattendeesByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Agmattendees) session.load(com.sift.easycoopfin.models.Agmattendees.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees getAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Agmattendees) session.get(com.sift.easycoopfin.models.Agmattendees.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees[] listAgmattendeesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listAgmattendeesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees[] listAgmattendeesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listAgmattendeesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees[] listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agmattendees as Agmattendees");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Agmattendees[]) list.toArray(new Agmattendees[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees[] listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agmattendees as Agmattendees");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Agmattendees[]) list.toArray(new Agmattendees[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmattendeesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadAgmattendeesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Agmattendees[] agmattendeeses = listAgmattendeesByQuery(session, condition, orderBy);
		if (agmattendeeses != null && agmattendeeses.length > 0)
			return agmattendeeses[0];
		else
			return null;
	}
	
	public Agmattendees loadAgmattendeesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Agmattendees[] agmattendeeses = listAgmattendeesByQuery(session, condition, orderBy, lockMode);
		if (agmattendeeses != null && agmattendeeses.length > 0)
			return agmattendeeses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateAgmattendeesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateAgmattendeesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmattendeesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateAgmattendeesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateAgmattendeesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmattendeesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agmattendees as Agmattendees");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateAgmattendeesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateAgmattendeesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Agmattendees as Agmattendees");
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
			_logger.error("iterateAgmattendeesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees createAgmattendees() {
		return new com.sift.easycoopfin.models.Agmattendees();
	}
	
	public boolean save(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(agmattendees);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Agmattendees agmattendees)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(agmattendees);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Agmattendees agmattendees)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(agmattendees);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Agmattendees agmattendees)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(agmattendees);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Agmattendees agmattendees)", e);
			throw new PersistentException(e);
		}
	}
	
	public Agmattendees loadAgmattendeesByCriteria(AgmattendeesCriteria agmattendeesCriteria) {
		Agmattendees[] agmattendeeses = listAgmattendeesByCriteria(agmattendeesCriteria);
		if(agmattendeeses == null || agmattendeeses.length == 0) {
			return null;
		}
		return agmattendeeses[0];
	}
	
	public Agmattendees[] listAgmattendeesByCriteria(AgmattendeesCriteria agmattendeesCriteria) {
		return agmattendeesCriteria.listAgmattendees();
	}


            public void deleteWithQuery(String query,int memberId,int agmId)  throws PersistentException {
           //int resul
                try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			Query exQuery = session.createQuery(query);
                        exQuery.setParameter("member_id",memberId);
                        exQuery.setParameter("agms_id",agmId);
                        int result = exQuery.executeUpdate();
                        
                        
                        System.out.printf("Result for Id: %s Agm: %s "+result, memberId, agmId+"\n");
			//query.setLockMode("this", lockMode);
			
		}
		catch (Exception e) {
			_logger.error("deleteWithQuery(String query)", e);
			throw new PersistentException(e);
		}
    }

    public int deleteParticipant(String query, List<Integer> list, int agmId) throws PersistentException {
        int result = 0;
                try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			Query exQuery = session.createQuery(query);
                        exQuery.setParameterList("member_id",list);
                        exQuery.setParameter("agms_id",agmId);
                        
                        result = exQuery.executeUpdate();
                        
			
		}
		catch (Exception e) {
			_logger.error("deleteWithQuery(String query)", e);
			throw new PersistentException(e);
		}
           return result;
    }

    @Override
    public List<Agmattendees> listParticipantsByCriteria(AgmattendeesCriteria agmattendeesCriteria) {
       return agmattendeesCriteria.listParticipants();
    }
}
