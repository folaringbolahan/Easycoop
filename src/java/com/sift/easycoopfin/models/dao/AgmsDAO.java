
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Agms;
import com.sift.easycoopfin.models.AgmsCriteria;
import java.util.List;
import org.orm.*;

public interface AgmsDAO {
	public Agms loadAgmsByORMID(int id) throws PersistentException;
	public Agms getAgmsByORMID(int id) throws PersistentException;
	public Agms loadAgmsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms getAgmsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms loadAgmsByORMID(PersistentSession session, int id) throws PersistentException;
	public Agms getAgmsByORMID(PersistentSession session, int id) throws PersistentException;
	public Agms loadAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms getAgmsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms[] listAgmsByQuery(String condition, String orderBy) throws PersistentException;
	public Agms[] listAgmsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms[] listAgmsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Agms[] listAgmsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms loadAgmsByQuery(String condition, String orderBy) throws PersistentException;
	public Agms loadAgmsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms loadAgmsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Agms loadAgmsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Agms createAgms();
	public boolean save(com.sift.easycoopfin.models.Agms agms) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Agms agms) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Agms agms) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Agms agms) throws PersistentException;
	public Agms loadAgmsByCriteria(AgmsCriteria agmsCriteria);
	public Agms[] listAgmsByCriteria(AgmsCriteria agmsCriteria);
        public List<Agms> listAllAgmsByCriteria(AgmsCriteria agmsCriteria);
}
