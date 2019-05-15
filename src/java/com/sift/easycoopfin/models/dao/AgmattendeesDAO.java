
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.AgmattendeesCriteria;
import com.sift.easycoopfin.models.Agmattendees;
import org.orm.*;
import java.util.List;

public interface AgmattendeesDAO {
	public Agmattendees loadAgmattendeesByORMID(int id) throws PersistentException;
	public Agmattendees getAgmattendeesByORMID(int id) throws PersistentException;
	public Agmattendees loadAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees getAgmattendeesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees loadAgmattendeesByORMID(PersistentSession session, int id) throws PersistentException;
	public Agmattendees getAgmattendeesByORMID(PersistentSession session, int id) throws PersistentException;
	public Agmattendees loadAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees getAgmattendeesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees[] listAgmattendeesByQuery(String condition, String orderBy) throws PersistentException;
	public Agmattendees[] listAgmattendeesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees[] listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Agmattendees[] listAgmattendeesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees loadAgmattendeesByQuery(String condition, String orderBy) throws PersistentException;
	public Agmattendees loadAgmattendeesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees loadAgmattendeesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Agmattendees loadAgmattendeesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agmattendees createAgmattendees();
	public boolean save(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Agmattendees agmattendees) throws PersistentException;
	public Agmattendees loadAgmattendeesByCriteria(AgmattendeesCriteria agmattendeesCriteria);
	public Agmattendees[] listAgmattendeesByCriteria(AgmattendeesCriteria agmattendeesCriteria);
        public List<Agmattendees> listParticipantsByCriteria(AgmattendeesCriteria agmattendeesCriteria);
        public void deleteWithQuery(String query, int memberId,int agmId ) throws PersistentException ;
        public int deleteParticipant(String query, List<Integer> list, int agmId) throws PersistentException;
}
