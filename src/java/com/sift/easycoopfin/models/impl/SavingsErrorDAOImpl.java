/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.SavingsError;
import com.sift.easycoopfin.models.SavingsErrorCriteria;
import static com.sift.easycoopfin.models.impl.SavingsErrorDAOImpl.iterateSavingsErrorByQuery;
import java.util.List;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class SavingsErrorDAOImpl implements com.sift.easycoopfin.models.dao.SavingsErrorDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsErrorDAOImpl.class);
	public SavingsError loadSavingsErrorByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
                        
			return loadSavingsErrorByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError getSavingsErrorByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getSavingsErrorByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getSavingsErrorByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadSavingsErrorByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError getSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getSavingsErrorByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (SavingsError) session.load(com.sift.easycoopfin.models.SavingsError.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError getSavingsErrorByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (SavingsError) session.get(com.sift.easycoopfin.models.SavingsError.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getSavingsErrorByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SavingsError) session.load(com.sift.easycoopfin.models.SavingsError.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError getSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SavingsError) session.get(com.sift.easycoopfin.models.SavingsError.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError[] listSavingsErrorByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listSavingsErrorByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError[] listSavingsErrorByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listSavingsErrorByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError[] listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.SavingsError as SavingsError");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (SavingsError[]) list.toArray(new SavingsError[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError[] listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.SavingsError as SavingsError");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (SavingsError[]) list.toArray(new SavingsError[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadSavingsErrorByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadSavingsErrorByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		SavingsError[] SavingsErrores = listSavingsErrorByQuery(session, condition, orderBy);
		if (SavingsErrores != null && SavingsErrores.length > 0)
			return SavingsErrores[0];
		else
			return null;
	}
	
	public SavingsError loadSavingsErrorByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		SavingsError[] SavingsErrores = listSavingsErrorByQuery(session, condition, orderBy, lockMode);
		if (SavingsErrores != null && SavingsErrores.length > 0)
			return SavingsErrores[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSavingsErrorByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateSavingsErrorByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSavingsErrorByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateSavingsErrorByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSavingsErrorByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSavingsErrorByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.SavingsError as SavingsError");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSavingsErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSavingsErrorByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.SavingsError as SavingsError");
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
			_logger.error("iterateSavingsErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError createSavingsError() {
		return new com.sift.easycoopfin.models.SavingsError();
	}
	
	public boolean save(com.sift.easycoopfin.models.SavingsError SavingsError) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(SavingsError);
                        
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.SavingsError SavingsError)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(savingsError);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.SavingsError SavingsError)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(savingsError);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.SavingsError savingsError)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(savingsError);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.SavingsError savingsError)", e);
			throw new PersistentException(e);
		}
	}
	
	public SavingsError loadSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria) {
		SavingsError[] savingsErrores = listSavingsErrorByCriteria(savingsErrorCriteria);
		if(savingsErrores == null || savingsErrores.length == 0) {
			return null;
		}
		return savingsErrores[0];
	}
	
	public SavingsError[] listSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria) {
		return savingsErrorCriteria.listSavingsError();
	}

    @Override
    public List<SavingsError> listAllSavingByQuery(String condition, String orderBy) throws PersistentException {
       PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.SavingsError as SavingsError");
        if (condition != null) {
            sb.append(" Where ").append(condition);
            
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<SavingsError> list = query.list();
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllSavingsErrorByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
    public List<SavingsError> listAllSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria){
        return savingsErrorCriteria.listAllSavingsError();
    }
}

