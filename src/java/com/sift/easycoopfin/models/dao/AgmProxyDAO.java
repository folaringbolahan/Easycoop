/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.AgmProxy;
import com.sift.easycoopfin.models.AgmProxyCriteria;
import java.util.List;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public interface AgmProxyDAO {
    public AgmProxy loadAgmProxyByORMID(int id) throws PersistentException;
	public AgmProxy getAgmProxyByORMID(int id) throws PersistentException;
	public AgmProxy loadAgmProxyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy getAgmProxyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy loadAgmProxyByORMID(PersistentSession session, int id) throws PersistentException;
	public AgmProxy getAgmProxyByORMID(PersistentSession session, int id) throws PersistentException;
	public AgmProxy loadAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy getAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy[] listAgmProxyByQuery(String condition, String orderBy) throws PersistentException;
        public List<AgmProxy> listAgmProxysByQuery(String condition, String orderBy) throws PersistentException;
	public AgmProxy[] listAgmProxyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy[] listAgmProxyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
        public List<AgmProxy> listAgmProxysByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public AgmProxy[] listAgmProxyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy loadAgmProxyByQuery(String condition, String orderBy) throws PersistentException;
	public AgmProxy loadAgmProxyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy loadAgmProxyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public AgmProxy loadAgmProxyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public AgmProxy createAgmProxy();
	public boolean save(com.sift.easycoopfin.models.AgmProxy member) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.AgmProxy member) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.AgmProxy member) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.AgmProxy member) throws PersistentException;
	public AgmProxy loadAgmProxyByCriteria(AgmProxyCriteria memberCriteria);
	public AgmProxy[] listAgmProxyByCriteria(AgmProxyCriteria memberCriteria);
        public List<AgmProxy> listAllAgmProxys(AgmProxyCriteria memberCriteria);
}
