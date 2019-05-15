
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Users;
import com.sift.easycoopfin.models.UsersCriteria;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class UsersDAOImpl implements com.sift.easycoopfin.models.dao.UsersDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(UsersDAOImpl.class);
	/**public Users loadUsersByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadUsersByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadUsersByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users getUsersByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getUsersByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getUsersByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadUsersByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadUsersByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users getUsersByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getUsersByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getUsersByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Users) session.load(com.sift.easycoopfin.models.Users.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadUsersByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users getUsersByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Users) session.get(com.sift.easycoopfin.models.Users.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getUsersByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Users) session.load(com.sift.easycoopfin.models.Users.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users getUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Users) session.get(com.sift.easycoopfin.models.Users.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	**/
	public Users[] listUsersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listUsersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users[] listUsersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listUsersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users[] listUsersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Users as Users");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Users[]) list.toArray(new Users[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listUsersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users[] listUsersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Users as Users");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Users[]) list.toArray(new Users[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listUsersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadUsersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadUsersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Users[] userses = listUsersByQuery(session, condition, orderBy);
		if (userses != null && userses.length > 0)
			return userses[0];
		else
			return null;
	}
	
	public Users loadUsersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Users[] userses = listUsersByQuery(session, condition, orderBy, lockMode);
		if (userses != null && userses.length > 0)
			return userses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateUsersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateUsersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateUsersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateUsersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateUsersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateUsersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Users as Users");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateUsersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateUsersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Users as Users");
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
			_logger.error("iterateUsersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users createUsers() {
		return new com.sift.easycoopfin.models.Users();
	}
	
	public boolean save(com.sift.easycoopfin.models.Users users) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(users);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Users users)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Users users) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(users);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Users users)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Users users) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(users);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Users users)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Users users) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(users);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Users users)", e);
			throw new PersistentException(e);
		}
	}
	
	public Users loadUsersByCriteria(UsersCriteria usersCriteria) {
		Users[] userses = listUsersByCriteria(usersCriteria);
		if(userses == null || userses.length == 0) {
			return null;
		}
		return userses[0];
	}
	
	public Users[] listUsersByCriteria(UsersCriteria usersCriteria) {
		return usersCriteria.listUsers();
	}
}
