
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.CompanyCriteria;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Products;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class CompanyDAOImpl implements com.sift.easycoopfin.models.dao.CompanyDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(CompanyDAOImpl.class);
	public Company loadCompanyByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadCompanyByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadCompanyByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company getCompanyByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getCompanyByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getCompanyByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadCompanyByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadCompanyByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company getCompanyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getCompanyByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getCompanyByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Company) session.load(com.sift.easycoopfin.models.Company.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadCompanyByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company getCompanyByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Company) session.get(com.sift.easycoopfin.models.Company.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getCompanyByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Company) session.load(com.sift.easycoopfin.models.Company.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company getCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Company) session.get(com.sift.easycoopfin.models.Company.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company[] listCompanyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listCompanyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company[] listCompanyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listCompanyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company[] listCompanyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Company as Company");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Company[]) list.toArray(new Company[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listCompanyByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company[] listCompanyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Company as Company");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Company[]) list.toArray(new Company[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listCompanyByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadCompanyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadCompanyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Company[] companys = listCompanyByQuery(session, condition, orderBy);
		if (companys != null && companys.length > 0)
			return companys[0];
		else
			return null;
	}
	
	public Company loadCompanyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Company[] companys = listCompanyByQuery(session, condition, orderBy, lockMode);
		if (companys != null && companys.length > 0)
			return companys[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateCompanyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateCompanyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateCompanyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateCompanyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateCompanyByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateCompanyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Company as Company");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateCompanyByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateCompanyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Company as Company");
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
			_logger.error("iterateCompanyByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company createCompany() {
		return new com.sift.easycoopfin.models.Company();
	}
	
	public boolean save(com.sift.easycoopfin.models.Company company) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(company);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Company company)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Company company) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(company);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Company company)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Company company) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(company);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Company company)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Company company) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(company);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Company company)", e);
			throw new PersistentException(e);
		}
	}
	
	public Company loadCompanyByCriteria(CompanyCriteria companyCriteria) {
		Company[] companys = listCompanyByCriteria(companyCriteria);
		if(companys == null || companys.length == 0) {
			return null;
		}
		return companys[0];
	}
	 public List<Company> listCompaniesByCriteria(CompanyCriteria companyCriteria){
             return companyCriteria.listCompanyies();
         }
	public Company[] listCompanyByCriteria(CompanyCriteria companyCriteria) {
		return companyCriteria.listCompany();
	}

 
    public List<Company> listAllCompanyByQuery(String condition, String orderBy) throws PersistentException {
          try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listCompanysByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listAllCompanyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
    public List<Company> listCompanysByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Company as Company");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<Company> list = query.list();
            //return (Products[]) list.toArray(new Products[list.size()]);
            return list;
        } catch (Exception e) {
            _logger.error("listProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
}
