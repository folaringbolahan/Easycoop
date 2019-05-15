
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.BranchCriteria;
import com.sift.easycoopfin.models.Branch;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class BranchDAOImpl implements com.sift.easycoopfin.models.dao.BranchDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(BranchDAOImpl.class);
	public Branch loadBranchByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadBranchByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadBranchByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch getBranchByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getBranchByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getBranchByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadBranchByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadBranchByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch getBranchByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getBranchByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getBranchByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Branch) session.load(com.sift.easycoopfin.models.Branch.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadBranchByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch getBranchByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Branch) session.get(com.sift.easycoopfin.models.Branch.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getBranchByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Branch) session.load(com.sift.easycoopfin.models.Branch.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch getBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Branch) session.get(com.sift.easycoopfin.models.Branch.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch[] listBranchByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listBranchByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch[] listBranchByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listBranchByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch[] listBranchByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Branch as Branch");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Branch[]) list.toArray(new Branch[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listBranchByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch[] listBranchByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Branch as Branch");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Branch[]) list.toArray(new Branch[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listBranchByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadBranchByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadBranchByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Branch[] branchs = listBranchByQuery(session, condition, orderBy);
		if (branchs != null && branchs.length > 0)
			return branchs[0];
		else
			return null;
	}
	
	public Branch loadBranchByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Branch[] branchs = listBranchByQuery(session, condition, orderBy, lockMode);
		if (branchs != null && branchs.length > 0)
			return branchs[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateBranchByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateBranchByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateBranchByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateBranchByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateBranchByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateBranchByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Branch as Branch");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateBranchByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateBranchByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Branch as Branch");
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
			_logger.error("iterateBranchByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch createBranch() {
		return new com.sift.easycoopfin.models.Branch();
	}
	
	public boolean save(com.sift.easycoopfin.models.Branch branch) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(branch);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Branch branch)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Branch branch) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(branch);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Branch branch)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Branch branch) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(branch);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Branch branch)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Branch branch) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(branch);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Branch branch)", e);
			throw new PersistentException(e);
		}
	}
	
	public Branch loadBranchByCriteria(BranchCriteria branchCriteria) {
		Branch[] branchs = listBranchByCriteria(branchCriteria);
		if(branchs == null || branchs.length == 0) {
			return null;
		}
		return branchs[0];
	}
	
	public Branch[] listBranchByCriteria(BranchCriteria branchCriteria) {
		return branchCriteria.listBranch();
	}
}
