
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Users;
import com.sift.easycoopfin.models.UsersCriteria;
import org.orm.*;

public interface UsersDAO {
	/**public Users loadUsersByORMID(int id) throws PersistentException;
	public Users getUsersByORMID(int id) throws PersistentException;
	public Users loadUsersByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users getUsersByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users loadUsersByORMID(PersistentSession session, int id) throws PersistentException;
	public Users getUsersByORMID(PersistentSession session, int id) throws PersistentException;
	public Users loadUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users getUsersByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;**/
	public Users[] listUsersByQuery(String condition, String orderBy) throws PersistentException;
	public Users[] listUsersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users[] listUsersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Users[] listUsersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users loadUsersByQuery(String condition, String orderBy) throws PersistentException;
	public Users loadUsersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users loadUsersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Users loadUsersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Users createUsers();
	public boolean save(com.sift.easycoopfin.models.Users users) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Users users) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Users users) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Users users) throws PersistentException;
	public Users loadUsersByCriteria(UsersCriteria usersCriteria);
	public Users[] listUsersByCriteria(UsersCriteria usersCriteria);
}
