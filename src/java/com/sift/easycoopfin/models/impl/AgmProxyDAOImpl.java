/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;
import com.sift.easycoopfin.models.AgmProxy;
import com.sift.easycoopfin.models.AgmProxyCriteria;
import com.sift.easycoopfin.models.dao.AgmProxyDAO;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

/**
 *
 * @author logzy
 */
public class AgmProxyDAOImpl implements AgmProxyDAO{
    
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmProxyDAOImpl.class);

    public AgmProxy loadAgmProxyByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadAgmProxyByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadAgmProxyByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy getAgmProxyByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getAgmProxyByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getAgmProxyByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadAgmProxyByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadAgmProxyByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy getAgmProxyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getAgmProxyByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getAgmProxyByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (AgmProxy) session.load(com.sift.easycoopfin.models.AgmProxy.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadAgmProxyByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy getAgmProxyByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (AgmProxy) session.get(com.sift.easycoopfin.models.AgmProxy.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getAgmProxyByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (AgmProxy) session.load(com.sift.easycoopfin.models.AgmProxy.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy getAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (AgmProxy) session.get(com.sift.easycoopfin.models.AgmProxy.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getAgmProxyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy[] listAgmProxyByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listAgmProxyByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy[] listAgmProxyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listAgmProxyByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy[] listAgmProxyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.AgmProxy as AgmProxy");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (AgmProxy[]) list.toArray(new AgmProxy[list.size()]);
        } catch (Exception e) {
            _logger.error("listAgmProxyByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy[] listAgmProxyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.AgmProxy as AgmProxy");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            List list = query.list();
            return (AgmProxy[]) list.toArray(new AgmProxy[list.size()]);
        } catch (Exception e) {
            _logger.error("listAgmProxyByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadAgmProxyByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadAgmProxyByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        AgmProxy[] proxys = listAgmProxyByQuery(session, condition, orderBy);
        if (proxys != null && proxys.length > 0) {
            return proxys[0];
        } else {
            return null;
        }
    }

    public AgmProxy loadAgmProxyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        AgmProxy[] proxys = listAgmProxyByQuery(session, condition, orderBy, lockMode);
        if (proxys != null && proxys.length > 0) {
            return proxys[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateAgmProxyByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateAgmProxyByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("iterateAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateAgmProxyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateAgmProxyByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateAgmProxyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.AgmProxy as AgmProxy");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateAgmProxyByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateAgmProxyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.AgmProxy as AgmProxy");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateAgmProxyByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy createAgmProxy() {
        return new com.sift.easycoopfin.models.AgmProxy();
    }

    public boolean save(com.sift.easycoopfin.models.AgmProxy proxy) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(proxy);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.easycoopfin.models.AgmProxy proxy)", e);
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.AgmProxy proxy) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(proxy);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.easycoopfin.models.AgmProxy proxy)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.AgmProxy proxy) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(proxy);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.AgmProxy proxy)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.AgmProxy proxy) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(proxy);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.AgmProxy proxy)", e);
            throw new PersistentException(e);
        }
    }

    public AgmProxy loadAgmProxyByCriteria(AgmProxyCriteria proxyCriteria) {
        AgmProxy[] proxys = listAgmProxyByCriteria(proxyCriteria);
        if (proxys == null || proxys.length == 0) {
            return null;
        }
        return proxys[0];
    }

    public AgmProxy[] listAgmProxyByCriteria(AgmProxyCriteria proxyCriteria) {
        return proxyCriteria.listAgmProxy();
    }

    @Override
    public List<AgmProxy> listAllAgmProxys(AgmProxyCriteria proxyCriteria) {
        return proxyCriteria.listAllAgmProxy();
    }

    @Override
    public List<AgmProxy> listAgmProxysByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listAgmProxysByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listAgmProxyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<AgmProxy> listAgmProxysByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.AgmProxy as AgmProxy");
        if (condition != null) {            
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (List<AgmProxy>) list;
        } catch (Exception e) {
            _logger.error("listAgmProxysByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
        
    }
}
